package com.riopapa.dicbible.model;

public class Searched {
    public int bible;
    public int chapter;
    public int verse;
    public String text;

    public Searched(int bible, int chapter, int verse, String text) {
        this.bible = bible;
        this.chapter = chapter;
        this.verse = verse;
        this.text = text;
    }
}