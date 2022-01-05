package com.example.pickphotofromgallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    static final int PICK_PHOTO = 1;
    Button btnPick ;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    void initViews(){
        btnPick = findViewById(R.id.btn_pick);
        image = findViewById(R.id.img);

        getImage();

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select..."),PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO && resultCode == RESULT_OK){
            Uri photo = data.getData();
            if(data != null){
                image.setImageURI(photo);
            }
        }
    }
    //to get image from other apps
    void getImage(){
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if(type.startsWith("image/")){
                handleSetImage(intent);
            }
        }
    }
    void handleSetImage(Intent intent){
        Uri img = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(img != null){
            image.setImageURI(img);
        }
    }
}