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
import com.example.whatsappdrive.Fragments.ImagesFragment;
import com.example.whatsappdrive.Utils.ViewImage;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.ModelClass;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewModel> {

    ImagesFragment context;
    ArrayList<ModelClass> arrayList;

    public ImageAdapter(ImagesFragment context, ArrayList<ModelClass> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commonlayout,null,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewModel holder, int position) {

        final ModelClass modelClass = arrayList.get(position);
        holder.play.setVisibility(View.INVISIBLE);
        if (modelClass.getUri().toString().toLowerCase().endsWith(".png") || modelClass.getUri().toString().toLowerCase().endsWith(".jpg")){
            Glide.with(context).load(modelClass.getUri()).into(holder.status);
            holder.status.setOnClickListener(view -> {
            Intent intent = new Intent(context.getActivity(), ViewImage.class);
            intent.putExtra("URI_IMAGE", modelClass.getUri().toString());
            context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewModel extends RecyclerView.ViewHolder {
        ImageView status,play;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.thumbnail);
            play=itemView.findViewById(R.id.play);
        }
    }
}
