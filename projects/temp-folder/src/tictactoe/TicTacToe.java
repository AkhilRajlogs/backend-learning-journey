package tictactoe;

import java.util.Scanner;

public class TicTacToe {

    private Player player1, player2;
    private Board board;


    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();
        t.startGame();
    }


    public void startGame() {
        Scanner s = new Scanner(System.in);
        //Take player input
        player1 = takePlayerInput(1, s);
        player2 = takePlayerInput(2, s);
        
        //check duplicate symbol
        while (player1.getSymbol()==player2.getSymbol()){
            System.out.println("Symbol already taken!! Please re-enter a different symbol.");
            player2.setSymbol(s.next().charAt(0));
            s.nextLine(); // to clean up left over and \n before next scan
        }

        //Create the Board            
            board = new Board(player1.getSymbol());

            board.printBoard();
        //Play the Game
        boolean player1Turn = true;

        //to be upgraded later
        while (true) {
            Player currentPlayer = player1Turn ? player1 : player2;
            int boardSize = board.getBoardSize();

            int row = readIndex(s, currentPlayer, "row", boardSize);
            int col = readIndex(s, currentPlayer, "column", boardSize);

            boolean moveMade = board.makeMove(row, col, currentPlayer.getSymbol());

            if (!moveMade) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board.printBoard();

            GameStatus status = board.getGameStatus();
            switch (status) {
                case PLAYER1_WINS: {
                    System.out.println("Player 1: " + player1.getName() + " has WON");
                    return;
                }

                case PLAYER2_WINS: {
                    System.out.println("Player 2: " + player2.getName() + " has WON");
                    return;
                }

                case DRAW: {
                    System.out.println("It is a DRAW!!");
                    return;
                }
                    
            
                case IN_PROGRESS:{
                    player1Turn = !player1Turn;
                    continue;
                }
            }
        }
    }

    private Player takePlayerInput(int num, Scanner s){
        System.out.println("Enter Player-" + num + " name: ");
        String name = s.nextLine();
        System.out.println("Enter Player-"+ num + " symbol: ");
        char symbol = s.next().charAt(0);
        s.nextLine(); // to clean up left over and \n before next scan

        return new Player(name, symbol);
    }

    private int readIndex (Scanner s, Player player, String prompt, int boardSize) {

        System.out.println("Hi " + player.getName() + ", Please enter your " + prompt + " number:");
        while (true) {
            if(!s.hasNextInt()){
                System.out.println("Invalid input! Non-Integer value. Please retry.");
                s.nextLine();
                continue;
            }

            int value = s.nextInt() - 1;

            if(value < 0 || value >= boardSize){
                System.out.println("Invalid input! Please enter integers from 1 to " + boardSize + ".");
                continue;
            }

            s.nextLine();

            return value;
        }
    }
}
