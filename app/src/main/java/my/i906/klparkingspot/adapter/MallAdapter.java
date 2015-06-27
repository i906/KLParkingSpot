package my.i906.klparkingspot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import my.i906.klparkingspot.model.Mall;
import my.i906.klparkingspot.view.MallView;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.ViewHolder> {

    private static final int SORT_AVAILABILITY = 0;
    private static final int SORT_NAME = 1;
    private static final int SORT_LAST_UPDATED = 2;

    protected List<Mall> mList;
    protected int mSortMode = SORT_LAST_UPDATED;

    public MallAdapter() {
        mList = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        MallView mv = new MallView(parent.getContext());
        return new ViewHolder(mv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MallView mv = (MallView) holder.itemView;
        mv.setMall(mList.get(position));
    }

    public void sortByAvailability() {
        mSortMode = SORT_AVAILABILITY;
        Collections.sort(mList, COMPARATOR_AVAILABILITY);
        notifyDataSetChanged();
    }

    public void sortByName() {
        mSortMode = SORT_NAME;
        Collections.sort(mList, COMPARATOR_NAME);
        notifyDataSetChanged();
    }

    public void sortByLastUpdated() {
        mSortMode = SORT_LAST_UPDATED;
        Collections.sort(mList);
        Collections.reverse(mList);
        notifyDataSetChanged();
    }

    public void setMallList(List<Mall> list) {
        mList = list;

        switch (mSortMode) {
            case SORT_AVAILABILITY:
                sortByAvailability();
                break;
            case SORT_NAME:
                sortByName();
                break;
            case SORT_LAST_UPDATED:
                sortByLastUpdated();
                break;
        }
    }

    public Mall getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    private static final Comparator<Mall> COMPARATOR_NAME = (lhs, rhs) -> {
        if (lhs.getName() == null) return 1;
        if (rhs.getName() == null) return -1;
        return lhs.getName().compareTo(rhs.getName());
    };

    private static final Comparator<Mall> COMPARATOR_AVAILABILITY = (lhs, rhs) -> lhs.getLot() - rhs.getLot();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
