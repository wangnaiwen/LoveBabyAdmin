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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.wnw.lovebabyadmin.adapter.ProductImageAdapter;
import com.wnw.lovebabyadmin.config.NetConfig;
import com.wnw.lovebabyadmin.domain.HotSale;
import com.wnw.lovebabyadmin.domain.Mc;
import com.wnw.lovebabyadmin.domain.Product;
import com.wnw.lovebabyadmin.domain.ProductImage;
import com.wnw.lovebabyadmin.domain.Sc;
import com.wnw.lovebabyadmin.domain.SpecialPrice;
import com.wnw.lovebabyadmin.net.NetUtil;
import com.wnw.lovebabyadmin.presenter.DeleteProductPresenter;
import com.wnw.lovebabyadmin.presenter.FindImagesByProductIdPresenter;
import com.wnw.lovebabyadmin.presenter.FindMcsPresenter;
import com.wnw.lovebabyadmin.presenter.FindScByIdPresenter;
import com.wnw.lovebabyadmin.presenter.FindScByMcIdPresenter;
import com.wnw.lovebabyadmin.presenter.InsertHotSalePresenter;
import com.wnw.lovebabyadmin.presenter.InsertSpecialPricePresenter;
import com.wnw.lovebabyadmin.presenter.UpdateProductPresenter;
import com.wnw.lovebabyadmin.upload.ImageForm;
import com.wnw.lovebabyadmin.upload.ImageUploadRequest;
import com.wnw.lovebabyadmin.upload.ResponseListener;
import com.wnw.lovebabyadmin.view.IDeleteProductView;
import com.wnw.lovebabyadmin.view.IFindImagesByProductIdView;
import com.wnw.lovebabyadmin.view.IFindMcsVIew;
import com.wnw.lovebabyadmin.view.IFindScByMcIdView;
import com.wnw.lovebabyadmin.view.IInsertHotSaleView;
import com.wnw.lovebabyadmin.view.IInsertSpecialPriceView;
import com.wnw.lovebabyadmin.view.IUpdateProductView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2017/5/12.
 */

