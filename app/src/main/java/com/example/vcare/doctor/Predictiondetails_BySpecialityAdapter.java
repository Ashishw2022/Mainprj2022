package com.example.vcare.doctor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.admin.Admin_Doctor_Details;
import com.example.vcare.predictor.DiseasePrediction;

import java.util.ArrayList;
import java.util.List;

public class Predictiondetails_BySpecialityAdapter extends RecyclerView.Adapter<Predictiondetails_BySpecialityAdapter.ViewHolder> {

    private List<DiseasePrediction> listData;
    private List<String> emaildata;
    private Context mContext;
    private String name;
    private String email;
    private String symptoms;

    public Predictiondetails_BySpecialityAdapter(List<DiseasePrediction> listData, List<String> emaildata, Context mContext) {
        this.listData = listData;
        this.emaildata = emaildata;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.diseasepred_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiseasePrediction d = listData.get(viewHolder.getAdapterPosition());
                name = d.getPatientName();
                email = emaildata.get(viewHolder.getAdapterPosition());
                symptoms = d.getSymptoms().replace("[","").replace("]","");
                Intent intent=new Intent(view.getContext(), predictiondetails.class);
                intent.putExtra("Patient Name",name);
                intent.putExtra("Email ID",email);
                intent.putExtra("Symptoms",symptoms);
                intent.putExtra("ApprovalId",d.getPid());
                intent.putExtra("speciality",d.getDiscategory());
                intent.putExtra("age",d.getAge());
              //  intent.putExtra("gender",d.getGender());
                intent.putExtra("PredictedDisease",d.getPredictedDisease());
                view.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    public void filterList(ArrayList<Doctors_Profile> filterdNames) {
       // this.listData = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiseasePrediction ld=listData.get(position);

        holder.name.setText(ld.getPatientName());
        holder.emailid.setText(emaildata.get(position));
        holder.sym.setText(ld.getSymptoms().replace("[","").replace("]",""));
        holder.predictedDis.setText(ld.getPredictedDisease());
        holder.apid.setText(ld.getPid());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,emailid,sym,predictedDis,apid;
        ImageView doc_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            emailid=(TextView) itemView.findViewById(R.id.email);
            sym=(TextView) itemView.findViewById(R.id.sym);
            predictedDis=(TextView) itemView.findViewById(R.id.predicte_Disease);
            apid=(TextView) itemView.findViewById(R.id.appid);
        }
    }
}
