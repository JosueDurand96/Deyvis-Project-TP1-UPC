package com.example.greenfield;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    String fotoString;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fotoString = getIntent().getStringExtra("fotoString");
        imageView2 = findViewById(R.id.imageView2);

//        byte[] foodImage = fotoString.getBytes();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
//        imageView2.setImageBitmap(bitmap);

    }
}