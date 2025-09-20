package game;

import board.*;
import pieces.King;
import players.Player;
import pieces.Piece;

import java.util.List;

import java.util.Scanner;

public class Game {
    private final Board board;
    private final String whiteName;
    private final String blackName;
    private static boolean deformed = false;

    public Game(String whiteName, String blackName) {
        this.whiteName = whiteName;
        this.blackName = blackName;
        board = new Board();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("White player's name: ");
        String whiteName = scanner.nextLine();

        if (whiteName.equals("changemyformat")) {
            deformed = true;
            System.out.print("White player's name: ");
            whiteName = scanner.nextLine();
        }


        System.out.print("Black player's name: ");
        String blackName = scanner.nextLine();

        Game game = new Game(whiteName, blackName);
        game.run();

        scanner.close();
    }

    public void run() {
        Player whitePlayer = new Player(Piece.WHITE, this.whiteName);
        Player blackPlayer = new Player(Piece.BLACK, this.blackName);
        Scanner scanner = new Scanner(System.in);

        if (deformed) {
            board.format();
        }

        int moveCounter = 0;

        while (!board.isCheckmate(Piece.BLACK) && !board.isCheckmate(Piece.WHITE)){

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

            if (input.length() != 2) {
                System.out.println("Provide only the file and the rank");
                continue;
            }

            int color = player.getColor();
            Square originSquare = new Square(input.charAt(0), input.charAt(1) - '0');

            if (this.board.isOutsideBoard(originSquare)) {
                System.out.println("Choose a file and rank inside the board!");
                continue;
            }


            if (color != this.board.getPiece(originSquare).getColor()) {
                System.out.println("That piece isn't yours, try again!");
                continue;
            }

            List<Square> legalMoves = this.board.calculateLegalMoves(originSquare);

            if (legalMoves.isEmpty()) {
                System.out.println("This piece has no legal moves! Choose another");
                continue;
            }

            if (board.isChecked(color) && !(this.board.getPiece(originSquare) instanceof King)) {
                legalMoves = this.board.pieceStopsCheck(originSquare);

                if (legalMoves.isEmpty()) {
                    System.out.println("This piece has no legal moves! Choose another");
                    continue;
                }
            }

            System.out.println("Select where you want to move it to (h for help)");
            Square targetSquare;
            while (true) {
                input = scanner.nextLine();

                if (input.equals("h")) {
                    System.out.println("You can type the following commands");
                    System.out.println("legalmoves - to see this pieces legal moves");
                    continue;
                }

                if (input.equals("legalmoves")) {
                    System.out.println("Your piece can move to => ");
                    System.out.print("|");
                    for (Square move : legalMoves) {
                        System.out.print(" " + move + " |");
                    }
                    System.out.println();
                    continue;
                }

                if (input.length() != 2){
                    System.out.println("Provide only the file and the rank");
                    continue;
                }

                targetSquare = new Square(input.charAt(0), input.charAt(1) - '0');
                if (this.board.isOutsideBoard(targetSquare)) {
                    System.out.println("Choose a file and rank inside the board!");
                    continue;
                }
                break;
            }

            // Verify if attempted move is in piece's legal moves
            for (Square legalMove: legalMoves) {
                if (legalMove.equals(targetSquare)) {
                    if (player.makeMove(this.board, originSquare, targetSquare)) {
                        moveCounter++;
                    }
                    break;
                }
            }
        }
        this.board.printBoard();
        if (moveCounter % 2 == 0) {
            System.out.println("Black wins!");
        } else {
            System.out.println("White wins!");
        }

    }

}
