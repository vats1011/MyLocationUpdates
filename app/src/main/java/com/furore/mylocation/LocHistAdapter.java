package com.furore.mylocation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.furore.intern.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by diksha on 23/4/15.
 */
public class LocHistAdapter extends RecyclerView.Adapter<LocHistAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public List<LocPojo> data = Collections.emptyList();
    View view;

    Context con;


    public LocHistAdapter(Context context, List<LocPojo> data) {
        con = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.listelement, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocPojo current = data.get(position);

        try {

            holder.latitude.setText(current.latitude);
            holder.longitude.setText(current.longitude);
            holder.address.setText(current.address);
            holder.date.setText(current.date);
            holder.time.setText(current.time);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView latitude,longitude,address,date,time;



        public MyViewHolder(View itemView) {
            super(itemView);

            latitude = (TextView) itemView.findViewById(R.id.tv_latitude);
            longitude = (TextView) itemView.findViewById(R.id.tv_longitude);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            time=(TextView) itemView.findViewById(R.id.tv_time);

        }
    }
}