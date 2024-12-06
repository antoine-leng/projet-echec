package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class ChessBoard {
    private final int rows = 14;
    private final int cols = 14;
    private ChessPiece[][] grid;

    public ChessBoard() {
        this.grid = new ChessPiece[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ChessPiece getPiece(int row, int col) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell: (" + row + ", " + col + ")");
        }
        return grid[row][col];
    }

    public void setPiece(int row, int col, ChessPiece piece) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell: (" + row + ", " + col + ")");
        }
        grid[row][col] = piece;
    }

    public boolean isValidCell(int row, int col) {
        boolean inTopLeftCorner = (row < 3 && col < 3);
        boolean inTopRightCorner = (row < 3 && col >= cols - 3);
        boolean inBottomLeftCorner = (row >= rows - 3 && col < 3);
        boolean inBottomRightCorner = (row >= rows - 3 && col >= cols - 3);
        return !(inTopLeftCorner || inTopRightCorner || inBottomLeftCorner || inBottomRightCorner);
    }
}
