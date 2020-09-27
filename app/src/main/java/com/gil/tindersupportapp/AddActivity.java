package com.gil.tindersupportapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gil.tindersupportapp.model.MatchData;

public class AddActivity extends AppCompatActivity {
    private AlertDialog emailDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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
        lay.addView(iconUrl);
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
                        Log.v("car_num", "positive email dialog button clicked");
                        String realNameString = realName.getText().toString();
                        String descString = description.getText().toString();
                        String iconUrlString = iconUrl.getText().toString();
                        String phoneSavedString = phoneSavedName.getText().toString();
                        String facebookString = facebookLink.getText().toString();
                        String instagramString = instagramLink.getText().toString();
                        String locationString = location.getText().toString();
                        String seekingString = seeking.getText().toString();

                        MatchData newPerson = new MatchData(realNameString,descString,iconUrlString,phoneSavedString,facebookString,instagramString,locationString
                                ,seekingString);
                        StoreItemsFragment.mData.add(newPerson);
                        StoreItemsFragment.mAdapter.addItem();
                        emailDialog.dismiss();
                        finish();
                    }
                });
            }
        });


        return alertDialog;
    }

}