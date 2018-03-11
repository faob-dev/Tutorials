package com.faob.t3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sun.jna.Native;

public class MainActivity extends AppCompatActivity {

    static {
        Native.register(FooLib.class, "foo");
        Native.register(BarLib.class, "bar");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Message        = " + FooLib.greet());
        System.out.println("Ranadom Number = " + BarLib.randomNumber());
    }

}

class FooLib {
    public static native String greet();
}

class BarLib {
    public static native int randomNumber();
}
