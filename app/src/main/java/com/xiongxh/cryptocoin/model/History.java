package com.xiongxh.cryptocoin.model;


public class History {
    private double time;
    private double close;
    private double high;
    private double low;
    private double open;
    private double volumefrom;
    private double volumeto;

    public History(){}

    public void setTime(double time){
        this.time = time;
    }

    public double getTime(){
        return this.time;
    }

    public void setClose(double close){
        this.close = close;
    }
    public double getClose(){
        return this.close;
    }

    public void setHigh(double high){
        this.high = high;
    }

    public double getHigh(){
        return this.high;
    }

    public void setLow(double low){
        this.low = low;
    }

    public double getLow(){
        return this.low;
    }

    public void setOpen(double open){
        this.open = open;
    }

    public double getOpen(){
        return this.open;
    }

    public void setVolumefrom(double volumefrom){
        this.volumefrom = volumefrom;
    }

    public double getVolumefrom(){
        return this.volumefrom;
    }

    public void setVolumeto(double volumeto){
        this.volumeto = volumeto;
    }

    public double getVolumeto(){
        return this.volumeto;
    }
}
