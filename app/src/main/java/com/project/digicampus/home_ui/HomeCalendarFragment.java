package com.project.digicampus.home_ui;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.project.digicampus.R;
import com.project.digicampus.Utils;
import com.project.digicampus.models.GroupModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class HomeCalendarFragment extends Fragment {

    public HomeCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home_calendar, container, false);
        final CompactCalendarView compactCalendarView = mView.findViewById(R.id.calendar_view);
        final Toolbar calendarToolbar = mView.findViewById(R.id.calendar_toolbar);

        addEventsFromDB(compactCalendarView);
        calendarToolbar.setTitleTextColor(Color.WHITE);
        Calendar cal = Calendar.getInstance();
        calendarToolbar.setTitle(new SimpleDateFormat("MMMM").format(cal.getTime()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events "+ events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
                calendarToolbar.setTitle(new SimpleDateFormat("MMMM").format(cal.getTime()));
            }
        });

        return mView;
    }

    public void addEventsFromDB(CompactCalendarView compactCalendarView){
        ArrayList<Event> eventsList = new ArrayList<>();
        DatabaseReference groupsDB = Utils.getGroupsDBRef();
        groupsDB.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot rootData = task.getResult();
                for (DataSnapshot rootGroup : rootData.getChildren()) {
                    GroupModel model = rootGroup.getValue(GroupModel.class);
                    if (model != null && model.getAssignedStudents().contains(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())) {
                        model.getLectureDays().forEach(day -> {
                            eventsList.add(new Event(Color.GREEN, day, rootGroup.getKey()));
                        });
                    }
                }
                eventsList.forEach(compactCalendarView::addEvent);
                Toast.makeText(getContext(), "Added " + eventsList.size(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}