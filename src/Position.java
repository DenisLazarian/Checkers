import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    private static final int length = 8;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean sameDiagonalAs(Position other) {
        return diagonal(other) || contraDiagonal(other);
    }

    private boolean diagonal(Position other) {
        int x = this.x;
        int y = this.y;
        do{
            if(other.checkCoords(x, y)) return true;
            x++;
            y++;
        } while(x < length && y < length);

        x = this.x;
        y = this.y;
        do{
            if(other.checkCoords(x, y)) return true;
            x--;
            y--;
        }while(x >= 0 && y >= 0);

        return false;
    }

    private boolean contraDiagonal(Position other) {
        int y = this.y;
        int x = this.x;
        do{
            if(other.checkCoords(x, y)) return true;
            x++;
            y--;
        } while(y >= 0);

        x = this.x;
        y = this.y;
        do{
            if(other.checkCoords(x, y)) return true;
            x--;
            y++;
        }while(x >= 0);

        return false;
    }


    private boolean checkCoords(int x, int y){
        return this.x == x && this.y == y;
    }



    public static int distance(Position pos1, Position pos2) {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
    }

    public static Position middle(Position pos1, Position pos2) {
        return new Position((pos1.x + pos2.x) / 2, (pos1.y + pos2.y) / 2);
    }

    // Needed for testing and debugging

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

