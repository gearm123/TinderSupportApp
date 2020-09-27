package com.mgroup.senstore;

import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.core.app.ActivityCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;

import com.mgroup.senstore.adapters.AppsAdapter;
import com.mgroup.senstore.utils.UtilityFunctions;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE_EXTERNAL_STORAGE = 945;
    private static final int PERMISSIONS_REQUEST_CODE_PHONE_STATE = 944;
    private NavController mNavigationController;
    private BottomNavigationView mNavView;
    private AppsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();

        if (!UtilityFunctions.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,PERMISSIONS_REQUEST_CODE_EXTERNAL_STORAGE);
        }

        if (!UtilityFunctions.checkPermission(this, Manifest.permission.READ_PHONE_STATE)) {

            requestPermission(Manifest.permission.READ_PHONE_STATE,PERMISSIONS_REQUEST_CODE_PHONE_STATE);
        }

    }


    private void requestPermission(String permission,int permissionCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permission},
                permissionCode);
    }

    private void setupNavigation() {
        mNavView = findViewById(R.id.nav_view);
        mNavigationController = Navigation.findNavController(this, R.id.main_content);
        NavigationUI.setupWithNavController(mNavView, mNavigationController);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    UtilityFunctions.makeToast(this,R.string.premission_missing);
                }
                break;
            }

            case PERMISSIONS_REQUEST_CODE_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!App.get().isReadyToRead())
                    {
                        if(UtilityFunctions.isNetworkAvailable(this)) {
                            Log.v("MGCarAppStore", "permission granted now and has internet connection- reading catalog from main activity");
                            App.get().readCatalog();
                        }else {
                            UtilityFunctions.makeToast(this,R.string.no_internet);
                        }


                    }
                } else {
                    UtilityFunctions.makeToast(this,R.string.premission_missing_phone);
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavigationController.navigateUp();
    }

    public void setAdapter(AppsAdapter adapter)
    {
        this.mAdapter=adapter;
    }

}
