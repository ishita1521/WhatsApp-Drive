package com.example.whatsappdrive.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappdrive.Utils.Constant;
import com.example.whatsappdrive.Fragments.AllStatusFragment;
import com.example.whatsappdrive.Utils.ViewImage;
import com.example.whatsappdrive.Utils.ModelClass;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.ViewVideo;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewModel> {
    AllStatusFragment context;
    ArrayList<ModelClass> arrayList;

    public StatusAdapter(AllStatusFragment context, ArrayList<ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public StatusAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.three_tier_layout,null,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewModel holder, @SuppressLint("RecyclerView") int position) {

        final ModelClass modelClass=arrayList.get(position);
        if(modelClass.getUri().toString().endsWith(".mp4"))
            holder.play.setVisibility(View.VISIBLE);
        else
            holder.play.setVisibility(View.INVISIBLE);

        Glide.with(context).load(modelClass.getUri()).into(holder.status);
        holder.status.setOnClickListener(view -> {
            if(modelClass.getUri().toString().endsWith(".mp4")){
                final String path=arrayList.get(position).getPath();
                String despath= Environment.getExternalStorageDirectory().getAbsolutePath()+ Constant.SAVE_FOLDER_NAME;
                Intent intent=new Intent(context.getActivity(), ViewVideo.class);
                intent.putExtra("DES_PATH_VIDEO",despath);
                intent.putExtra("FILE_VIDEO_PATH",path);
                intent.putExtra("FILENAME_VIDEO",modelClass.getFilename());
                intent.putExtra("URI_VIDEO",modelClass.getUri().toString());
                context.startActivity(intent);
            }
            else{
                final String path=arrayList.get(position).getPath();
                String despath= Environment.getExternalStorageDirectory().getAbsolutePath()+Constant.SAVE_FOLDER_NAME;
                Intent intent=new Intent(context.getActivity(), ViewImage.class);
                intent.putExtra("DES_PATH_IMAGE",despath);
                intent.putExtra("FILE_IMAGE_PATH",path);
                intent.putExtra("FILENAME_IMAGE",modelClass.getFilename());
                intent.putExtra("URI_IMAGE",modelClass.getUri().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewModel extends RecyclerView.ViewHolder{
        ImageView status,play;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
                status=itemView.findViewById(R.id.thumbnail_0f_status);
                play=itemView.findViewById(R.id.play_button);
        }
    }
}
