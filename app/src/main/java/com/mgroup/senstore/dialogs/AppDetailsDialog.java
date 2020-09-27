package com.mgroup.senstore.dialogs;

import android.content.DialogInterface;
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
import com.mgroup.senstore.R;
import com.mgroup.senstore.model.SensorData;

import android.content.Context;

public class AppDetailsDialog extends DialogFragment implements View.OnClickListener {
    public interface AppActionsClickListener {
        void onInstallClicked(SensorData sensorData);

        void onRemoveClicked(SensorData sensorData);
    }

    public static View mRoot;
    private SensorData mData;
    public static SensorData aData;
    private ImageView mIcon;
    private TextView mTitle;
    private TextView mDescption;
    private TextView mAction;
    private TextView mCause;
    private Button mCloseButton;
    private AppActionsClickListener mListner;

    // TODO: add listener
    public static AppDetailsDialog build(SensorData sensorData, AppActionsClickListener listner, Context context) {
        aData= sensorData;
        AppDetailsDialog dialog = new AppDetailsDialog();
        dialog.setAppDetails(sensorData);
        dialog.setListener(listner);
        return dialog;
    }

    private void setListener(AppActionsClickListener listner) {
        mListner = listner;
    }

    private void setAppDetails(SensorData sensorData) {
        mData = sensorData;
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
        mAction = mRoot.findViewById(R.id.action);
        mCause = mRoot.findViewById(R.id.cause);
        mCloseButton = mRoot.findViewById(R.id.close_button);

        if (mData != null) {
            int cornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.cortner_radius);
            Glide.with(getContext())
                    .load(mData.getIconUrl())
                    .transform(new RoundedCorners(cornerRadius))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mIcon);
            mTitle.setText(mData.getDescription());
            mTitle.setTextColor(getTextColor(mData.getColor()));
            mDescption.setText(mData.getTooltip());
            mAction.setText(mData.getaction());
            mCause.setText(mData.getcause());
            hideViews();

        }
        mCloseButton.setOnClickListener(this);
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

        }if(mAction.getText().toString().isEmpty()){
            mAction.setVisibility(View.GONE);
        }if(mCause.getText().toString().isEmpty()){
            mCause.setVisibility(View.GONE);
        }


    }

}