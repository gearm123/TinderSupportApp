package com.mgroup.senstore.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.mgroup.senstore.R;
import com.mgroup.senstore.interfaces.OnAppClickListener;
import com.mgroup.senstore.model.SensorData;

public class AppView extends FrameLayout {


    private SensorData mAppData;
    private ImageView mAppIcon;
    private TextView mAppName;
    private TextView mAppDescription;
    private OnAppClickListener mListener;
    private View mActionTouchTarget;

    public AppView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_app, this, true);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mAppIcon = findViewById(R.id.app_icon);
        mAppName = findViewById(R.id.app_name);
        mActionTouchTarget = findViewById(R.id.action_touch_target);
        mActionTouchTarget.setOnClickListener(mInternalActionClickListner);


    }

    private OnClickListener mInternalActionClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemActionClick(mAppData);
            }
        }
    };

    public void setAppData(SensorData appData, OnAppClickListener listener) {
        mAppData = appData;
        mListener = listener;
        refreshViews();
    }

    private void refreshViews() {
        mAppName.setText(mAppData.getTitleSmall());

        if (TextUtils.isEmpty(mAppData.getIconUrl())) {
            mAppIcon.setVisibility(View.GONE);
        } else {
            mAppIcon.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(mAppData.getIconUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mAppIcon);

        }
    }
}