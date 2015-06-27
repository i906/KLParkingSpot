package my.i906.klparkingspot.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import my.i906.klparkingspot.adapter.MallAdapter;
import my.i906.klparkingspot.model.Mall;
import my.i906.klparkingspot.model.Pgis;
import my.i906.klparkingspot.view.DividerItemDecoration;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MallAdapter();
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
                    }

                    @Override
                    public void onNext(List<Mall> malls) {
                        mAdapter.setMallList(malls);
                        mListContainer.setRefreshing(false);
                        setListShown(true, true);
                    }
                });

        mSubscription.add(s);
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
