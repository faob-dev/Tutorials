package com.faob.mavenaar;

import java.util.UUID;

public class MyLib {
    //loading native library
    static {
        System.loadLibrary("native-lib");
    }

    //calls native method and returns version
    public static native String getLibVersion();

    //returns unique id
    public static String getUniqueId() {
        return UUID.randomUUID().toString();
    }
}
