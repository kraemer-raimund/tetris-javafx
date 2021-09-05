package tetris;

import javafx.scene.paint.Paint;

/**
 * Created by Raimund Krämer on 25.06.2017.
 */
public class TetrisRectangle {
    private Paint paint;

    public TetrisRectangle(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }
}
