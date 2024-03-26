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
        if(isColorTurn(Player.WHITE))
            return isValidByPieceColor(position, WHITE_DIRECTIONS);
        else return isValidByPieceColor(position, BLACK_DIRECTIONS);
    }



    public boolean isValidByPieceColor(Position p, Direction[] constant){
        return (board.isEmpty(constant[0].apply(p)) ||
                board.isEmpty(constant[1].apply(p)) ||
                isColor(constant[0].apply(p)) ||
                isColor(constant[1].apply(p))) &&
                !board.isEmpty(p) && !isColor(p)
//                && isValidDirection(p, constant)
                ;
    }

//    private boolean isValidDirection(Position p, Direction[] c) {  // no funciona
//        if(isColorTurn(Player.WHITE)){
//            return (p.getX() - c[0].apply(p).getX()) > 0 || (p.getX() - c[1].apply(p).getX()) > 0;
//        }else{
//            return (p.getX() - c[0].apply(p).getX()) > 0 || (p.getX() - c[1].apply(p).getX()) > 0;
//        }
//    }


    public boolean isColor(Position p){
        if(currentPlayer.toString().equals(Player.WHITE.toString())) return board.isBlack(p);
        else return board.isWhite(p);
    }


    // Assumes validFrom is a valid starting position
    public boolean isValidTo(Position validFrom, Position to) {
        return isValidFrom(validFrom) &&
                board.isEmpty(to) &&
                validFrom.sameDiagonalAs(to) &&
                Position.distance(validFrom,to) == 2 ||
                isValidFrom(validFrom) &&
                isColorByTurn(validFrom) &&
                validFrom.sameDiagonalAs(to) &&
                Position.distance(validFrom,to) == 4;
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