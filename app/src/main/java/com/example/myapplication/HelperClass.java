package com.example.myapplication;

public class HelperClass {
    String name,mobile,email,addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public HelperClass(String name, String mobile, String email, String addr) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.addr = addr;
    }

    public HelperClass() {
    }
}
