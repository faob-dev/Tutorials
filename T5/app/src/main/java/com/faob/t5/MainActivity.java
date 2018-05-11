package com.faob.t5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sun.jna.JNIEnv;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Structure;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.ShortByReference;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static {
        Map<String, Boolean> options = Collections.singletonMap(Library.OPTION_ALLOW_OBJECTS, Boolean.TRUE);
        Native.register(MainActivity.class, NativeLibrary.getInstance("native-lib", options));
    }

    public static String name = "FaoB";
    public static void update1(int res) {
        System.out.println("update " + res);
    }

    public String phone = "12345678";
    public void update2(int res) {
        System.out.println("update " + res);
    }

    public native void stringMapping(String msg);
    public native void integerMappingByValue(short a, int b, long c);
    public native void integerMappingByRef(ShortByReference a, IntByReference b, LongByReference c);
    public native void decimalMappingByValue(float a, double b);
    public native void decimalMappingByRef(FloatByReference a, DoubleByReference b);
    public native void booleanMappingByValue(boolean a);
    public native void booleanMappingByRef(IntByReference a);
    public native void classMapping(JNIEnv env, Class<?> clazz);
    public native void objectMapping(JNIEnv env, MainActivity object);
    public native void structureByValue(TestStructure.ByValue str);
    public native void structureByRef(TestStructure.ByReference str);
    public native void arrayMapping(int array[], int len);
    public native void bufferMapping(IntBuffer buffer, int len);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String type mapping
        stringMapping("Hello");

        //Integer type mapping by value
        short a1 = 10; int b1 = 20; long c1 = 30L;
        integerMappingByValue(a1, b1, c1);
        System.out.println("After: " + a1 + ", " + b1 + ", " + c1);

        //Integer type mapping by reference
        ShortByReference a2 = new ShortByReference((short) 10);
        IntByReference b2= new IntByReference(20);
        LongByReference c2 = new LongByReference(30L);
        integerMappingByRef(a2, b2, c2);
        System.out.println("After: " + a2.getValue() + ", " + b2.getValue() + ", " + c2.getValue());

        //Decimal type mapping by value
        float a3 = 20.0f;
        double b3 = 30.0d;
        decimalMappingByValue(a3, b3);
        System.out.println("After: " + a3 + "f, " + b3 + "d");

        //Decimal type mapping by reference
        FloatByReference a4 = new FloatByReference(20.0f);
        DoubleByReference b4 = new DoubleByReference(30.0d);
        decimalMappingByRef(a4, b4);
        System.out.println("After: " + a4.getValue() + "f, " + a4.getValue() + "d");

        //Boolean type mapping by value
        boolean a5 = false;
        booleanMappingByValue(a5);
        System.out.println("After: a:" + a5);

        //Boolean type mapping by reference
        IntByReference a6 = new IntByReference(0);
        booleanMappingByRef(a6);
        System.out.println("After: a: " + (a6.getValue()==1 ? "true" : "false"));

        //Class type mapping
        classMapping(JNIEnv.CURRENT, MainActivity.class);

        //Object type mapping
        objectMapping(JNIEnv.CURRENT, this);

        //Structure type mapping by value
        TestStructure.ByValue struct1 = new TestStructure.ByValue();
        struct1.value = 55.6;
        structureByValue(struct1);
        System.out.println("After: value = " + struct1.value);

        //Structure type mapping by reference
        TestStructure.ByReference struct2 = new TestStructure.ByReference();
        struct2.value = 55.6;
        structureByRef(struct2);
        System.out.println("After: value = " + struct2.value);

        //Array type mapping
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        arrayMapping(array, array.length);

        for (int i : array) {
            System.out.println(i);
        }

        //NIO buffer type mapping
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 1; i <= 10; i++) {
            intBuffer.put(i);
        }
        intBuffer.flip();

        bufferMapping(intBuffer, intBuffer.capacity());
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}

class TestStructure extends Structure {
    double value;
    static class ByValue extends TestStructure implements Structure.ByValue {}
    static class ByReference extends TestStructure implements Structure.ByReference {}

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("value");
    }
}