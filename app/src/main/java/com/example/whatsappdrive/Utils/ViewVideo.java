package com.example.whatsappdrive.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.whatsappdrive.R;

import java.io.File;
import java.io.IOException;

public class ViewVideo extends AppCompatActivity {
    ImageView download;
    VideoView statusvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        statusvideo=findViewById(R.id.statusvideo);
        download=findViewById(R.id.video_status_download);

        Intent intent=getIntent();
        String despath=intent.getStringExtra("DES_PATH_VIDEO");
        String filename=intent.getStringExtra("FILENAME_VIDEO");
        String filepath=intent.getStringExtra("FILE_VIDEO_PATH");
        String uri=intent.getStringExtra("URI_VIDEO");
        download.setVisibility(View.INVISIBLE);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(statusvideo);
        Uri uri1= Uri.parse(uri);
        statusvideo.setMediaController(mediaController);
        statusvideo.setVideoURI(uri1);
        statusvideo.requestFocus();
        statusvideo.start();

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
                Toast.makeText(ViewVideo.this, "Download Complete!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}