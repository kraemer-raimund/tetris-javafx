package tetris;

import javafx.scene.paint.Color;

/**
 * Created by Raimund Krämer on 25.06.2017.
 */
public class TetrisStoneT extends TetrisStone {

    TetrisStoneT(int x, int y, int rotationTimes90) {
        super(x, y, rotationTimes90, Color.LIGHTBLUE);
    }

    @Override
    public int[][] getRectanglePositions4x2() {

        switch (getRotationTimes90()) {
            case 0:
                return new int[][]{
                        {getX() - 1, getY()},
                        {getX(), getY()},
                        {getX() + 1, getY()},
                        {getX(), getY() + 1}
                };
            case 1:
                return new int[][]{
                        {getX(), getY() - 1},
                        {getX(), getY()},
                        {getX(), getY() + 1},
                        {getX() - 1, getY()}
                };
            case 2:
                return new int[][]{
                        {getX() + 1, getY() + 1},
                        {getX(), getY() + 1},
                        {getX() - 1, getY() + 1},
                        {getX(), getY()}
                };
            case 3:
                return new int[][]{
                        {getX(), getY() + 1},
                        {getX(), getY()},
                        {getX(), getY() - 1},
                        {getX() + 1, getY()}
                };
            default:
                throw new IllegalStateException();
        }
    }

}
