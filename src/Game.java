public class Game {

    private static final Direction[] WHITE_DIRECTIONS = {Direction.NW, Direction.NE};
    private static final Direction[] BLACK_DIRECTIONS = {Direction.SW, Direction.SE};

    private final Board board;
    private Player currentPlayer;
    private boolean hasWon;

    public Game(Board board) {
        this.hasWon = false;
        this.board = board;
        this.currentPlayer = Player.WHITE;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean hasWon() {
        return hasWon;
    }

    private boolean isColorTurn(Player color){
        return this.currentPlayer.toString().equals(color.toString());
    }

    public boolean isValidFrom(Position position) {
        if(isColorTurn(Player.WHITE)) {
            return isValidByPieceColor(position, WHITE_DIRECTIONS);
        } else {
            return isValidByPieceColor(position, BLACK_DIRECTIONS);
        }
    }

    public boolean isValidByPieceColor(Position p, Direction[] c){
        boolean t0;
        boolean t1;
        if(isColorTurn(Player.WHITE)){
            t0 = board.isBlack(c[0].apply(p));
            t1 = board.isBlack(c[1].apply(p));
        }else{
            t0 = board.isWhite(c[0].apply(p));
            t1 = board.isWhite(c[1].apply(p));
        }

        return  !board.isEmpty(p) && !isColor(p) &&
                ((board.isEmpty(c[0].apply(p)) ||
                  board.isEmpty(c[1].apply(p))) ||
                (t0 && board.isEmpty(c[0].apply(c[0].apply(p))) ||
                  t1 && board.isEmpty(c[1].apply(c[1].apply(p)))));
    }



    public boolean isColor(Position p){
        if(isColorTurn(Player.WHITE)) return board.isBlack(p);
        else return board.isWhite(p);
    }


    // Assumes validFrom is a valid starting position
    public boolean isValidTo(Position validFrom, Position to) {
        return false;
//        return isValidFrom(validFrom) &&
//                board.isEmpty(to) &&
//                validFrom.sameDiagonalAs(to) &&
//                Position.distance(validFrom,to) == 2 ||
//                isValidFrom(validFrom) &&
//                isColorByTurn(validFrom) &&
//                validFrom.sameDiagonalAs(to) &&
//                Position.distance(validFrom,to) == 4;
    }

    private boolean isColorByTurn(Position validFrom) {
        if(isColorTurn(Player.WHITE)){
            return board.isBlack(Game.WHITE_DIRECTIONS[0].apply(validFrom)) || board.isBlack(Game.WHITE_DIRECTIONS[1].apply(validFrom));
        }else{
            return board.isWhite(Game.BLACK_DIRECTIONS[0].apply(validFrom)) || board.isWhite(Game.BLACK_DIRECTIONS[1].apply(validFrom));
        }
    }


    // Assumes both positions are valid
    public Move move(Position validFrom, Position validTo) {
        if(isValidFrom(validFrom) && isValidTo(validFrom, validTo)){
            Move move;
            if(Position.distance(validFrom, validTo) == 4)
                move = new Move(validFrom, Position.middle(validFrom, validTo),validTo);
            else move = new Move(validFrom, null, validTo);

            updateBoard(move);
            return move;
        }
        return null;
    }

    private void updateBoard(Move move) {
        if(isColorTurn(Player.WHITE)) {
            board.setWhite(move.getTo());
            currentPlayer = Player.BLACK;
        } else {
            board.setBlack(move.getTo());
            currentPlayer = Player.WHITE;
        }
        board.setEmpty(move.getMiddle());
        board.setEmpty(move.getFrom());
    }

    // Only for testing

    public void setPlayerForTest(Player player) {
        this.currentPlayer = player;
    }
}
