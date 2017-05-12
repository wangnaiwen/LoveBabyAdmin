package com.wnw.lovebabyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wnw.lovebabyadmin.R;
import com.wnw.lovebabyadmin.domain.Article;

import java.util.List;

/**
 * Created by wnw on 2017/5/9.
 */

public class ArticleAdapter extends BaseAdapter {
    private Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article>  articles){
        this.context = context;
        this.articleList = articles;
    }

    public void setArticleList(List<Article>  articles){
        this.articleList = articles;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ArticleHolder articleHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_article, null);
            articleHolder = new ArticleHolder();
            articleHolder.imageView = (ImageView)view.findViewById(R.id.article_cover_icon);
            articleHolder.titleTv = (TextView)view.findViewById(R.id.article_title);
            articleHolder.timeTv = (TextView) view.findViewById(R.id.article_publish_time);
            articleHolder.readNumTv = (TextView)view.findViewById(R.id.article_read_num);
            articleHolder.praiseNumTv = (TextView)view.findViewById(R.id.article_praise_num);
            view.setTag(articleHolder);
        }else{
            articleHolder = (ArticleHolder)view.getTag();
        }
        Article article = articleList.get(i);
        Glide.with(context).load(article.getCoverImg()).error(R.mipmap.b1).into(articleHolder.imageView);
        articleHolder.titleTv.setText(article.getTitle());
        articleHolder.timeTv.setText(article.getTime());
        articleHolder.readNumTv.setText(article.getReadTimes()+"");
        articleHolder.praiseNumTv.setText(article.getLikeTimes()+"");
        return view;
    }

    private class  ArticleHolder{
        ImageView imageView;
        TextView titleTv;
        TextView timeTv;
        TextView readNumTv;
        TextView praiseNumTv;
    }
}
