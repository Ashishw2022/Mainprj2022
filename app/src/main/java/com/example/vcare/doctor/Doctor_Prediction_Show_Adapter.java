package com.example.vcare.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.predictor.DiseasePrediction;

import java.util.ArrayList;

public class Doctor_Prediction_Show_Adapter extends RecyclerView.Adapter<Doctor_Prediction_Show_Adapter.ViewHolder> {

    private ArrayList<DiseasePrediction> diseasePrediction;

    public Doctor_Prediction_Show_Adapter(ArrayList<DiseasePrediction> diseasePrediction){
        this.diseasePrediction = diseasePrediction;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseasepred_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiseasePrediction p_class = diseasePrediction.get(position);
        holder.name.setText("Name: "+p_class.getPatientName());
        holder.symp.setText("\n Symptom: "+p_class.getSymptoms());
        holder.email.setText("Email: "+p_class.getPatientEmail());
        holder.approvalId.setText("Approval Id: "+p_class.getPid());
        holder.predictedDisease.setText("Predicted Disease: "+p_class.getPredictedDisease());
    }

    @Override
    public int getItemCount() {
        return diseasePrediction.size();
    }

    public void filterList(ArrayList<DiseasePrediction> filterdNames) {
        this.diseasePrediction=filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, symp, email, approvalId, predictedDisease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            symp = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            approvalId = (TextView) itemView.findViewById(R.id.date);
            predictedDisease = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
