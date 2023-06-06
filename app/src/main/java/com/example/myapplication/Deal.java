package com.example.myapplication;

public class Deal
{
    String cid,name,qty,price,cname,cemail,img,status,add,crpid,mno,fid;

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Deal(String cid, String name, String qty, String price, String cname, String cemail, String img, String status, String add, String crpid, String mno, String fid) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.cname = cname;
        this.cemail = cemail;
        this.img=img;
        this.status=status;
        this.cid=cid;
        this.add=add;
        this.crpid=crpid;
        this.mno=mno;
        this.fid=fid;
    }
    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCrpid() {
        return crpid;
    }

    public void setCrpid(String crpid) {
        this.crpid = crpid;
    }


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Deal() {
    }
}