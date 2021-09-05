package tetris;

import javafx.scene.paint.Color;

public class TetrisStoneInvertedZ extends TetrisStone {

    TetrisStoneInvertedZ(int x, int y, int rotationTimes90) {
        super(x, y, rotationTimes90, Color.PURPLE);
    }

    @Override
    public int[][] getRectanglePositions4x2() {

        switch (getRotationTimes90()) {
            case 0:
                return new int[][]{
                        {getX() - 1, getY() + 1},
                        {getX(), getY() + 1},
                        {getX(), getY()},
                        {getX() + 1, getY()}
                };
            case 1:
                return new int[][]{
                        {getX(), getY() - 1},
                        {getX(), getY()},
                        {getX() + 1, getY()},
                        {getX() + 1, getY() + 1}
                };
            case 2:
                return new int[][]{
                        {getX() - 1, getY() + 1},
                        {getX(), getY() + 1},
                        {getX(), getY()},
                        {getX() + 1, getY()}
                };
            case 3:
                return new int[][]{
                        {getX() - 1, getY() - 1},
                        {getX() - 1, getY()},
                        {getX(), getY()},
                        {getX(), getY() + 1}
                };
            default:
                throw new IllegalStateException();
        }
    }

}
