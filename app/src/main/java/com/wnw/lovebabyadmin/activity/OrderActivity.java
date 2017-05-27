package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.fragment.StatisticsFragment;
import com.wnw.lovebabyadmin.fragment.WaitSendFragment;

/**
 * Created by wnw on 2017/5/27.
 */

public class OrderActivity extends FragmentActivity{

    private TabLayout tabLayout;
    private LinearLayout fragmentLayout;
    private FragmentManager fragmentManager;

    private Fragment currentFragment;

    private WaitSendFragment waitSendFragment;
    private StatisticsFragment statisticsFragment;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        initView();
        setDefaultFragment();
    }

    private void initView(){
        tabLayout = (TabLayout)findViewById(R.id.layout_tab);
        fragmentLayout = (LinearLayout)findViewById(R.id.fragment_layout);

        tabLayout.addTab(tabLayout.newTab().setText("订单统计"));
        tabLayout.addTab(tabLayout.newTab().setText("待发货订单"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        statisticsFragment = new StatisticsFragment();
        transaction.replace(R.id.fragment_layout, statisticsFragment);
        transaction.commit();
        currentFragment = statisticsFragment;
        currentPosition = 0;
    }

    private void setFragment(int position){
        if (position == currentPosition){
            //一样
        }else {
            if(position  == 0){
                changeFragment(statisticsFragment);
                currentPosition = position;
            }else{
                if (waitSendFragment == null){
                    waitSendFragment = new WaitSendFragment();
                }
                changeFragment(waitSendFragment);
                currentPosition = position;
            }
        }
    }
    /**
     * change the fragment
     * */
    private void changeFragment(Fragment to){
        FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (!to.isAdded()) {	// 先判断是否被add过
            transaction.hide(currentFragment).add(R.id.fragment_layout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
