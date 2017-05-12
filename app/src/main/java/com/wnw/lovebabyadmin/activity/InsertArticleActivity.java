package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.config.NetConfig;
import com.wnw.lovebabyadmin.domain.Article;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.InsertArticlePresenter;
import com.wnw.lovebabyadmin.upload.ImageForm;
import com.wnw.lovebabyadmin.upload.ImageUploadRequest;
import com.wnw.lovebabyadmin.upload.ResponseListener;
import com.wnw.lovebabyadmin.view.IInsertArticleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wnw on 2017/5/9.
 */

public class InsertArticleActivity extends Activity implements View.OnClickListener,
        IInsertArticleView{

    private ImageView back;
    private TextView finishTv;
    private EditText authorTv;
    private EditText titleTv;
    private EditText contentTv;
    private ImageView articleImg;

    private Article article;

    private InsertArticlePresenter insertArticlePresenter;

    String path = null;                 //判断是否更改图片的依据
    private Bitmap myBitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        initView();
        initPresenter();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        finishTv = (TextView) findViewById(R.id.tv_finish);
        authorTv = (EditText) findViewById(R.id.et_author);
        titleTv = (EditText) findViewById(R.id.et_title);
        contentTv = (EditText) findViewById(R.id.et_content);
        articleImg = (ImageView) findViewById(R.id.img_article_icon);

        back.setOnClickListener(this);
        finishTv.setOnClickListener(this);
        articleImg.setOnClickListener(this);
    }

    private void initPresenter(){
        insertArticlePresenter = new InsertArticlePresenter(this,this);
    }

    ProgressDialog dialog = null;

    private void showDialogs() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    @Override
    public void showInsertArticleResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            //上传成功
            Toast.makeText(InsertArticleActivity.this,"文章发表成功",Toast.LENGTH_LONG).show();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            //上传失败
            Toast.makeText(InsertArticleActivity.this,"文章发表失败",Toast.LENGTH_LONG).show();
        }
    }

    private void startInsertArticle(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(InsertArticleActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
        }else {
            uploadAvatar(myBitmapImage);
        }
    }

    //插入文章
    private void insertArticle() {
        insertArticlePresenter.insertArticle(article);
    }

    //上传头像
    private void uploadAvatar(Bitmap bitmap) {
        showDialogs();
        try {
            //利用时间戳得到文件名称
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = new Date();
            String imageName = simpleDateFormat.format(date);
            List<ImageForm> imageFormList = new ArrayList<>();
            imageFormList.add(new ImageForm(bitmap, imageName));
            Request request = new ImageUploadRequest(NetConfig.IMAGE_UPLOAD, imageFormList, new ResponseListener() {
                @Override
                public void onResponse(String response) {
                    //得到返回的文章封面的链接
                    article.setCoverImg(NetConfig.IMAGE_PATH + response);
                    insertArticle();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialogs();
                    error.printStackTrace();
                    Toast.makeText(InsertArticleActivity.this, "上传图片失败", Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_finish:
                //uploadImg();
                if (authorTv.getText().toString().trim().isEmpty()){
                    authorTv.setHint("请输入作者的名字");
                }else if (titleTv.getText().toString().trim().isEmpty()){
                    titleTv.setHint("请输入文章标题的名字");
                }else if (contentTv.getText().toString().trim().isEmpty()){
                    contentTv.setHint("请输入微信文章内容对应的链接");
                }else if (path == null){
                    Toast.makeText(InsertArticleActivity.this, "请选择文章封面", Toast.LENGTH_LONG).show();
                }else{
                    //开始插入文章
                    article = new Article();
                    article.setAuthor(authorTv.getText().toString().trim());
                    article.setTitle(titleTv.getText().toString().trim());
                    article.setContent(contentTv.getText().toString().trim());
                    startInsertArticle();
                }
                break;
            case R.id.img_article_icon:
                //获取系统选择图片intent
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //开启选择图片功能响应码为PICK_CODE
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (intent != null) {
                //获取图片路径
                //获取所有图片资源
                Uri uri = intent.getData();
                //设置指针获得一个ContentResolver的实例
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                //返回索引项位置
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                //返回索引项路径
                path = cursor.getString(index);
                Log.d("image", path);
                cursor.close();
                //这个jar包要求请求的图片大小不得超过3m所以要进行一个压缩图片操作
                resizePhoto();
                articleImg.setImageBitmap(myBitmapImage);

            }
        }
    }

    //压缩图片
    private void resizePhoto() {
        //得到BitmapFactory的操作权
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 如果设置为 true ，不获取图片，不分配内存，但会返回图片的高宽度信息。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //计算宽高要尽可能小于1024
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        //设置图片缩放的倍数。假如设为 4 ，则宽和高都为原来的 1/4 ，则图是原来的 1/16 。
        options.inSampleSize = (int) Math.ceil(ratio);
        //我们这里并想让他显示图片所以这里要置为false
        options.inJustDecodeBounds = false;
        //利用Options的这些值就可以高效的得到一幅缩略图。
        myBitmapImage = BitmapFactory.decodeFile(path, options);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}