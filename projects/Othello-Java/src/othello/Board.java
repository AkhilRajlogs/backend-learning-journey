package othello;

public class Board {

    private static final int BOARD_SIZE = 8;

    private char[][] board;

    private final char p1Symbol, p2Symbol;

    private static final char EMPTY = ' ';

    public Board(char p1Symbol, char p2Symbol) {

        board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        // Currently assumes a 2-player game with fixed symbols
        this.p1Symbol = p1Symbol;
        this.p2Symbol = p2Symbol;

        initializeBoard();
    }

    public void printBoard() {
        
        //header row with column headings
        System.out.print(" ");
        for (int col = 0; col < BOARD_SIZE; col++) {

            System.out.print(col + " ");
        }
        System.out.println();

        //print rows                    
        for (int row = 0; row < BOARD_SIZE; row++) {
            //row number
            System.out.print(row + " ");

            for (int col = 0; col < BOARD_SIZE; col++) {

                char cell = board[row][col];
                if (cell == EMPTY) {
                    System.out.print(". ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }


    public boolean isValidMove(int row, int column, char symbol) {
        // A valid move in Othello is one which can turn opponent pieces to own symbol 

        // Symbol check
        if (!isValidSymbol(symbol)) {
            return false;
        }
        
        // Boundary check 
        if (!isInBounds(row, column)) {
            return false;
        }

        // Empty check 
        if (board[row][column] != EMPTY) {
            return false;
        }

        // Direction loop 
        for (int[] direction : DIRECTIONS) {
            int rowStep = direction[0];
            int colStep = direction[1];

            if (checkDirection(row, column, rowStep, colStep, symbol)) {
                return true;
            }
        }
        
        return false;
    }

    public boolean makeMove(int row, int column, char symbol) {

        if (!isValidMove(row, column, symbol)) {
            return false;            
        }

        board[row][column] = symbol;
        for (int[] direction : DIRECTIONS) {
            int rowStep = direction[0];
            int colStep = direction[1];

            if (checkDirection(row, column, rowStep, colStep, symbol)) {
                flipInDirection(row, column, rowStep, colStep, symbol);
            }
        }
        return true;
    }

    public boolean hasValidMove(char symbol) {

        if (!isValidSymbol(symbol)) {
            return false;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (isValidMove(row, col, symbol)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int countPieces(char symbol) {
        if (!isValidSymbol(symbol)) {
            return -1;
        }

        int count = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == symbol) {
                    count++;
                }
            }            
        }
        return count;
    }

    public char[][] getBoardCopy() {
        char[][] copy = new char[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length); // Return defensive copy to preserve encapsulation
        }

        return copy;

    }


    private void initializeBoard() {
        // Othello game starts with 2 pieces of each player in the centre diagonally 
        int mid = BOARD_SIZE / 2;
        board[mid - 1][mid - 1] = p2Symbol;
        board[mid - 1][mid] = p1Symbol;
        board[mid][mid - 1] = p1Symbol;
        board[mid][mid] = p2Symbol;
    }

    private boolean checkDirection(int row, int column, int rowStep, int colStep, char symbol) {

        char opponentSymbol = getOpponentSymbol(symbol);

        int currentRow = row + rowStep;
        int currentCol = column + colStep;

        // First found piece should be opponent's, not EMPTY, not OutOfBounds and not ownSymbol 
        if (!isInBounds(currentRow, currentCol) || board[currentRow][currentCol] != opponentSymbol) {
            return false;
        }

        // Move further in same direction 
        currentRow += rowStep;
        currentCol += colStep;

        while (isInBounds(currentRow, currentCol)) {

            if (board[currentRow][currentCol] == EMPTY) {
                return false;
            }

            if (board[currentRow][currentCol] == symbol) {
                return true;
            }

            currentRow += rowStep;
            currentCol += colStep;
        }
        return false;
    }
        
    private boolean isInBounds(int row, int column) {
        return row >= 0 && column >= 0 && row < BOARD_SIZE && column < BOARD_SIZE;
    }

    private void flipInDirection(int row, int column, int rowStep, int colStep, char symbol) {
        
        char opponentSymbol = getOpponentSymbol(symbol);
        int currentRow = row + rowStep;
        int currentCol = column + colStep;

        while (isInBounds(currentRow, currentCol) && board[currentRow][currentCol] == opponentSymbol) {
            board[currentRow][currentCol] = symbol;
            currentRow += rowStep;
            currentCol += colStep;
        }
    }

    private boolean isValidSymbol(char symbol) {
        return symbol == p1Symbol || symbol == p2Symbol;
    }

    private char getOpponentSymbol(char symbol) {
        return (symbol == p1Symbol) ? p2Symbol : p1Symbol;
    }



    private static final int[][] DIRECTIONS = {
        { 0, -1 }, { -1, -1 },
        { -1,  0 }, { -1,  1 },
        { 0,  1 }, {  1,  1 },
        { 1,  0 }, {  1, -1 }
    };

}