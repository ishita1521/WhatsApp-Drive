package com.example.whatsappdrive.Utils;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static final String FOLDER_NAME="/WhatsApp/";
    public static final String SAVE_FOLDER_NAME="/WhatsAppDrive/SavedStatus";
    public static final String MY_STATUS_FOLDER_NAME="/WhatsAppDrive/MyStatus";
    public static final String IMAGE_FOLDER="/WhatsApp/Media/WhatsApp Images";
    public static final String VIDEO_FOLDER="/WhatsApp/Media/WhatsApp Video";
    public static final String DOCUMENT_FOLDER="/WhatsApp/Media/WhatsApp Documents";
    public static final String AUDIO_FOLDER="/WhatsApp/Media/WhatsApp Audio";
    public static final String GIF_FOLDER="/WhatsApp/Media/WhatsApp Animated Gifs";
    public static final String STICKER_FOLDER="/WhatsApp/Media/WhatsApp Stickers";

    public String mystatus_dir(){
        File sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + Constant.MY_STATUS_FOLDER_NAME);

        if (!sdIconStorageDir.exists()) {
            sdIconStorageDir.mkdirs();
        }
        return Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + Constant.MY_STATUS_FOLDER_NAME;
    }
}
