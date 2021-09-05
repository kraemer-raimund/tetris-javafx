package tetris;

import javafx.scene.paint.Color;

/**
 * Created by Raimund Krämer on 23.06.2017.
 */
public abstract class TetrisStone {

    private int x;
    private int y;
    private int rotationTimes90;
    private Color color;

    TetrisStone(int x, int y, int rotationTimes90, Color color) {
        this.x = x;
        this.y = y;
        this.rotationTimes90 = rotationTimes90;
        this.color = color;
    }

    void moveDown() {
        y++;
    }

    void moveUp() {
        y--;
    }

    void moveLeft() {
        x--;
    }

    void moveRight() {
        x++;
    }

    void turnLeft() {
        rotationTimes90--;
        if (rotationTimes90 == -1) {
            rotationTimes90 = 3;
        }
    }

    void turnRight() {
        rotationTimes90++;
        if (rotationTimes90 == 4) {
            rotationTimes90 = 0;
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getRotationTimes90() {
        return rotationTimes90;
    }

    Color getColor() {
        return color;
    }

    public abstract int[][] getRectanglePositions4x2();

    boolean canMoveLeft(int boundLeft, TetrisRectangle[][] deadStones) {
        for (int[] posCurrent : getRectanglePositions4x2()) {
            if (posCurrent[0] == boundLeft) {
                return false;
            }
            if (deadStones[posCurrent[0] - 1][posCurrent[1]] != null) {
                return false;
            }
        }
        return true;
    }
    boolean canMoveRight(int boundRight, TetrisRectangle[][] deadStones) {
        for (int[] posCurrent : getRectanglePositions4x2()) {
            if (posCurrent[0] == boundRight) {
                return false;
            }
            if (deadStones[posCurrent[0] + 1][posCurrent[1]] != null) {
                return false;
            }
        }
        return true;
    }
}
