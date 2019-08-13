package com.zero.reddit.ui.detail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zero.reddit.App;
import com.zero.reddit.R;
import com.zero.reddit.constants.Constants;
import com.zero.reddit.model.comments.CommentResponse;
import com.zero.reddit.utils.CommonUtils;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.detail_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshComment)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private DetailPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private String name;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((App) getApplication()).getAppComponent().inject(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        mPresenter.bind(this);

        getIncomingIntent();
        initRecyclerView();
        getComments();
        swipeRefresh();

    }

    private void swipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(
                this::swipeUpdate
        );
    }

    private void swipeUpdate() {
        getComments();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Inject
    void setPresenter(DetailPresenter presenter) {
        this.mPresenter = presenter;
    }

    private void getIncomingIntent() {
        name = getIntent().getStringExtra(Constants.SUBREDDIT);
        id = getIntent().getStringExtra(Constants.ID);
    }

    private void getComments() {
        mPresenter.getComments(name, id);
    }


    @Override
    public void onFetchDataProgress() {
        showLoading();
    }

    @Override
    public void onFetchDataSuccess(CommentResponse response) {
        DetailAdapter adapter = new DetailAdapter(response);
        mRecyclerView.setAdapter(adapter);
        hideLoading();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
        mPresenter.dispose();
    }

}
