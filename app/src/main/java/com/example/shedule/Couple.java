package com.example.shedule;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Couple {
    private String discipline, auditorium, lecturer, building, kindOfWork, beginLesson, endLesson, date;
   // private LocalDate date;
    DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public Couple(String discipline, String audithorium, String lecturer, String building, String kindOfWork, String beginLesson, String endLesson, String date){
        this.discipline = discipline;
        this.auditorium = audithorium;
        this.lecturer = lecturer;
        this.date = date;
        this.building = building;
        this.kindOfWork = kindOfWork;
        this.beginLesson = beginLesson;
        this.endLesson = endLesson;
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

}
