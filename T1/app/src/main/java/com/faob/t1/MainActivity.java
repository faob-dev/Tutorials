package com.faob.t1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public class MainActivity extends AppCompatActivity {
    static {
        Native.register(MainActivity.class, Platform.C_LIBRARY_NAME);
    }

    public static native int getpid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("process id = " + getpid());
    }
}
