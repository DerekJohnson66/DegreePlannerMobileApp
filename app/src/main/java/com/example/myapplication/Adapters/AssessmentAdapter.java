package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Assessment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessmentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(assessmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentHolder holder, int position) {
        Assessment currentAssessment = assessments.get(position);
        holder.textViewTitle.setText(currentAssessment.getTitle());
        holder.textViewDueDate.setText(String.valueOf(currentAssessment.getDueDateMonth() + "/" + currentAssessment.getDueDateDay()
                + "/" + currentAssessment.getDueDateYear()));

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments){
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    public Assessment getAssessmentAt(int position){
        return assessments.get(position);
    }

    class AssessmentHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDueDate;


        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_assessment_title);
            textViewDueDate = itemView.findViewById(R.id.text_view_assessment_due_date);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(assessments.get(position));
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(assessments.get(position));
                    }
                }

            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Assessment assessments);
    }

    public void setOnItemClickListener(AssessmentAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
