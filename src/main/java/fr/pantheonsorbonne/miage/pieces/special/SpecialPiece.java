package fr.pantheonsorbonne.miage.pieces.special;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public abstract class SpecialPiece extends ChessPiece {
    public SpecialPiece(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isSpecialPiece() {
        return true; // Les pièces spéciales sont identifiées comme telles
    }
}
