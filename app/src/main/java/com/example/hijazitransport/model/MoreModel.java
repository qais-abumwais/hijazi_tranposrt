package com.example.hijazitransport.model;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hijazitransport.R;
import com.example.hijazitransport.activity.EditProfile;
import com.example.hijazitransport.activity.TripSchedule;

import java.util.ArrayList;
import java.util.List;

public class MoreModel {
    private AppCompatActivity Root;
    private int label;
    private MoreModelEnum moreModelEnum;
    private int icon;
    public MoreModel(AppCompatActivity root, int label, MoreModelEnum moreModelEnum, int icon) {
        Root = root;
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

    public MoreModelEnum getMoreModelEnum() {
        return moreModelEnum;
    }

    public void setMoreModelEnum(MoreModelEnum moreModelEnum) {
        this.moreModelEnum = moreModelEnum;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static List<MoreModel> getMoreMenu(){
        MoreModel signOut=new MoreModel(null, R.string.sign_out,MoreModelEnum.LogOut,R.drawable.ic_iconmonstr_log_out);

        MoreModel TripSchedule=new MoreModel(new TripSchedule(), R.string.trip_schedule,MoreModelEnum.Activity,R.drawable.ic_iconmonstr_calendar_more);
        MoreModel EditProfile=new MoreModel(new EditProfile(), R.string.edit_profile,MoreModelEnum.Activity,R.drawable.ic_iconmonstr_edit_6);

        List<MoreModel> moreModelList=new ArrayList<>();

        moreModelList.add(TripSchedule);
        moreModelList.add(EditProfile);
        moreModelList.add(signOut);

        return moreModelList;
    }

}
