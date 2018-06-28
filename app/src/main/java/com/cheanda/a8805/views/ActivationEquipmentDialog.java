package com.cheanda.a8805.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheanda.a8805.R;
import com.cheanda.a8805.conf.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by dell on 2017/12/21.
 */

public class ActivationEquipmentDialog extends Dialog {
    private  Context mContext;
    private  String mEquipmentID;

    public void setEquipmentID(String equipmentID) {
        mEquipmentID = equipmentID;
    }

    public ActivationEquipmentDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_equipment_activation_dialog);
        TextView tv = (TextView) findViewById(R.id.equipment_id_tv);
        tv.setText(mEquipmentID);
        ImageView codeImage = (ImageView) findViewById(R.id.code_image);
        loadImage(codeImage);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = 854; // 宽度
        lp.height = 487; // 高度
        setCancelable(false);

    }

    private void loadImage(ImageView codeImage) {
        Picasso.with(getContext()).load(Constants.URLS.QR_CODE).error(R.mipmap.bg_001).placeholder(R.mipmap.bg_00).into(codeImage);
    }
}
