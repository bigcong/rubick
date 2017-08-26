package com.cc.rubick.util;

import android.app.Application;

import com.cc.rubick.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigcong on 28/12/2016.
 */

public class MyDate extends Application {
    private List<String> cookie = new ArrayList<>();
    //private String URL = "http://bigcong.tunnel.qydev.com/";
    private String URL = "http://192.168.199.112:8082/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private User user = new User();


    public List<String> getCookie() {
        return cookie;
    }

    public void setCookie(List<String> cookie) {
        this.cookie = cookie;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


}
