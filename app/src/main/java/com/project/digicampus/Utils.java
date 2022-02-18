package com.project.digicampus;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Utils {
    static boolean isEditTextEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    static String getEditTextString(EditText e){
        return e.getText().toString().trim();
    }


   public static FirebaseDatabase getDBInstance(){
        return FirebaseDatabase.getInstance("https://digicampus-29612-default-rtdb.europe-west1.firebasedatabase.app/");
   }

   public static DatabaseReference getGroupsDBRef(){ return getDBInstance().getReference("/groups"); }
   public static DatabaseReference getUserDBRef(){ return getDBInstance().getReference("/users"); }
   public static DatabaseReference getSubjectDBRef() { return getDBInstance().getReference("/subjects"); }
   public static DatabaseReference getAnnouncementDBRef() { return getDBInstance().getReference("/announcements"); }
}
