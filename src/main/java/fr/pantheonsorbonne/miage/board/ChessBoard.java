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
        final String HORIZONTAL_LINE = "   " + "-----".repeat(cols); // Ligne horizontale pour chaque rangée

        // Affichage des indices des colonnes
        System.out.print("  "); // Espace pour les indices de lignes
        for (int col = 0; col < cols; col++) {
            System.out.printf(" %3d ", col);
        }
        System.out.println();

        // Affichage des lignes
        for (int i = 0; i < rows; i++) {
            System.out.println(HORIZONTAL_LINE); // Ligne horizontale

            // Indice de la ligne
            System.out.printf("%2d |", i);
            for (int j = 0; j < cols; j++) {
                String content;

                // Contenu de la case : pièce ou vide
                if (!isValidCell(i, j)) {
                    content = "##";
                } else {
                    ChessPiece piece = grid[i][j];
                    content = (piece != null) ? piece.getNotation() + "  " : " "; // Ajout de 4 espaces
                }

                // Affichage avec un espacement fixe (6 caractères pour chaque case)
                System.out.printf(" %-3s|", content);
            }
            System.out.println(); // Nouvelle ligne pour la prochaine rangée
        }

        // Dernière ligne horizontale
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
            return false; // Hors limites du tableau
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

        System.out.println("Déplacement : " + piece.getNotation() + " de (" + row + ", " + col + ") à (" + targetRow
                + ", " + targetCol + ")");

        setPiece(targetRow, targetCol, piece);
        setPiece(row, col, null);
        piece.setPosition(targetRow, targetCol);
    }

    private void moveSpecialPiece(SpecialPiece specialPiece, int row, int col, int targetRow, int targetCol) {
        int dRow = Integer.signum(targetRow - row);
        int dCol = Integer.signum(targetCol - col);

        int currentRow = row + dRow;
        int currentCol = col + dCol;

        while (currentRow != targetRow || currentCol != targetCol) {
            ChessPiece target = getPiece(currentRow, currentCol);

            if (target != null) {
                if (target instanceof SpecialPiece || target.getSymbol() == 'K') {
                    throw new IllegalArgumentException(
                            "Pièce spéciale bloquée par un roi ou une autre pièce spéciale.");
                }
                setPiece(currentRow, currentCol, null);
            }

            currentRow += dRow;
            currentCol += dCol;
        }

        setPiece(targetRow, targetCol, specialPiece);
        setPiece(row, col, null);
        specialPiece.setPosition(targetRow, targetCol);
    }

    public void promotePawn(int row, int col) {
        ChessPiece piece = getPiece(row, col);
        if (piece instanceof Pawn) {
            String color = piece.getColor();
            setPiece(row, col, new Queen(color, row, col));
            System.out.println("Pion promu en reine !");
        }
    }

    public boolean isEnemyPiece(int row, int col, String color) {
        ChessPiece piece = getPiece(row, col);
        return piece != null && !piece.getColor().equals(color);
    }

    public boolean isKingInCheck(String kingColor) {
        int kingRow = -1, kingCol = -1;

        // Trouver la position du roi
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

        // Si le roi est absent, retourner false (pas en échec)
        if (kingRow == -1 || kingCol == -1) {
            System.out.println("Roi de la couleur " + kingColor + " absent du plateau.");
            return false;
        }

        // Vérifier si une pièce adverse peut capturer le roi
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) {
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && !piece.getColor().equals(kingColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (move[0] == kingRow && move[1] == kingCol) {
                                return true; // Le roi est en échec
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isPlayerInPat(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) { // Vérifier d'abord si la cellule est valide
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(playerColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (isValidCell(move[0], move[1])) { // Vérification de la cellule cible
                                ChessPiece temp = getPiece(move[0], move[1]);
                                setPiece(move[0], move[1], piece);
                                setPiece(row, col, null);
                                boolean inCheck = isKingInCheck(playerColor);
                                setPiece(row, col, piece);
                                setPiece(move[0], move[1], temp);
                                if (!inCheck)
                                    return false; // Il existe un mouvement légal
                            }
                        }
                    }
                }
            }
        }
        return true; // Aucun mouvement légal
    }

}
