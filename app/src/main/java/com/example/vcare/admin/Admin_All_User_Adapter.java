package com.example.vcare.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.patient.User_Profile;

import java.util.ArrayList;
import java.util.List;

public class Admin_All_User_Adapter extends RecyclerView.Adapter<Admin_All_User_Adapter.ViewHolder> {

    private List<User_Profile> listData;
    private List<String> emaildata;
    private Context mContext;
    private String name;
    private String email;
    private String speciality;

    public Admin_All_User_Adapter(List<User_Profile> listData, List<String> emaildata, Context mContext) {
        //this.listData = listData;
        this.emaildata = emaildata;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public void filterList(ArrayList<User_Profile> filterdNames) {
       // this.listData = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User_Profile ld=listData.get(position);

        holder.name.setText(ld.getName());
        holder.emailid.setText(emaildata.get(position));
//        holder.spec.setText(ld.getType());
//        if(ld.getDoc_pic()!=null){
//            Picasso.with(mContext).load(ld.getDoc_pic().getUrl()).into(holder.doc_image);}
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,emailid,spec;
        //ImageView doc_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nameTextView);
            emailid=(TextView) itemView.findViewById(R.id.emailTextView);
//            spec=(TextView) itemView.findViewById(R.id.specialityTextView);
//            doc_image=(ImageView) itemView.findViewById(R.id.imageView_doc);
        }
    }
}
