package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments_table", foreignKeys = @ForeignKey( entity = Course.class,
parentColumns = "courseId",
childColumns = "cId",
onDelete = 5))
public class Assessment {

    @ColumnInfo(name = "assessmentId")
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    @ColumnInfo(name = "cId")
    private int courseId;

    private String title;
    private String type;
    private int dueDateMonth;
    private int dueDateDay;
    private int dueDateYear;

//    public Assessment(int cId) {
//        this.cId = cId;
//    }
//
//    @Ignore
    public Assessment(int courseId,  String title, String type, int dueDateMonth, int dueDateDay, int dueDateYear) {
//        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.dueDateMonth = dueDateMonth;
        this.dueDateDay = dueDateDay;
        this.dueDateYear = dueDateYear;
    }

    public void setAssessmentId(int id) {
        this.assessmentId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDueDateMonth(int dueDateMonth) {
        this.dueDateMonth = dueDateMonth;
    }

    public void setDueDateDay(int dueDateDay) {
        this.dueDateDay = dueDateDay;
    }

    public void setDueDateYear(int dueDateYear) {
        this.dueDateYear = dueDateYear;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getDueDateMonth() {
        return dueDateMonth;
    }

    public int getDueDateDay() {
        return dueDateDay;
    }

    public int getDueDateYear() {
        return dueDateYear;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
