package com.example.whatsappdrive.Adapter;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappdrive.Fragments.AudioFragment;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.ModelClass;

import java.io.IOException;
import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewModel> {

    AudioFragment context;
    ArrayList<ModelClass> arrayList;
    public AudioAdapter(AudioFragment context, ArrayList<ModelClass> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public AudioAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.three_tier_layout,null,false);
        return new AudioAdapter.ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioAdapter.ViewModel holder, int position) {

        final ModelClass modelClass=arrayList.get(position);
        holder.play.setVisibility(View.VISIBLE);
        Glide.with(context).load(R.drawable.audio).into(holder.status);

        holder.status.setOnClickListener(view -> {
            MediaPlayer mediaPlayer=new MediaPlayer();
            try{
                mediaPlayer.setDataSource(context.getContext(),modelClass.getUri());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }catch (IOException e){
                e.printStackTrace();
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
            status = itemView.findViewById(R.id.thumbnail_0f_status);
            play = itemView.findViewById(R.id.play_button);
        }
    }
}
