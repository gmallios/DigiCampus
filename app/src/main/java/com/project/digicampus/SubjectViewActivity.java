package com.project.digicampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.project.digicampus.home_ui.Announcements.AnnouncementsFragment;
import com.project.digicampus.models.SubjectModel;
import com.project.digicampus.subject_ui.SubjectAnnouncementFragment;
import com.project.digicampus.subject_ui.SubjectAssignmentsFragment;
import com.project.digicampus.subject_ui.SubjectAttendanceFragment;
import com.project.digicampus.subject_ui.SubjectGroupsFragment;

import java.util.Objects;

public class SubjectViewActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private SubjectModel mSubject;
    private String mSubjectID;
    private BottomNavigationView bottomNavigationView;

    SubjectAnnouncementFragment announcementsFragment = new SubjectAnnouncementFragment();
    SubjectGroupsFragment subjectGroupsFragment = new SubjectGroupsFragment();
    SubjectAssignmentsFragment subjectAssignmentsFragment = new SubjectAssignmentsFragment();
    SubjectAttendanceFragment subjectAttendanceFragment = new SubjectAttendanceFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();

        if(data == null)
            this.onBackPressed();  //Go back if no data

        mSubjectID = data != null ? data.getString("SUBJ_RTDB_ID") : null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view);

        //Load Subject from DB
        DatabaseReference ref = Utils.getSubjectDBRef();
        ref.child(mSubjectID).get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                //Failed loading -> go back
                Toast.makeText(this, "Υπήρξε κάποιο πρόβλημα", Toast.LENGTH_SHORT).show();
                this.onBackPressed();
            } else {
                DataSnapshot dataObj = task.getResult();
                mSubject = dataObj.getValue(SubjectModel.class);

            }
        });

        bottomNavigationView = findViewById(R.id.subjects_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.announcement_bottom_menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.announcement_bottom_menu) {
            replaceFragment(R.id.fragment_layout_subjectview, announcementsFragment);
            return true;
        } else if (itemId == R.id.groups_bottom_menu) {
            replaceFragment(R.id.fragment_layout_subjectview, subjectGroupsFragment);
            return true;
        } else if (itemId == R.id.assignments_bottom_menu) {
            replaceFragment(R.id.fragment_layout_subjectview, subjectAssignmentsFragment);
            return true;
        } else if (itemId == R.id.attendance_bottom_menu) {
            replaceFragment(R.id.fragment_layout_subjectview, subjectAttendanceFragment);
            return true;
        }
        return false;
    }

    private void replaceFragment(int viewID, Fragment newFragment){
        getSupportFragmentManager().beginTransaction().replace(viewID, newFragment).commit();
    }
}