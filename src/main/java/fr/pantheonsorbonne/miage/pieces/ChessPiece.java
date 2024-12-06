package fr.pantheonsorbonne.miage.pieces;

import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;

public abstract class ChessPiece {
    protected String color; // Couleur : "1", "2", "3", "4"
    protected int row, col; // Position actuelle

    public ChessPiece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Génère la notation de la pièce.
     * Exemple : 1P pour une tour rouge normale, 1Ps pour une tour rouge spéciale.
     */
    public String getNotation() {
        String pieceType = this.getClass().getSimpleName().substring(0, 1).toUpperCase(); // Exemple : "P" pour Pawn
        String specialSuffix = isSpecialPiece() ? "s" : ""; // Ajoute "s" si la pièce est spéciale
        return color + pieceType + specialSuffix;
    }

    // Méthode abstraite pour vérifier si la pièce est spéciale
    public abstract boolean isSpecialPiece();

    // Méthode abstraite pour obtenir les mouvements possibles
    public abstract List<int[]> getPossibleMoves(ChessBoard board);

    // Méthode abstraite pour obtenir le symbole (affichage, peut être supprimé si non utilisé)
    public abstract char getSymbol();
}
