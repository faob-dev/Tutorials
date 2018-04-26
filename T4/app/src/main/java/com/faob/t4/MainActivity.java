package com.faob.t4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sun.jna.Callback;
import com.sun.jna.JNIEnv;
import com.sun.jna.Native;

public class MainActivity extends AppCompatActivity {
    static {
        Native.register(MainActivity.class, "native-lib");
    }

    public native void logExample();

    public native void exceptionExample(JNIEnv env, int age);

    public native void syncCallbackExample(Callback callback);

    public native void asyncCallbackExample(Callback callback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logExample();

        try {
            exceptionExample(JNIEnv.CURRENT, -1);
        } catch (Exception e) {
            System.out.println("C++ Exception: " + e.getMessage());
        }

        System.out.println("before synCallbackExample()");
        syncCallbackExample(new Callback() {
            public void callback(int num) {
                System.out.println("Number = " + num + " : " + Thread.currentThread().getName());
            }
        });
        System.out.println("after synCallbackExample()");

        System.out.println("before asyncCallbackExample()");
        asyncCallbackExample(new Callback() {
            public void callback(int num) {
                System.out.println("Number = " + num + " : " + Thread.currentThread().getName());
            }
        });
        System.out.println("after asyncCallbackExample()");
    }
}
