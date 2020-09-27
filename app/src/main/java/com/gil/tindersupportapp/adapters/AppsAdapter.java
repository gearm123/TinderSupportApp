package com.gil.tindersupportapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.gil.tindersupportapp.R;
import com.gil.tindersupportapp.interfaces.OnAppClickListener;
import com.gil.tindersupportapp.model.MatchData;
import com.gil.tindersupportapp.widgets.AppView;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.MyViewHolder> {

    private List<MatchData> mDataset;
    private OnAppClickListener mItemListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AppView appView;

        public MyViewHolder(AppView v) {
            super(v);
            appView = v;
            v.setTag(this);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AppsAdapter(List<MatchData> myDataset, OnAppClickListener listener) {
        mDataset = myDataset;
        mItemListener = listener;
    }

    public void notifyAppItemChanged(MatchData matchData) {
;        for (int i = 0; i < mDataset.size(); i++) {
            if (mDataset.get(i).getRealNameame().equals(matchData.getRealNameame())) {
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateItem(MatchData matchData) {
        ;        for (int i = 0; i < mDataset.size(); i++) {
            if (mDataset.get(i).getRealNameame().equals(matchData.getRealNameame())) {
                mDataset.set(i, matchData);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void addItem(){
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AppsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        AppView v = (AppView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app, parent, false);
        // v.setTag(this);
        v.setBackground(parent.getContext().getResources().getDrawable(R.drawable.bg_list_selector));
        v.setFocusable(true);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                    mItemListener.onItemClick(mDataset.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.appView.setAppData(mDataset.get(position), mItemListener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}