package com.example.whatsappdrive.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatsappdrive.R;

import java.io.File;
import java.io.IOException;

public class ViewImage extends AppCompatActivity {
    ImageView statusimage,download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        statusimage=findViewById(R.id.statusimage);
        download=findViewById(R.id.download_image_status);
        download.setVisibility(View.INVISIBLE);

        Intent intent=getIntent();
        String despath=intent.getStringExtra("DES_PATH_IMAGE");
        String filename=intent.getStringExtra("FILENAME_IMAGE");
        String filepath=intent.getStringExtra("FILE_IMAGE_PATH");
        String uri=intent.getStringExtra("URI_IMAGE");

        Glide.with(getApplicationContext()).load(uri).into(statusimage);

        if(despath!=null&&filename!=null&&filepath!=null) {
            download.setVisibility(View.VISIBLE);

            download.setOnClickListener(view -> {
                try {
                    File despath2 = new File(despath);
                    File file1 = new File(filepath);
                    org.apache.commons.io.FileUtils.copyFileToDirectory(file1, despath2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{despath + filename},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {
                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri1) {
                            }
                        });
                Toast.makeText(ViewImage.this, "Download Complete!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}