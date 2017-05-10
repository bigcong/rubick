package com.cc.rubick.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cc.rubick.LoginActivity;
import com.cc.rubick.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * Created by bigcong on 28/12/2016.
 */

public class MyRestTemplate extends RestTemplate {
    private String cookie = "";
    private HttpHeaders headers = new HttpHeaders();
    private Context context;
    private MyDate myData =null;
    private HttpEntity<?> gg = null;


    public MyRestTemplate(Context context) {
        this.context = context;
        this.myData = (MyDate) context.getApplicationContext();
        this.getMessageConverters().add(new StringHttpMessageConverter());
        if (!myData.getCookie().isEmpty()) {
            headers.set("Cookie", myData.getCookie().get(0));

        }
        this.getMessageConverters().add(new GsonHttpMessageConverter());
        this.getMessageConverters().add(new StringHttpMessageConverter());

    }


    public JsonObject get(String url) {
        Log.v("url", url);
        gg = new HttpEntity<String>("", headers);

        ResponseEntity<String> str = this.exchange(myData.getURL() + url, HttpMethod.GET, gg, String.class);
        Log.v("接口返回内容为", str.getBody());
        JsonObject jsonObject = new JsonParser().parse(str.getBody()).getAsJsonObject();

        if (jsonObject.get("status").getAsInt() == 501) {//过期，重写登录
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }

        return jsonObject.get("data").getAsJsonObject();
    }

    public User login(String url, Object o) {
        headers.setContentType(new MediaType("application", "json"));
        gg = new HttpEntity(o, headers);
        ResponseEntity<String> str = this.exchange(myData.getURL() + url, HttpMethod.POST, gg, String.class);
        Log.v("接口返回内容为", str.getBody());
        JsonObject jsonObject = new JsonParser().parse(str.getBody()).getAsJsonObject();
        if (jsonObject.get("status").getAsInt() == 501) {//过期，重写登录
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }
        List<String> cookiesList = str.getHeaders().get("Set-Cookie");

        myData.setCookie(cookiesList);


        User u = new Gson().fromJson(jsonObject.get("data").getAsJsonObject(), User.class);
        myData.setUser(u);
        SharedPreferences.Editor editor = context.getSharedPreferences("cookies", Context.MODE_PRIVATE).edit();

        editor.putString("cookie", cookiesList.get(0));
        editor.putString("user", jsonObject.get("data").getAsJsonObject().toString());
        editor.commit();

        return u;
    }

    public JsonObject post(String url, Object o) {
        gg = new HttpEntity(o, headers);
        Log.v("传入内容",new Gson().toJson(o));

        ResponseEntity<String> str = this.exchange(myData.getURL() + url, HttpMethod.POST, gg, String.class);
        Log.v("接口返回内容为", str.getBody());
        JsonObject jsonObject = new JsonParser().parse(str.getBody()).getAsJsonObject();
        if (jsonObject.get("status").getAsInt() == 501) {//过期，重写登录
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }
        return jsonObject;

    }
}
