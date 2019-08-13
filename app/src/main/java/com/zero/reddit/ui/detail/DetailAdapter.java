package com.zero.reddit.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zero.reddit.R;
import com.zero.reddit.model.comments.CommentResponse;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailAdapterViewHolder> {

    private CommentResponse mResponse;

    DetailAdapter(CommentResponse mResponse) {
        this.mResponse = mResponse;
    }

    @NonNull
    @Override
    public DetailAdapter.DetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout, parent, false);
        return new DetailAdapter.DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailAdapterViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mResponse.getData().getChildren().size();
    }

    class DetailAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView user_comments;

        DetailAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            user_comments = itemView.findViewById(R.id.user_comments);
        }

        void bindView(int position) {

            String name = mResponse.getData().getChildren().get(position).getData().getAuthor();
            String comments = mResponse.getData().getChildren().get(position).getData().getBody();

            username.setText(name);
            user_comments.setText(comments);

        }
    }
}
