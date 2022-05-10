package com.example.shedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class CoupleViewHolder extends RecyclerView.ViewHolder {
    final ImageView type;
    final TextView name, audithorium, date, time, building;
    private CoupleViewHolder(View view) {
        super(view);
        type = view.findViewById(R.id.type);
        name = view.findViewById(R.id.name);
        audithorium = view.findViewById(R.id.audithorium);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        building = view.findViewById(R.id.building);

    }

    static CoupleViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new CoupleViewHolder(view);
    }
}
