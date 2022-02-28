package com.project.digicampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digicampus.R;
import com.project.digicampus.models.AnnouncementModel;
import com.project.digicampus.models.SubjectModel;

import java.util.ArrayList;

public class SubjectCardAdapter extends RecyclerView.Adapter<SubjectCardAdapter.Viewholder> implements Filterable {

    private Context context;
    private ArrayList<SubjectModel> subjectModelArrayList;
    private ArrayList<SubjectModel> subjectModelArrayListFiltered;
    private static ClickListener clickListener;
    private ItemFilter mFilter = new ItemFilter();

    // Constructor
    public SubjectCardAdapter(Context context, ArrayList<SubjectModel> subjectModelArrayList) {
        this.context = context;
        this.subjectModelArrayList = subjectModelArrayList;
        this.subjectModelArrayListFiltered = subjectModelArrayList;
    }

    @NonNull
    @Override
    public SubjectCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects_card_layout, parent, false);
        return new Viewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SubjectCardAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        SubjectModel model = subjectModelArrayListFiltered.get(position);
        holder.subjectModel = model; // Pass whole model
        holder.subjectNameTextView.setText(model.getName());
        holder.subjectInfoTextView.setText(model.getDescription());
        holder.subjectSubscribedView.setVisibility(View.INVISIBLE); //TODO: Display checkmark on subscribed

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return subjectModelArrayListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        SubjectCardAdapter.clickListener = clickListener;
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView subjectNameTextView, subjectInfoTextView;
        private AppCompatImageView subjectSubscribedView;
        private SubjectModel subjectModel;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            subjectNameTextView = itemView.findViewById(R.id.card_subject_name);
            subjectInfoTextView = itemView.findViewById(R.id.card_subject_info);
            subjectSubscribedView = itemView.findViewById(R.id.card_subject_subscribed);
        }

        public SubjectModel getSubjectModel(){
            return subjectModel;
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

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<SubjectModel> list = subjectModelArrayList;

            int count = list.size();
            final ArrayList<SubjectModel> nlist = new ArrayList<>();

            SubjectModel filterableItem;

            for (int i = 0; i < count; i++) {
                filterableItem = list.get(i);
                if (filterableItem.getName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableItem);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            subjectModelArrayListFiltered = (ArrayList<SubjectModel>) results.values;
            notifyDataSetChanged();
        }
    }
}

