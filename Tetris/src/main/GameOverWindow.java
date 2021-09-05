package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameOverWindow {

    public GameOverWindow(int stoneCount, int lineCount) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Game Over");
        stage.setMinWidth(400);

        Label stoneCountLabel = new Label();
        stoneCountLabel.setText("Stones: " + stoneCount);

        Label lineCountLabel = new Label();
        lineCountLabel.setText("Lines cleared: " + lineCount);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                stoneCountLabel,
                lineCountLabel,
                exitButton
        );
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

}
