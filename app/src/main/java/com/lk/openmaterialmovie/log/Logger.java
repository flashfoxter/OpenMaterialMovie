/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.log;

import android.support.annotation.Nullable;

import com.lk.openmaterialmovie.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@SuppressWarnings("unused")
public class Logger {

    public static void i(String log) {
        if (BuildConfig.DEBUG) {
            com.orhanobut.logger.Logger.i(log);
        }
    }

    public static void v(String log) {
        if (BuildConfig.DEBUG) {
            com.orhanobut.logger.Logger.v(log);
        }
    }

    public static void w(String log) {
        com.orhanobut.logger.Logger.w(log);
    }

    public static void d(String log) {
        if (BuildConfig.DEBUG) {
            com.orhanobut.logger.Logger.d(log);
        }
    }

    public static void e(String log) {
        if (BuildConfig.DEBUG) {
            com.orhanobut.logger.Logger.e(log);
        }
    }

    public static void e(Throwable throwable, String log, Nullable args) {
        if (BuildConfig.DEBUG) {
            com.orhanobut.logger.Logger.e(throwable, log, args);
        }
    }

    public static void e(Exception ex) {
        if (BuildConfig.DEBUG) {
            Writer writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            String s = writer.toString();
            com.orhanobut.logger.Logger.e(s);
        }
    }
}
