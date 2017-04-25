package com.sky.commonutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sky.commonutil.http.core.NoCompleteSubscriber;
import com.sky.commonutil.test.GankEntity;
import com.sky.commonutil.test.GankHttpMethod;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLoadData;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLoadData = (Button) findViewById(R.id.btn_load);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        mBtnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGankList();
            }
        });
    }

    private void loadGankList() {
        NoCompleteSubscriber<List<GankEntity>> subscriber = new NoCompleteSubscriber<List<GankEntity>>() {
            @Override
            public void onNext(List<GankEntity> gankEntities) {
                mTvContent.setText(gankEntities.get(0).toString());
            }

            @Override
            public void onError(Throwable t) {
                mTvContent.setText(t.toString());
            }
        };
        GankHttpMethod.getInstance().getGankList(subscriber, 10, 1);
    }
}
