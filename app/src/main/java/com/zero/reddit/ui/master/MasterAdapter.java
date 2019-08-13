package com.zero.reddit.ui.master;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zero.reddit.R;
import com.zero.reddit.constants.Constants;
import com.zero.reddit.model.posts.RedditResponse;
import com.zero.reddit.ui.detail.DetailActivity;

class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MasterAdapterViewHolder> {

    private RedditResponse mResponse;
    private Context mContext;
    private OnPostClickListener mOnPostClickListener;

    MasterAdapter(RedditResponse mResponse, Context context, OnPostClickListener onPostClickListener) {
        this.mResponse = mResponse;
        this.mContext = context;
        this.mOnPostClickListener = onPostClickListener;
    }

    @NonNull
    @Override
    public MasterAdapter.MasterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_layout, parent, false);
        return new MasterAdapter.MasterAdapterViewHolder(view, mOnPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterAdapter.MasterAdapterViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mResponse.getData().getChildren().size();
    }

    class MasterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView post_image;
        private TextView post_title;
        private TextView post_author;
        private Button commentButton;
        private OnPostClickListener mOnPostClickListener;

        MasterAdapterViewHolder(@NonNull View itemView, OnPostClickListener onPostClickListener) {
            super(itemView);

            post_image = itemView.findViewById(R.id.feed_image);
            post_title = itemView.findViewById(R.id.post_title);
            post_author = itemView.findViewById(R.id.author);
            commentButton = itemView.findViewById(R.id.comment_button);

            this.mOnPostClickListener = onPostClickListener;
            itemView.setOnClickListener(this);


        }

        void bindView(int position) {
            String postImage = mResponse.getData().getChildren().get(position).getData().getUrl();
            String title = mResponse.getData().getChildren().get(position).getData().getTitle();
            String author = mResponse.getData().getChildren().get(position).getData().getAuthor();
            String subreddit = mResponse.getData().getChildren().get(position).getData().getSubreddit();
            String id = mResponse.getData().getChildren().get(position).getData().getId();

            Glide.with(mContext).load(postImage).into(post_image);
            post_title.setText(title);
            post_author.setText(author);

            commentButton.setOnClickListener(view -> {

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Constants.SUBREDDIT, subreddit);
                intent.putExtra(Constants.ID, id);
                mContext.startActivity(intent);

            });

        }

        @Override
        public void onClick(View view) {
            mOnPostClickListener.onPostClick(getAdapterPosition(), mResponse);
        }
    }

    interface OnPostClickListener {
        void onPostClick(int position, RedditResponse mResponse);
    }

}
