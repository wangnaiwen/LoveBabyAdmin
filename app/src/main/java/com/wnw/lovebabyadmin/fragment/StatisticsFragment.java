package com.wnw.lovebabyadmin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindSumPricePresenter;
import com.wnw.lovebabyadmin.view.IFindSumPriceView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by wnw on 2017/5/27.
 */

public class StatisticsFragment extends Fragment implements View.OnClickListener, IFindSumPriceView{
    private View view;
    private Context context;
    private LayoutInflater inflater;

    private ScrollView normalView;
    private TextView nullTv;
    private ColumnChartView monthChartView;
    private ColumnChartView datChartView;

    private ArrayList<Integer> months;
    private ArrayList<Integer> days;

    private FindSumPricePresenter findSumPricePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_statistics, null);
        initView();
        initPresenter();
        findSumPrice();
        return view;
    }

    private void initView(){
        normalView = (ScrollView)view.findViewById(R.id.sw_normal);
        nullTv = (TextView)view.findViewById(R.id.tv_null);
        monthChartView = (ColumnChartView)view.findViewById(R.id.column_chart_month);
        datChartView = (ColumnChartView)view.findViewById(R.id.column_chart_day);

        nullTv.setOnClickListener(this);
    }

    private void initPresenter(){
        findSumPricePresenter = new FindSumPricePresenter(context, this);
    }

    private void findSumPrice(){
        if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            normalView.setVisibility(View.GONE);
            nullTv.setVisibility(View.VISIBLE);
        }else {
            findSumPricePresenter.findSumPrice();
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showSumPrice(ArrayList<Integer> days, ArrayList<Integer> months) {
        dismissDialogs();
        this.days = days;
        this.months = months;
        generateMonthsData();
        generateDaysData();
    }

    private void generateMonthsData(){

        List<String> nameList = new ArrayList<>();
        Calendar ca1 = Calendar.getInstance();// 得到一个Calendar的实例

        //最近七天
        int length = months.size();
        ca1.add(Calendar.MONTH, -length);// 月份减1
        for (int i = 1; i <= length; i++) {
            ca1.add(Calendar.YEAR,0);
            ca1.add(Calendar.MONTH,+1);// 月份减1
            Date resultDate = ca1.getTime(); // 结果
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            nameList.add(sdf.format(resultDate));
        }

        //每个集合显示几条柱子
        int numSubcolumns = 1;
        //显示多少个集合
        int numColumns = months.size();
        //保存所有的柱子
        List<Column> columns = new ArrayList<Column>();
        //保存每个竹子的值
        List<SubcolumnValue> values;
        List<AxisValue> axisXValues = new ArrayList<AxisValue>();
        //对每个集合的柱子进行遍历
        for (int i = numColumns - 1; i >= 0; i--) {
            values = new ArrayList<SubcolumnValue>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubcolumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                values.add(new SubcolumnValue(months.get(i)/100, ChartUtils.pickColor()));
                //设置X轴的柱子所对应的属性名称
                axisXValues.add(new AxisValue(i).setLabel(nameList.get(i)));
            }
            //将每个属性的拥有的柱子，添加到Column中
            Column column = new Column(values);
            //是否显示每个柱子的Lable
            column.setHasLabels(true);
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            column.setHasLabelsOnlyForSelected(false);
            //将每个属性得列全部添加到List中
            columns.add(column);
        }
        //设置Columns添加到Data中
        ColumnChartData data = new ColumnChartData(columns);
        //设置X轴显示在底部，并且显示每个属性的Lable，字体颜色为黑色，X轴的名字为“学历”，每个柱子的Lable斜着显示，距离X轴的距离为8
        data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("日期").setHasTiltedLabels(true).setMaxLabelChars(8));
        //属性值含义同X轴
        data.setAxisYLeft(new Axis().setHasLines(true).setName("收入").setTextColor(Color.BLACK).setMaxLabelChars(5));
        //最后将所有值显示在View中
        monthChartView.setColumnChartData(data);

    }

    private void generateDaysData(){
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        List<String> nameList = new ArrayList<>();

        //最近三十天
        //ca.setTime(new Date()); // 设置时间为当前时间
        int length = days.size();
        ca.add(Calendar.DATE, -length);// 日期减1
        for (int i = 1; i <= length; i++) {
            ca.add(Calendar.YEAR,0); // 年份减1
            ca.add(Calendar.MONTH,0);// 月份减1
            ca.add(Calendar.DATE, +1);// 日期减1
            Date resultDate = ca.getTime(); // 结果
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            nameList.add(sdf.format(resultDate));
        }

        //每个集合显示几条柱子
        int numSubcolumns = 1;
        //显示多少个集合
        int numColumns = days.size();
        //保存所有的柱子
        List<Column> columns = new ArrayList<Column>();
        //保存每个竹子的值
        List<SubcolumnValue> values;
        List<AxisValue> axisXValues = new ArrayList<AxisValue>();
        //对每个集合的柱子进行遍历
        for (int i = numColumns-1; i >= 0; i --) {
            values = new ArrayList<SubcolumnValue>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubcolumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                values.add(new SubcolumnValue(days.get(i)/100, ChartUtils.pickColor()));
                //设置X轴的柱子所对应的属性名称
                axisXValues.add(new AxisValue(i).setLabel(nameList.get(i)));
            }
            //将每个属性的拥有的柱子，添加到Column中
            Column column = new Column(values);
            //是否显示每个柱子的Lable
            column.setHasLabels(true);
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            column.setHasLabelsOnlyForSelected(false);
            //将每个属性得列全部添加到List中
            columns.add(column);
        }
        //设置Columns添加到Data中
        ColumnChartData data = new ColumnChartData(columns);
        //设置X轴显示在底部，并且显示每个属性的Lable，字体颜色为黑色，X轴的名字为“学历”，每个柱子的Lable斜着显示，距离X轴的距离为8
        data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.BLACK).setName("月份").setHasTiltedLabels(true).setMaxLabelChars(8));
        //属性值含义同X轴
        data.setAxisYLeft(new Axis().setHasLines(true).setName("收入").setTextColor(Color.BLACK).setMaxLabelChars(5));
        //最后将所有值显示在View中
        datChartView.setColumnChartData(data);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_null:
                findSumPrice();
                break;
        }
    }
}
