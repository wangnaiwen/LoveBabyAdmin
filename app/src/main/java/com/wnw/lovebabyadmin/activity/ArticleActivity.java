package com.wnw.lovebabyadmin.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.ArticleAdapter;
import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.DeleteArticlePresenter;
import com.wnw.lovebabyadmin.presenter.FindAllArticlePresenter;
import com.wnw.lovebabyadmin.presenter.InsertArticlePresenter;
import com.wnw.lovebabyadmin.view.IDeleteArticleView;
import com.wnw.lovebabyadmin.view.IFindAllArticleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/9.
 */

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IFindAllArticleView,IDeleteArticleView,
        AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener{

    private ListView listView;
    private TextView nullArticleTv;


    private List<Article> articleList = new ArrayList<>();

    private boolean isFirst = true;  //第一次查找
    private int page = 1;
    private boolean isEnd = false;

    private FindAllArticlePresenter findAllArticlePresenter;
    private DeleteArticlePresenter deleteArticlePresenter;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initPresenter();
        findArticle();
    }

    //初始化
    private void initView(){
        listView = (ListView)findViewById(R.id.college_lv);
        nullArticleTv = (TextView)findViewById(R.id.null_article);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        listView.setOnItemLongClickListener(this);
        nullArticleTv.setOnClickListener(this);
    }


    // 初始化，加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article, menu);
        return true;
    }
    //菜单选中监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_article) {
            Intent intent = new Intent(this, InsertArticleActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPresenter(){
        findAllArticlePresenter = new FindAllArticlePresenter(this,this);
        deleteArticlePresenter = new DeleteArticlePresenter(this, this);
    }

    private void findArticle(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            nullArticleTv.setVisibility(View.VISIBLE);
        }else {
            findAllArticlePresenter.findAllArticles(page);
        }
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

    @Override
    public void showArticles(List<Article> articles) {
        dismissDialogs();
        if (articles == null){
            nullArticleTv.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            page ++;
            if (articles.size() < 10){
                isEnd = true;
            }
            nullArticleTv.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            articleList.addAll(articles);
            setAdapter();
        }
    }

    private void setAdapter(){
        if (articleAdapter == null){
            articleAdapter = new ArticleAdapter(this, articleList);
            listView.setAdapter(articleAdapter);
        }else{
            articleAdapter.setArticleList(articleList);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFirst){
            isFirst = false;
        }else {
            reFindArticle();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.null_article:
                reFindArticle();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.college_lv:
                Intent intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra("article", articleList.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    private int deletePos = 0;
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.college_lv:
                deletePos = i;
                showDeleteDialog();
                break;
        }
        return true;
    }

    private void deleteArticle(){
        deleteArticlePresenter.setDeleteArticle(articleList.get(deletePos).getId());
    }

    private void showDeleteDialog(){
         new AlertDialog.Builder(this)
                .setTitle("删除该文章")
                .setMessage("你确定删除该文章？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteArticle();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showDeleteResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "删除文章成功", Toast.LENGTH_SHORT).show();
            reFindArticle();
        }else {
            Toast.makeText(this, "删除文章失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 当不滚动时
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                //监听ListView滑动到底部，加载更多评论
                if(isEnd){  //到底
                    Toast.makeText(this,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    findArticle();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    private void reFindArticle(){
        articleList.clear();
        isEnd = false;
        page = 1;
        findArticle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        findAllArticlePresenter.setView(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

