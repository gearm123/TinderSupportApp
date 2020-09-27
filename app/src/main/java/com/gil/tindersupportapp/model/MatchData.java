
package com.gil.tindersupportapp.model;

public class MatchData {

    private String realName;

    private String description;

    private String iconUrl;

    private String phoneSavedName;

    private String facebookLink;

    private String instagramLink;

    private String location;

    private String seeking;


    public MatchData(String realName, String description, String iconUrl, String phoneSavedName, String facebookLink, String instagramLink, String location, String seeking)
    {
        this.realName=realName;
        this.description=description;
        this.iconUrl=iconUrl;
        this.phoneSavedName=phoneSavedName;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.location = location;
        this.seeking = seeking;
    }

    public String getRealNameame() {
        return realName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneSavedName() {
        return phoneSavedName;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getLocation() {
        return location;
    }

    public String getSeeking() {
        return seeking;
    }

}