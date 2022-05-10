package com.example.shedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CoupleViewModel extends AndroidViewModel {
    public LiveData<List<Couple>> livedata;
    public ScheduleDB db;
    public CoupleDao dao;
    public CoupleViewModel(Application application){
        super(application);
        db = ScheduleDB.getDataBase(application);
        dao = db.coupleDao();
        livedata = dao.getAll();

    }
    LiveData<List<Couple>> getLivedata(){return livedata;}
}
