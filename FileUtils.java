package com.example.xplorestylefilemanager;

import java.io.File;

public class FileUtils {
    public static boolean isStorageReadable() {
        String state = android.os.Environment.getExternalStorageState();
        return android.os.Environment.MEDIA_MOUNTED.equals(state) ||
               android.os.Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static boolean isStorageWritable() {
        return android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
    }

    public static boolean deleteFile(File file) {
        return file != null && file.exists() && file.delete();
    }
}
