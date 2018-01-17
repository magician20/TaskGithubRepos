package com.example.magic.taskgithubrepos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.magic.taskgithubrepos.R;
import com.example.magic.taskgithubrepos.data.Repository;
import com.example.magic.taskgithubrepos.utilities.Constants;
import com.example.magic.taskgithubrepos.utilities.ReposUtils;

import java.util.List;

/**
 * Created by Magician on 12/24/2017.
 * RecyclerView
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {
    private static final String TAG = ReposAdapter.class.getSimpleName();
   // private static int viewHolderCount;
    private final Context mContext;

    private List<Repository> mRepositoryList;
    private ReposAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ReposAdapterOnClickHandler {
        void onClick(Repository repos);
    }

    /**
     * Creates a ReposAdapter.with listener or without.
     *
     * @param context Used to talk to the UI and app resources.
     * @param clickHandler Used to handle the listener.
     */

    public ReposAdapter(@NonNull Context context, ReposAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override

    public ReposAdapter.ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.custom_repos_list;//R.layout.custom_repos_list/repos_list.

        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        view.setFocusable(true);//touch mode focucs

        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ReposViewHolder holder, int position) {
        Repository repository = mRepositoryList.get(position);
        //Text Views data
        holder.mTextName.setText(repository.getName());
        holder.meTextDescription.setText(repository.getDescription());
        holder.meTextDescription.setMovementMethod(new ScrollingMovementMethod());
        holder.mTextOwnerName.setText(repository.getOwner());
        //Background Color
        //GradientDrawable background = (GradientDrawable) holder.itemView.getBackground();
        int backgroundColor = ReposUtils.getBackgroundColor(repository.isFork());
        //background.setColor(backgroundColor);
        holder.itemView.setBackgroundResource(backgroundColor);

    }


    /**
     * This method is called by MainActivity after a load has finished,as well as when the Loader
     * responsible for loading the Repos data is reset. When this method is called,
     * we assume we have a completely new set of data,
     * so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newData the new List to use as Repos's data source
     */
    public void setData(List<Repository> newData) {//not sure yet
        // this is mean I want to clear the data because I'm going to end ,
        // or this null mean no more data to fetch(solved before enter)
        if (mRepositoryList != null && newData == null) {
            mRepositoryList.clear();
            notifyDataSetChanged();
            return;
        }
        //Add the new data to the old one (this new data is different from old one )
        //here gather the 2 data
        if (mRepositoryList != null && newData != null) {
            mRepositoryList.addAll(newData);
        }
        //here at the start point where this is first assign for the list
        else if (mRepositoryList == null && newData != null) {
            mRepositoryList = newData;
        }
        notifyItemRangeInserted(getItemCount(), Constants.NetworkConstants.NUMBER_OF_ITEMS);
    }

    //View Holder Class
    public class ReposViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView mTextName;
        TextView mTextOwnerName;
        TextView meTextDescription;

        public ReposViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.textName);
            mTextOwnerName = (TextView) itemView.findViewById(R.id.textOwner);
            meTextDescription = (TextView) itemView.findViewById(R.id.textDescription);

            //itemView.setOnClickListener(this); //for fast click
            itemView.setOnLongClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            if (mRepositoryList != null) {
//                Repository repository = mRepositoryList.get(adapterPosition);
//                mClickHandler.onClick(repository);
//            }
//
//        }

        @Override
        public boolean onLongClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (mRepositoryList != null) {
                Repository repository = mRepositoryList.get(adapterPosition);
                mClickHandler.onClick(repository);
            }
            return true;
        }
    }

    /**
     * Returns an integer code related to the type of View we want the ViewHolder to be at a given
     * position. This method is useful when we want to use different layouts for different items
     * depending on their position.
     *
     * @param position index within our RecyclerView and Cursor
     * @return the view type (today or future day)
     */
    @Override
    public int getItemViewType(int position) {//not use yet
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (null == mRepositoryList) return 0;
        return mRepositoryList.size();
    }

}
