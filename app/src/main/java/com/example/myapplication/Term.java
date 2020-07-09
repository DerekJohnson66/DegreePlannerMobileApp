package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class Term {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Integer termId;

    private String title;
    private int startDateDay;
    private int startDateMonth;
    private int startDateYear;
    private int endDateDay;
    private int endDateMonth;
    private int endDateYear;

    public Term(String title, int startDateDay, int startDateMonth, int startDateYear, int endDateDay, int endDateMonth, int endDateYear) {
        this.title = title;
        this.startDateDay = startDateDay;
        this.startDateMonth = startDateMonth;
        this.startDateYear = startDateYear;
        this.endDateDay = endDateDay;
        this.endDateMonth = endDateMonth;
        this.endDateYear = endDateYear;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public int getId() {
        return termId;
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

    public void setId(int id) {

        this.termId = id;
    }
}
