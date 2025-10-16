package br.cesul.musicbattle.model;

import org.bson.types.ObjectId;

public class Track {
    private ObjectId id;
    private String title;
    private String artist;
    private int votes;

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getVotes() { return votes; }
    public void setVotes(int votes) { this.votes = votes; }
}