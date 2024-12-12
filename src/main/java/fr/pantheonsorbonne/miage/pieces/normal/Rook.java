package fr.pantheonsorbonne.miage.pieces.normal;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class Rook extends NormalPiece {

    public Rook(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] direction : directions) {
            for (int step = 1; step < 14; step++) {
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
    public char getSymbol() {
        return 'R';
    }
}
