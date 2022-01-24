package com.project.digicampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.digicampus.R;
import com.project.digicampus.models.AnnouncementModel;
import com.project.digicampus.models.SubjectModel;
import com.project.digicampus.models.UserModel;

import java.util.ArrayList;


public class AnnouncementCardAdapter extends RecyclerView.Adapter<AnnouncementCardAdapter.Viewholder> {

    private Context context;
    private ArrayList<AnnouncementModel> announcementModelArrayList;
    private static ClickListener clickListener;

    // Constructor
    public AnnouncementCardAdapter(Context context, ArrayList<AnnouncementModel> announcementModelArrayList) {
        this.context = context;
        this.announcementModelArrayList = announcementModelArrayList;
    }

    @NonNull
    @Override
    public AnnouncementCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_card_layout, parent, false);
        return new Viewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AnnouncementCardAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        AnnouncementModel model = announcementModelArrayList.get(position);
        holder.announcementModel = model; // Pass whole model
        holder.announcementTitleView.setText(model.getTitle());
        holder.announcementContentView.setText(model.getContent());
        DatabaseReference db = FirebaseDatabase.getInstance("https://digicampus-29612-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/subjects");
        db.child(model.getSubjectID()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                SubjectModel subjectModel = task.getResult().getValue(SubjectModel.class);
                assert subjectModel != null;
                holder.announcementSubjectName.setText(subjectModel.getName());
            }
        });

    }


    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return announcementModelArrayList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        AnnouncementCardAdapter.clickListener = clickListener;
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView announcementTitleView, announcementSubjectName, announcementContentView;
        private AnnouncementModel announcementModel;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            announcementContentView = itemView.findViewById(R.id.card_announcement_content);
            announcementSubjectName = itemView.findViewById(R.id.card_announcement_subject_name);
            announcementTitleView = itemView.findViewById(R.id.card_announcement_title);
        }

        public AnnouncementModel getAnnouncementModel(){
            return announcementModel;
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

    }
}

