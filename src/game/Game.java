package game;

import board.Board;
import pieces.King;
import players.Player;
import pieces.Piece;
import moves.Move;
import java.util.List;

import java.util.Scanner;

public class Game {
    private Board board;
    private String whiteName;
    private String blackName;

    public Game(String whiteName, String blackName) {
        this.whiteName = whiteName;
        this.blackName = blackName;
        board = new Board();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("White player's name: ");
        String whiteName = scanner.nextLine();

        System.out.print("Black player's name: ");
        String blackName = scanner.nextLine();

        Game game = new Game(whiteName, blackName);
        game.run();

        scanner.close();
    }

    public void run() {
        int whiteWins = 0, blackWins = 0;
        Player whitePlayer = new Player(Piece.WHITE, this.whiteName);
        Player blackPlayer = new Player(Piece.BLACK, this.blackName);
        Scanner scanner = new Scanner(System.in);

        int moveCounter = 0;

        while (true){

            Player player;

            if (moveCounter % 2 == 0) {
                player = whitePlayer;
            } else {
                player = blackPlayer;
            }
            this.board.printBoard();
            System.out.println(player.getName() + "'s Turn");
            System.out.println("Select the piece you want to move (using:letter number)");

            String input = scanner.nextLine();
            int pastLetter = (int)input.charAt(0) - 97;
            int pastNumber = (int)input.charAt(1) - '0' - 1;
            System.out.println(pastLetter +""+ pastNumber);
            int color = player.getColor();

            Piece piece = this.board.getPiece(pastLetter, pastNumber);
            System.out.println(piece);
            if (piece == null) {
                System.out.println("Off limits! Try again");
                continue;
            }

            if (color != this.board.getPiece(pastLetter, pastNumber).getColor()) {
                System.out.println("That piece isn't yours, try again!");
                continue;
            }

            System.out.println("Select where you want to move it to (or type legalmoves to see all possible moves)");
            char newLetter;
            int newNumber;
            while (true) {
                input = scanner.nextLine();
                if (input.equals("legalmoves")) {
                    System.out.println("Your piece can move to => ");
                    List<Move> legalMoves = this.board.calculateLegalMoves(pastLetter, pastNumber);
                    System.out.print("|");
                    for (Move move : legalMoves) {
                        System.out.print("" + (char)(move.getX()+97) + (move.getY()+1) + "|");
                    }
                    System.out.println();
                } else {
                    newLetter = input.charAt(0);
                    newNumber = (int)input.charAt(1) - '0' - 1;
                    break;
                }
            }
            List<Move> legalMoves = this.board.calculateLegalMoves(pastLetter, pastNumber);
            Move move = new Move((int)newLetter - 97, newNumber);
            for (Move legalMove: legalMoves) {
                if (legalMove.equals(move)) {
                    if (player.makeMove(this.board, (char)(pastLetter+97), pastNumber+1, newLetter, newNumber+1)) {
                        System.out.println("Sucessful");
                        moveCounter++;
                    }
                    break;
                }
            }
            int playerColor = player.getColor();
            if (playerColor == 0) {
                playerColor = 1;
            } else {
                playerColor = 0;
            }
            int row = findKing(playerColor)[0];
            int col = findKing(playerColor)[1];
            System.out.println("kings coords = " + row + col);
            System.out.println("is there a check?" + board.isCheck((char)(col + 97), row - 1 ));
        }


    }

    public int[] findKing(int color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = this.board.getPiece(row,col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new int[] {row, col}; // Return the king's position
                }
            }
        }
        return null; // King not found
    }

}
