package com.example.droid.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by ss on 2/11/2016.
 */
public class FileUtil {

    private static  final String AppMoverShopFolder = "/AppMoverShop/";
    private static final String FontAwesomePath = "fonts/FontAwesome.ttf";

    public static String readDataFromFile(Context context , String fileName) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public static void createFolderIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

    public static boolean checkFileExisting(String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return true;
        else
            return false;
    }

    public static String getExternalDirectoryPath() {
        String filePath = "";
        try {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AppMoverShopFolder;
            createFolderIfNotExists(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file != null)
                file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Typeface getTypefaceFontAwesome(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FontAwesomePath);
    }

    public static Bitmap getFacebookProfilePicture(String userID){
        try {
            URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=normal");
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            return bitmap;
        }catch (Exception e){
            return null;
        }
    }


}
