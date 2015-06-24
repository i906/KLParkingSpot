package my.i906.klparkingsport.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import butterknife.InjectView;
import my.i906.klparkingsport.R;

public abstract class BaseRecyclerFragment extends BaseFragment {

    @InjectView(R.id.list)
    protected RecyclerView mRecyclerView;

    @InjectView(R.id.progress_container)
    protected View mProgressContainer;

    @InjectView(R.id.recycler_container)
    protected SwipeRefreshLayout mListContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListShown(false, true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        setupSwipeRefreshView();
    }

    protected abstract void setupRecyclerView();

    protected void onRefresh() {
    }

    protected void setupSwipeRefreshView() {
        mListContainer.setOnRefreshListener(BaseRecyclerFragment.this::onRefresh);
    }

    protected void setListShown(boolean shown, boolean animate) {
        setViewVisibility(mListContainer, shown, animate);
        setViewVisibility(mProgressContainer, !shown, animate);
    }

    protected void setViewVisibility(View view, boolean visible, boolean animate) {
        if (view.getVisibility() == View.VISIBLE && visible) return;
        if (view.getVisibility() == View.GONE && !visible) return;

        if (visible) {
            if (animate) {
                view.startAnimation(
                        AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            } else {
                view.clearAnimation();
            }
            view.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                view.startAnimation(
                        AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            } else {
                view.clearAnimation();
            }
            view.setVisibility(View.GONE);
        }
    }
}
