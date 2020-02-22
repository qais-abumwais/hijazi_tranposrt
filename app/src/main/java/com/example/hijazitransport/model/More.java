package com.example.hijazitransport.model;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hijazitransport.R;
import com.example.hijazitransport.activity.EditProfile;
import com.example.hijazitransport.activity.TripSchedule;

import java.util.ArrayList;
import java.util.List;

public class More {
    private AppCompatActivity Root;
    private int label;
    private MoreEnum moreModelEnum;
    private int icon;

    public More(AppCompatActivity root, int label, MoreEnum moreModelEnum, int icon) {
        this.Root = root;
        this.label = label;
        this.moreModelEnum = moreModelEnum;
        this.icon = icon;
    }

    public AppCompatActivity getRoot() {
        return Root;
    }

    public void setRoot(AppCompatActivity root) {
        Root = root;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public MoreEnum getMoreModelEnum() {
        return moreModelEnum;
    }

    public void setMoreModelEnum(MoreEnum moreModelEnum) {
        this.moreModelEnum = moreModelEnum;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static List<More> getMoreMenu() {
        More signOut = new More(null, R.string.sign_out, MoreEnum.LogOut, R.drawable.ic_iconmonstr_log_out);
        More TripSchedule = new More(new TripSchedule(), R.string.trip_schedule, MoreEnum.Activity, R.drawable.ic_iconmonstr_calendar_more);
        More EditProfile = new More(new EditProfile(), R.string.edit_profile, MoreEnum.Activity, R.drawable.ic_iconmonstr_edit_6);

        List<More> moreModelList = new ArrayList<>();

        moreModelList.add(TripSchedule);
        moreModelList.add(EditProfile);
        moreModelList.add(signOut);

        return moreModelList;
    }

}
