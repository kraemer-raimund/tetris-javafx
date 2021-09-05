package tetris;

import javafx.scene.paint.Color;

public class TetrisStoneSquare extends TetrisStone {

    TetrisStoneSquare(int x, int y, int rotationTimes90) {
        super(x, y, rotationTimes90, Color.LIGHTGOLDENRODYELLOW);
    }

    @Override
    public int[][] getRectanglePositions4x2() {

        switch (getRotationTimes90()) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new int[][]{
                        {getX(), getY()},
                        {getX() + 1, getY()},
                        {getX(), getY() + 1},
                        {getX() + 1, getY() + 1}
                };
            default:
                throw new IllegalStateException();
        }
    }

}
