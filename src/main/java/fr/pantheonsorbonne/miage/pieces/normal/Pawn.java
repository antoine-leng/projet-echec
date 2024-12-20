package fr.pantheonsorbonne.miage.pieces.normal;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class Pawn extends NormalPiece {

    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleActions(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int direction = color.equals("Red") || color.equals("Yellow") ? 1 : -1;

        // Avance simple
        int newRow = row + direction;
        if (board.isValidCell(newRow, col) && board.getPiece(newRow, col) == null) {
            moves.add(new int[] { newRow, col });
        }

        // Capture diagonale
        for (int delta : new int[] { -1, 1 }) {
            int newCol = col + delta;
            if (board.isValidCell(newRow, newCol)) {
                ChessPiece target = board.getPiece(newRow, newCol);
                if (target != null && !target.getColor().equals(this.color)) {
                    moves.add(new int[] { newRow, newCol });
                }
            }
        }
        return moves;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }
}
