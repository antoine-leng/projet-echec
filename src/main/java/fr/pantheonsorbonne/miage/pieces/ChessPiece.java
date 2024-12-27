package fr.pantheonsorbonne.miage.pieces;

import java.util.List;
import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.utils.ColorUtil;

// Classe de base abstraite pour toutes les pièces d'échecs
public abstract class ChessPiece {
    protected String color; // Couleur du joueur (1: Rouge, 2: Vert, 3: Jaune, 4: Bleu)
    protected int row, col;
    protected String dynamicColor; // Couleur modifiable pour l'affichage (ex: gris)

    public ChessPiece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public void setColor(String dynamicColor) {
        this.dynamicColor = dynamicColor;
    }

    // Retourne la couleur du joueur
    public String getColor() {
        return color;
    }

    // Retourne la position actuelle de la pièce
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

    // Méthode pour récupérer l'icône Unicode de la pièce, avec couleur
    public String getUnicode() {
        String baseSymbol;

        // Obtenir le symbole Unicode de base
        char symbol = getSymbol();
        if (symbol == 'K') {
            baseSymbol = "\u265A"; // Roi ♚
        } else if (symbol == 'Q') {
            baseSymbol = "\u265B"; // Reine ♛
        } else if (symbol == 'R') {
            baseSymbol = "\u265C"; // Tour ♜
        } else if (symbol == 'B') {
            baseSymbol = "\u265D"; // Fou ♝
        } else if (symbol == 'N') {
            baseSymbol = "\u265E"; // Cavalier ♞
        } else if (symbol == 'P') {
            baseSymbol = "\u265F"; // Pion ♟
        } else {
            baseSymbol = "?";
        }

        // Ajouter la couleur en fonction du joueur
        if (color.equals("1")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.RED); // Rouge
        } else if (color.equals("2")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.GREEN); // Vert
        } else if (color.equals("3")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.YELLOW); // Jaune
        } else if (color.equals("4")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.BLUE); // Bleu
        } else {
            return baseSymbol; // Sans couleur
        }
    }

    // Méthode pour distinguer les pièces spéciales avec un "s" (suffixe)
    public String getNotation() {
        String unicode = getUnicode();
        return isSpecialPiece() ? unicode + "s" : unicode;
    }

    // Vérifie si la pièce est une pièce spéciale (super-pièce)
    public abstract boolean isSpecialPiece();

    // Retourne les actions possibles pour la pièce
    public abstract List<int[]> getPossibleActions(ChessBoard board);

    // Retourne le symbole de la pièce (ex: K pour King, Q pour Queen, etc.)
    public abstract char getSymbol();
}
