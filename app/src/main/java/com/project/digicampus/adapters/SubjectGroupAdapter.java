package com.project.digicampus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.project.digicampus.R;
import com.project.digicampus.models.GroupModel;
import com.project.digicampus.models.SubjectModel;

import java.util.ArrayList;
import java.util.Objects;

public class SubjectGroupAdapter extends RecyclerView.Adapter<SubjectGroupAdapter.Viewholder> {
    private Context mContext;
    private ArrayList<GroupModel> groupModelArrayList;
    private static ClickListener clickListener;

    public SubjectGroupAdapter(Context context, ArrayList<GroupModel> arrayList){
        this.mContext = context;
        this.groupModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public SubjectGroupAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_card_layout, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SubjectGroupAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        GroupModel model = groupModelArrayList.get(position);
        holder.groupModel = model; // Pass whole model
        holder.groupNameView.setText(String.format("Ομάδα %d", position+1));
        if(model.getAssignedStudents().contains(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())){
            holder.groupSubedView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return groupModelArrayList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(SubjectGroupAdapter.ClickListener clickListener){
        SubjectGroupAdapter.clickListener = clickListener;
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView groupNameView;
        private ImageView groupSubedView;
        private GroupModel groupModel;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            groupNameView = itemView.findViewById(R.id.group_card_group_name);
            groupSubedView = itemView.findViewById(R.id.group_card_subscribed);
            groupSubedView.setVisibility(View.GONE);
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
