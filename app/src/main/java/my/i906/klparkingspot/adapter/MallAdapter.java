package my.i906.klparkingspot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import my.i906.klparkingspot.model.Mall;
import my.i906.klparkingspot.view.MallView;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.ViewHolder> {

    protected List<Mall> mList;

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

    public void setMallList(List<Mall> list) {
        mList = list;
        Collections.sort(mList);
        Collections.reverse(mList);
        notifyDataSetChanged();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
