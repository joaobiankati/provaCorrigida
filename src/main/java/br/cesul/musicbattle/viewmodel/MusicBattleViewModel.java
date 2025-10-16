package br.cesul.musicbattle.viewmodel;

import br.cesul.musicbattle.model.Track;
import br.cesul.musicbattle.model.TrackBuilder;
import br.cesul.musicbattle.repository.TrackRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;

public class MusicBattleViewModel {
    private final TrackRepository repository;
    private final ObservableList<Track> tracks;
    private final StringProperty filterText = new SimpleStringProperty("");
    private final StringProperty newTitle = new SimpleStringProperty("");
    private final StringProperty newArtist = new SimpleStringProperty("");
    private String currentRankingMode = "Por votos";

    public MusicBattleViewModel() {
        this.repository = new TrackRepository();
        this.tracks = FXCollections.observableArrayList();
        loadTracks();
        filterText.addListener((obs, oldVal, newVal) -> loadTracks());
    }

    public void loadTracks() {
        tracks.clear();
        tracks.addAll(repository.findByQuery(filterText.get()));
        applyRanking();
    }

    public void addTrack() {
        if (newTitle.get().isBlank() || newArtist.get().isBlank()) return;
        
        Track track = TrackBuilder.create()
            .withTitle(newTitle.get())
            .withArtist(newArtist.get())
            .withVotes(0)
            .build();

        repository.insert(track);
        loadTracks();
        newTitle.set("");
        newArtist.set("");
    }

    public void voteTrack(Track track) {
        if (track != null) {
            repository.incrementVote(track.getId());
            loadTracks();
        }
    }

    public void setRankingMode(String mode) {
        this.currentRankingMode = mode;
        applyRanking();
    }

    private void applyRanking() {
        Comparator<Track> comparator = switch (currentRankingMode) {
            case "Por votos" -> Comparator.<Track>comparingInt(Track::getVotes).reversed()
                .thenComparing(Track::getTitle)
                .thenComparing(Track::getArtist);
            
            case "Por título" -> Comparator.comparing(Track::getTitle)
                .thenComparing(Track::getArtist)
                .thenComparingInt(Track::getVotes).reversed();
            
            case "Por artista" -> Comparator.comparing(Track::getArtist)
                .thenComparing(Track::getTitle)
                .thenComparingInt(Track::getVotes).reversed();
            
            default -> Comparator.<Track>comparingInt(Track::getVotes).reversed();
        };

        FXCollections.sort(tracks, comparator);
    }

    public ObservableList<Track> getTracks() {
        return tracks;
    }

    public StringProperty filterTextProperty() {
        return filterText;
    }

    public StringProperty newTitleProperty() {
        return newTitle;
    }

    public StringProperty newArtistProperty() {
        return newArtist;
    }
}