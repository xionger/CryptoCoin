package com.xiongxh.cryptocoin.model;

public class News {
    private String mNewsImageSrc;

    private String mNewsTitle;

    private String mNewsDescription;

    private String mNewsTime;

    private String mNewsSource;

    private String mNewsUrl;

    public News(){}

    public News(String newsImageSrc, String newsTitle, String newsDescription,
                String newsTime, String newsSource, String newsUrl){
        this.mNewsImageSrc = newsImageSrc;
        this.mNewsTitle = newsTitle;
        this.mNewsDescription = newsDescription;
        this.mNewsTime = newsTime;
        this.mNewsSource = newsSource;
        this.mNewsUrl = newsUrl;
    }

    public void setNewsImageSrc(String newsImageSrc){
        this.mNewsImageSrc = newsImageSrc;
    }

    public String getNewsImageSrc(){
        return mNewsImageSrc;
    }

    public void setNewsTitle(String newsTitle){
        this.mNewsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public void setNewsDescription(String newsDescription){
        this.mNewsDescription = newsDescription;
    }

    public String getNewsDescription() {
        return mNewsDescription;
    }

    public void setNewsTime(String newsTime){
        this.mNewsTime = newsTime;
    }

    public String getNewsTime() { return mNewsTime; }

    public void setNewsSource(String newsSource){
        this.mNewsSource = newsSource;
    }

    public String getNewsSource() { return mNewsSource; }

    public void setNewsUrl(String newsUrl){
        this.mNewsUrl = newsUrl;
    }

    public String getNewsUrl() { return mNewsUrl; }
}
