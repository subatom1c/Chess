package board;

import pieces.*;
import java.util.List;
import java.util.ArrayList;
import moves.*;

public class Board {

    private Piece[][] chessBoard = new Piece[8][8];

    public Board() {
        // the rook
        chessBoard[0][0] = new Rook(Piece.WHITE);
        chessBoard[0][7] = new Rook(Piece.WHITE);
        chessBoard[7][0] = new Rook(Piece.BLACK);
        chessBoard[7][7] = new Rook(Piece.BLACK);

        // knight
        chessBoard[0][1] = new Knight(Piece.WHITE);
        chessBoard[0][6] = new Knight(Piece.WHITE);
        chessBoard[7][1] = new Knight(Piece.BLACK);
        chessBoard[7][6] = new Knight(Piece.BLACK);

        // bishop
        chessBoard[0][2] = new Bishop(Piece.WHITE);
        chessBoard[0][5] = new Bishop(Piece.WHITE);
        chessBoard[7][2] = new Bishop(Piece.BLACK);
        chessBoard[7][5] = new Bishop(Piece.BLACK);

        // queen
        chessBoard[0][3] = new Queen(Piece.WHITE);
        chessBoard[7][3] = new Queen(Piece.BLACK);

        // king
        chessBoard[0][4] = new King(Piece.WHITE);
        chessBoard[7][4] = new King(Piece.BLACK);

        // pawns
        for (int col = 0; col < 8; col++) {
            chessBoard[1][col] = new Pawn(Piece.WHITE);
            chessBoard[6][col] = new Pawn(Piece.BLACK);
        }

        // Empty spaces in rows 2 to 5
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                chessBoard[row][col] = new EmptyPiece();
            }
        }
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return chessBoard[y][x];
    }

    public Move coordsCoverter(char letter, int number) {
        return new Move((int)(letter) - 97, number - 1);
    }

    public void setPiece(Piece piece, int x, int y) {
        this.chessBoard[y][x] = piece;
    }

    // Calculate possible moves for certain piece
    public List<Move> calculateLegalMoves(int pieceX, int pieceY) {
        // Piece attributes

        Piece piece = chessBoard[pieceY][pieceX];
        boolean jumpOverPieces = piece.canJumpOverPieces();
        boolean hasMoved = piece.hasMoved();
        int color = piece.getColor();
        List<MoveRule> captureMoves = piece.getMovesCapture();
        List<MoveRule> moves = piece.getMoves();
        // Legal Moves
        List<Move> legalMoves = new ArrayList<>();

        // Idea - Iterate through each move, Iterate through range of numberOfTimes
        for (MoveRule move: captureMoves) {
            int numberOfTimes = move.getNumberOfTimes();
            int x = move.getX();
            int y = move.getY();
            for (int i = 1; i <= numberOfTimes; i++) {
                    int toTestY = pieceY + y*i;
                    int toTestX = pieceX + x*i;
                    if (toTestY < 0 || toTestY > 7 || toTestX < 0 || toTestX > 7) {
                        break;
                    }
                    // Square has same color piece
                    if (getPiece(toTestX, toTestY).getColor() == color) {
                        break;
                    }
                    // if square has enemy's piece
                    if (!getPiece(toTestX, toTestY).isEmpty()){
                        legalMoves.add(new Move(toTestX,toTestY));
                        break;
                    }
                    // if square is empty lets continue
            }
        }
        for (MoveRule move: moves) {
            int numberOfTimes = move.getNumberOfTimes();
            int x = move.getX();
            int y = move.getY();
            for (int i = 1; i <= numberOfTimes; i++) {
                int toTestY = pieceY + y*i;
                int toTestX = pieceX + x*i;
                if (toTestY < 0 || toTestY > 7 || toTestX < 0 || toTestX > 7) {
                    break;
                }
                // Add if square is empty, break when piece is found
                if (getPiece(toTestX, toTestY).isEmpty()) {
                    legalMoves.add(new Move(toTestX, toTestY));
                } else {
                    break;
                }
            }
        }

        return legalMoves;
    }

    public boolean move(Move oldPosition, Move newPosition) {
        int pieceX = oldPosition.getX();
        int pieceY = oldPosition.getY();
        int newX = newPosition.getX();
        int newY = newPosition.getY();
        Piece piece = getPiece(pieceX, pieceY);
        List<Move> legalMoves = this.calculateLegalMoves(pieceX, pieceY);
        for (Move move: legalMoves) {
            int x = move.getX();
            int y = move.getY();
            if (newX == x && newY == y) {
                tradeSquares(pieceX, pieceY, newX, newY);
                piece.moves(); // piece has moved now
                return true;
            }
        }
        return false;
    }

    public void tradeSquares(int piece1X, int piece1Y, int piece2X, int piece2Y) {
        Piece piece1 = getPiece(piece1X, piece1Y);
        Piece piece2 = new EmptyPiece();
        setPiece(piece1, piece2X, piece2Y);
        setPiece(piece2, piece1X, piece1Y);
    }

    public void printBoard() {
        for (int row = 7; row >= 0; row--) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < 8; col++) {
                System.out.print(getPiece(col, row));
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int letter = 97; letter <= 104; letter++) {
            System.out.print("\u2008" + (char)letter + "\u2008");
        }
        System.out.println("");
    }

    // idea - Checks if piece has direct access to the king
    public boolean isCheck(char letter, int number) {
        int col = number + 1;
        int row = (int) letter - 97;
        System.out.println("checking check of " + row+ col);
        // row, col are the kings coordinates
        // now we have to verify every direction
        List<MoveRule> directions = new ArrayList<>(List.of(
                new MoveRule(-1, 1, 8),
                new MoveRule(1, 1, 8),
                new MoveRule(-1, -1, 8),
                new MoveRule(1, -1, 8),
                new MoveRule(1,0,8),
                new MoveRule(-1, 0, 8),
                new MoveRule(0, 1, 8),
                new MoveRule(0, -1, 8),
                new MoveRule(-1, 2, 1),
                new MoveRule(-1, -2, 1),
                new MoveRule(1, 2, 1),
                new MoveRule(1, -2, 1),
                new MoveRule(-2, 1, 1),
                new MoveRule(-2, -1, 1),
                new MoveRule(2, 1, 1),
                new MoveRule(2, -1, 1)
        ));

        for (MoveRule move: directions) {
            int numberOfTimes = move.getNumberOfTimes();
            int x = move.getX();
            int y = move.getY();
            for (int i = 1; i <= numberOfTimes; i++) {
                int toTestY = row + y*i;
                int toTestX = col + x*i;
                if (toTestY < 0 || toTestY > 7 || toTestX < 0 || toTestX > 7) {
                    break;
                }
                // If there's a piece and color is different from the kings
                Piece piece = this.getPiece(toTestX, toTestY);
                Piece king = this.getPiece(col, row);
                System.out.println(king);
                System.out.println("checking colors = " + piece.getColor() + king.getColor());
                if (!getPiece(toTestX, toTestY).isEmpty() && getPiece(toTestX, toTestY).getColor() != getPiece(col, row).getColor()) {
                    System.out.println("we found a piece attacking the king");
                    System.out.println("lets see if its attacking us!");
                    System.out.println(piece);
                    System.out.println(king);
                    List<Move> attackingPieceMoves = this.calculateLegalMoves(toTestX, toTestY);
                    for (Move attackingMove: attackingPieceMoves) {
                        if (attackingMove.getX() == col && attackingMove.getY() == row) {
                            return true;
                        }
                    }
                    break;
                    }
                }
            }
        return false;
    }

    public boolean isCheckMate(char letter, int number){return false;}

}
