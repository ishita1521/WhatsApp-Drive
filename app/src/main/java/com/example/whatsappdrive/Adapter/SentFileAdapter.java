package com.example.whatsappdrive.Adapter;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappdrive.Fragments.SentFileFragment;
import com.example.whatsappdrive.Utils.ViewImage;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.ModelClass;
import com.example.whatsappdrive.Utils.ViewVideo;

import java.io.IOException;
import java.util.ArrayList;

public class SentFileAdapter extends RecyclerView.Adapter<SentFileAdapter.ViewModel> {

    SentFileFragment context;
    ArrayList<ModelClass> arrayList;

    public SentFileAdapter(SentFileFragment context, ArrayList<ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SentFileAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_file_layout,null,false);
        return new SentFileAdapter.ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentFileAdapter.ViewModel holder, int position) {
        final ModelClass modelClass=arrayList.get(position);

        Boolean condition=modelClass.getUri().toString().endsWith(".acc")||modelClass.getUri().toString().endsWith(".m4a")||
                modelClass.getUri().toString().endsWith(".flac")||modelClass.getUri().toString().endsWith(".wav")||
                modelClass.getUri().toString().endsWith(".mp3")||modelClass.getUri().toString().endsWith(".mp4")||
                modelClass.getUri().toString().endsWith(".wma")||modelClass.getUri().toString().endsWith(".");

        if(modelClass.getUri().toString().endsWith(".mp4")) {
            holder.play.setVisibility(View.VISIBLE);
            Glide.with(context).load(modelClass.getUri()).into(holder.status);
        }
        else if(modelClass.getUri().toString().endsWith(".png")||modelClass.getUri().toString().endsWith(".jpg")){
            holder.play.setVisibility(View.INVISIBLE);
            Glide.with(context).load(modelClass.getUri()).into(holder.status);
        }
        else if(modelClass.getUri().toString().endsWith(".pdf")) {
            Glide.with(context).load(R.drawable.pdf).into(holder.status);
            holder.play.setVisibility(View.INVISIBLE);
        }
        else if(condition){
            holder.play.setVisibility(View.VISIBLE);
            Glide.with(context).load(R.drawable.audio).into(holder.status);
        }
        else {
            Glide.with(context).load(R.drawable.others).into(holder.status);
            holder.play.setVisibility(View.INVISIBLE);
        }

        holder.status.setOnClickListener(view -> {
            if(modelClass.getUri().toString().endsWith(".mp4")){
                Intent intent=new Intent(context.getActivity(), ViewVideo.class);
                intent.putExtra("URI_VIDEO",modelClass.getUri().toString());
                context.startActivity(intent);
            }
            else if(modelClass.getUri().toString().endsWith(".png")||modelClass.getUri().toString().endsWith(".jpg")){
                Intent intent=new Intent(context.getActivity(), ViewImage.class);
                intent.putExtra("URI_IMAGE",modelClass.getUri().toString());
                context.startActivity(intent);
            }
            else if(condition){
                MediaPlayer mediaPlayer=new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(context.getContext(),modelClass.getUri());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                ContentResolver cR = context.getContext().getContentResolver();
                String type = cR.getType(modelClass.getUri());
                StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(modelClass.getUri(), type);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewModel extends RecyclerView.ViewHolder {
        ImageView status, play;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.thumbnail_sentFile);
            play = itemView.findViewById(R.id.play_sentFile);
        }
    }
}
