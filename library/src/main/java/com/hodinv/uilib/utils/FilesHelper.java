package com.hodinv.uilib.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by vhodin on 22.12.2015.
 */
public class FilesHelper {
    /**
     * Return proper directory for files. Handles null for external media
     *
     * @param context to get proper directory
     * @return existed directory
     */
    @NonNull
    public static File getDataDir(Context context, @Nullable String fileType) {
        File directory = context.getExternalFilesDir(fileType);
        if (directory == null) {
            if (Environment.getExternalStorageDirectory() != null) {
                directory = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/" +
                        ((fileType != null) ? (fileType + "/") : ""));
            } else {
                throw new RuntimeException("No external storage");
            }
        }
        if (!directory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
        }
        return directory;
    }

    @NonNull
    public static File getDataDir(Context context) {
        return getDataDir(context, null);
    }


    /**
     * return full filename by name and context
     *
     * @param context  to get directory
     * @param fileName filename without path
     * @return file with path
     */
    public static File getFileByName(Context context, String fileName, String fileType) {
        return new File(getDataDir(context, fileType) + "/" + fileName);
    }

    /**
     * return full filename by name and context
     *
     * @param context  to get directory
     * @param fileName filename without path
     * @return file with path
     */
    public static File getFileByName(Context context, String fileName) {
        return new File(getDataDir(context) + "/" + fileName);
    }

}
