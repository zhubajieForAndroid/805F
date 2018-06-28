package com.cheanda.a8805.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.cheanda.a8805.R;
import com.cheanda.a8805.views.loopView.LoopView;
import com.cheanda.a8805.views.loopView.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by dell on 2017/7/26.
 */

public class TakePhotoPopWin extends PopupWindow {
    private View view;
    private ArrayList<String> mDataList = new ArrayList<>();
    private Context mContext;
    private final LoopView mLoopView;
    private OnLoopViewItemINdexListener mOnLoopViewItemINdexListener;

    public TakePhotoPopWin(Context context) {
        mContext = context;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.take_photo_pop, null);

        mLoopView = (LoopView) view.findViewById(R.id.loopView);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
        initLoopView();

    }
    private void initLoopView() {
        for (int i = 0; i < 23; i++) {
            if (i>=10){
                mDataList.add("20"+i);
            }else {
                mDataList.add("200"+i);
            }

        }
        //设置是否循环播放
        //mLoopView.setNotLoop();
        //滚动监听
        mLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mOnLoopViewItemINdexListener.itemIndex(index);            }
        });
        //设置原始数据
        mLoopView.setItems(mDataList);

        //设置初始位置
        mLoopView.setInitPosition(0);
    }
    public void setOnLoopViewItemINdexListener(OnLoopViewItemINdexListener l){
        mOnLoopViewItemINdexListener = l;
    }

    public interface OnLoopViewItemINdexListener{
        void itemIndex(int index);
    }


}
