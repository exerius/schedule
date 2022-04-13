package com.example.shedule;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Couple {
    @NonNull
    @PrimaryKey
    private String lessonOid;
    private String discipline, auditorium, lecturer, building, kindOfWork, beginLesson, endLesson, date;
    public Couple(String discipline, String auditorium, String lecturer, String building, String kindOfWork, String beginLesson, String endLesson, String date, @NonNull String lessonOid){
        this.discipline = discipline;
        this.auditorium = auditorium;
        this.lecturer = lecturer;
        this.date = date;
        this.building = building;
        this.kindOfWork = kindOfWork;
        this.beginLesson = beginLesson;
        this.endLesson = endLesson;
        this.lessonOid = lessonOid;
    }
    public String getDiscipline(){
        return this.discipline;
    }

    public String getAuditorium(){
        return this.auditorium;
    }
    public String getLecturer(){
        return this.lecturer;
    }
    public String getBuilding(){
        return this.building;
    }
    public String getKindOfWork(){
        return this.kindOfWork;
    }
    public String getDate(){
        return this.date;
    }
    public void setDiscipline(String discipline){
        this.discipline = discipline;
    }
    public void setAuditorium(String auditorium){
        this.auditorium = auditorium;
    }
    public void setLecturer(String lecturer){
        this.lecturer = lecturer;
    }
    public void setBuilding(String building){
        this.building = building;
    }
    public void setKindOfWork(String kindOfWork){
        this.kindOfWork = kindOfWork;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getBeginLesson(){
        return this.beginLesson;
    }
    public String getEndLesson(){
        return this.endLesson;
    }
    public void setBeginLesson(String beginLesson){
        this.beginLesson = beginLesson;
    }
    public void setEndLesson(String endLesson){
        this.endLesson = endLesson;
    }
    public void setLessonOid(String lessonOid) {this.lessonOid = lessonOid;}
    @NonNull
    public String getLessonOid() {return this.lessonOid;}

}
