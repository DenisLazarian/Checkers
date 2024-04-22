import java.util.Objects;

public class Cell {

    private static final char C_FORBIDDEN = '·';
    private static final char C_EMPTY = ' ';
    private static final char C_WHITE = 'w';
    private static final char C_BLACK = 'b';

    public static final Cell FORBIDDEN = new Cell(C_FORBIDDEN);
    public static final Cell EMPTY = new Cell(C_EMPTY);
    public static final Cell WHITE = new Cell(C_WHITE);
    public static final Cell BLACK = new Cell(C_BLACK);

    private final char status;

    private Cell(char status) {
        this.status = status;
    }

    public static Cell fromChar(char status) {
        Cell c = null;
        switch (status) {
            case C_FORBIDDEN:   c = Cell.FORBIDDEN;     break;
            case C_EMPTY:       c = Cell.EMPTY;         break;
            case C_WHITE:       c = Cell.WHITE;         break;
            case C_BLACK:       c = Cell.BLACK;         break;
            default: break;
        }
        return c;
    }


    public boolean isForbidden() {
        return this.status == FORBIDDEN.status;
    }

    public boolean isEmpty() {
        return this.status == EMPTY.status;

    }

    public boolean isWhite() {
        return this.status == WHITE.status;
    }

    public boolean isBlack() {
        return this.status == BLACK.status;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }
}