package br.cesul.musicbattle.model;

import org.bson.types.ObjectId;

public class TrackBuilder {
    private Track track;

    public TrackBuilder() {
        track = new Track();
    }

    public TrackBuilder withId(ObjectId id) {
        track.setId(id);
        return this;
    }

    public TrackBuilder withTitle(String title) {
        track.setTitle(title);
        return this;
    }

    public TrackBuilder withArtist(String artist) {
        track.setArtist(artist);
        return this;
    }

    public TrackBuilder withVotes(int votes) {
        track.setVotes(votes);
        return this;
    }

    public Track build() {
        return track;
    }

    public static TrackBuilder create() {
        return new TrackBuilder();
    }
}
