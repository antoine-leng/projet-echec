package fr.pantheonsorbonne.miage.pieces.normal;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class Knight extends NormalPiece {

    public Knight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}  
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (board.isValidCell(newRow, newCol)) {
                ChessPiece target = board.getPiece(newRow, newCol);
                if (target == null || !target.getColor().equals(this.color)) {
                    moves.add(new int[]{newRow, newCol});
                }
            }
        }
        return moves;
    }

    @Override
    public char getSymbol() {
        return 'N'; 
    }
}
