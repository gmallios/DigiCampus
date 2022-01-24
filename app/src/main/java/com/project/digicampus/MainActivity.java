package com.project.digicampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button login_btn;
    private Button signup_btn;
    private EditText input_email;
    private EditText input_password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setLoginTitleText();
        mAuth = FirebaseAuth.getInstance();
        login_btn = findViewById(R.id.login_signin_btn);
        input_email = findViewById(R.id.login_form_email);
        input_password = findViewById(R.id.login_form_password);
        signup_btn = findViewById(R.id.login_signup_btn);

        signup_btn.setOnClickListener(v -> {
            Intent openSignup = new Intent(MainActivity.this,SignupActivity.class);
            startActivity(openSignup);
        });

        login_btn.setOnClickListener(v -> {
            Log.d("LOGIN", input_email.getText().toString().trim());
            Log.d("LOGIN", input_password.getText().toString().trim());
            mAuth.signInWithEmailAndPassword(input_email.getText().toString().trim(), input_password.getText().toString().trim())
                    .addOnCompleteListener(MainActivity.this, task -> {
                        if(task.isSuccessful()){
                            // Login Success
                            FirebaseUser user = mAuth.getCurrentUser();
                            openHome();
                        } else {
                            // Login Fail

                        }
                    });
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Logged in go to next activity as signed in
            FirebaseUser user = mAuth.getCurrentUser();
            openHome();
        }
    }


    private void setLoginTitleText(){
        int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        SpannableString login_title_1 = new SpannableString(this.getResources().getString(R.string.login_title_1));
        SpannableString login_title_2 = new SpannableString(this.getResources().getString(R.string.login_title_2));
        TextView title = (TextView) findViewById(R.id.login_title_main);
        login_title_1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.dark_text)), 0, login_title_1.length(), flag);
        login_title_2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.login_text_digicampus)), 0, login_title_2.length(), flag);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(login_title_1);
        builder.append(" ");
        builder.append(login_title_2);
        title.setText(builder);
    }

    private void openHome(){
        // Construct intent
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}