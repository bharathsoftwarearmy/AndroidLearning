package com.bijesh.exchange.myapplication.logging;

import android.os.Environment;

import com.bijesh.exchange.myapplication.utils.DateAndTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by Bijesh on 11/13/2016.
 */

public class MyLog {

    public static BufferedWriter out;
    public static void w(String tag,String value) {
                /*
                 * Function to initially create the log file and it also writes the time of creation to file.
                 */
        try {
            File Root = Environment.getExternalStorageDirectory();
            if(Root.canWrite()){
                File  LogFile = new File(Root, "MyAppLog.txt");
                FileWriter LogWriter = new FileWriter(LogFile, true);
                out = new BufferedWriter(LogWriter);
                out.write(DateAndTime.getCurrentTime()+" : "+tag+" : "+value);
                out.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*public static void w(String tag,String value) {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        File myFile = new File(externalStorageDir, "myapplog.txt");

        if (myFile.exists()) {
            try {
                FileOutputStream fostream = new FileOutputStream(myFile);
                OutputStreamWriter oswriter = new OutputStreamWriter(fostream);
                BufferedWriter bwriter = new BufferedWriter(oswriter);
                bwriter.write(DateAndTime.getCurrentTime()+" : "+tag+" : "+value);
                bwriter.newLine();
                bwriter.close();
                oswriter.close();
                fostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
