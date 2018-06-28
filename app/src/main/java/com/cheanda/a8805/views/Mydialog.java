package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheanda.a8805.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * 弹出查询结果图片的dialog
 */

public class Mydialog extends Dialog {
    private static final String TAG = "Mydialog";
    private Context mContext;
    private View mBar;
    private ZoomImageView mZoomImageView;
    private String imageUrl = "";
    private TextView mErrorText;


    public Mydialog(Context context, int themeResId) {
        super(context,themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.view_dialog, null);
        this.addContentView(layout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        initView();
        initData();

        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.height = (int) (d.heightPixels*0.8f);
        lp.width = (int) (d.widthPixels*0.8f);
        window.setAttributes(lp);
    }
    public void setData(String url){
        imageUrl = url;
    }


    private void initView() {
        mBar = findViewById(R.id.progressbar);
        mZoomImageView = (ZoomImageView) findViewById(R.id.zoomImageview);
        mErrorText = (TextView) findViewById(R.id.tv_error);

    }

    private void initData() {
       if (!"".equals(imageUrl)){
           Picasso.with(mContext).load(imageUrl).into(mTargeet);
       }
    }
    //图片加载监听
    Target mTargeet = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            //添加水印图片
            //Bitmap newBItmap = addWatermarkBItmap(bitmap);
            if (bitmap != null){
                mZoomImageView.setImageBitmap(bitmap);
            }
            mBar.setVisibility(View.GONE);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mErrorText.setText("图片加载失败");
            mBar.setVisibility(View.GONE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    private Bitmap addWatermarkBItmap(Bitmap bitmap) {
        Bitmap watermarkBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.shuiyin1);
        if (bitmap != null){
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
            //将该图片作为画布
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bitmap,0,0,null);
            //在画布上绘制水印图片
            canvas.drawBitmap(watermarkBitmap, bitmap.getWidth()/12, bitmap.getHeight()/12, null);
            return newBitmap;
        }else {
            return null;
        }
    }
}
