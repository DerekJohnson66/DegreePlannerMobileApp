package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @ColumnInfo(name = "noteId")
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private String note;

    @ColumnInfo(name = "cId")
    private int cId;

    public Note(String note, int cId) {
        this.note = note;
        this.cId = cId;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNote() {
        return note;
    }

    public int getCId() {
        return cId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }
}
