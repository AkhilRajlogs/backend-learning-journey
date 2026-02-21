package othello;

public class Othello {

    private final Board board;
    private char currentPlayer;
    private final char p1Symbol, p2Symbol;

    public Othello(char p1Symbol, char p2Symbol) {

        this.board = new Board(p1Symbol, p2Symbol);
        this.p1Symbol = p1Symbol;
        this.p2Symbol = p2Symbol;

        this.currentPlayer = p1Symbol;

    }

    public boolean playMove(int row, int column) {
        if (!board.makeMove(row, column, currentPlayer)) {
            return false;
        }
        switchPlayer();
        //if the next player has no valid move, switch back
        if (!board.hasValidMove(currentPlayer)) {
            switchPlayer();
        }
        return true;
    }

    public boolean isGameOver() {
        return !board.hasValidMove(p1Symbol) && !board.hasValidMove(p2Symbol);
    }

    public char getWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("Game is not over yet");
        }

        int p1Count = board.countPieces(p1Symbol);
        int p2Count = board.countPieces(p2Symbol);

        if (p1Count > p2Count) {
            return p1Symbol;
        } else if (p2Count > p1Count) {
            return p2Symbol;
        } else {
            return '\0'; // draw
        }
    }

    public char getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean hasValidMove() {
        return board.hasValidMove(currentPlayer);
    }

    public char[][] getBoardSnapshot() {
        return board.getBoardCopy();
    }

    
    private void switchPlayer() {
        currentPlayer = (currentPlayer == p1Symbol) ? p2Symbol : p1Symbol;
    }

}