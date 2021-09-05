package main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tetris.fxml"));
        Scene scene = new Scene(loader.load());

        TetrisController controller = loader.getController();
        controller.startGame(scene);

        primaryStage.setOnCloseRequest(event -> controller.stopGame());

        primaryStage.setTitle("Tetris by Raimund Krämer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
