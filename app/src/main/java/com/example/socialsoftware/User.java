package com.example.socialsoftware;

import java.io.Serializable;

//Serializable（序列化）
public class User implements Serializable {
    private String name;            //用户名
    private String password;        //密码
    private String code;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String code) {
        this.name = name;
        this.password = password;
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

