package com.itba.workin.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class PublicUser {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender\"")
    @Expose
    private String gender;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("lastActivity")
    @Expose
    private Date lastActivity;

    public PublicUser() {
    }

    public PublicUser(int id, String username, String gender, String avatarUrl, Date date, Date lastActivity) {
        super();
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.date = date;
        this.lastActivity = lastActivity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

}
