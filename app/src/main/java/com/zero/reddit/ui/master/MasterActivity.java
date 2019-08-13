package com.zero.reddit.ui.master;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zero.reddit.App;
import com.zero.reddit.R;
import com.zero.reddit.model.posts.RedditResponse;
import com.zero.reddit.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zero.reddit.utils.CommonUtils.isConnectedToNetwork;

public class MasterActivity extends AppCompatActivity implements MasterView, MasterAdapter.OnPostClickListener {


    @BindView(R.id.master_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MasterPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLayoutManager;
    private String after;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        ((App) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);
        mPresenter.bind(this);

        initRecyclerView();
        getPosts();
        getNextPost();
        swipeRefresh();

    }

    private void swipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(
                this::swipeUpdate
        );
    }

    private void swipeUpdate() {
        getPosts();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * Fetching first page post without specifying values
     **/
    private void getPosts() {
        if (isConnectedToNetwork(this)) {
            mPresenter.getPosts("");
        } else {
            Toast.makeText(this, R.string.connect_to_internet, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Telling dagger to inject a presenter to Master Activity
     * using setPresenter
     **/
    @Inject
    void setPresenter(MasterPresenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Getting next page post with after token value
     */
    private void getNextPost() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentItems = mLayoutManager.getChildCount();
                int scrolledItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                int totalItems = mLayoutManager.getItemCount();

                if (currentItems + scrolledItems == totalItems) {
                    mPresenter.getPosts(after);
                }

            }
        });

    }

    @Override
    public void onFetchDataProgress() {
        showLoading();
    }

    /**
     * Populate data in RecyclerView
     */
    @Override
    public void onFetchDataSuccess(RedditResponse response) {
        MasterAdapter adapter = new MasterAdapter(response, this, this);
        mRecyclerView.setAdapter(adapter);
        hideLoading();
        after = response.getData().getAfter();
    }

    @Override
    public void onFetchDataError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onPostClick(int position, RedditResponse mResponse) {
        String url = mResponse.getData().getChildren().get(position).getData().getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
        mPresenter.dispose();
    }

}
