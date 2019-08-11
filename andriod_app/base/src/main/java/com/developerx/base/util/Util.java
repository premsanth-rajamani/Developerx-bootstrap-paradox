package com.developerx.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.developerx.base.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getFormattedDate(String date){
        String formattedDate = "";
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = input.parse(date);
            formattedDate = output.format(d);
            return formattedDate;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "NA";
    }

    public static Bitmap getTextDrawable(String username, Context context) {


//        if (username == null) {
//            Bitmap icon = drawableToBitmap(context.getResources().getDrawable(R.drawable.user_profile));
//            return icon;
//        }

        String firstWord = null;
        String secondWord = null;

        if (username.contains(" ")) {
            String[] words = username.split(" ");
            firstWord = words[0];
            secondWord = words[1];

        } else {
            firstWord = username;
        }
        String text = "";
        if (!TextUtils.isEmpty(firstWord)) {
            text = firstWord.substring(0, 1).toUpperCase();
            if (!TextUtils.isEmpty(secondWord)) {
                text = text.toUpperCase() + secondWord.substring(0, 1).toUpperCase();
            } else {
                text = text + firstWord.substring(1, 2);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(160, 160, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        //draw circle
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.profile_pic_background));
        canvas.drawCircle(80, 80, 80, paint);

        //Draw text
        paint.setColor(context.getResources().getColor(R.color.profile_pic_text));
        paint.setTextSize(60f);
        canvas.drawText(text, 45, 105, paint);
        return bitmap;

    }

}
