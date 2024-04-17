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
        else
            return isValidByPieceColor(position, BLACK_DIRECTIONS);
    }

    private boolean isValidByPieceColor(Position p, Direction[] c){
        boolean[] t = new boolean[2];

        isPieceColor(t, c, p);

        return  !hasWon && !board.isEmpty(p) && !isColor(p) &&
                ((board.isEmpty(c[0].apply(p)) ||
                  board.isEmpty(c[1].apply(p))) ||
                (t[0] && board.isEmpty(c[0].apply(c[0].apply(p))) ||
                  t[1] && board.isEmpty(c[1].apply(c[1].apply(p)))));
    }



    private boolean isColor(Position p){
        if(isColorTurn(Player.WHITE)) return board.isBlack(p);
        else return board.isWhite(p);
    }


    // Assumes validFrom is a valid starting position
    public boolean isValidTo(Position validFrom, Position to) {
        if(isColorTurn(Player.WHITE))
            return isPosibleValidMoveByTurn(validFrom, to , WHITE_DIRECTIONS);
        else
            return isPosibleValidMoveByTurn(validFrom, to , BLACK_DIRECTIONS);
    }

    private boolean isPosibleValidMoveByTurn(Position validFrom, Position to, Direction[] c) {
        boolean[] t = new boolean[2];

        isPieceColor(t, c, validFrom);

        return isValidFrom(validFrom) &&
                to.sameDiagonalAs(validFrom) && board.isEmpty(to) && board.isEmpty(to) &&
                iAmNotReturningBack(validFrom,to) &&
                (Position.distance(validFrom,to) == 2 ||
                        t[0] && to.equals(c[0].apply(c[0].apply(validFrom))) ||
                        t[1] && to.equals(c[1].apply(c[1].apply(validFrom))));

    }

    private void isPieceColor(boolean[] t, Direction[] c, Position vf){
        if(isColorTurn(Player.WHITE)){
            t[0] = board.isBlack(c[0].apply(vf));
            t[1] = board.isBlack(c[1].apply(vf));
        }else{
            t[0] = board.isWhite(c[0].apply(vf));
            t[1] = board.isWhite(c[1].apply(vf));
        }
    }

    private boolean iAmNotReturningBack(Position vf, Position vt) {
        if(isColorTurn(Player.WHITE))
            return vf.getY() > vt.getY();
         else
            return  vf.getY() < vt.getY();
    }

    // Assumes both positions are valid
    public Move move(Position validFrom, Position validTo) {
        if(isValidFrom(validFrom) && isValidTo(validFrom, validTo)){
            Move move;

            if(Position.distance(validFrom, validTo) == 4)
                move = new Move(validFrom, Position.middle(validFrom, validTo),validTo);
            else
                move = new Move(validFrom, null, validTo);

            updateBoard(move);
            checkAndDeclareWinner(move);
            changePlayerTurn();
            return move;
        }
        return null;
    }

    private void checkAndDeclareWinner(Move move) {
        if(isColorTurn(Player.BLACK) && (move.getTo().getY() == board.getHeight()-1 || board.getNumWhites() == 0 || totalMoves('w') == 0))
            this.hasWon = true;
        else if( isColorTurn(Player.WHITE) && (move.getTo().getY() == 0 || board.getNumBlacks() == 0 || totalMoves('b') == 0))
            this.hasWon = true;
    }

    private void updateBoard(Move move) {
        if(isColorTurn(Player.WHITE))
            board.setWhite(move.getTo());
        else
            board.setBlack(move.getTo());

        board.setEmpty(move.getMiddle());
        board.setEmpty(move.getFrom());
    }

    private int totalMoves(char c){
        int validMoves = 0, x = 0;

        for (int i = 0; i < board.getHeight(); i++) {
            int j = 0;
            while(j < board.getWidth()) {
                if(board.toString().charAt(x) != '\n'){
                    if(c == board.toString().charAt(x) && isValidFrom(new Position(i, j))) {
                        validMoves++;
                    }
                    j++;
                }
                x++;
            }
        }
        return validMoves;
    }

    private void changePlayerTurn(){
        if(!hasWon){
            if(isColorTurn(Player.WHITE))
                this.currentPlayer = Player.BLACK;
            else
                this.currentPlayer = Player.WHITE;
        }
    }

    // Only for testing

    public void setPlayerForTest(Player player) {
        this.currentPlayer = player;
    }
}
