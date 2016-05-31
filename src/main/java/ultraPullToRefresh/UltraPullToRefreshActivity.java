package ultraPullToRefresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gemptc.myapplicationaa.R;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class UltraPullToRefreshActivity extends AppCompatActivity {
    //PtrFrameLayout 是一个可以起到下拉刷新的实现的父类布局
    // PtrClassicFrameLayout是经典头部，不用自己添加头部，最常用的
    PtrFrameLayout mPtrFrameLayout;
    ListView mListView;
    List<String> mockStrList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra_pull_to_refresh);
        Intent intent = getIntent();
        //1.find the listview
        mListView = (ListView) findViewById(R.id.load_more_listview);
        //2.绑定模拟的数据
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mockStrList);
        mListView.setAdapter(mAdapter);
        //3.设置下拉刷新组件和事件监听
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);
        //设置加载时间
        mPtrFrameLayout.setLoadingMinTime(1000);
        //设置刷新时候的事件监听
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            //判断是否可以下拉刷新
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            //处理数据的刷新
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //实现下拉刷新的功能
                mPtrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        mAdapter.notifyDataSetChanged();//通知数据集改变,界面刷新
                        mPtrFrameLayout.refreshComplete();//表示刷新完成
                    }
                }, 500);
            }
        });
        //设置延时自动刷新数据
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 2000);
    }
    private int count = 1;
    //模拟数据
    public void loadData() {
        for (int i = 0; i < 10; i++) {
            mockStrList.add("内容编号：" + count);
            count++;
        }
    }

}
