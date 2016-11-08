package com.lab.study.app_flipper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class Flipit {

    // 動畫加速效果
    private Interpolator accelerator = new AccelerateInterpolator();

    // 動畫減速效果
    private Interpolator decelerator = new DecelerateInterpolator();

    // 參數 direction : 1:right to left, -1:left to right
    // 參數 speed : 翻動速度
    public void play(int direction, int speed, ImageView imageView1, final Context context) {
        // 顯示到隱藏翻轉設定
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                imageView1, "rotationY", 0f, -180f * direction);
        // 顯示到隱藏完成時間
        animator.setDuration(speed);
        // 加入動畫加速效果
        animator.setInterpolator(accelerator);

        // 動畫監聽器
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                Toast.makeText(context, "翻轉完畢", Toast.LENGTH_SHORT).show();
            }
        });

        // 動畫啓動
        animator.start();
    }

    public void play(int direction, int speed, ImageView imageView1, ImageView imageView2, final Context context) {

        final ImageView visibleView;
        final ImageView invisibleView;

        if (imageView1.getVisibility() == View.GONE) {
            visibleView = imageView2;
            invisibleView = imageView1;
        } else {
            invisibleView = imageView2;
            visibleView = imageView1;
        }

        // 顯示到隱藏翻轉設定
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(
                visibleView, "rotationY", 0f, -90f * direction);
        // 顯示到隱藏完成時間
        visToInvis.setDuration(speed);
        // 加入動畫加速效果
        visToInvis.setInterpolator(accelerator);

        // 隱藏到顯示翻轉設定
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(
                invisibleView, "rotationY", 90f * direction, 0f);
        // 隱藏到顯示完成時間
        invisToVis.setDuration(speed);
        // 加入動畫減速效果
        invisToVis.setInterpolator(decelerator);

        // 顯示到隱藏動畫監聽器
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleView.setVisibility(View.GONE);
                invisToVis.start();
                invisibleView.setVisibility(View.VISIBLE);
            }
        });

        // 隱藏到顯示動畫監聽器
        invisToVis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {

            }
        });

        // 動畫啓動
        visToInvis.start();
    }



}
