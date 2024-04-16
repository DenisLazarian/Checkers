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
        return false;
    }



    // Assumes validFrom is a valid starting position
    public boolean isValidTo(Position validFrom, Position to) {
        return false;
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
