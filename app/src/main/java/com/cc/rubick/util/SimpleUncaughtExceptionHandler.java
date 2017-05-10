package com.cc.rubick.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by bigcong on 2017/2/23.
 */

public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWiter = new PrintWriter(stringWriter);
        e.printStackTrace();
        Log.v("sssssss", stringWriter.toString());
    }
}
