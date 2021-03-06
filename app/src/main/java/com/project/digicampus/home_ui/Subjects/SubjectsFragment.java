package com.project.digicampus.home_ui.Subjects;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.project.digicampus.SubjectViewActivity;
import com.project.digicampus.Utils;
import com.project.digicampus.adapters.SubjectCardAdapter;
import com.project.digicampus.databinding.FragmentSubjectsBinding;
import com.project.digicampus.models.SubjectModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private FragmentSubjectsBinding binding;
    private RecyclerView mRecyclerView;
    private ArrayList<SubjectModel> mSubjects;
    private Map<SubjectModel, String> mSubjectsKV; // Maps subjectid to subject class
    private Gson gson;
    private ProgressBar mProgressBar;
    private SubjectCardAdapter subjectCardAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subjectsViewModel =
                new ViewModelProvider(this).get(SubjectsViewModel.class);

        binding = FragmentSubjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = binding.subjectsRecyclerview;
        mSubjectsKV = new HashMap<>();
        mSubjects = new ArrayList<>();
        DatabaseReference subjectsDB = Utils.getSubjectDBRef();

        subjectsDB.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                DataSnapshot dataObj = task.getResult();
                for(DataSnapshot rootSubjectSnapshot: dataObj.getChildren()){
                    String key = rootSubjectSnapshot.getKey();
                    SubjectModel model = rootSubjectSnapshot.getValue(SubjectModel.class);
                    mSubjects.add(model);
                    mSubjectsKV.put(model, key);
                }
                subjectCardAdapter = new SubjectCardAdapter(getActivity(), mSubjects);

                subjectCardAdapter.setOnItemClickListener(new SubjectCardAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.d("Subject", "clicked on item with id" + getSubjectIDbyPos(position));
                        openSubjectView(getSubjectIDbyPos(position));
                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(subjectCardAdapter);
            }
        });



//        final TextView textView = binding.textGallery;
//        subjectsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private String getSubjectIDbyPos(int pos){
        return mSubjectsKV.get(mSubjects.get(pos));
    }


    private void openSubjectView(String id) {
        // ID Refers to firebase RTDB id
        Intent toOpen = new Intent(requireActivity(), SubjectViewActivity.class);
        toOpen.putExtra("SUBJ_RTDB_ID", id);
        startActivity(toOpen);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public SubjectCardAdapter getAdapter(){
        return subjectCardAdapter;
    }
}