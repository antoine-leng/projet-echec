package fr.pantheonsorbonne.miage.pieces.special;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import java.util.ArrayList;
import java.util.List;

public class SpecialRook extends SpecialPiece {

    public SpecialRook(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] direction : directions) {
            for (int step = 1; step <= 3; step++) { // Super Tour : portée limitée à 3 cases
                int newRow = row + direction[0] * step;
                int newCol = col + direction[1] * step;
                if (!board.isValidCell(newRow, newCol)) break;

                ChessPiece target = board.getPiece(newRow, newCol);
                if (target == null) {
                    moves.add(new int[]{newRow, newCol});
                } else {
                    if (!target.getColor().equals(this.color)) {
                        moves.add(new int[]{newRow, newCol});
                    }
                    break;
                }
            }
        }
        return moves;
    }

    @Override
    public void crush(ChessBoard board, int targetRow, int targetCol) {
        // Logique d'élimination des pièces sur le chemin
        ChessPiece target = board.getPiece(targetRow, targetCol);
        if (target != null) {
            board.setPiece(targetRow, targetCol, null); // Supprime la pièce
        }
    }

    @Override
    public char getSymbol() {
        return 'S'; // Symbole pour la super tour
    }
}
