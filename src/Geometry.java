import acm.graphics.GDimension;
import acm.graphics.GPoint;

import java.awt.*;

public class Geometry {

    private final int windowWidth;
    private final int windowHeight;
    private final int numCols;
    private final int numRows;
    private final double boardPadding;
    private final double cellPadding;


    public Geometry(int windowWidth, int windowHeight, int numCols, int numRows, double boardPadding, double cellPadding) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.numCols = numCols;
        this.numRows = numRows;
        this.boardPadding = boardPadding;
        this.cellPadding = cellPadding;
    }


    public int getRows() {
        return numRows;

    }

    public int getColumns() {
        return numCols;
    }


    public GDimension boardDimension() {
        return new GDimension(
                    windowWidth - windowWidth * cellPadding,
                    windowHeight - windowHeight * cellPadding
        );
    }

    public GPoint boardTopLeft() {
        return new GPoint(
                (windowWidth * boardPadding),
                (windowHeight * boardPadding)
        );
    }

    public GDimension cellDimension() {
        return new GDimension(
                boardDimension().getWidth()  / numCols,
                boardDimension().getHeight() / numRows
        );
    }

    public GPoint cellTopLeft(int x, int y) {
        return new GPoint(
                cellDimension().getWidth() * x + boardTopLeft().getX(),
                cellDimension().getHeight() * y + boardTopLeft().getY()
        );
    }

    public GDimension tokenDimension() {
        return new GDimension(
                cellDimension().getWidth()*(1-cellPadding*2),
                cellDimension().getHeight()*(1-cellPadding*2)

        );  // 90 * 0,80 = 72 por que se aplica un padding del 10% tanto por arriba como por abajo, derecha e izquierda.
    }

    public GPoint tokenTopLeft(int x, int y) {
        return new GPoint(
                  cellTopLeft(x,y).getX() + (cellDimension().getWidth()*(cellPadding)),
                  cellTopLeft(x,y).getY() + (cellDimension().getHeight()*(cellPadding))
        );
    }

    public GPoint centerAt(int x, int y) {
        return new GPoint(
                cellTopLeft(x,y).getX() + cellDimension().getWidth() / 2,
                cellTopLeft(x,y).getY() + cellDimension().getHeight() / 2
        );
    }

    public Position xyToCell(double x, double y) {
        return new Position(
                (int) ((x - boardTopLeft().getX()) / cellDimension().getWidth()),
                (int) ((y - boardTopLeft().getY()) / cellDimension().getHeight())
        );
    }
}
