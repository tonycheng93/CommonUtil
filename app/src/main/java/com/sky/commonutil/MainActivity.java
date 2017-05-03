package com.sky.commonutil;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sky.commonutil.http.core.NoCompleteSubscriber;
import com.sky.commonutil.model.GankEntity;
import com.sky.commonutil.test.GankHttpMethod;
import com.sky.commonutil.ui.GankAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout
        .OnRefreshListener {

    private static final String TAG = "MainActivity";

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private GankAdapter mAdapter;
    private List<GankEntity> mGankEntityList;
    private LinearLayoutManager mLayoutManager;
    private static final int SIZE = 10;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent)
                , getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color
                        .colorPrimaryDark));
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new GankAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        if (mGankEntityList != null) {
            mGankEntityList.clear();
        }
        loadGankList();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter
                    .getItemCount()) {
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private void loadGankList() {
        NoCompleteSubscriber<List<GankEntity>> subscriber = new
                NoCompleteSubscriber<List<GankEntity>>() {
                    @Override
                    public void onNext(List<GankEntity> gankEntities) {
                        if (gankEntities != null && gankEntities.size() > 0) {
                            mGankEntityList = gankEntities;
                            mAdapter.setData(mGankEntityList);
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: " + t.toString());
                    }
                };

        GankHttpMethod.getInstance().getGankList(subscriber, SIZE, mPage);
    }

    private void loadMore() {
        mPage += 1;
        NoCompleteSubscriber<List<GankEntity>> subscriber = new
                NoCompleteSubscriber<List<GankEntity>>() {
                    @Override
                    public void onNext(List<GankEntity> gankEntities) {
                        if (gankEntities != null && gankEntities.size() > 0) {
                            mGankEntityList.addAll(gankEntities);
                            mAdapter.setData(mGankEntityList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }
                };
        GankHttpMethod.getInstance().getGankList(subscriber, SIZE, mPage);
    }
}
