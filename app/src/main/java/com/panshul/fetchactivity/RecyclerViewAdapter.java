package com.panshul.fetchactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<DisplayObject> list;
    Context context;

    public RecyclerViewAdapter(List<DisplayObject> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_items,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder (RecyclerViewAdapter.MyViewHolder holder, int position) {
        DisplayObject object = list.get(position);

        holder.listIdTextView.setText(String.valueOf(object.getListId()));

        if(object.id.length() > 100){
            holder.idTextView.setText(object.id.substring(0,99)+"...");
        } else {
            holder.idTextView.setText(object.id);
        }
        if(object.name.length() > 100){
            holder.namesTextView.setText(object.name.substring(0,99)+"...");
        } else {
            holder.namesTextView.setText(object.name);
        }

        holder.idTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RecyclerView", String.valueOf(holder.idTextView.getText().length()));
                if(holder.idTextView.getText().length() > 103){
                    holder.idTextView.setText(list.get(position).id.substring(0,99)+"...");
                } else {
                    holder.idTextView.setText(list.get(position).id);
                }
            }
        });

        holder.namesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.namesTextView.getText().length() > 103){
                    holder.namesTextView.setText(list.get(position).name.substring(0,99)+"...");
                } else {
                    holder.namesTextView.setText(list.get(position).name);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listIdTextView, idTextView, namesTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            listIdTextView = itemView.findViewById(R.id.listIdTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
            namesTextView = itemView.findViewById(R.id.namesTextView);
        }
    }
}
