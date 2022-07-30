package com.example.whatsappdrive.Fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whatsappdrive.Adapter.ImageAdapter;
import com.example.whatsappdrive.Adapter.StatusAdapter;
import com.example.whatsappdrive.R;
import com.example.whatsappdrive.Utils.Constant;
import com.example.whatsappdrive.Utils.ModelClass;

import java.io.File;
import java.util.ArrayList;

public class ImagesFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    File[] files;
    ArrayList<ModelClass> fileslist=new ArrayList<>();
    ImageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.framelayout, container, false);

        refreshLayout=view.findViewById(R.id.swipeuprefresh);
        recyclerView=view.findViewById(R.id.recyclerView);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            new Handler().postDelayed(() -> refreshLayout.setRefreshing(false),1000);
            fileslist.clear();
            setupLayout();
        });
        setupLayout();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupLayout() {
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new ImageAdapter(this,getdata());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<ModelClass> getdata() {
        ModelClass modelClass;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.IMAGE_FOLDER;
        File targetDir = new File(targetPath);
        files = targetDir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                modelClass = new ModelClass();
                if(Uri.fromFile(file).toString().endsWith(".png")||Uri.fromFile(file).toString().endsWith(".jpg")) {
                    modelClass.setUri(Uri.fromFile(file));
                    modelClass.setFilename(file.getName());
                    modelClass.setPath(file.getAbsolutePath());
                    if (!modelClass.getUri().toString().endsWith(".nomedia"))
                        fileslist.add(modelClass);
                }
            }
        }
        return fileslist;
    }

    @Override
    public void onResume() {
        super.onResume();
        fileslist.clear();
        setupLayout();
    }
}