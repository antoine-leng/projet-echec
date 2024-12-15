package fr.pantheonsorbonne.miage.robots;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.game.Action;

import java.util.List;

public abstract class RobotPlayer {
    protected String color;

    public RobotPlayer(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract Action playTurn(ChessBoard board);
}
