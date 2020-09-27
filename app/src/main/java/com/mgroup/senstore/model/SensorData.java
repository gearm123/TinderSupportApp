
package com.mgroup.senstore.model;

public class SensorData {

    private String appName;


    private String description;

    private String iconUrl;

    private String tooltip;

    private String titleSmall;

    private String action;

    private String cause;

    private String color;


    public SensorData(String appName, String description, String iconUrl, String tooltip,String titleSmall,String action,String cause,String color)
    {
        this.appName=appName;
        this.description=description;
        this.iconUrl=iconUrl;
        this.tooltip=tooltip;
        this.titleSmall = titleSmall;
        this.action = action;
        this.cause = cause;
        this.color = color;
    }

    public String getAppName() {
        return appName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getTitleSmall() {
        return titleSmall;
    }

    public String getaction() {
        return action;
    }

    public String getcause() {
        return cause;
    }

    public String getColor() {
        return color;
    }

}