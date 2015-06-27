package my.i906.klparkingspot.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.InjectView;
import butterknife.OnClick;
import my.i906.klparkingspot.R;
import my.i906.klparkingspot.adapter.MallAdapter;
import my.i906.klparkingspot.model.Mall;
import my.i906.klparkingspot.model.Pgis;
import my.i906.klparkingspot.view.DividerItemDecoration;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ParkingFragment extends BaseRecyclerFragment {

    private CompositeSubscription mSubscription = new CompositeSubscription();
    private LinearLayoutManager mLayoutManager;
    private MallAdapter mAdapter;

    @InjectView(R.id.error_container)
    protected View mErrorContainer;

    @InjectView(R.id.tv_error)
    protected TextView mErrorMessageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new MallAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parking, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    @Override
    protected void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        if (mRecyclerView.getAdapter() == null) mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        Subscription s = mApi.getParkingSpots()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Pgis::getMalls)
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(10, TimeUnit.SECONDS)))
                .subscribe(new Subscriber<List<Mall>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        if (e instanceof RetrofitError) {
                            RetrofitError error = (RetrofitError) e;
                            switch (error.getKind()) {
                                case NETWORK:
                                    mErrorMessageView.setText(R.string.error_no_network);
                                    break;
                                case CONVERSION:
                                    mErrorMessageView.setText(R.string.error_conversion);
                                    break;
                                case HTTP:
                                    mErrorMessageView.setText(R.string.error_http);
                                    break;
                                default:
                                    mErrorMessageView.setText(R.string.error_unexpected);
                            }
                        } else {
                            mErrorMessageView.setText(R.string.error_unexpected);
                        }

                        setContentVisibility(false, true);
                        setProgressVisibility(false, true);
                        setErrorVisibility(true, true);
                    }

                    @Override
                    public void onNext(List<Mall> malls) {
                        mAdapter.setMallList(malls);
                        mListContainer.setRefreshing(false);
                        setContentVisibility(true, true);
                        setProgressVisibility(false, true);
                        setErrorVisibility(false, true);
                    }
                });

        mSubscription.add(s);
    }

    @OnClick(R.id.btn_retry)
    protected void onRetryButtonClicked() {
        onRefresh();
        setContentVisibility(false, true);
        setProgressVisibility(true, true);
        setErrorVisibility(false, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_parking, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_sort_availability:
                mAdapter.sortByAvailability();
                break;
            case R.id.menu_sort_name:
                mAdapter.sortByName();
                break;
            case R.id.menu_sort_updated:
                mAdapter.sortByLastUpdated();
                break;
        }

        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    private void setContentVisibility(boolean visible, boolean animate) {
        setViewVisibility(mListContainer, visible, animate);
    }

    private void setProgressVisibility(boolean visible, boolean animate) {
        setViewVisibility(mProgressContainer, visible, animate);
    }

    private void setErrorVisibility(boolean visible, boolean animate) {
        setViewVisibility(mErrorContainer, visible, animate);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSubscription.unsubscribe();
    }

    public static ParkingFragment newInstance() {
        return new ParkingFragment();
    }
}
