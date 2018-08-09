package com.kejarkoding.crudsqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kejarkoding.crudsqlite.MainActivity;
import com.kejarkoding.crudsqlite.R;
import com.kejarkoding.crudsqlite.model.Usermodel;

import java.util.List;

public class UserModelAdapter extends RecyclerView.Adapter<UserModelAdapter.ViewHolder> {
    private Context context;
    private List<Usermodel>usermodels;

    public UserModelAdapter(Context context,List<Usermodel>usermodelList){
        this.context = context;
        this.usermodels = usermodelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
              holder.nama.setText(usermodels.get(position).getName());
              holder.tinggi.setText(usermodels.get(position).getTall());
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      int id = usermodels.get(position).getId();
                      String ids = String.valueOf(id);
                      String nama = usermodels.get(position).getName();
                      String tall = usermodels.get(position).getTall();
                      Toast.makeText(context, usermodels.get(position).getId()+" ", Toast.LENGTH_SHORT).show();
                      Intent i = new Intent(context,MainActivity.class);
                      i.putExtra("id",ids);
                      i.putExtra("nama",nama);
                      i.putExtra("tinggi",tall);
                      v.getContext().startActivity(i);
                  }

              });

    }

    @Override
    public int getItemCount() {
        return usermodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama ,tinggi;
        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView)itemView.findViewById(R.id.nm);
            tinggi =(TextView)itemView.findViewById(R.id.tinggi);
        }
    }
}