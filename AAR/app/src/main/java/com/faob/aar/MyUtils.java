package com.faob.aar;

public class MyUtils {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String greetFromJNI();

    public static int square(int num) {
        return num * num;
    }
}
