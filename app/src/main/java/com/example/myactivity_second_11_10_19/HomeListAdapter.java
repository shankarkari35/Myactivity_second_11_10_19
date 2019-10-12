package com.example.myactivity_second_11_10_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.HashMap;
class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<HashMap<String, String>> mArray;

    public HomeListAdapter(Context cxt, ArrayList<HashMap<String, String>> mArray){
        this.mContext = cxt;
        this.mArray = mArray;
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textno, txtstatus,servecename,textdate;
        public ViewHolder(View v){
            super(v);
            textno = (TextView) v.findViewById(R.id.noeedittext);
             servecename= (TextView) v.findViewById(R.id.servicename);
            textdate = (TextView) v.findViewById(R.id.txtBdate);
            txtstatus = (TextView) v.findViewById(R.id.txtstatus);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String,String> map = mArray.get(position);
        holder.textno.setText(map.get("bno"));
        holder.servecename.setText(map.get("service_name"));
        holder.textdate.setText(map.get("booking_date"));
        holder.txtstatus.setText(map.get("bokking_status"));

        // MainActivity.description1=map.get("detail");
    }

    @Override
    public int getItemCount()
    {
        return mArray.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_activity, parent, false);
        ViewHolder vh = new ViewHolder(mRowView);

        return vh;
    }
}