public class EditProductActivity extends Activity implements View.OnClickListener,
        IUpdateProductView, IInsertSpecialPriceView, IInsertHotSaleView,
        IFindImagesByProductIdView, IDeleteProductView ,IFindMcsVIew, IFindScByMcIdView {

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

    private TextView setHotSaleTv;
    private TextView setSpecialPriceTv;
    private TextView deleteProductTv;

    private ListView imageLv;
    private ProductImageAdapter imageAdapter;
    private List<ProductImage> imageList = new ArrayList<>();


    private Map<String, List<Sc>> map = new HashMap<>();
    private List<Mc> mcList = new ArrayList<>();

    private int selectedScId = 0;
    private int selectedMcId = 0;

    private UpdateProductPresenter updateProductPresenter;
    private InsertHotSalePresenter insertHotSalePresenter;
    private InsertSpecialPricePresenter insertSpecialPricePresenter;
    private FindImagesByProductIdPresenter findImagesByProductIdPresenter;
    private DeleteProductPresenter deleteProductPresenter;
    private FindScByMcIdPresenter findScByMcIdPresenter;
    private FindMcsPresenter findMcsPresenter;

    String path = null;                 //判断是否存在图片的依据
    private Bitmap myBitmapImage;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        getData();
        initView();
        initPresenter();
        startFindMcs();
        startFindImages();
    }

    private void getData(){
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        selectedScId = product.getScId();
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
        setSpecialPriceTv = (TextView)findViewById(R.id.tv_set_special_price);
        setHotSaleTv = (TextView)findViewById(R.id.tv_set_hot_sale);
        deleteProductTv = (TextView)findViewById(R.id.tv_delete_product);

        imageLv = (ListView)findViewById(R.id.lv_image);

        imageLv.setDivider(null);

        pickMcRl.setOnClickListener(this);
        pickScRl.setOnClickListener(this);
        back.setOnClickListener(this);
        finishTv.setOnClickListener(this);
        coverImg.setOnClickListener(this);
        setSpecialPriceTv.setOnClickListener(this);
        setHotSaleTv.setOnClickListener(this);
        deleteProductTv.setOnClickListener(this);

        nameTv.setText(product.getName());
        numberingTv.setText(product.getNumbering());
        brandTv.setText(product.getBrand());
        double s = ((double)product.getStandardPrice()) /100;
        double r = ((double)product.getRetailPrice()) / 100;
        standardPriceEt.setText(s+"");
        retailPriceEt.setText(r+"");
        countEt.setText(product.getCount()+"");
        Glide.with(this).load(product.getCoverImg()).into(coverImg);
        descriptionEt.setText(product.getDescription());

        //mcNameTv.setText(mcList.get(selectedMcId).getName());
        //scNameTv.setText((map.get(mcList.get(selectedMcId).getName())).get(0).getName());
    }

    private void initPresenter(){
        findMcsPresenter = new FindMcsPresenter(this, this);
        findScByMcIdPresenter = new FindScByMcIdPresenter(this, this);
        updateProductPresenter = new UpdateProductPresenter(this,this);
        insertHotSalePresenter = new InsertHotSalePresenter(this,this);
        insertSpecialPricePresenter = new InsertSpecialPricePresenter(this,this);
        findImagesByProductIdPresenter = new FindImagesByProductIdPresenter(this, this);
        deleteProductPresenter = new DeleteProductPresenter(this,this);
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

    private void startFindMcs(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "无网络", Toast.LENGTH_SHORT).show();
        }else {
            findMcsPresenter.findMcs();
        }
    }

    private void startFindScByMcId(){
        findScByMcIdPresenter.findScbyMcId(mcList.get(pos).getId());
    }

    @Override
    public void showMcs(List<Mc> mcs) {
        this.mcList = mcs;
        startFindScByMcId();
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
            getProductScId();

        }else {
            startFindScByMcId();
        }
    }

    private void getProductScId(){
        int length = mcList.size();
        int scPosition = 0;
        for (int i = 0; i < length; i ++){
            List<Sc> scList = map.get(mcList.get(i).getName());
            int length2 = scList.size();
            boolean isFound = false;
            for (int j = 0; j < length2; j++){
                if (product.getScId() == scList.get(j).getId()){
                    scPosition = j;
                    selectedMcId = i;
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                break;
            }
        }
        mcNameTv.setText(mcList.get(selectedMcId).getName());
        scNameTv.setText(map.get(mcList.get(selectedMcId).getName()).get(scPosition).getName());
    }

    private void setAdapter(){
        if (imageAdapter != null){
            imageAdapter.setImageList(imageList);
            imageAdapter.notifyDataSetChanged();
        }else{
            Log.d("www", imageList.size()+"");
            imageAdapter = new ProductImageAdapter(this, imageList);
            imageLv.setAdapter(imageAdapter);
        }
    }


    private void startInsertSpecialPrice(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
            SpecialPrice specialPrice = new SpecialPrice();
            specialPrice.setProductId(product.getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            specialPrice.setTime(simpleDateFormat.format(date));
            insertSpecialPricePresenter.insertSpecialPrice(specialPrice);
        }
    }

    private void startInsertHotSale(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
            HotSale hotSale = new HotSale();
            hotSale.setProductId(product.getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            hotSale.setTime(simpleDateFormat.format(date));
            insertHotSalePresenter.insertHotSale(hotSale);
        }
    }

    private void startUpdateProduct(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
            if (path != null){
                uploadAvatar(myBitmapImage);
            }else {
                updateProduct();
            }
        }
    }

    private void startFindImages(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(EditProductActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
        }else {
            findImagesByProductIdPresenter.findImagesByProductId(product.getId());
        }
    }

    private void startDeleteProduct(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
            deleteProductPresenter.deleteProduct(product.getId());
        }
    }

    //更新
    private void updateProduct() {
        updateProductPresenter.updateProduct(product);

    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    @Override
    public void showInsertHotSaleResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "设置失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showInsertSpecialPriceResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "设置失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUpdateProductResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("isDelete", false);
            intent.putExtra("product", product);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showDeleteProductResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("isDelete", true);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProductImages(List<ProductImage> images) {
        dismissDialogs();
        if (images == null){

        }else {
            this.imageList = images;
            setAdapter();
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
                }else{
                    //开始插入类别
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
                    startUpdateProduct();
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
            case R.id.tv_set_hot_sale:
                startInsertHotSale();
                break;
            case R.id.tv_set_special_price:
                startInsertSpecialPrice();
                break;
            case R.id.tv_delete_product:
                startDeleteProduct();
                break;
        }
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
                    updateProduct();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialogs();
                    error.printStackTrace();
                    Toast.makeText(EditProductActivity.this, "上传图片失败", Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
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
        insertHotSalePresenter.setView(null);
        insertSpecialPricePresenter.setView(null);
        updateProductPresenter.setView(null);
    }
}