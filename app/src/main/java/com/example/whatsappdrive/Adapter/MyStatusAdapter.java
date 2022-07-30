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
import com.example.whatsappdrive.Fragments.MyStatusFragment;
import com.example.whatsappdrive.Utils.ViewImage;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.Constant;
import com.example.whatsappdrive.Utils.ModelClass;
import com.example.whatsappdrive.Utils.ViewVideo;

import java.util.ArrayList;

public class MyStatusAdapter extends RecyclerView.Adapter<MyStatusAdapter.ViewModel> {

    MyStatusFragment context;
    ArrayList<ModelClass> arrayList;

    public MyStatusAdapter(MyStatusFragment context, ArrayList<ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyStatusAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commonlayout,null,false);
        return new MyStatusAdapter.ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStatusAdapter.ViewModel holder, int position) {

        final ModelClass modelClass=arrayList.get(position);
        if(modelClass.getUri().toString().endsWith(".mp4"))
            holder.play.setVisibility(View.VISIBLE);
        else
            holder.play.setVisibility(View.INVISIBLE);

        Glide.with(context).load(modelClass.getUri()).into(holder.savedstatus);
        holder.savedstatus.setOnClickListener(view -> {
            if(modelClass.getUri().toString().endsWith(".mp4")){
                final String path=arrayList.get(position).getPath();
                Intent intent=new Intent(context.getActivity(), ViewVideo.class);
                intent.putExtra("URI_VIDEO",modelClass.getUri().toString());
                context.startActivity(intent);
            }
            else{
                final String path=arrayList.get(position).getPath();
                String despath= Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.SAVE_FOLDER_NAME+"MyStatus/";
                Intent intent=new Intent(context.getActivity(), ViewImage.class);
                intent.putExtra("URI_IMAGE",modelClass.getUri().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewModel extends RecyclerView.ViewHolder {
        ImageView savedstatus,play;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            savedstatus = itemView.findViewById(R.id.thumbnail);
            play = itemView.findViewById(R.id.play);
        }
    }
}
