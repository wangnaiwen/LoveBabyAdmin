package com.wnw.lovebabyadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.adapter.ImageAdapter;
import com.wnw.lovebabyadmin.bean.SerializableMap;
import com.wnw.lovebabyadmin.config.NetConfig;
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.domain.ProductImage;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.InsertProductImagePresenter;
import com.wnw.lovebabyadmin.presenter.InsertProductPresenter;
import com.wnw.lovebabyadmin.upload.ImageForm;
import com.wnw.lovebabyadmin.upload.ImageUploadRequest;
import com.wnw.lovebabyadmin.upload.ResponseListener;
import com.wnw.lovebabyadmin.view.IInsertProductImageView;
import com.wnw.lovebabyadmin.view.IInsertProductView;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2017/5/23.
 */

public class InsertProductActivity extends Activity implements View.OnClickListener,
        IInsertProductView, IInsertProductImageView {

    private ImageView back;
    private TextView finishTv;

    private RelativeLayout pickMcRl;
    private RelativeLayout pickScRl;
    private TextView mcNameTv;
    private TextView scNameTv;
    private ImageView coverImg;
    private EditText nameTv;
    private EditText brandTv;
    private EditText numberingTv;
    private EditText standardPriceEt;
    private EditText retailPriceEt;
    private EditText countEt;
    private EditText descriptionEt;
    private RelativeLayout addImageRl;

    private ListView imageLv;
    private ImageAdapter imageAdapter;
    private List<String> paths;

    private List<Mc> mcList;
    private Map<String, List<Sc>> map;

    private int selectedScId = 0;
    private int selectedMcId = 0;
    private Product product;

    private InsertProductPresenter insertProductPresenter;
    private InsertProductImagePresenter insertProductImagePresenter;

    String path = null;                 //判断是否存在图片的依据
    private Bitmap myBitmapImage;

    private List<Bitmap> bitmapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        getMcList();
        initView();
        initPresenter();
    }

    private void getMcList(){
        Intent intent = getIntent();
        mcList = (List<Mc>) intent.getSerializableExtra("mcList");
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        map = serializableMap.getMap();

        List<Sc> scList = map.get(mcList.get(0).getName());
        selectedScId = scList.get(0).getId();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        finishTv = (TextView) findViewById(R.id.tv_finish);

        pickMcRl = (RelativeLayout)findViewById(R.id.rl_pick_mc);
        mcNameTv = (TextView)findViewById(R.id.tv_mc_name);
        pickScRl = (RelativeLayout)findViewById(R.id.rl_pick_sc);
        scNameTv = (TextView)findViewById(R.id.tv_sc_name);

        nameTv = (EditText) findViewById(R.id.et_product_name);
        brandTv = (EditText)findViewById(R.id.et_brand_name);
        numberingTv = (EditText)findViewById(R.id.et_numbering);
        standardPriceEt = (EditText)findViewById(R.id.et_standard_price);
        retailPriceEt = (EditText)findViewById(R.id.et_retail_price);
        countEt = (EditText)findViewById(R.id.et_product_count);
        descriptionEt = (EditText)findViewById(R.id.et_product_description);
        coverImg = (ImageView) findViewById(R.id.img_product_cover);

        imageLv = (ListView)findViewById(R.id.lv_image);
        addImageRl = (RelativeLayout)findViewById(R.id.rl_add_image);

        imageLv.setDivider(null);

        pickMcRl.setOnClickListener(this);
        pickScRl.setOnClickListener(this);
        back.setOnClickListener(this);
        finishTv.setOnClickListener(this);
        addImageRl.setOnClickListener(this);
        coverImg.setOnClickListener(this);

        mcNameTv.setText(mcList.get(selectedMcId).getName());
        scNameTv.setText((map.get(mcList.get(selectedMcId).getName())).get(0).getName());
    }

    private void initPresenter(){
        insertProductPresenter = new InsertProductPresenter(this,this);
        insertProductImagePresenter = new InsertProductImagePresenter(this,this);
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
    public void showInsertProductResult(int id) {
        if (id != 0){
            //上传成功
            product.setId(id);
            uploadImage();
        }else {
            dismissDialogs();
            //上传失败
            Toast.makeText(InsertProductActivity.this,"产品上架失败",Toast.LENGTH_LONG).show();
        }
    }

    private int uploadImagePosition = 0;
    @Override
    public void showInsertProductImageResult(boolean isSuccess) {
        uploadImagePosition ++;
        if (isSuccess){
            //上传成功
            if (uploadImagePosition == bitmapList.size()){
                dismissDialogs();
                Toast.makeText(InsertProductActivity.this,"产品上架成功",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }else {
            //上传失败
            dismissDialogs();
            Toast.makeText(InsertProductActivity.this,"产品上架失败",Toast.LENGTH_LONG).show();
        }
    }


    private void startInsertProductImage(ProductImage image){
        insertProductImagePresenter.insertProductImage(image);
    }

    private synchronized void uploadImage(){
        showDialogs();
        try {
            uploadImagePosition = 0;
            int length = bitmapList.size();
            for (int i = 0 ; i < length; i++){
                //利用时间戳得到文件名称
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                Date date = new Date();
                String imageName = simpleDateFormat.format(date);
                List<ImageForm> imageFormList = new ArrayList<>();
                imageFormList.add(new ImageForm(bitmapList.get(i), imageName));
                Request request = new ImageUploadRequest(NetConfig.IMAGE_UPLOAD, imageFormList, new ResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        ProductImage image = new ProductImage();
                        image.setProductId(product.getId());
                        image.setPath(NetConfig.IMAGE_PATH + response);
                        startInsertProductImage(image);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialogs();
                        error.printStackTrace();
                        Toast.makeText(InsertProductActivity.this, "上传图片失败", Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startInsertProduct(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(InsertProductActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
        }else {
            uploadAvatar(myBitmapImage);
        }
    }

    //插入
    private void insertProduct() {
        insertProductPresenter.insertProduct(product);
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
                    //得到返回的次累呗封面的链接
                    product.setCoverImg(NetConfig.IMAGE_PATH + response);
                    insertProduct();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialogs();
                    error.printStackTrace();
                    Toast.makeText(InsertProductActivity.this, "上传图片失败", Toast.LENGTH_LONG).show();
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
                if (nameTv.getText().toString().trim().isEmpty()){
                    nameTv.setHint("请输入产品名称");
                }else if (brandTv.getText().toString().trim().isEmpty()){
                    brandTv.setHint("请输入品牌名称");
                }else if (numberingTv.getText().toString().trim().isEmpty()){
                    numberingTv.setHint("请输入产品编号");
                }else if (standardPriceEt.getText().toString().trim().isEmpty()){
                    standardPriceEt.setHint("建议销售价");
                }else if (retailPriceEt.getText().toString().trim().isEmpty()){
                    retailPriceEt.setHint("请输入现售价");
                }else if (countEt.getText().toString().trim().isEmpty()){
                    countEt.setHint("请输入产品库存");
                }else if (descriptionEt.getText().toString().trim().isEmpty()){
                    descriptionEt.setHint("请输产品描述");
                }else if (path == null){
                    Toast.makeText(InsertProductActivity.this, "请选择产品封面", Toast.LENGTH_LONG).show();
                } else{
                    //开始插入类别
                    product = new Product();
                    product.setName(nameTv.getText().toString().trim());
                    product.setBrand(brandTv.getText().toString().trim());
                    product.setCount(Integer.parseInt(countEt.getText().toString().trim()));
                    product.setScId(selectedScId);
                    double s = Double.parseDouble(standardPriceEt.getText().toString().trim()) * 100;
                    double r = Double.parseDouble(retailPriceEt.getText().toString().trim()) * 100;
                    product.setStandardPrice((long)s);
                    product.setRetailPrice((long)r);
                    product.setNumbering(numberingTv.getText().toString().trim());
                    product.setDescription(descriptionEt.getText().toString().trim());
                    startInsertProduct();
                }
                break;
            case R.id.rl_pick_mc:
                showMcDialog();
                break;
            case R.id.rl_pick_sc:
                showScDialog();
                break;
            case R.id.img_product_cover:
                //获取系统选择图片intent
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //开启选择图片功能响应码为PICK_CODE
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_add_image:
                selectMulPictures();
                break;
        }
    }

    AlertDialog alertDialog = null;
    private void showMcDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.list_mc, null);
        ListView mcLv = (ListView)view.findViewById(R.id.lv_mc);
        final List<String> mcNameList = new ArrayList<String>();
        int length = mcList.size();
        for (int i = 0 ; i < length; i++){
            mcNameList.add(mcList.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mcNameList);
        mcLv.setAdapter(arrayAdapter);
        mcLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMcId = i;
                mcNameTv.setText(mcNameList.get(i));
                selectedScId = (map.get(mcList.get(selectedMcId).getName())).get(0).getId();
                scNameTv.setText((map.get(mcList.get(selectedMcId).getName())).get(0).getName());
                alertDialog.dismiss();
            }
        });
        alertDialog = new AlertDialog.Builder(this)
                .setView(view).create();
        alertDialog.show();
    }

    AlertDialog alertDialog1 = null;
    private void showScDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.list_mc, null);
        ListView mcLv = (ListView)view.findViewById(R.id.lv_mc);
        final List<String> scNameList = new ArrayList<String>();
        final List<Sc> scList = map.get(mcList.get(selectedMcId).getName());
        int length = scList.size();
        for (int i = 0 ; i < length; i++){
            scNameList.add(scList.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scNameList);
        mcLv.setAdapter(arrayAdapter);
        mcLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedScId = scList.get(i).getId();
                scNameTv.setText(scNameList.get(i));
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = new AlertDialog.Builder(this)
                .setView(view).create();
        alertDialog1.show();
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
                coverImg.setImageBitmap(myBitmapImage);
            }
        }else if (requestCode == 2 && resultCode == RESULT_OK && intent != null) {
            List<String> pathList = intent.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            paths = pathList;
            resizePicture();
        }
    }

    //压缩图片,开多线程去操作，防止主线程阻塞
    private void resizePicture() {
        final int length = paths.size();
        bitmapList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < length; i++){
                    //得到BitmapFactory的操作权
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // 如果设置为 true ，不获取图片，不分配内存，但会返回图片的高宽度信息。
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(paths.get(i), options);
                    //计算宽高要尽可能小于1024
                    double ratio=Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024f);
                    //设置图片缩放的倍数。假如设为 4 ，则宽和高都为原来的 1/4 ，则图是原来的 1/16 。
                    options.inSampleSize=(int)Math.ceil(ratio);
                    //我们这里并想让他显示图片所以这里要置为false
                    options.inJustDecodeBounds=false;
                    //利用Options的这些值就可以高效的得到一幅缩略图。
                    bitmapList.add(BitmapFactory.decodeFile(paths.get(i),options));
                }

                //交给Handler处理
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**处理异步操作*/
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:     //设置adapter
                    setAdapter();
                    break;
            }
        }
    };

    private void setAdapter(){
        if (bitmapList.size() == 0){

        }else {
            if (imageAdapter != null){
                imageAdapter.setImageList(bitmapList);
                imageAdapter.notifyDataSetChanged();
            }else{
                imageAdapter = new ImageAdapter(this, bitmapList);
                imageLv.setAdapter(imageAdapter);
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

    private void selectMulPictures(){
        // 自定义图片加载器
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
                Glide.with(context).load(path).into(imageView);
            }
        };

        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(true)
                // “确定”按钮背景色
                .btnBgColor(R.color.colorAccent)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(R.color.colorAccent)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(R.color.colorAccent)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, 2);
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
        insertProductPresenter.setView(null);
    }
}