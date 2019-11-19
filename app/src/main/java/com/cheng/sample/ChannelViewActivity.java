package com.cheng.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cheng.channel.Channel;
import com.cheng.channel.ChannelView;
import com.cheng.channel.PositionChangeCallBack;

import java.util.ArrayList;
import java.util.List;

public class ChannelViewActivity extends AppCompatActivity implements ChannelView.OnChannelListener, PositionChangeCallBack {
    private String TAG = getClass().getSimpleName();
    private ChannelView channelView;
    private List<Channel> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);

        channelView = findViewById(R.id.channelView);
        Log.i(TAG, "onCreate");
        init();
    }

    private void init() {
        String[] myChannel = {"要闻", "视频", "新时代", "娱乐", "体育", "军事", "NBA", "国际", "科技", "财经", "汽车", "电影", "游戏", "独家", "房产",
                "图片", "时尚", "呼和浩特", "三打白骨精"};
        String[] recommendChannel = {"综艺", "美食", "育儿", "冰雪", "必读", "政法网事", "都市",
                "NFL", "韩流"};

        List<Channel> myChannelList = new ArrayList<>();
        List<Channel> recommendChannelList = new ArrayList<>();

        for (String aMyChannel : myChannel) {
            Channel channel = new Channel(aMyChannel);
            myChannelList.add(channel);
        }

        for (String aMyChannel : recommendChannel) {
            Channel channel = new Channel(aMyChannel);
            recommendChannelList.add(channel);
        }

        allList.addAll(myChannelList);
        allList.addAll(recommendChannelList);

        channelView.addPlate("我的频道", myChannelList);
        channelView.addPlate("推荐频道", recommendChannelList);
        channelView.inflateData();
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelEditBackground(R.drawable.bg_channel_edit);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);
        channelView.setPositionChangeCallBack(this);
    }

    @Override
    public void channelEditFinish(List<Channel> channelList) {
        Log.i(TAG, channelList.toString());
        Log.i(TAG, channelView.getMyChannel().toString());
    }

    @Override
    public void channelEditStart() {

    }

    @Override
    public void changePosition(int oldPosition, int newPosition) {
        Log.e("csh", "callback = " + oldPosition + ", " + newPosition);
        if (allList.isEmpty()) {
            return;
        }
        Channel channel1 = allList.get(oldPosition);
        Channel channel2 = allList.get(newPosition);
        allList.set(oldPosition, channel2);
        allList.set(newPosition, channel1);

        StringBuilder stringBuilder = new StringBuilder();
        for (Channel channel : allList) {
            stringBuilder.append(channel.getChannelName() + ", ");
        }
        Log.e("csh", ">>> " + stringBuilder.toString());
    }
}
