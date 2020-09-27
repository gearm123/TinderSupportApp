package com.gil.tindersupportapp.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.widget.Toast;
import androidx.core.content.ContextCompat;


public class UtilityFunctions {


    public static boolean checkPermission(Context context,String permission)
    {
        if (ContextCompat.checkSelfPermission(context,permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void makeToast(Context context,int stringToDisplay)
    {
        Toast.makeText(context,stringToDisplay,Toast.LENGTH_SHORT).show();
    }




}
