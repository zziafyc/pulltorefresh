package com.example.gemptc.myapplicationaa;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PullToRefreshListView mPullToRefreshListView;//PullToRefreshListView实例
    List<Music> mList = new ArrayList<>();
    MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        //1.find the listview
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        //2.绑定模拟的数据
        loadData();
        mAdapter = new MyAdapter(MainActivity.this, mList);
        mPullToRefreshListView.setAdapter(mAdapter);
        //3.设置上拉加载下拉刷新组件和事件监听
        //设置刷新模式为BOTH才可以上拉和下拉都能起作用,必须写在前面
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时头部的状态
        initRefreshListView();
        //设置上拉和下拉时候的监听器
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉时
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MainActivity.this).execute();//执行下载数据
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MainActivity.this).execute();
            }
        });


    }
    private int count = 1;
    //模拟数据
    public void loadData() {
        for (int i = 0; i < 10; i++) {
            mList.add(new Music("歌曲" + count, "歌手" + count));
            count++;
        }
    }
    static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {//定义返回值的类型
        //后台处理
        private MainActivity activity;

        public LoadDataAsyncTask(MainActivity activity) {
            this.activity = activity;
        }
        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //加载数据
            activity.loadData();
            return "success";
        }

        //  onPostExecute（）是对返回的值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                activity.mAdapter.notifyDataSetChanged();//通知数据集改变,界面刷新
                activity.mPullToRefreshListView.onRefreshComplete();//表示刷新完成
            }
        }
    }

    public void initRefreshListView() {
        ILoadingLayout startLabels = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在拉");
        startLabels.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }


}
