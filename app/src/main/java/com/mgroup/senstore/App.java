package com.mgroup.senstore;

import android.Manifest;
import android.app.Application;
import android.util.Log;
import com.mgroup.senstore.utils.AsyncResponse;
import com.mgroup.senstore.utils.JsonTask;
import com.mgroup.senstore.utils.UtilityFunctions;
import org.json.JSONArray;


public class App extends Application implements AsyncResponse {
    private static App INSTANCE;
    private boolean mIsAppReadyToRock = false;
    private boolean mIsReadyToRead = true;
     JsonTask asyncTask =new JsonTask(this);
    private JSONArray mAppArray;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (!UtilityFunctions.checkPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            mIsReadyToRead=false;
        } else {
            if(UtilityFunctions.isNetworkAvailable(getApplicationContext()))
            {
                Log.v("MGCarAppStore","app has needed permissions and internet connection - reading catalog");
                readCatalog();
            } else{
                UtilityFunctions.makeToast(getApplicationContext(),R.string.no_internet);
            }
        }


    }

    public void readCatalog() {
        Log.v("MGCarAppStore","reading catalog");
        asyncTask.setResonse(this);
        asyncTask.execute();
    }

    @Override
    public void processFinish(JSONArray output){
        mAppArray=output;
        mIsAppReadyToRock = true;
        Log.v("MGCarAppStore","received json from server");

    }

    public JSONArray getAllServerAppsData() {
        return mAppArray;
    }

    public boolean isAppReadyToTock() {
        return mIsAppReadyToRock;
    }

    public boolean isReadyToRead() {
        return mIsReadyToRead;
    }


}
