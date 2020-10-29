package com.example.socialsoftware.model;

public class ChatFakeUser {
    private String name;
    private String age;
    private String avatar;
    //构造器：Constructor
    public ChatFakeUser(String name, String age, String avatar) {
        this.name = name;
        this.age = age;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
