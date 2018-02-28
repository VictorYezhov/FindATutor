package fatproject.Helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fatproject.activities.MainAplication;
import fatproject.findatutor.R;
import io.paperdb.Paper;

/**
 * Created by Victor on 23.02.2018.
 */

public class ImageSaver {

    public static  String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(MainAplication.getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath= new File(directory,"profile.jpg");
        System.err.println(mypath.getPath());
        MainAplication.savePathToPhoto(mypath.getPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path)
    {
        Bitmap b = null;
        try {
            File f=new File(path);
             b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;

    }

}
