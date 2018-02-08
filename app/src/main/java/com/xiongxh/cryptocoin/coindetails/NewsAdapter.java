package com.xiongxh.cryptocoin.coindetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, List<News> newses){
        super(context, 0, newses);
    }

    @BindView(R.id.news_image)
    ImageView imageView;
    @BindView(R.id.news_title)
    TextView titleView;
    @BindView(R.id.news_description)
    TextView descriptionView;
    @BindView(R.id.news_time)
    TextView timeView;
    @BindView(R.id.news_source)
    TextView sourceView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_news_list, parent, false);
        }

        ButterKnife.bind(this, listItemView);

        News currentNews = getItem(position);



        titleView.setText(currentNews.getNewsTitle());
        descriptionView.setText(currentNews.getNewsDescription());
        sourceView.setText(currentNews.getNewsSource());
        timeView.setText(currentNews.getNewsTime());

        String imageUrl = currentNews.getNewsImageSrc();

        if (imageUrl != null) {
            Glide.with(getContext())
                    .load(currentNews.getNewsImageSrc())
                    .into(imageView);

            setViewVisibility(imageView, true);
        }else{
            setViewVisibility(imageView, false);
        }

        return listItemView;
    }

    private void setViewVisibility(View view, boolean isShown){
        if (isShown){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
