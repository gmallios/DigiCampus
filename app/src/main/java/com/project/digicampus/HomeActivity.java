package com.project.digicampus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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
import com.project.digicampus.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private TextView nav_user_name;
    private TextView nav_user_email;


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private DatabaseReference userDB;
    private UserModel mUser;
    private Gson gson = new Gson();
    private FirebaseUser currentUser;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                R.id.nav_announcements, R.id.nav_subjects)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        loadUserData();

//        // Load and setup dynamic navdrawer items
//        final Menu menu = navigationView.getMenu();
//        menu.add("Runtime");
//
//        final SubMenu subMenu = menu.addSubMenu("Μαθήματα");
//        subMenu.add("Μάθημα 1");




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
        MenuItem item = menu.getItem(0);
        MenuItem item2 = menu.getItem(1);
        SpannableString s = new SpannableString(getString(R.string.action_settings));
        SpannableString s2 = new SpannableString(getString(R.string.text_menu_action_logout));
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        s2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s2.length(), 0);
        item.setTitle(s);
        item2.setTitle(s2);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_action_settings){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if(item.getItemId() == R.id.menu_action_logout){
            FirebaseAuth.getInstance().signOut();
            Intent logOut = new Intent(this, MainActivity.class);
            logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logOut);
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
}