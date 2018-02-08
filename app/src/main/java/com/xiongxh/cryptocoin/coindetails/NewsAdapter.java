package com.xiongxh.cryptocoin.coindetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.model.News;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, List<News> newses){
        super(context, 0, newses);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_news_list, parent, false);
        }

        News currentNews = getItem(position);

        //Bitmap bmp = getBitmapFromUrl(currentNews.getNewsImageSrc());
        final ImageView imageView = (ImageView) listItemView.findViewById(R.id.news_image);
        //imageView.setImageBitmap(bmp);
        //Picasso.with(getContext()).load(currentNews.getNewsImageSrc()).into(imageView);

        String imageUrl = currentNews.getNewsImageSrc();

        if (imageUrl != null) {
            Glide.with(getContext())
                    .load(currentNews.getNewsImageSrc())
                    .into(imageView);

            setViewVisibility(imageView, true);
        }else{
            setViewVisibility(imageView, false);
        }


        TextView titleView = (TextView) listItemView.findViewById(R.id.news_title);
        titleView.setText(currentNews.getNewsTitle());

        TextView tagsView = (TextView) listItemView.findViewById(R.id.news_tags);
        tagsView.setText(newsTag(currentNews.getNewsTime(), currentNews.getNewsSource()));

        //TextView timeView = (TextView) listItemView.findViewById(R.id.news_time);
        //timeView.setText(currentNews.getNewsTime());

        //TextView sourceView = (TextView) listItemView.findViewById(R.id.news_source);
        //sourceView.setText(currentNews.getNewsSource());

        return listItemView;
    }

    public String newsTag(String time, String source){
        return time + ", from " + source;
    }

    private void setViewVisibility(View view, boolean isShown){
        if (isShown){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
