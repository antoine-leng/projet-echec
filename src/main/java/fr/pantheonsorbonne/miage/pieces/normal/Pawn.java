package fr.pantheonsorbonne.miage.pieces.normal;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class Pawn extends NormalPiece {

    private boolean firstMove = true; // Indique si le pion n'a pas encore bougé

    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleActions(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = getPawnDirections(); // Obtenir les directions en fonction de la couleur

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Déplacement d'une case
            if (board.isValidCell(newRow, newCol) && board.getPiece(newRow, newCol) == null) {
                moves.add(new int[]{newRow, newCol});

                // Déplacement de deux cases (si premier mouvement)
                if (firstMove) {
                    int newRow2 = newRow + direction[0];
                    int newCol2 = newCol + direction[1];
                    if (board.isValidCell(newRow2, newCol2) && board.getPiece(newRow2, newCol2) == null) {
                        moves.add(new int[]{newRow2, newCol2});
                    }
                }
            }

            // Capture diagonale
            for (int[] diagonal : getPawnDiagonals()) {
                int captureRow = row + diagonal[0];
                int captureCol = col + diagonal[1];
                if (board.isValidCell(captureRow, captureCol)) {
                    ChessPiece target = board.getPiece(captureRow, captureCol);
                    if (target != null && !target.getColor().equals(this.color)) {
                        moves.add(new int[]{captureRow, captureCol});
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        firstMove = false; // Le premier déplacement est effectué
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    // Retourne les directions possibles en fonction de la couleur
    private int[][] getPawnDirections() {
        return switch (this.color) {
            case "1" -> new int[][]{{-1, 0}}; // Rouge : vers le haut
            case "3" -> new int[][]{{1, 0}};  // Jaune : vers le bas
            case "4" -> new int[][]{{0, 1}};  // Bleu : vers la droite
            case "2" -> new int[][]{{0, -1}}; // Vert : vers la gauche
            default -> new int[][]{};
        };
    }

    // Retourne les diagonales possibles pour la capture en fonction de la couleur
    private int[][] getPawnDiagonals() {
        return switch (this.color) {
            case "1" -> new int[][]{{-1, -1}, {-1, 1}}; // Rouge : diagonales vers le haut
            case "3" -> new int[][]{{1, -1}, {1, 1}};   // Jaune : diagonales vers le bas
            case "4" -> new int[][]{{-1, 1}, {1, 1}};   // Bleu : diagonales vers la droite
            case "2" -> new int[][]{{-1, -1}, {1, -1}}; // Vert : diagonales vers la gauche
            default -> new int[][]{};
        };
    }
}
