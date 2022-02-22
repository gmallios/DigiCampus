package com.project.digicampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.digicampus.models.UserModel;
import com.project.digicampus.models.UserType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class SignupActivity extends AppCompatActivity {

    private EditText dateText;
    private EditText nameText;
    private EditText surnameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText passwordConfirmText;
    private RadioGroup accountTypeGroup;
    private Button signup_btn;

    private DatabaseReference userDBRef;

    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.signup_title));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
           // actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatePickerDialog.OnDateSetListener datePickerListener = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDateLabel();
        };

        dateText = findViewById(R.id.signup_form_age_input);
        dateText.setOnClickListener(v -> new DatePickerDialog(SignupActivity.this,datePickerListener,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        nameText = findViewById(R.id.signup_form_name_input);
        surnameText = findViewById(R.id.signup_form_surname_input);
        emailText = findViewById(R.id.signup_form_email_input);
        accountTypeGroup = findViewById(R.id.signup_form_accountype);
        signup_btn = findViewById(R.id.signup_form_button);
        passwordText = findViewById(R.id.signup_form_password_input);
        passwordConfirmText = findViewById(R.id.signup_form_password_confirmation_input);

        ArrayList<EditText> viewsToCheck =new ArrayList<>();
        viewsToCheck.add(nameText);
        viewsToCheck.add(surnameText);
        viewsToCheck.add(emailText);
        viewsToCheck.add(dateText);
        viewsToCheck.add(passwordText);
        viewsToCheck.add(passwordConfirmText);

        signup_btn.setOnClickListener(v -> {
            boolean formOk = true;
            String email = Utils.getEditTextString(emailText);
            String password = Utils.getEditTextString(passwordText);

            for (EditText field: viewsToCheck) {
                if(Utils.isEditTextEmpty(field))
                    formOk = false;
                    field.setError("Υποχρεωτικό πεδίο");
            }




            if(!password.equals(Utils.getEditTextString(passwordConfirmText)))
                formOk = false;

            if(formOk){
                String firstName = Utils.getEditTextString(nameText);
                String lastName = Utils.getEditTextString(surnameText);
                String date = Utils.getEditTextString(dateText);
                Integer userType = 0;

                if(accountTypeGroup.getCheckedRadioButtonId() == R.id.signup_form_radiogroup_professor)
                    userType = 1; // Prof


                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                userDBRef = Utils.getUserDBRef();
                Integer finalUserType = userType;
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, task->{
                    if(task.isSuccessful()){
                        // Create user in realtime db
                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                        UserModel newUser = new UserModel(uid, email, firstName, lastName, finalUserType, date);
                        userDBRef.child(uid).setValue(newUser).addOnCompleteListener(SignupActivity.this, createUserDBTask -> {
                            if(createUserDBTask.isSuccessful()){
                                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Υπήρξε κάποιο πρόβλημα με την εγγραφή", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Υπήρξε κάποιο πρόβλημα με την εγγραφή", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private void updateDateLabel(){
        if(dateText.getError() != null){
            dateText.setError(null);
        }
        String myFormat="dd-MM-yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        dateText.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            onBackPressed();
        }
        return true;
    }



}