package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import tetris.TetrisGame;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class TetrisController implements Initializable {

    @FXML private Canvas mainCanvas;
    @FXML private Canvas nextCanvas;
    @FXML private Label stoneCount;
    @FXML private Label lineCount;

    private static final int DRAW_DELAY_MILLIS = 250;
    private static final int GRIDWIDTH = 20;
    private static final int GRIDHEIGHT = 30;
    private static final int RECTANGLE_SIZE = 10;

    private TetrisGame tetrisGame;
    private Timer timer;

    private boolean gameOver = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(10, 10, 50, 50);
    }

    void startGame(Scene scene) {
        tetrisGame = new TetrisGame(
                GRIDWIDTH, GRIDHEIGHT, mainCanvas, nextCanvas,
                scene, RECTANGLE_SIZE, this
        );
        timer = new Timer();
        timer.schedule(tetrisGame, 0, DRAW_DELAY_MILLIS);
        update();
    }

    void stopGame() {
        timer.cancel();
    }

    public void update() {
        Platform.runLater(() -> {
            stoneCount.setText("Stones: " + tetrisGame.getStoneCount());
            lineCount.setText("Lines: " + tetrisGame.getClearedRows());
        });
    }

    public void gameOver() {
        // Avoid opening the game over window twice,
        // because it is opened in another thread.
        if (gameOver)
            return;

        gameOver = true;

        stopGame();
        Platform.runLater(() -> {
            int stoneCount = tetrisGame.getStoneCount();
            int lineCount = tetrisGame.getClearedRows();
            new GameOverWindow(stoneCount, lineCount);
        });
    }

}
