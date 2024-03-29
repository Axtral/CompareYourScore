package com.rougevincloud.chat.lib.cache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        cacheDir = new File(context.getCacheDir()+"/images");
        if(!cacheDir.mkdirs()) {
            boolean created = cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());

        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if(files == null) {
            return;
        }

        for(File f : files) {
            f.delete();
        }
    }
}
