package com.project.digicampus.home_ui.Announcements;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.project.digicampus.Utils;
import com.project.digicampus.adapters.AnnouncementCardAdapter;
import com.project.digicampus.databinding.FragmentAnnouncementsBinding;
import com.project.digicampus.models.AnnouncementModel;

import java.util.ArrayList;

public class AnnouncementsFragment extends Fragment {

    private AnnouncementsViewModel announcementsViewModel;
    private FragmentAnnouncementsBinding binding;
    private RecyclerView mRecyclerView;
    private AnnouncementCardAdapter mCardAdapter;
    private ArrayList<AnnouncementModel> mAnnouncements;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        announcementsViewModel =
                new ViewModelProvider(this).get(AnnouncementsViewModel.class);
        mAnnouncements = new ArrayList<>();
        binding = FragmentAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRecyclerView = binding.announcementsRecyclerview;
        DatabaseReference announcementsDB = Utils.getAnnouncementDBRef();
        announcementsDB.get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        DataSnapshot dataObj = task.getResult();
                        for(DataSnapshot rootSubjectSnapshot: dataObj.getChildren()) {
                            AnnouncementModel model = rootSubjectSnapshot.getValue(AnnouncementModel.class);
                            mAnnouncements.add(model);
                            assert model != null;
                            Log.d("Announcements", "Received announcement " + model.getTitle());
                            mCardAdapter = new AnnouncementCardAdapter(getActivity(), mAnnouncements);
                            mCardAdapter.setOnItemClickListener(new AnnouncementCardAdapter.ClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Log.d("Anouncement", "clicked on item");
                                }

                                @Override
                                public void onItemLongClick(int position, View v) {

                                }
                            });
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setAdapter(mCardAdapter);
                        }
                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}