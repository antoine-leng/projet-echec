package fr.pantheonsorbonne.miage.pieces.special;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public abstract class SpecialPiece extends ChessPiece {

    public SpecialPiece(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isSpecialPiece() {
        return true; // Les pièces spéciales sont identifiées comme telles
    }

    // Pouvoir d'écrasement (élimine les pièces sur son chemin)
    public abstract void crush(ChessBoard board, int targetRow, int targetCol);
}
