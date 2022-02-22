package com.project.digicampus;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.project.digicampus.databinding.ActivityHomeBinding;
import com.project.digicampus.models.SubjectModel;
import com.project.digicampus.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    //TODO: - Search
    //      - LongClick
    //      - Notification

    private TextView nav_user_name;
    private TextView nav_user_email;


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private DatabaseReference userDB;
    private UserModel mUser;
    private final Gson gson = new Gson();
    private FirebaseUser currentUser;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSubjectsToUtils();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(settingsIntent);
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;

        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_announcements, R.id.nav_subjects, R.id.nav_calendar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        loadUserData();

    }

    @SuppressLint("SetTextI18n")
    private void loadUserData(){
        userDB = FirebaseDatabase.getInstance("https://digicampus-29612-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/users");
        userDB.child(currentUser.getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                mUser = gson.fromJson(String.valueOf(task.getResult().getValue()), UserModel.class);
                //Log.d("BIRTHDATE", mUser.getBirthDate());
                // Setup nav header contents
                View headerView = navigationView.getHeaderView(0);
                nav_user_name = headerView.findViewById(R.id.nav_drawer_user_name);
                nav_user_email = headerView.findViewById(R.id.nav_drawer_user_email);
                nav_user_name.setText(mUser.getFirstName() + " " + mUser.getLastName());
                nav_user_email.setText(mUser.getEmail());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        // Fix menu item color
        for(int i=0; i<menu.size();i++){
            MenuItem item = menu.getItem(i);
            SpannableString s = new SpannableString(item.getTitle());
            s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
            item.setTitle(s);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.menu_action_settings){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if(id == R.id.menu_action_logout){
            FirebaseAuth.getInstance().signOut();
            Intent logOut = new Intent(this, MainActivity.class);
            logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logOut);
            return true;
        } else if(id == R.id.show_example_notification){
            createNotificationChannel();
            showExampleNotification();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void showExampleNotification(){
        String SUBJECT_ID = "-MuC5rf2GOKcsEu2QqGj";
        String ANNOUNC_ID = "";
        String subjectName;
        Intent notifIntent = new Intent(this,SubjectViewActivity.class);
        notifIntent.putExtra("SUBJ_RTDB_ID", SUBJECT_ID);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0")
                .setSmallIcon(R.drawable.ic_baseline_announcement_24)
                .setContentTitle(String.format("Νέα ανακοίνωση στο μάθημα %s", Objects.requireNonNull(Utils.mSubjectIDMap.get(SUBJECT_ID)).getName()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void loadSubjectsToUtils(){
        DatabaseReference subjectsDB = Utils.getSubjectDBRef();

        subjectsDB.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                DataSnapshot dataObj = task.getResult();
                for (DataSnapshot rootSubjectSnapshot : dataObj.getChildren()) {
                    String key = rootSubjectSnapshot.getKey();
                    SubjectModel model = rootSubjectSnapshot.getValue(SubjectModel.class);
                    Utils.mSubjectIDMap.put(key, model);
                }
            }
        });
    }
}