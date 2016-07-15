package com.challenge.spiderapp.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by eliete on 7/14/16.
 */
public class Utils {

    public static Long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }

    public static String createMd5Hash(String key){

        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(key.getBytes());
            byte messageDigest[] = msgDigest.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

             return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
