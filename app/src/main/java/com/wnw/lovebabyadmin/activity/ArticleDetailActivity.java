package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Article;

/**
 * Created by wnw on 2017/5/11.
 */

public class ArticleDetailActivity extends Activity implements View.OnClickListener{

    private ImageView back;
    private TextView editTv;
    private WebView articleWv;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getArticle();
        initView();
    }

    private void getArticle(){
        Intent intent = getIntent();
        article = (Article)intent.getSerializableExtra("article");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        editTv = (TextView)findViewById(R.id.tv_edit);
        articleWv = (WebView)findViewById(R.id.wv_article);

        WebSettings webSettings = articleWv.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。 这个取决于setSupportZoom(), 若setSupportZoom(false)，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        articleWv.loadUrl(article.getContent());
        back.setOnClickListener(this);
        editTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_edit:
                Intent intent = new Intent(this, EditArticleActivity.class);
                intent.putExtra("article", article);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            article = (Article)data.getSerializableExtra("article");
            articleWv.loadUrl(article.getContent());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
