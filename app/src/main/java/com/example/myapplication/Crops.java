package com.example.myapplication;
public class Crops
{
    String name;
    String desc1;
    String crops;
    String qty2;
    String price3;
    String  cropImg;
    String uid;
    String cid;

    String unit;
    public String getCid()
    {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public Crops(String name, String desc1, String crops, String qty2, String price3, String cropImg, String uid, String cid,String unit) {
        this.name = name;
        this.desc1 = desc1;
        this.crops = crops;
        this.qty2 = qty2;
        this.price3 = price3;
        this.cropImg=cropImg;
        this.uid=uid;
        this.cid=cid;
        this.unit=unit;
    }

    public Crops()
    {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getCrops() {
        return crops;
    }

    public String getQty2() {
        return qty2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public void setQty2(String qty2) {
        this.qty2 = qty2;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public void setCropImg(String cropImg) {
        this.cropImg = cropImg;
    }
    public String getPrice3() {
        return price3;
    }
    public String getCropImg() {
        return cropImg;
    }

}

