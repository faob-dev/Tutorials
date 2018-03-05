package com.faob.t2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sun.jna.Native;

public class MainActivity extends AppCompatActivity {

    //loading native library
    static {
        Native.register(MainActivity.class, "native-lib");
    }

    //native methods signatures
    native int add(int a, int b);
    native int subtract(int a, int b);
    native int divide(int a, int b);
    native int multiply(int a, int b);
    native String libName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calling native methods
        System.out.println("add: " + add(4, 2));
        System.out.println("subtract: " + subtract(4, 2));
        System.out.println("divide: " + divide(4, 2));
        System.out.println("multiply: " + multiply(4, 2));
        System.out.println(libName());
    }
}


