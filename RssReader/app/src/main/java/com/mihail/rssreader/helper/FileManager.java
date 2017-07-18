package com.mihail.rssreader.helper;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Hell on 7/18/2017.
 */

public class FileManager {

    private static FileManager instance;
    private WeakReference<Context> mContextReference;

    private FileManager() { }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public void init(Context context) {
        mContextReference = new WeakReference<>(context);
    }

    public void deinit() {
        mContextReference.clear();
    }

    public void saveFile(InputStream input, String name) {
        File file = new File(mContextReference.get().getCacheDir(), name);
        if (input != null) {
            try {
                makeFile(input, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getCacheFile(String name) {
        return new File(mContextReference.get().getCacheDir(), name);
    }

    private void makeFile(InputStream input, File file) throws IOException {
        if (file != null) {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[4096];
            int i;
            while ((i = input.read(bytes, 0, 4069)) != -1) {
                fos.write(bytes, 0, i);
            }
            fos.close();
        }
    }

}
