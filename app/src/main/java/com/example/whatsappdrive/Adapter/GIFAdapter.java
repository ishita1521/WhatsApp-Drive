package com.example.whatsappdrive.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappdrive.Fragments.AnimatedGIFfragment;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.ModelClass;
import com.example.whatsappdrive.Utils.ViewVideo;

import java.util.ArrayList;

public class GIFAdapter extends RecyclerView.Adapter<GIFAdapter.ViewModel> {

    AnimatedGIFfragment context;
    ArrayList<ModelClass> arrayList;

    public GIFAdapter(AnimatedGIFfragment context, ArrayList<ModelClass> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public GIFAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.three_tier_layout,null,false);
        return new GIFAdapter.ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GIFAdapter.ViewModel holder, int position) {
        final ModelClass modelClass=arrayList.get(position);
        holder.play.setVisibility(View.VISIBLE);

        Glide.with(context).load(modelClass.getUri()).into(holder.status);
        holder.status.setOnClickListener(view -> {
            Intent intent=new Intent(context.getActivity(), ViewVideo.class);
            intent.putExtra("URI_VIDEO",modelClass.getUri().toString());
            context.startActivity(intent);
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
