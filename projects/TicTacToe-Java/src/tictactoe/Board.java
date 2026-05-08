package tictactoe;

public class Board {
    private char board[][];
    private final int boardSize = 3; //initial board assumes a 3 X 3 game
    private char p1Symbol;
    //to count total number of cells filled:-
    private int count;
    private static final char EMPTY = ' ';

    public Board(char p1Symbol){
        board = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j] = EMPTY;
            }
        }
        //Currently the board assumes a 2 player game with fixed symbols
        this.p1Symbol = p1Symbol;
    }

    public void printBoard(){
        for( int i = 0; i  < boardSize; i++){
            for( int j = 0; j < boardSize; j++){
                System.out.print(board[i][j]);
                if(j<boardSize-1){
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if(i<boardSize-1){
                System.out.println("---------");
            }
        }
    }

    public boolean makeMove(int row, int col, char symbol){
        //check OutOfBound moves
        if(row < 0 || row >= boardSize || col <0 || col >=boardSize){
            return false;
        }

        //check if already occupied
        if(board[row][col] != EMPTY){ 
            return false;
        }
            //Make move
            board[row][col] = symbol;
            count++;
            
            return true;
        
    }

    public GameStatus getGameStatus(){
        
        //row check
        for(int i = 0; i < boardSize; i++){
            if(board[i][0] != EMPTY && board[i][0]==board[i][1] && board[i][1]==board[i][2]){
                return board[i][0] == p1Symbol ? GameStatus.PLAYER1_WINS : GameStatus.PLAYER2_WINS;
            }
        }

        //column check
        for(int j = 0; j < boardSize; j++) {
            if(board[0][j] != EMPTY && board[0][j]==board[1][j] && board[1][j]==board[2][j]){
                return board[0][j] == p1Symbol ? GameStatus.PLAYER1_WINS : GameStatus.PLAYER2_WINS;
            }
        }

        //diagonal check
        if(board[1][1] != EMPTY){
            if( (board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2]==board[1][1] && board[1][1] == board[2][0])) {
                return board[1][1] == p1Symbol ? GameStatus.PLAYER1_WINS : GameStatus.PLAYER2_WINS;
            }
        }

        if(count==boardSize*boardSize) {
            return GameStatus.DRAW;
        }else {
            return GameStatus.IN_PROGRESS;
        }
        
    }

    public int getBoardSize(){
        return this.boardSize;
    }

}
