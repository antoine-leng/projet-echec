package fr.pantheonsorbonne.miage.pieces;

import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;

public abstract class ChessPiece {
    protected String color; 
    protected int row, col; 
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


    public String getNotation() {
        String pieceType = this.getClass().getSimpleName().substring(0, 1).toUpperCase(); 
        String specialSuffix = isSpecialPiece() ? "s" : "";
        return color + pieceType + specialSuffix;
    }


    public abstract boolean isSpecialPiece();


    public abstract List<int[]> getPossibleMoves(ChessBoard board);


    public abstract char getSymbol();
}
