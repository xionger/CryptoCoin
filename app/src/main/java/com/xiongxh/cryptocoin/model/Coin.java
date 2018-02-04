package com.xiongxh.cryptocoin.model;

public class Coin {
    private String symbol;
    private long coinId;
    private String coinName;
    private String url;
    private String imageUrl;
    private String algorithm;
    private String proofType;
    private long supply;
    private int sponsor;

    public Coin(){}

    public Coin(String symbol,
                long coinId,
                String coinName,
                String url,
                String imageUrl,
                String algorithm,
                String proofType,
                long supply,
                int sponsor){
        this.symbol = symbol;
        this.coinId = coinId;
        this.coinName = coinName;
        this.url = url;
        this.imageUrl = imageUrl;
        this.algorithm = algorithm;
        this.proofType = proofType;
        this.supply = supply;
        this.sponsor = sponsor;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public void setCoinId(long coinId){
        this.coinId = coinId;
    }

    public long getCoinId(){
        return this.coinId;
    }

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

    public void setSupply(long supply){
        this.supply = supply;
    }

    public long getSupply(){
        return this.supply;
    }

    public void setSponsored(int sponsor){
        this.sponsor = sponsor;
    }

    public int getSponsor(){
        return this.sponsor;
    }
}