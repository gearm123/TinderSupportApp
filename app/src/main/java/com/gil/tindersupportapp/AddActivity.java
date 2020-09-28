package com.gil.tindersupportapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gil.tindersupportapp.model.MatchData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private AlertDialog emailDialog;
    private List<MatchData> currentData = new ArrayList<>();
    private boolean isEdit = false;
    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        if (getIntent().getExtras() != null) {
            isEdit = getIntent().getExtras().getBoolean("edit", false);
            oldName = getIntent().getExtras().getString("old_name", "");
        }
        emailDialog = createEmailDialog();
        emailDialog.show();
    }

    private AlertDialog createEmailDialog() {
        AlertDialog.Builder builder;
        AlertDialog alertDialog;
        final EditText realName;
        final EditText description;
        final EditText iconUrl;
        final EditText phoneSavedName;
        final EditText facebookLink;
        final EditText instagramLink;
        final EditText location;
        final EditText seeking;

        Log.v("car_num", "creating dialog");
        builder = new AlertDialog.Builder(this);

        realName = new EditText(this);
        realName.setHint(getString(R.string.insert_real_name));

        description = new EditText(this);
        description.setHint(getString(R.string.insert_description));

        iconUrl = new EditText(this);
        iconUrl.setHint(getString(R.string.insert_icon));

        phoneSavedName = new EditText(this);
        phoneSavedName.setHint(getString(R.string.insert_saved_phone));

        facebookLink = new EditText(this);
        facebookLink.setHint(getString(R.string.insert_facebook));

        instagramLink = new EditText(this);
        instagramLink.setHint(getString(R.string.insert_instagram));

        location = new EditText(this);
        location.setHint(getString(R.string.insert_location));

        seeking = new EditText(this);
        seeking.setHint(getString(R.string.insert_seeking));


        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(realName);
        lay.addView(description);
        lay.addView(phoneSavedName);
        lay.addView(location);
        lay.addView(seeking);
        builder.setTitle(getString(R.string.title));

        builder.setView(lay);
        builder.setCancelable(false);

        builder.setPositiveButton(getString(R.string.submit), null);

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                emailDialog.dismiss();
                finish();
            }
        });

        alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button b = emailDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Log.v("tinder_support", "positive email dialog button clicked");
                        String realNameString = realName.getText().toString();
                        String descString = description.getText().toString();
                        String iconUrlString = iconUrl.getText().toString();
                        String phoneSavedString = phoneSavedName.getText().toString();
                        String facebookString = facebookLink.getText().toString();
                        String instagramString = instagramLink.getText().toString();
                        String locationString = location.getText().toString();
                        String seekingString = seeking.getText().toString();

                        MatchData newPerson = new MatchData(realNameString, descString, iconUrlString, phoneSavedString, facebookString, instagramString, locationString
                                , seekingString);
                        if (!isEdit) {
                            Log.v("tinder_support", "we are adding");
                            StoreItemsFragment.mData.add(newPerson);
                            StoreItemsFragment.mAdapter.addItem();
                            addToPreferences(newPerson);
                        } else {
                            Log.v("tinder_support", "we are editing");
                            edit(newPerson);
                        }
                        emailDialog.dismiss();
                        finish();
                    }
                });
            }
        });
        return alertDialog;
    }

    private void addToPreferences(MatchData toAdd) {
        SharedPreferences prefs = getSharedPreferences("tinder_support", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<List<MatchData>>() {
        }.getType();
        String currentArray = prefs.getString("current_array", null);
        if (currentArray != null) {
            Log.v("tinder_support", "current array is not null " + currentArray);
            currentData = gson.fromJson(currentArray, type);
            currentData.add(toAdd);
            editor.putString("current_array", gson.toJson(currentData));
            editor.apply();
        } else {
            Log.v("tinder_support", "current array is  null adding first");
            currentData.add(toAdd);
            editor.putString("current_array", gson.toJson(currentData));
            editor.apply();
        }

    }

    private void edit(MatchData toEdit) {
        SharedPreferences prefs = getSharedPreferences("tinder_support", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<List<MatchData>>() {
        }.getType();
        for (int i = 0; i < StoreItemsFragment.mData.size(); i++) {
            if (oldName.equals(StoreItemsFragment.mData.get(i).getRealNameame())) {
                StoreItemsFragment.mData.set(i, toEdit);
                StoreItemsFragment.mAdapter.notifyAppItemChangedString(oldName);
            }
        }

        String currentArray = prefs.getString("current_array", null);
        currentData = gson.fromJson(currentArray, type);
        for(int j = 0; j < currentData.size();j++){
            if (oldName.equals(currentData.get(j).getRealNameame())){
                currentData.set(j,toEdit);
                editor.putString("current_array", gson.toJson(currentData));
                editor.apply();
                break;
            }

        }

    }

}