package com.wnw.lovebabyadmin.upload;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ImageForm {
    //需要上传的图片资源，因为这里测试为了方便起见，直接把 bigmap 传进来，真正在项目中一般不会这般做，而是把图片的路径传过来，在这里对图片进行二进制转换
    private Bitmap bitmap;
    private String imageName;

    public ImageForm(Bitmap bitmap, String imageName) {
        this.bitmap = bitmap;
        this.imageName = imageName;
    }

    public String getFormName() {
    //参数 接收的Multipart名称
        return "image" ;
    }

    public String getImageName() {
        return imageName + ".png";
    }

    //对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) ;
        return bos.toByteArray();
    }
}
