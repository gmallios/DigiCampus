package com.project.digicampus.subject_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.project.digicampus.R;
import com.project.digicampus.Utils;
import com.project.digicampus.adapters.SubjectGroupAdapter;
import com.project.digicampus.models.GroupModel;

import java.util.ArrayList;


public class SubjectGroupsFragment extends Fragment {


    private ArrayList<String> groupIDs;
    private ArrayList<GroupModel> mGroups;
    private RecyclerView mRecyclerView;
    private SubjectGroupAdapter mCardAdapter;

    public SubjectGroupsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_subject_groups, container, false);

        mRecyclerView = mView.findViewById(R.id.subject_groups_recyclerview);
        mGroups = new ArrayList<>();


        // Get Data from activity
        if(getArguments() != null){
            groupIDs = getArguments().getStringArrayList("SUBJECT_GROUPS_IDS");
        }




        DatabaseReference groupsDB = Utils.getGroupsDBRef();
        groupsDB.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DataSnapshot rootData = task.getResult();
                for(DataSnapshot rootGroup: rootData.getChildren()) {
                    if (groupIDs.contains(rootGroup.getKey())) {
                        //Toast.makeText(getContext(), rootGroup.getKey(), Toast.LENGTH_SHORT).show();
                        mGroups.add(rootGroup.getValue(GroupModel.class));
                    }
                }
                // Data fetched & mGroups populated...
                mCardAdapter = new SubjectGroupAdapter(getActivity(), mGroups);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mCardAdapter);
            }
        });
        return mView;
    }

}