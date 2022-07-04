package com.example.vcare.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;

import java.util.ArrayList;

public class Doctor_Appointment_Show_Adapter extends RecyclerView.Adapter<Doctor_Appointment_Show_Adapter.ViewHolder> {

    private ArrayList<Appointment_details> appointments;

    public Doctor_Appointment_Show_Adapter(ArrayList<Appointment_details> appointments){
        this.appointments = appointments;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_payment_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment_details p_class = appointments.get(position);
        holder.name.setText("Token ID: "+p_class.getTransaction());
        holder.phone.setText("\n Name: "+p_class.getName() + "\n Phone No: " +p_class.getPhone());
        holder.email.setText("Booked for: "+p_class.getDname());
        holder.date.setText("Date: "+p_class.getDate());
        holder.time.setText("Time: "+p_class.getTime());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void filterList(ArrayList<Appointment_details> filterdNames) {
        this.appointments=filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, date, time, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
