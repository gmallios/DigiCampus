package com.project.digicampus;

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

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button login_btn;
    private Button signup_btn;
    private EditText input_email;
    private EditText input_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setLoginTitleText();
        login_btn = findViewById(R.id.login_signin_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
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
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}