package com.faob.t6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sun.jna.Native;

public class MainActivity extends AppCompatActivity {
    static {
        Native.register(MainActivity.class, "native-lib");
    }

    private Button button;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public native void toGrayScale(int pixels[], int len);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    //will open camera activity
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //result of camera activity handled here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //getting mutable bitmap from intent and set it to ImageView
            Bundle extras = data.getExtras();
            Bitmap bitmap = ((Bitmap) extras.get("data"))
                    .copy(Bitmap.Config.ARGB_8888, true);

            //getting pixels from bitmap
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

            //calling native method
            toGrayScale(pixels, pixels.length);

            //updating grayscale pixels of bitmap and showing in ImageView
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            imageView.setImageBitmap(bitmap);
        }
    }
}
