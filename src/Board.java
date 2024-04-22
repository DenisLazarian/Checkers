import java.util.StringTokenizer;

// Only a rectangle of cells. Does not know the game rules.

public class Board {

    private final int width;
    private final int height;
    private final Cell[][] cells;
    private int numBlacks;
    private int numWhites;

    public Board(int width, int height, String board) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        int x = 0, j;

        for (int i = 0; i < height; i++) {
            j = 0;
            while(j < width){
                if(board.charAt(x) != '\n'){
                    cells[i][j] = Cell.fromChar(board.charAt(x));
                    if(board.charAt(x) == 'b')
                        numBlacks++;
                    else if(board.charAt(x) == 'w')
                        numWhites++;
                    j++;
                }
                x++;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumBlacks() {
        return numBlacks;
    }

    public int getNumWhites() {
        return numWhites;
    }

    private boolean isOutOfBounds(Position pos){
        return  pos.getX() >= width  ||
                pos.getX() < 0       ||
                pos.getY() >= height ||
                pos.getY() < 0;
    }

    public boolean isForbidden(Position pos) {
        return  pos == null || isOutOfBounds(pos) || cells[pos.getY()][pos.getX()].isForbidden();
    }

    public boolean isBlack(Position pos) {
        return !isForbidden(pos) && cells[pos.getY()][pos.getX()].isBlack();
    }

    public boolean isWhite(Position pos) {
        return !isForbidden(pos) && cells[pos.getY()][pos.getX()].isWhite();
    }

    public boolean isEmpty(Position pos) {
        return !isForbidden(pos) && cells[pos.getY()][pos.getX()].isEmpty();
    }

    public void setBlack(Position pos) {
        if(!isForbidden(pos)) {
            cells[pos.getY()][pos.getX()] = Cell.BLACK;
            numBlacks++;
        }
    }

    public void setWhite(Position pos) {
            if(!isForbidden(pos)) {
                cells[pos.getY()][pos.getX()] = Cell.WHITE;
                numWhites++;
            }
    }

    public void setEmpty(Position pos) {
        if(isWhite(pos)) numWhites--;
        else if(isBlack(pos)) numBlacks--;

        if(!isForbidden(pos)) cells[pos.getY()][pos.getX()] = Cell.EMPTY;
    }

    // For testing and debugging

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(cells[y][x].toString());
            }
            if (y != height - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
