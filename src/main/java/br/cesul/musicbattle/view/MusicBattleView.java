package br.cesul.musicbattle.view;

import br.cesul.musicbattle.model.Track;
import br.cesul.musicbattle.viewmodel.MusicBattleViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MusicBattleView {
    @FXML private TextField filterField;
    @FXML private TextField titleField;
    @FXML private TextField artistField;
    @FXML private Button addBtn;
    @FXML private Button voteBtn;
    @FXML private TableView<Track> table;
    @FXML private TableColumn<Track, String> colTitle;
    @FXML private TableColumn<Track, String> colArtist;
    @FXML private TableColumn<Track, Integer> colVotes;
    @FXML private ComboBox<String> rankingCombo;

    private MusicBattleViewModel viewModel;

    public void initialize() {
        viewModel = new MusicBattleViewModel();
        setupTable();
        setupBindings();
        setupEvents();
        setupRankingCombo();
    }

    private void setupTable() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        colVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));
        table.setItems(viewModel.getTracks());
    }

    private void setupBindings() {
        filterField.textProperty().bindBidirectional(viewModel.filterTextProperty());
        titleField.textProperty().bindBidirectional(viewModel.newTitleProperty());
        artistField.textProperty().bindBidirectional(viewModel.newArtistProperty());
    }

    private void setupEvents() {
        addBtn.setOnAction(e -> onAddTrack());
        voteBtn.setOnAction(e -> onVoteTrack());
        rankingCombo.setOnAction(e -> onRankingChanged());
    }

    private void setupRankingCombo() {
        rankingCombo.getItems().addAll(
            "Por votos",
            "Por título",
            "Por artista"
        );
        rankingCombo.getSelectionModel().selectFirst();
    }

    private void onAddTrack() {
        viewModel.addTrack();
        titleField.clear();
        artistField.clear();
        titleField.requestFocus();
    }

    private void onVoteTrack() {
        Track selectedTrack = table.getSelectionModel().getSelectedItem();
        if (selectedTrack != null) {
            viewModel.voteTrack(selectedTrack);
        }
    }

    private void onRankingChanged() {
        String selectedRanking = rankingCombo.getValue();
        if (selectedRanking != null) {
            viewModel.setRankingMode(selectedRanking);
        }
    }
}
