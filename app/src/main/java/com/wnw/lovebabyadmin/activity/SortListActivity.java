package com.wnw.lovebabyadmin.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.SortListAdapter;
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.fragment.SortListFragment;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindMcsPresenter;
import com.wnw.lovebabyadmin.presenter.FindScByMcIdPresenter;
import com.wnw.lovebabyadmin.view.IFindMcsVIew;
import com.wnw.lovebabyadmin.view.IFindScByMcIdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IFindMcsVIew, IFindScByMcIdView {

    private RelativeLayout searchBar;
    private LinearLayout normalView;
    private TextView noSortTv;

    private Map map = new HashMap();
    private List<Mc> mcList = new ArrayList<>();

    private ListView sortListLv;
    private SortListAdapter sortListAdapter;

    private FindMcsPresenter findMcsPresenter;
    private FindScByMcIdPresenter findScByMcIdPresenter;

    private List<SortListFragment> fragmentList = new ArrayList<>();

    public static int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initView();
        initPresenter();
        startFindMcs();
    }

    // 初始化，加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }
    //菜单选中监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_product) {

            return true;
        }else if (id == R.id.action_edit_special_price) {

            return true;
        }else if (id == R.id.action_edit_hot_sale) {

            return true;
        }else if (id == R.id.action_add_mc) {

            return true;
        }else if (id == R.id.action_add_sc) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        sortListLv = (ListView)findViewById(R.id.lv_sort_list);
        normalView = (LinearLayout)findViewById(R.id.normal_view);
        noSortTv = (TextView)findViewById(R.id.no_sort);
        searchBar = (RelativeLayout)findViewById(R.id.btn_search_bar);

        searchBar.setOnClickListener(this);
        sortListLv.setDivider(null);
        sortListLv.setOnItemClickListener(this);
        noSortTv.setOnClickListener(this);
    }

    private void initPresenter(){
        findMcsPresenter = new FindMcsPresenter(this,this);
        findScByMcIdPresenter = new FindScByMcIdPresenter(this,this);
    }

    private void startFindMcs(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "无网络", Toast.LENGTH_SHORT).show();
            noSortTv.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        }else {
            findMcsPresenter.findMcs();
        }
    }

    private void startFindScByMcId(){
        findScByMcIdPresenter.findScbyMcId(mcList.get(pos).getId());
    }

    private void initFirstFragment(){
        //创建MyFragment对象
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_sort_list, fragmentList.get(0));
        fragmentTransaction.commit();
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void setAdapter(){
        sortListAdapter = new SortListAdapter(this, mcList);
        sortListLv.setAdapter(sortListAdapter);
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showMcs(List<Mc> mcs) {
        if(mcs != null){
            this.mcList = mcs;
            startFindScByMcId();
            noSortTv.setVisibility(View.GONE);
            normalView.setVisibility(View.VISIBLE);
        }else{
            //查找失败
            noSortTv.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        }
    }

    private int pos = 0;
    @Override
    public void showScs(List<Sc> scs) {
        if(scs == null){
            map.put(mcList.get(pos), new ArrayList<>());
        }else {
            map.put(mcList.get(pos), scs);
        }

        pos ++;
        if (pos == mcList.size()){
            //全部数据都加载完成
            dismissDialogs();
            setAdapter();
            initFragmentList();
            initFirstFragment();
        }else {
            startFindScByMcId();
        }
    }

    private void initFragmentList(){
        int length = mcList.size();
        for (int i = 0 ; i < length; i++){
            SortListFragment sortListFragment = new SortListFragment();
            sortListFragment.setScList((List<Sc>) map.get(mcList.get(i)));
            fragmentList.add(sortListFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.no_sort:
                startFindMcs();
                break;
            case R.id.btn_search_bar:
                Intent intent = new Intent(this, SearchGoodsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //拿到当前位置
        //即使刷新adapter
        sortListAdapter.notifyDataSetChanged();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (!fragmentList.get(i).isAdded()) {	// 先判断是否被add过
            fragmentTransaction.hide(fragmentList.get(mPosition)).add(R.id.fragment_sort_list, fragmentList.get(i)).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            fragmentTransaction.hide(fragmentList.get(mPosition)).show(fragmentList.get(i)).commit(); // 隐藏当前的fragment，显示下一个
        }
        mPosition = i;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
