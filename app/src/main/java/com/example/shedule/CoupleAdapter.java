package com.example.shedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoupleAdapter extends RecyclerView.Adapter<CoupleAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Couple> couples;

    CoupleAdapter(Context context, List<Couple> couples) {
        this.couples = couples;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    @NonNull
    public CoupleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CoupleAdapter.ViewHolder holder, int position) {
        Couple couple = couples.get(position);
        holder.name.setText(couple.getDiscipline());
        holder.audithorium.setText(couple.getAuditorium());
        holder.date.setText(couple.getDate().toString());
        holder.time.setText(new String(couple.getBeginLesson()+"-"+couple.getEndLesson()));
        holder.building.setText(couple.getBuilding());
        String type = couple.getKindOfWork();
        switch (type){
            case "Семинар":
                holder.type.setImageResource(R.drawable.seminar);
                break;
            case "Лекция":
                holder.type.setImageResource(R.drawable.lecture);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return couples.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView type;
        final TextView name, audithorium, date, time, building;
        ViewHolder(View view){
            super(view);
            type = view.findViewById(R.id.type);
            name = view.findViewById(R.id.name);
            audithorium = view.findViewById(R.id.audithorium);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            building = view.findViewById(R.id.building);
        }
    }
}
