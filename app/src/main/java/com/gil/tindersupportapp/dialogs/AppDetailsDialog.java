package com.gil.tindersupportapp.dialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.gil.tindersupportapp.AddActivity;
import com.gil.tindersupportapp.R;
import com.gil.tindersupportapp.StoreItemsFragment;
import com.gil.tindersupportapp.model.MatchData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AppDetailsDialog extends DialogFragment implements View.OnClickListener {
    public interface AppActionsClickListener {
        void onInstallClicked(MatchData matchData);

        void onRemoveClicked(MatchData matchData);
    }

    public static View mRoot;
    private MatchData mDataDialog;
    public static MatchData aData;
    private ImageView mIcon;
    private TextView mTitle;
    private TextView mDescption;
    private TextView savedPhone;
    private TextView location;
    private TextView seeking;
    private Button mCloseButton;
    private ImageView mRemoveButton;
    private ImageView mEditButton;
    private List<MatchData> currentData = new ArrayList<>();
    private AppActionsClickListener mListner;
    private Context mContext;

    // TODO: add listener
    public static AppDetailsDialog build(MatchData matchData, AppActionsClickListener listner, Context context) {
        aData= matchData;
        AppDetailsDialog dialog = new AppDetailsDialog();
        dialog.setAppDetails(matchData);
        dialog.setListener(listner);
        return dialog;
    }

    private void setListener(AppActionsClickListener listner) {
        mListner = listner;
    }

    private void setAppDetails(MatchData matchData) {
        mDataDialog = matchData;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.dialog_app_details, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIcon = mRoot.findViewById(R.id.app_icon);
        mTitle = mRoot.findViewById(R.id.app_title);
        mDescption = mRoot.findViewById(R.id.app_description);
        savedPhone = mRoot.findViewById(R.id.saved_phone);
        location = mRoot.findViewById(R.id.location);
        seeking = mRoot.findViewById(R.id.seeking);
        mRemoveButton = mRoot.findViewById(R.id.remove_icon);
        mEditButton = mRoot.findViewById(R.id.edit_icon);
        mCloseButton = mRoot.findViewById(R.id.close_button);

        if (mDataDialog != null) {
            int cornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.cortner_radius);
            Glide.with(getContext())
                    .load(mDataDialog.getIconUrl())
                    .transform(new RoundedCorners(cornerRadius))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mIcon);
            mTitle.setText("Name: "+mDataDialog.getRealNameame());
            mTitle.setTextColor(Color.BLUE);
            mDescption.setText(mDataDialog.getDescription());
            savedPhone.setText("Saved On Phone: "+mDataDialog.getPhoneSavedName());
            location.setText("Location: "+mDataDialog.getLocation());
            seeking.setText("Seeking: "+mDataDialog.getSeeking());
            hideViews();

        }
        mCloseButton.setOnClickListener(this);
        mRemoveButton.setOnClickListener(this);
        mEditButton.setOnClickListener(this);
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onPause()
    {
        Log.v("MGCarAppStore","in AppDetailsDialog fragment on pause");
        super.onPause();
        dismiss();
    }

    @Override
    public void onClick(View v) {
        Log.v("MGCarAppStore","onclicked - "+v.getId());
        switch (v.getId()) {
            case R.id.close_button:
                dismiss();
                break;
            case R.id.remove_icon:
                remove(mDataDialog.getRealNameame());
                dismiss();
                break;
            case R.id.edit_icon:
                Intent i=new Intent(getContext(), AddActivity.class);
                i.putExtra("edit", true);
                i.putExtra("old_name", mDataDialog.getRealNameame());
                getContext().startActivity(i);
                dismiss();
                break;
        }
    }

    private int getTextColor(String color){
        switch (color){

            case "blue":
                return Color.BLUE;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.rgb(255, 165, 0);
            case "red":
                return Color.RED;
            default:
                return Color.GRAY;
        }


    }

    private void hideViews(){

        if(mDescption.getText().toString().isEmpty()) {
            mDescption.setVisibility(View.GONE);
            }

    }

    private void remove (String name){
        SharedPreferences prefs = getContext().getSharedPreferences("tinder_support", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson=new Gson();
        Type type = new TypeToken<List<MatchData>>(){}.getType();
        for(int i = 0;i<StoreItemsFragment.mData.size();i++){
            if(name.equals(StoreItemsFragment.mData.get(i).getRealNameame())){
                StoreItemsFragment.mData.remove(i);
                StoreItemsFragment.mAdapter.notifyDataSetChanged();
            }
        }

        String currentArray = prefs.getString("current_array", null);
        currentData = gson.fromJson(currentArray,type);
        for(int j =0; j < currentData.size();j++){
            if(name.equals(currentData.get(j).getRealNameame())){
                currentData.remove(j);
                break;
            }
        }
        editor.putString("current_array", gson.toJson(currentData));
        editor.apply();
    }

}
