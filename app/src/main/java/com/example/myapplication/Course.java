package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "course_table", foreignKeys = @ForeignKey(entity = Term.class,
parentColumns = "id",
childColumns =  "termId",
onDelete = 5))
public class Course {

    @ColumnInfo(name = "courseId")
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    @ColumnInfo(name = "termId")
    private Integer termId;

    private String title;
    private int startDateDay;
    private int startDateMonth;
    private int startDateYear;
    private int endDateDay;
    private int endDateMonth;
    private int endDateYear;
    private String status;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String note;

    public Course(String title, int startDateDay, int startDateMonth, int startDateYear, int endDateDay, int endDateMonth, int endDateYear, String status, String mentorName, String mentorPhone, String mentorEmail, Integer termId) {
        this.title = title;
        this.startDateDay = startDateDay;
        this.startDateMonth = startDateMonth;
        this.startDateYear = startDateYear;
        this.endDateDay = endDateDay;
        this.endDateMonth = endDateMonth;
        this.endDateYear = endDateYear;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termId = termId;
        this.note = note;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDateDay(int startDateDay) {
        this.startDateDay = startDateDay;
    }

    public void setStartDateMonth(int startDateMonth) {
        this.startDateMonth = startDateMonth;
    }

    public void setStartDateYear(int startDateYear) {
        this.startDateYear = startDateYear;
    }

    public void setEndDateDay(int endDateDay) {
        this.endDateDay = endDateDay;
    }

    public void setEndDateMonth(int endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public void setEndDateYear(int endDateYear) {
        this.endDateYear = endDateYear;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public int getStartDateDay() {
        return startDateDay;
    }

    public int getStartDateMonth() {
        return startDateMonth;
    }

    public int getStartDateYear() {
        return startDateYear;
    }

    public int getEndDateDay() {
        return endDateDay;
    }

    public int getEndDateMonth() {
        return endDateMonth;
    }

    public int getEndDateYear() {
        return endDateYear;
    }

    public String getStatus() {
        return status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {

        this.courseId = id;
    }


}
