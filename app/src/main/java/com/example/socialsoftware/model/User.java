package com.example.socialsoftware.model;

import java.io.Serializable;

//Serializable（序列化）
public class User implements Serializable {
    private String name;            //用户名
    private String password;        //密码
    private String code;

    private String real_name;
    private String real_name_jp;
    private String gender;
    private String date_of_birth;
    private String nationality;
    private String postcode;
    private String address;
    private String phone_number;
    private String friend;

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String code) {
        this.name = name;
        this.password = password;
        this.code = code;
    }

    public User(String real_name) {
        this.real_name = real_name;
    }

    public User(String real_name, String real_name_jp, String gender, String date_of_birth, String nationality, String postcode, String address, String phone_number) {
        this.name = name;
        this.password = password;
        this.code = code;
        this.real_name = real_name;
        this.real_name_jp = real_name_jp;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.nationality = nationality;
        this.postcode = postcode;
        this.address = address;
        this.phone_number = phone_number;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name_jp() {
        return real_name_jp;
    }

    public void setReal_name_jp(String real_name_jp) {
        this.real_name_jp = real_name_jp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

