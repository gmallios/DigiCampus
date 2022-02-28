package com.project.digicampus.home_ui.Announcements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.digicampus.models.AnnouncementModel;

import java.util.ArrayList;

public class AnnouncementsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<AnnouncementModel>> mAnnouncementModels;

    public AnnouncementsViewModel() {
        mAnnouncementModels = new MutableLiveData<ArrayList<AnnouncementModel>>();
    }

    public void setmAnnouncementModels(ArrayList<AnnouncementModel> models){
        mAnnouncementModels.setValue(models);
    }

}