package com.child.monitoring;

import android.graphics.drawable.Drawable;

public class App {
    public  String pid,cid,logo,name,hours,percent,date;


    public App(String pid, String cid, String logo, String name,String hours,String percent,String date) {
        this.pid = pid;
        this.cid = cid;
        this.logo = logo;
        this.name = name;
        this.hours = hours;
        this.percent = percent;
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
