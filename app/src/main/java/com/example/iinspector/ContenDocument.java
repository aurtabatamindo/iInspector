package com.example.iinspector;

import com.google.firebase.database.PropertyName;

import java.util.List;

public class ContenDocument {

    @PropertyName("contents")
    private List<String> contents;

    public ContenDocument() {
        // Must have a public no-argument constructor
    }

    // Initialize all fields of a dungeon
    public ContenDocument(List<String> contents) {
        this.contents = contents;
    }

    @PropertyName("contents")
    public List<String> getDungeonGroup() {
        return contents;
    }

    @PropertyName("contents")
    public void setDungeonGroup(List<String> contents) {
        this.contents = contents;
    }
}