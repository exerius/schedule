package com.example.shedule;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class CoupleAdapter extends ListAdapter<Couple, CoupleViewHolder> {
   public List<Couple> couples;

    CoupleAdapter(@NonNull DiffUtil.ItemCallback<Couple> diffCallback, List<Couple> couples) {
        super(diffCallback);
        this.couples = couples;
    }
    @Override
    @NonNull
    public CoupleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return CoupleViewHolder.create(parent);
    }
    @Override
    public void onBindViewHolder(CoupleViewHolder holder, int position) {
        Couple couple = couples.get(position);
        holder.name.setText(couple.getDiscipline());
        holder.audithorium.setText(couple.getAuditorium());

        holder.date.setText(couple.getDate().toString());
        holder.time.setText(
                new String(couple.getBeginLesson()+"-"+couple.getEndLesson()));
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

    static class CoupleDiff extends DiffUtil.ItemCallback<Couple> {

        @Override
        public boolean areItemsTheSame(@NonNull Couple oldItem, @NonNull Couple newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Couple oldItem, @NonNull Couple newItem) {
            return oldItem.getLessonOid().equals(newItem.getLessonOid());
        }
    }
    @Override
    public int getItemCount() {
        return couples.size();
    }
}
