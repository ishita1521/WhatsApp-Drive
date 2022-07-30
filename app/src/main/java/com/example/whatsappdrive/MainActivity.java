package com.example.whatsappdrive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.whatsappdrive.Fragments.AllStatusFragment;
import com.example.whatsappdrive.Fragments.AnimatedGIFfragment;
import com.example.whatsappdrive.Fragments.AudioFragment;
import com.example.whatsappdrive.Fragments.DocFragment;
import com.example.whatsappdrive.Fragments.ImagesFragment;
import com.example.whatsappdrive.Fragments.MyStatusFragment;
import com.example.whatsappdrive.Fragments.SavedStatusFragment;
import com.example.whatsappdrive.Fragments.SentFileFragment;
import com.example.whatsappdrive.Fragments.Stickerfragment;
import com.example.whatsappdrive.Fragments.VideoFragment;
import com.example.whatsappdrive.Utils.Constant;
import com.example.whatsappdrive.Utils.bitmapURI;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    int request_code=101;
    Menu menu;

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";

    Bundle bundle = new Bundle();
    SentFileFragment sentFileFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayout();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent);
            }
        }

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null) {
                    updateTitleandDrawer(fragment);
                }
        });
    }

    private void updateTitleandDrawer(Fragment fragment) {

        String fragmentName=fragment.getClass().getName();
        menu=navigationView.getMenu();
        MenuItem menuItem;

        if(fragmentName.equals(AllStatusFragment.class.getName())){
            toolbar.setTitle("All Status");
            menuItem=menu.findItem(R.id.allStatus);
        }else if(fragmentName.equals(SavedStatusFragment.class.getName())){
            toolbar.setTitle("Saved Status");
            menuItem=menu.findItem(R.id.savedStatus);
        }else if(fragmentName.equals(MyStatusFragment.class.getName())){
            toolbar.setTitle("My Status");
            menuItem=menu.findItem(R.id.myStatus);
        }else if(fragmentName.equals(ImagesFragment.class.getName())){
            toolbar.setTitle("Images");
            menuItem=menu.findItem(R.id.Images);
        }else if(fragmentName.equals(VideoFragment.class.getName())){
            toolbar.setTitle("Videos");
            menuItem=menu.findItem(R.id.Videos);
        }else if(fragmentName.equals(DocFragment.class.getName())){
            toolbar.setTitle("Documents");
            menuItem=menu.findItem(R.id.Documents);
        }else if(fragmentName.equals(AnimatedGIFfragment.class.getName())){
            toolbar.setTitle("GIFs");
            menuItem=menu.findItem(R.id.AnimatedGIF);
        }else if(fragmentName.equals(AudioFragment.class.getName())){
            toolbar.setTitle("Audio");
            menuItem=menu.findItem(R.id.Audio);
        }else if(fragmentName.equals(Stickerfragment.class.getName())){
            toolbar.setTitle("Sticker");
            menuItem=menu.findItem(R.id.Stickers);
        }else{
            toolbar.setTitle("Sent Files");
            menuItem=menu.findItem(R.id.sendImages);
        }
        menuItem.setChecked(true);
    }

    private void setupLayout() {

        if (arePermissionDenied()) {
            requestPermissions(PERMISSIONS, request_code); }

        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("All Statuses");
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            selectDrawerItem(item);
            return true;
        });

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        bitmapURI bituri=new bitmapURI();
        Bitmap bitmap=bituri.decodeUriToBitmap(MainActivity.this,imageUri);
        Boolean result=bituri.storeImage(bitmap,currentTime+"");

        if(result)
            Toast.makeText(this, "Status saved!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error Saving the status :(", Toast.LENGTH_SHORT).show();
    }

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Boolean result=false;
            for(int i=0;i<imageUris.size();i++){
                long currentTime = Calendar.getInstance().getTimeInMillis();
                bitmapURI bituri=new bitmapURI();
                Bitmap bitmap=bituri.decodeUriToBitmap(MainActivity.this,imageUris.get(i));
                result=bituri.storeImage(bitmap,currentTime+""+i);
            }
            if(result)
                Toast.makeText(this, "Status saved!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error Saving the status :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private void selectDrawerItem(MenuItem item) {
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
        drawerLayout.closeDrawers();
        switch (item.getItemId()){
            case R.id.allStatus:
                replaceFragment(new AllStatusFragment());
                break;
            case R.id.savedStatus:
                replaceFragment(new SavedStatusFragment());
                break;
            case R.id.myStatus:
                replaceFragment(new MyStatusFragment());
                break;
            case R.id.Images:
                replaceFragment(new ImagesFragment());
                break;
            case R.id.Videos:
                replaceFragment(new VideoFragment());
                break;
            case R.id.Documents:
                replaceFragment(new DocFragment());
                break;
            case R.id.AnimatedGIF:
                replaceFragment(new AnimatedGIFfragment());
                break;
            case R.id.Audio:
                replaceFragment(new AudioFragment());
                break;
            case R.id.Stickers:
                replaceFragment(new Stickerfragment());
                break;
            case R.id.sendImages:
                bundle.putString("DEST_LOCATION",Constant.IMAGE_FOLDER+"/Sent");
                sentFileFragment = new SentFileFragment();
                sentFileFragment.setArguments(bundle);
                replaceFragment(sentFileFragment);
                break;
            case R.id.sendVideos:
                bundle.putString("DEST_LOCATION",Constant.VIDEO_FOLDER+"/Sent");
                sentFileFragment = new SentFileFragment();
                sentFileFragment.setArguments(bundle);
                replaceFragment(sentFileFragment);
                break;
            case R.id.sendDocuments:
                bundle.putString("DEST_LOCATION",Constant.DOCUMENT_FOLDER+"/Sent");
                sentFileFragment = new SentFileFragment();
                sentFileFragment.setArguments(bundle);
                replaceFragment(sentFileFragment);
                break;
            case R.id.sendAudio:
                bundle.putString("DEST_LOCATION",Constant.AUDIO_FOLDER+"/Sent");
                sentFileFragment = new SentFileFragment();
                sentFileFragment.setArguments(bundle);
                replaceFragment(sentFileFragment);
                break;
            case R.id.sendGif:
                bundle.putString("DEST_LOCATION",Constant.GIF_FOLDER+"/Sent");
                sentFileFragment = new SentFileFragment();
                sentFileFragment.setArguments(bundle);
                replaceFragment(sentFileFragment);
                break;
            default:
                replaceFragment(new AllStatusFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
                .addToBackStack(fragment.getTag()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arePermissionDenied()) {
            requestPermissions(PERMISSIONS, request_code);
            return;
        }
        toolbar.setTitle("All Statuses");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllStatusFragment()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == request_code && grantResults.length > 0) {
            if (arePermissionDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            }
            else
                permissionGranted();
        }
    }

    private void permissionGranted() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SavedStatusFragment()).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    boolean checkStorageApi30() {
        AppOpsManager appOps = getApplicationContext().getSystemService(AppOpsManager.class);
        int mode = appOps.unsafeCheckOpNoThrow(
                MANAGE_EXTERNAL_STORAGE_PERMISSION,
                getApplicationContext().getApplicationInfo().uid,
                getApplicationContext().getPackageName()
        );
        return mode != AppOpsManager.MODE_ALLOWED;

    }

    private boolean arePermissionDenied() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return checkStorageApi30();
        }

        for (String permissions : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}