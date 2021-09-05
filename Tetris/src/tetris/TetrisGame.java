package tetris;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.TetrisController;

import java.util.Random;
import java.util.TimerTask;

public class TetrisGame extends TimerTask {
    private int gridWidth;
    private int gridHeight;
    private Canvas mainCanvas;
    private Canvas nextCanvas;
    private int rectSize;
    private TetrisRectangle[][] deadStones;
    private static final Random RANDOM = new Random();

    private int clearedRows = 0;
    private int stoneCount = 1;
    private TetrisStone currentStone;
    private TetrisStone nextStone = null;

    private TetrisController controller;

    public TetrisGame(int gridWidth, int gridHeight, Canvas mainCanvas,
                      Canvas nextCanvas, Scene scene, int rectSize, TetrisController controller) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.mainCanvas = mainCanvas;
        this.nextCanvas = nextCanvas;
        this.rectSize = rectSize;
        this.controller = controller;

        this.deadStones = new TetrisRectangle[gridWidth][gridHeight];

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.S) {
                currentStone.moveDown();
                checkCollision();
            }

            if (event.getCode() == KeyCode.A && currentStone.canMoveLeft(0, deadStones)) {
                currentStone.moveLeft();
            }

            if (event.getCode() == KeyCode.D && currentStone.canMoveRight(gridWidth - 1, deadStones)) {
                currentStone.moveRight();
            }

            if (event.getCode() == KeyCode.Q) {
                currentStone.turnLeft();
            }

            if (event.getCode() == KeyCode.E) {
                currentStone.turnRight();
            }

            draw();
        });

        currentStone = chooseRandomTetrisstone();
        nextStone = chooseRandomTetrisstone();
    }

    @Override
    public void run() {
        currentStone.moveDown();
        checkCollision();
        draw();
        clearFullRows();
    }

    public int getClearedRows() {
        return clearedRows;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    private void checkCollision() {
        outerloop:
        for (int[] posCurrent : currentStone.getRectanglePositions4x2()) {
            if (posCurrent[1] > gridHeight - 1) {
                currentStone.moveUp();
                killCurrentStone();
                break;
            }
            for (int x = 0; x < gridWidth; x++) {
                for (int y = 0; y < gridHeight; y++) {
                    if (deadStones[x][y] != null) {
                        if (posCurrent[0] == x && posCurrent[1] == y) {
                            currentStone.moveUp();
                            killCurrentStone();
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

    private void killCurrentStone() {
        addDeadStones(currentStone);
        currentStone = nextStone;
        nextStone = chooseRandomTetrisstone();
        stoneCount += 1;
        controller.update();
        checkGameOver();
    }

    private void addDeadStones(TetrisStone tetrisStone) {
        for (int[] pos : currentStone.getRectanglePositions4x2()) {
            deadStones[pos[0]][pos[1]] = new TetrisRectangle(tetrisStone.getColor());
        }
    }

    private void clearFullRows() {
        // Go over columns from bottom to top.
        columns:
        for (int row = gridHeight - 1; row >= 0; row--) {
            // Check if column is completely full.
            for (int column = 0; column < gridWidth; column++) {
                if (deadStones[column][row] == null) {
                    continue columns;
                }
            }
            // If it is, clear the column.
            for (int column = 0; column < gridWidth; column++) {
                deadStones[column][row] = null;
            }
            clearedRows += 1;
            controller.update();
            gravitateDeadStones(row);
        }
    }

    private void checkGameOver() {
        for (int x = 0; x < gridWidth; x++) {
            if (deadStones[x][5] != null) {
                controller.gameOver();
            }
        }
    }

    private void gravitateDeadStones(int clearedRow) {
        // Move all dead stones above the given row one down.
        for (int row = clearedRow - 1; row >= 0; row--) {
            for (int column = 0; column < gridWidth; column++) {
                if (deadStones[column][row] != null) {
                    TetrisRectangle current = deadStones[column][row];
                    deadStones[column][row] = null;
                    deadStones[column][row + 1] = current;
                }
            }
        }
    }

    private TetrisStone chooseRandomTetrisstone() {
        int rand = RANDOM.nextInt(7);

        int x = gridWidth / 2;
        int y = 5;
        int rotation = 0;

        switch (rand) {
            case 0:
                return new TetrisStoneI(x, y, rotation);
            case 1:
                return new TetrisStoneT(x, y, rotation);
            case 2:
                return new TetrisStoneL(x, y, rotation);
            case 3:
                return new TetrisStoneJ(x, y, rotation);
            case 4:
                return new TetrisStoneInvertedZ(x, y, rotation);
            case 5:
                return new TetrisStoneZ(x, y, rotation);
            case 6:
                return new TetrisStoneSquare(x, y, rotation);
            default:
                throw new IllegalStateException();
        }
    }

    private void draw() {
        clearCanvas();
        drawGame();
        drawNext();
    }

    private void drawGame() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (deadStones[x][y] != null) {
                    drawRectangle(mainCanvas, x, y, deadStones[x][y].getPaint(), Color.BLACK, 1);
                }
            }
        }

        for (int[] pos : currentStone.getRectanglePositions4x2()) {
            drawRectangle(mainCanvas, pos[0], pos[1], currentStone.getColor(), Color.BLACK, 1);
        }
    }

    private void drawNext() {
        GraphicsContext gc = nextCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, nextCanvas.getWidth(), nextCanvas.getHeight());

        for (int[] pos : nextStone.getRectanglePositions4x2()) {
            int x = pos[0] - gridWidth / 2 + 2;
            int y = pos[1] - 3;
            drawRectangle(nextCanvas, x, y, nextStone.getColor(), Color.BLACK, 1);
        }
    }

    private void clearCanvas() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc = nextCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    private void drawRectangle(Canvas canvas, int posX, int posY, Paint paint, Paint borderPaint, int borderWidth) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(borderPaint);
        gc.fillRect(
                posX * rectSize, posY * rectSize,
                rectSize, rectSize
        );
        gc.setFill(paint);
        gc.fillRect(
                posX * rectSize + 1, posY * rectSize + 1,
                rectSize - 2 * borderWidth, rectSize - 2 * borderWidth
        );
        // Draw highlight.
        gc.setFill(Color.WHITE);
        gc.fillRect(
                posX * rectSize + 2, posY * rectSize + 2,
                2, 2
        );
    }
}
