package board;

import pieces.*;
import java.util.ArrayList;

public class Board {
    Piece[][] board = new Piece[8][8];

    public Board() {
        // White major pieces
        board[0][0] = new Rook();
        board[0][1] = new Knight();
        board[0][2] = new Bishop();
        board[0][3] = new Piece();
        board[0][4] = new Piece();
        board[0][5] = new Piece();
        board[0][6] = new Piece();
        board[0][7] = new Piece();

        // Black pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(1, i);
        }

        // White pawns
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Piece();
        }

        // Black major pieces
        board[7][0] = new Piece();
        board[7][1] = new Piece();
        board[7][2] = new Piece();
        board[7][3] = new Piece();
        board[7][4] = new Piece();
        board[7][5] = new Piece();
        board[7][6] = new Piece();
        board[7][7] = new Piece();
    }
}
