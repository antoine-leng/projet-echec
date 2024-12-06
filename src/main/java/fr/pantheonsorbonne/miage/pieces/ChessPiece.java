package fr.pantheonsorbonne.miage.pieces;

import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;

public abstract class ChessPiece {
    protected String color; // Couleur (Rouge, Bleu, Jaune, Vert)
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

    // Méthode abstraite pour obtenir les mouvements possibles
    public abstract List<int[]> getPossibleMoves(ChessBoard board);

    // Méthode abstraite pour obtenir le symbole (affichage)
    public abstract char getSymbol();

    // Méthode abstraite pour vérifier si la pièce est spéciale
    public abstract boolean isSpecialPiece();
}
