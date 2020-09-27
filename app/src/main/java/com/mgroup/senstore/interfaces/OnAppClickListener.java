package com.mgroup.senstore.interfaces;

import com.mgroup.senstore.model.SensorData;

public interface OnAppClickListener {
    void onItemClick(SensorData item);
    void onItemActionClick(SensorData item);
}
