package com.cheanda.a8805.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 2018/1/2.
 */

public class FileUtil {
    public static File createNewFile(String fileName){
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
