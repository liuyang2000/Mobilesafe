package com.liuyang.com.mobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liuyang on 2016/6/5.
 */
public class StreamUtil {
    public static String stream2String(InputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int temp = -1;


        try {
            while ((temp = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, temp);
            }

            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;


    }
}
