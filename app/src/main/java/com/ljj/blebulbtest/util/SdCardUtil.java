package com.ljj.blebulbtest.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdCardUtil {
    public static void writeTxtToSdcard(String name, String message) {
        File exportDir = new File(Environment.getExternalStorageDirectory() + "/AAA_Bulb_test/");
        exportDir.mkdirs();
        File log = new File(Environment.getExternalStorageDirectory() + "/AAA_Bulb_test/", name + ".txt");
        LogUtil.e("File : " + log.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(log,false);
            fos.write(message.getBytes());
//            fos.flush();
            fos.close();
        } catch (Exception e) {
            LogUtil.e("Error opening LogUtil." + e);
        }
    }
    public static String readFromSdcard(String name) {
//        File exportDir = new File(Environment.getExternalStorageDirectory() + "/AAA_Bulb_test/");

        File file = new File(name);
//        File file = new File(Environment.getExternalStorageDirectory() + "/AAA_Bulb_test/ai/", name + ".txt");
        String text = null;
        try {
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer, "UTF-8");
            LogUtil.e("content : " + text );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;
    }

}
