package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.pieces.normal.*;
import fr.pantheonsorbonne.miage.pieces.special.*;

import java.util.List;

public class ChessBoard {
    private final int rows = 14;
    private final int cols = 14;
    private ChessPiece[][] grid;

    public ChessBoard() {
        this.grid = new ChessPiece[rows][cols];
    }

    public void display() {
        final String HORIZONTAL_LINE = "   " + "-----".repeat(cols);

        System.out.print("  ");
        for (int col = 0; col < cols; col++) {
            System.out.printf(" %3d ", col);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.println(HORIZONTAL_LINE);
            System.out.printf("%2d |", i);
            for (int j = 0; j < cols; j++) {
                String content;
                if (!isValidCell(i, j)) {
                    content = "##";
                } else {
                    ChessPiece piece = grid[i][j];
                    content = (piece != null) ? piece.getNotation() + "  " : " ";
                }
                System.out.printf(" %-3s|", content);
            }
            System.out.println();
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isValidCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        boolean inTopLeftCorner = (row < 3 && col < 3);
        boolean inTopRightCorner = (row < 3 && col >= cols - 3);
        boolean inBottomLeftCorner = (row >= rows - 3 && col < 3);
        boolean inBottomRightCorner = (row >= rows - 3 && col >= cols - 3);
        return !(inTopLeftCorner || inTopRightCorner || inBottomLeftCorner || inBottomRightCorner);
    }

    public ChessPiece getPiece(int row, int col) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Cellule invalide : (" + row + ", " + col + ")");
        }
        return grid[row][col];
    }

    public void setPiece(int row, int col, ChessPiece piece) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Cellule invalide : (" + row + ", " + col + ")");
        }
        grid[row][col] = piece;
    }

    public void attemptFusion(int row, int col, int targetRow, int targetCol) {
        ChessPiece piece1 = getPiece(row, col);
        ChessPiece piece2 = getPiece(targetRow, targetCol);

        if (piece1 == null || piece2 == null || !piece1.getColor().equals(piece2.getColor())) {
            throw new IllegalArgumentException("Fusion impossible : les pièces ne sont pas compatibles.");
        }

        ChessPiece superPiece = null;
        if (piece1 instanceof Rook && piece2 instanceof Rook) {
            superPiece = new SpecialRook(piece1.getColor(), row, col);
        } else if (piece1 instanceof Knight && piece2 instanceof Knight) {
            superPiece = new SpecialKnight(piece1.getColor(), row, col);
        } else if (piece1 instanceof Bishop && piece2 instanceof Bishop) {
            if ((row + col) % 2 != (targetRow + targetCol) % 2) {
                superPiece = new SpecialBishop(piece1.getColor(), row, col);
            }
        } else if (piece1 instanceof Queen && piece2 instanceof Queen) {
            superPiece = new SpecialQueen(piece1.getColor(), row, col);
        }

        if (superPiece != null) {
            setPiece(row, col, superPiece);
            setPiece(targetRow, targetCol, null);
            System.out.println("Fusion réussie !");
        } else {
            throw new IllegalArgumentException("Fusion non autorisée pour ces pièces.");
        }
    }

    public void movePiece(int row, int col, int targetRow, int targetCol) {
        ChessPiece piece = getPiece(row, col);
        if (piece == null) {
            throw new IllegalArgumentException("Aucune pièce à déplacer à (" + row + ", " + col + ")");
        }

        if (!isValidCell(targetRow, targetCol)) {
            throw new IllegalArgumentException("Cellule cible invalide : (" + targetRow + ", " + targetCol + ")");
        }

        if (getPiece(targetRow, targetCol) != null && !isEnemyPiece(targetRow, targetCol, piece.getColor())) {
            throw new IllegalArgumentException(
                    "Cellule (" + targetRow + ", " + targetCol + ") contient une pièce alliée.");
        }

        setPiece(targetRow, targetCol, piece);
        setPiece(row, col, null);
        piece.setPosition(targetRow, targetCol);
    }

    public void promotePawn(int row, int col) {
        ChessPiece piece = getPiece(row, col);
        if (piece instanceof Pawn) {
            setPiece(row, col, new Queen(piece.getColor(), row, col));
            System.out.println("Pion promu en reine !");
        }
    }

    public boolean isKingInCheck(String kingColor) {
        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) {
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(kingColor) && piece.getSymbol() == 'K') {
                        kingRow = row;
                        kingCol = col;
                        break;
                    }
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            return false; // Roi absent
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) {
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && !piece.getColor().equals(kingColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (move[0] == kingRow && move[1] == kingCol) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    

    public boolean isEnemyPiece(int row, int col, String color) {
        ChessPiece piece = getPiece(row, col);
        return piece != null && !piece.getColor().equals(color);
    }

    public boolean isPlayerInPat(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) { // Vérifie si la cellule est valide
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(playerColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (isValidCell(move[0], move[1])) { // Vérifie la validité de la cellule cible
                                ChessPiece temp = getPiece(move[0], move[1]);
                                setPiece(move[0], move[1], piece);
                                setPiece(row, col, null);
                                boolean inCheck = isKingInCheck(playerColor);
                                setPiece(row, col, piece);
                                setPiece(move[0], move[1], temp);
                                if (!inCheck) return false; // Il existe un mouvement légal
                            }
                        }
                    }
                }
            }
        }
        return true; // Aucun mouvement légal disponible
    }
    
    public boolean hasLegalMoves(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!isValidCell(row, col)) {
                    continue; // Ignorer les cellules invalides
                }
    
                ChessPiece piece = getPiece(row, col);
                if (piece != null && piece.getColor().equals(playerColor)) {
                    List<int[]> moves = piece.getPossibleActions(this);
                    for (int[] move : moves) {
                        int targetRow = move[0];
                        int targetCol = move[1];
    
                        if (isValidCell(targetRow, targetCol) && isValidMove(row, col, targetRow, targetCol, playerColor)) {
                            return true; // Un coup légal existe
                        }
                    }
                }
            }
        }
        return false; // Aucun coup légal disponible
    }
    
    
    private boolean isValidMove(int startRow, int startCol, int targetRow, int targetCol, String playerColor) {
        ChessPiece piece = getPiece(startRow, startCol);
        ChessPiece target = getPiece(targetRow, targetCol);
    
        setPiece(targetRow, targetCol, piece); // Simuler le mouvement
        setPiece(startRow, startCol, null);
        piece.setPosition(targetRow, targetCol);
    
        boolean isKingSafe = !isKingInCheck(playerColor);
    
        setPiece(startRow, startCol, piece); // Restaurer l'état
        setPiece(targetRow, targetCol, target);
        piece.setPosition(startRow, startCol);
    
        return isKingSafe; // Retourner si le mouvement est légal
    }

    public void removePlayerPieces(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) { // Vérifier si la cellule est valide
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(playerColor)) {
                        setPiece(row, col, null); // Supprimer la pièce
                    }
                }
            }
        }
    }
    
    
}
