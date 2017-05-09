package com.wnw.lovebabyadmin.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wnw.lovebabyadmin.R;


/**
 * Created by wnw on 2016/12/14.
 */

public class Util {
    /**
     * 显示进度加载框
     * */
    public static Dialog createLoadingDialog(Context context, String msg, boolean isAnimation, int imageId){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.dialog_view);

        ImageView spaceshipImage = (ImageView)v.findViewById(R.id.img);
        TextView tipTextView = (TextView)v.findViewById(R.id.tipTextView);

        if(imageId != 0){
            spaceshipImage.setImageResource(imageId);
        }

        if(isAnimation){
            //加载动画
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,  R.anim.loading_animation);
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        }

        if(TextUtils.isEmpty(msg)){
            tipTextView.setVisibility(View.GONE);
        }else{
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);
        }

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)); // 设置布局
        return loadingDialog;
    }
}
