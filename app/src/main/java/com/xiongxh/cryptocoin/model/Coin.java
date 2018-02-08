package com.xiongxh.cryptocoin.model;

public class Coin {
    private String symbol;
    //private long coinId;
    private String coinName;
    private String url;
    private String imageUrl;
    private String algorithm;
    private String proofType;
    private long totalSupply;
    private int sponsor;

    private double supply;
    private double price;
    private double mktcap;
    private double vol24h;
    private double vol24h2;
    private double open24h;
    private double high24h;
    private double low24h;
    private double trend;
    private double change;

    private String histo;

    private String news;

    public Coin(){}

    public Coin(String symbol){
        this.symbol = symbol;
    }


//    public Coin(String symbol,
//                long coinId,
//                String coinName,
//                String url,
//                String imageUrl,
//                String algorithm,
//                String proofType,
//                long supply,
//                int sponsor){
//        this.symbol = symbol;
//        this.coinId = coinId;
//        this.coinName = coinName;
//        this.url = url;
//        this.imageUrl = imageUrl;
//        this.algorithm = algorithm;
//        this.proofType = proofType;
//        this.supply = supply;
//        this.sponsor = sponsor;
//    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return this.symbol;
    }

//    public void setCoinId(long coinId){
//        this.coinId = coinId;
//    }
//
//    public long getCoinId(){
//        return this.coinId;
//    }

    public void setCoinName(String coinName){
        this.coinName = coinName;
    }

    public String getCoinName(){
        return this.coinName;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public void setAlgorithm(String algorithm){
        this.algorithm = algorithm;
    }

    public String getAlgorithm(){
        return this.algorithm;
    }

    public void setProofType(String proofType){
        this.proofType = proofType;
    }

    public String getProofType(){
        return this.proofType;
    }

    public void setTotalSupply(long totalSupply){
        this.totalSupply = totalSupply;
    }

    public long getTotalSupply(){
        return this.totalSupply;
    }

    public void setSponsor(int sponsor){
        this.sponsor = sponsor;
    }

    public int getSponsor(){
        return this.sponsor;
    }

    public void setSupply(double supply){
        this.supply = supply;
    }

    public double getSupply(){
        return this.supply;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public void setMktcap(double mktcap){
        this.mktcap = mktcap;
    }

    public double getMktcap(){
        return  this.mktcap;
    }

    public void setVol24h(double vol24h){
        this.vol24h = vol24h;
    }

    public double getVol24h(){
        return this.vol24h;
    }

    public void setVol24h2(double vol24h2){
        this.vol24h2 = vol24h2;
    }

    public double getVol24h2(){
        return this.vol24h2;
    }

    public void setOpen24h(double open24h){
        this.open24h = open24h;
    }

    public double getOpen24h(){
        return this.open24h;
    }

    public void setHigh24h(double high24h){
        this.high24h = high24h;
    }

    public double getHigh24h(){
        return this.high24h;
    }

    public void setLow24h(double low24h){
        this.low24h = low24h;
    }

    public double getLow24h(){
        return this.low24h;
    }

    public void setTrend(double trend){
        this.trend = trend;
    }

    public double getTrend(){
        return this.trend;
    }

    public void setChange(double change){
        this.change = change;
    }

    public double getChange(){
        return this.change;
    }

    public void setHisto(String histo){
        this.histo = histo;
    }

    public String getHisto(){
        return this.histo;
    }

    public void setNews(String news){
        this.news = news;
    }

    public String getNews(){
        return this.news;
    }

}