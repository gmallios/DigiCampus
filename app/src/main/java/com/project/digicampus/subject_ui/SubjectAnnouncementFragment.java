package com.project.digicampus.subject_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.project.digicampus.R;
import com.project.digicampus.Utils;
import com.project.digicampus.adapters.AnnouncementCardAdapter;
import com.project.digicampus.models.AnnouncementModel;

import java.util.ArrayList;


public class SubjectAnnouncementFragment extends Fragment {

    private String mSubjectID;
    private ArrayList<AnnouncementModel> mAnnouncements;
    private RecyclerView mRecyclerView;
    private AnnouncementCardAdapter mCardAdapter;

    public SubjectAnnouncementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_subject_announcement, container, false);
        if (getArguments() != null) {
            mSubjectID = getArguments().getString("SUBJECT_ID");
        }

        mAnnouncements = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.subject_announcements_recyclerview);

        DatabaseReference announcementDB = Utils.getAnnouncementDBRef();
        announcementDB.get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               DataSnapshot rootData = task.getResult();
               for(DataSnapshot rootSubjectSnapshot: rootData.getChildren()) {
                   AnnouncementModel model = rootSubjectSnapshot.getValue(AnnouncementModel.class);
                   if(model != null && model.getSubjectID().equals(mSubjectID))
                       mAnnouncements.add(model);
               }
               mCardAdapter = new AnnouncementCardAdapter(getActivity(), mAnnouncements);
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
               mRecyclerView.setLayoutManager(linearLayoutManager);
               mRecyclerView.setAdapter(mCardAdapter);
           }
        });

        return mView;
    }
}