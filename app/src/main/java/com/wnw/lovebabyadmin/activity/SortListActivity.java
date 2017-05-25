package com.wnw.lovebabyadmin.activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.SortListAdapter;
import com.wnw.lovebabyadmin.bean.SerializableMap;
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.fragment.SortListFragment;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.FindMcsPresenter;
import com.wnw.lovebabyadmin.presenter.FindScByMcIdPresenter;
import com.wnw.lovebabyadmin.presenter.InsertMcPresenter;
import com.wnw.lovebabyadmin.presenter.UpdateMcPresenter;
import com.wnw.lovebabyadmin.view.IFindMcsVIew;
import com.wnw.lovebabyadmin.view.IFindScByMcIdView;
import com.wnw.lovebabyadmin.view.IInsertMcView;
import com.wnw.lovebabyadmin.view.IUpdateMcView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IFindMcsVIew, IFindScByMcIdView,
        AdapterView.OnItemLongClickListener, IUpdateMcView , IInsertMcView{

    private RelativeLayout searchBar;
    private LinearLayout normalView;
    private TextView noSortTv;

    private Map<String, List<Sc>> map = new HashMap<>();
    private List<Mc> mcList = new ArrayList<>();

    private ListView sortListLv;
    private SortListAdapter sortListAdapter;

    private FindMcsPresenter findMcsPresenter;
    private FindScByMcIdPresenter findScByMcIdPresenter;
    private UpdateMcPresenter updateMcPresenter;
    private InsertMcPresenter insertMcPresenter;

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
            Intent intent = new Intent(this, InsertProductActivity.class);

            //传递数据
            final SerializableMap myMap=new SerializableMap();
            myMap.setMap(map);//将map数据添加到封装的myMap中
            Bundle bundle=new Bundle();
            bundle.putSerializable("map", myMap);
            intent.putExtra("mcList", (Serializable)mcList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }else if (id == R.id.action_edit_special_price) {

            return true;
        }else if (id == R.id.action_edit_hot_sale) {

            return true;
        }else if (id == R.id.action_add_mc) {
            showInsertMcDialog();
            return true;
        }else if (id == R.id.action_add_sc) {
            Intent intent = new Intent(this, InsertScActivity.class);
            intent.putExtra("mcList", (Serializable)mcList);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            reFindMcs();
        }
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
        sortListLv.setOnItemLongClickListener(this);
    }

    private void initPresenter(){
        findMcsPresenter = new FindMcsPresenter(this,this);
        findScByMcIdPresenter = new FindScByMcIdPresenter(this,this);
        updateMcPresenter = new UpdateMcPresenter(this,this);
        insertMcPresenter = new InsertMcPresenter(this,this);
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

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private void setAdapter(){
        if (sortListAdapter == null){
            sortListAdapter = new SortListAdapter(this, mcList);
            sortListLv.setAdapter(sortListAdapter);
        }else {
            sortListAdapter.setMcList(mcList);
            sortListAdapter.notifyDataSetChanged();
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
            map.put(mcList.get(pos).getName(), new ArrayList<Sc>());
        }else {
            map.put(mcList.get(pos).getName(), scs);
        }
        Log.d("wnw", pos+" " + mcList.size());
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

    private void startUpdateMc(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        }else {
            Mc mc = new Mc();
            mc.setId(mcList.get(position).getId());
            if (name.equals("")){
                Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show();
            }else {
                mc.setName(name);
                updateMcPresenter.updateMc(mc);
            }
        }
    }

    //更新名字返回的结果
    @Override
    public void showUpdateMcResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "名字更新成功", Toast.LENGTH_SHORT).show();
            mcList.get(position).setName(name);
            sortListAdapter.setMcList(mcList);
            sortListAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "名字更新失败", Toast.LENGTH_SHORT).show();
        }
    }

    private String name1;
    private void startInsertMc(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        }else {
            Mc mc = new Mc();
            mc.setName(name1);
            insertMcPresenter.insertMc(mc);
        }
    }

    //插入主类返回的数据
    @Override
    public void showInsertMcResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "插入主类成功", Toast.LENGTH_SHORT).show();
            reFindMcs();
        }else {
            Toast.makeText(this, "插入主类失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void reFindMcs(){
        //在这里重新加载当前页面
        mcList.clear();
        map.clear();
        pos = 0;
        mPosition = 0;
        fragmentList.clear();
        startFindMcs();
    }

    private void showInsertMcDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText editText = (EditText)view.findViewById(R.id.dialog_rename);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("插入主类")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name1 = editText.getText().toString().trim();
                        if (name1.isEmpty()){
                            Toast.makeText(SortListActivity.this, "主类名不能为空", Toast.LENGTH_SHORT).show();
                        }else {
                            startInsertMc();
                        }
                    }
                }).create();
        alertDialog.show();
    }

    private void initFragmentList(){
        int length = mcList.size();
        for (int i = 0 ; i < length; i++){
            SortListFragment sortListFragment = new SortListFragment();
            sortListFragment.setScList(map.get(mcList.get(i).getName()));
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

    private int position;
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_sort_list:
                position = i;
                name = mcList.get(i).getName();
                showRenameMcDialog();
                break;
        }
        return true;
    }

    private String name ="";
    private void showRenameMcDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        final EditText editText = (EditText)view.findViewById(R.id.dialog_rename);
        editText.setText(name);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("更新名字")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name = editText.getText().toString().trim();
                        startUpdateMc();
                    }
                }).create();
        alertDialog.show();
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
