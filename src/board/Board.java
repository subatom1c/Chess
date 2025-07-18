package board;

import pieces.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Piece[][] chessBoard = new Piece[8][8];

    public Board() {
        // rooks
        chessBoard[0][0] = new Rook(Piece.WHITE);
        chessBoard[0][7] = new Rook(Piece.WHITE);
        chessBoard[7][0] = new Rook(Piece.BLACK);
        chessBoard[7][7] = new Rook(Piece.BLACK);

        // knights
        chessBoard[0][1] = new Knight(Piece.WHITE);
        chessBoard[0][6] = new Knight(Piece.WHITE);
        chessBoard[7][1] = new Knight(Piece.BLACK);
        chessBoard[7][6] = new Knight(Piece.BLACK);

        // bishops
        chessBoard[0][2] = new Bishop(Piece.WHITE);
        chessBoard[0][5] = new Bishop(Piece.WHITE);
        chessBoard[7][2] = new Bishop(Piece.BLACK);
        chessBoard[7][5] = new Bishop(Piece.BLACK);

        // queens
        chessBoard[0][3] = new Queen(Piece.WHITE);
        chessBoard[7][3] = new Queen(Piece.BLACK);

        // kings
        chessBoard[0][4] = new King(Piece.WHITE);
        chessBoard[7][4] = new King(Piece.BLACK);

        // pawnss
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


    public Piece getPiece(Square square) {
        if (square.getX() < 0 || square.getX() > 7 || square.getY() < 0 || square.getY() > 7) {
            return null;
        }
        return chessBoard[square.getY()][square.getX()];
    }

    public void setPiece(Piece piece, Square square) {
        this.chessBoard[square.getY()][square.getX()] = piece;
    }


    // Find certain color's king
    // Returns kings square
    public Square findKing(int color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (this.getPiece(new Square(col, row)) instanceof pieces.King king) {
                    if (king.getColor() == color) {
                        return new Square(col, row);
                    }
                }
            }
        }
        return null;
    }

    // Filters kings moves based on probable checks
    public void filterKingMoves(int kingColor, List<Square> legalMoves) {
        filterAttackedSquaresKing(kingColor, legalMoves);
        filterOppositeDirectionKing(kingColor, legalMoves);
    }

    // Filters squares that are attacked
    public void filterAttackedSquaresKing(int defendingColor, List<Square> legalMoves) {
        for (int i = legalMoves.size() - 1; i >= 0; i--) {
            Square square = legalMoves.get(i);
            if (!getSquareAttackers(defendingColor ,square).isEmpty()) {
                legalMoves.remove(i);
            }
        }
    }

    // Filter squares for when the king is attacked in a certain direction but cannot move more in that direction
    public void filterOppositeDirectionKing (int kingColor, List<Square> legalMoves) {
        Square king = findKing(kingColor);

        List<List<Square>> allCheckingMoves = getSquareAttackers(kingColor, king);
        for (List<Square> checkingMoves: allCheckingMoves) {

            Square direction = king.subtract(checkingMoves.get(0)).normalize();
            for (int i = 0; i < 8; i++) {
                Square testingSquare = new Square(direction.getX()*i + king.getX(), direction.getY()*i + king.getY());
                List<Square> toRemove = new ArrayList<>();
                for (Square legalMove : legalMoves) {
                    if (legalMove.equals(testingSquare)) {
                        toRemove.add(legalMove);
                    }
                }
                legalMoves.removeAll(toRemove);
            }
        }
    }


    // Calculate capturing moves for a piece on a certain square
    public List<Square> calculateCapturingMoves (Square square) {

        Piece piece = this.getPiece(square);
        if (isOutsideBoard(square)) {
            return null;
        }

        int color = piece.getColor();
        
        List<Move> capturingDirections = piece.getMovesCapture();
        List<Square> capturingMoves = new ArrayList<>();

        List<Square> pinsDirections = getPinsDirection(square);

        if (getNumberOfPins(square) == 2) {
            return new ArrayList<>();
        }
        boolean isPinned = getNumberOfPins(square) == 1;

        // Get capture direction and number of times piece can move at once and iterate through all the moves
        for (Move move: capturingDirections) {
            int numberOfTimes = move.getNumberOfTimes();
            for (int i = 1; i <= numberOfTimes; i++) {

                Square testSquare = new Square(square.getX() + move.getX()*i, square.getY() + move.getY()*i);

                if (isOutsideBoard(testSquare)) {
                    break;
                }
                // Square has same color piece
                if (getPiece(testSquare).getColor() == color) {
                    break;
                }
                // if square has enemy's piece
                if (!getPiece(testSquare).isEmpty()){
                    // Case that piece is pinned, only add move if it's in the direction of the pin
                    if (!(getPiece(testSquare) instanceof King) && isPinned) {
                        Square moveDirection = new Square(testSquare.getX() - square.getX(), testSquare.getY() - square.getY()).normalize();
                        for (Square pin: pinsDirections) {
                            if (moveDirection.equals(pin)) {
                                capturingMoves.add(testSquare);
                            }
                        }
                        continue;
                    }
                    capturingMoves.add(testSquare);
                    break;
                }
                // if square is empty lets continue
            }
        }         

        if (piece instanceof King) {
            filterKingMoves(color, capturingMoves);
        }

        return capturingMoves;
    }

    // Calculate non-capturing moves for a piece on a certain square
    public List<Square> calculateNonCapturingMoves (Square square) {
        
        Piece piece = getPiece(square);
        if (isOutsideBoard(square)) {
            return null;
        }

        List<Square> pinsDirections = getPinsDirection(square);

        if (getNumberOfPins(square) == 2) {
            return new ArrayList<>();
        }

        boolean isPinned = getNumberOfPins(square) == 1;


        List<Move> capturingDirections = piece.getMoves();
        List<Square> legalMoves = new ArrayList<>();
        for (Move move: capturingDirections) {

            int numberOfTimes = move.getNumberOfTimes();
            for (int i = 1; i <= numberOfTimes; i++) {

                Square testSquare = new Square(square.getX() + move.getX()*i, square.getY() + move.getY()*i);

                if (isOutsideBoard(testSquare)) {
                    break;
                }

                if (getPiece(testSquare).isEmpty()) {

                    // Case that piece is pinned, only add move if it's in the direction of the pin
                    if (!(getPiece(testSquare) instanceof King) && isPinned) {
                        Square moveDirection = new Square(testSquare.getX() - square.getX(), testSquare.getY() - square.getY()).normalize();
                        for (Square pin: pinsDirections) {
                            if (moveDirection.equals(pin)) {
                                legalMoves.add(testSquare);
                            }
                        }
                        continue;
                    }

                    legalMoves.add(testSquare);
                } else {
                    // Break when finding another piece
                    break;
                }
            }
        }
        if (piece instanceof King) {
            filterKingMoves(piece.getColor(), legalMoves);
        }

        return legalMoves;
    }
    
    // Returns the full list of legal moves for a piece
    public List<Square> calculateLegalMoves(Square square) {
        List<Square> legalMoves = this.calculateCapturingMoves(square);
        legalMoves.addAll(this.calculateNonCapturingMoves(square));
        return legalMoves;
    }


    // Executes a move
    public boolean move(Square oldPosition, Square newPosition) {

        Piece piece = getPiece(oldPosition);
        List<Square> legalMoves = this.calculateLegalMoves(oldPosition);

        for (Square move: legalMoves) {
            if (move.equals(newPosition)) {
                tradeSquares(oldPosition, newPosition);
                piece.moves(); // piece has moved now
                return true;
            }
        }
        return false;
    }
    
    public void tradeSquares(Square oldSquare, Square newSquare) {
        setPiece(getPiece(oldSquare), newSquare);
        setPiece(new EmptyPiece(), oldSquare);
    }


    public void printBoard() {
        for (int row = 7; row >= 0; row--) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < 8; col++) {
                System.out.print(getPiece(new Square(col, row)));
                if (!(getPiece(new Square(col, row)) instanceof EmptyPiece)) {
                    if (col % 3 == 0) {
                        System.out.print("\u2006");
                    }
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int letter = 97; letter <= 104; letter++) {
            System.out.print("\u2008" + (char)letter + "\u2008");
        }
        System.out.println();
    }


    // Verifies if a square is outside the board
    public boolean isOutsideBoard(Square square) {
        return square.getX() < 0 || square.getX() > 7 || square.getY() < 0 || square.getY() > 7;
    }


    // Returns all the attackers and the path they take to attack the given square
    public List<List<Square>> getSquareAttackers(int defendingColor, Square originSquare) {

        List<List<Square>> checkingMoves = new ArrayList<>();

        // Test all possible directions, searching for possible attackers
        List<Move> moves = new ArrayList<>(List.of(
                new Move(-1, 1, 8),
                new Move(1, 1, 8),
                new Move(-1, -1, 8),
                new Move(1, -1, 8),
                new Move(1,0,8),
                new Move(-1, 0, 8),
                new Move(0, 1, 8),
                new Move(0, -1, 8),
                new Move(-1, 2, 1),
                new Move(-1, -2, 1),
                new Move(1, 2, 1),
                new Move(1, -2, 1),
                new Move(-2, 1, 1),
                new Move(-2, -1, 1),
                new Move(2, 1, 1),
                new Move(2, -1, 1)
        ));	
        
        for (Move move: moves) {
            List<Square> buffer = new ArrayList<>();
            for (int jump = 1; jump <= move.getNumberOfTimes(); jump++) {
                Square square = new Square(move.getX()*jump + originSquare.getX(), move.getY()*jump + originSquare.getY());
                if (isOutsideBoard(square)) {
                    break;
                }
                buffer.add(square);
                if (!(this.getPiece(square) instanceof EmptyPiece)){
                    if (this.getPiece(square).getColor() != defendingColor) {
                        if (this.getPiece(square).hasDirection(move.invert())){
                            checkingMoves.add(buffer);
                        }
                    }
                    break;
                }
            }
        }
        return checkingMoves;
    }

    // Calculates pins under a certain square
    // Returns a list of all the directions it is pinned
    public List<Square> getPinsDirection(Square defendingSquare) {

        int color = getPiece(defendingSquare).getColor();

        Square dir = getKingPiecePath(defendingSquare);
        if (dir == null) {
            return new ArrayList<>();
        }

        Move direction = new Move(dir.getX(), dir.getY(), 8);
        List<Square> pinsDirection = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            int x = defendingSquare.getX() + i * direction.getX();
            int y = defendingSquare.getY() + i * direction.getY();
            Square square = new Square(x, y);

            if (isOutsideBoard(square)) {
                break;
            }

            if (getPiece(square) instanceof EmptyPiece) {
                continue;
            }

            if (getPiece(square).getColor() != color) {
                if (getPiece(square).hasDirection(direction.invert())) {
                    pinsDirection.add(direction);
                    pinsDirection.add(direction.invert());
                }
                break;
            }
            break;
        }
        return pinsDirection;
    }

    public int getNumberOfPins(Square defendingSquare) {
        return getPinsDirection(defendingSquare).size() / 2;
    }


    // Returns direction the piece is alligned with
    public Square getKingPiecePath(Square pieceSquare) {

        Square kingSquare = findKing(getPiece(pieceSquare).getColor());
        if (kingSquare.isDiagonalHit(pieceSquare) || kingSquare.isHorizontalHit(pieceSquare) || kingSquare.isVerticalHit(pieceSquare)) {
            // Direction is the direction from king to piece
            return pieceSquare.subtract(kingSquare).normalize();
        }
        return null;
    }

    // Tells us if board is checked
    public boolean isChecked (int defendingColor) {
        Square king = this.findKing(defendingColor);

        return !this.getSquareAttackers(defendingColor, king).isEmpty();
    }

    // Checks if a piece can stop a check
    // Returns the squares the piece can jump to, to stop the check
    public List<Square> pieceStopsCheck(Square pieceSquare) {
        int defendingColor = getPiece(pieceSquare).getColor();
        List<Square> defendingCheckMoves = new ArrayList<>();
        List<List<Square>> checkingMoves = getSquareAttackers(defendingColor, findKing(defendingColor));

        if (checkingMoves.size() != 1) {
            return new ArrayList<>();
        }

        for (Square checkedSquare: checkingMoves.get(0)) {
            for (Square legalMove: calculateLegalMoves(pieceSquare)) {
                if (checkedSquare.equals(legalMove)) {
                    defendingCheckMoves.add(checkedSquare);
                }
            }
        }
        return defendingCheckMoves;
    }

    // Verifies if there's any piece on the board that can stop the check
    // Returns Map that maps Piece square to the defending square it needs to move to stopping the check
    public Map<Square,Square> stopsCheck (int defendingColor) {

        Map<Square, Square> stoppingMoves = new HashMap<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = new Square(col, row);
                if (this.getPiece(square).getColor() == defendingColor) {
                    for (Square stoppingMove: pieceStopsCheck(square)) {
                        // Overwrite isn't dangerous, we only need to know if the check can be stopped
                        stoppingMoves.put(square, stoppingMove);
                    }
                }
            }
        }
        return stoppingMoves;
    }

    // Tells us if isCheckmate for a certain color
    public boolean isCheckmate(int defendingColor) {

        Square kingSquare = findKing(defendingColor);

        List<List<Square>> attackingSquare = getSquareAttackers(defendingColor, kingSquare);
        if (attackingSquare.isEmpty()) {
            return false;
        }

        List<Square> legalMoves = calculateLegalMoves(kingSquare);
        for (Square move: legalMoves) {
            if (getSquareAttackers(defendingColor, move).isEmpty()) {
                return false;
            }
        }

        if (attackingSquare.size() >= 2) {
            return true;
        }

        // size of attackingSquare here is 1 always
        Map<Square, Square> stopsCheck = stopsCheck(defendingColor);
        return stopsCheck.keySet().isEmpty();
    }

}
