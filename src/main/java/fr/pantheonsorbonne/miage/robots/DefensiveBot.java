package fr.pantheonsorbonne.miage.robots;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.game.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefensiveBot extends RobotPlayer {
    private Random random = new Random();

    public DefensiveBot(String color) {
        super(color);
    }

    @Override
    public Action playTurn(ChessBoard board) {
        List<Action> safeMoves = new ArrayList<>();
        List<Action> legalMoves = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor().equals(this.color)) {
                    List<int[]> possibleMoves = piece.getPossibleActions(board);
                    for (int[] move : possibleMoves) {
                        Action action = new Action(row, col, move[0], move[1]);
                        if (isSafeMove(board, action)) {
                            safeMoves.add(action);
                        }
                        legalMoves.add(action);
                    }
                }
            }
        }

        if (!safeMoves.isEmpty()) {
            return safeMoves.get(random.nextInt(safeMoves.size()));
        } else if (!legalMoves.isEmpty()) {
            return legalMoves.get(random.nextInt(legalMoves.size()));
        }
        return null;
    }

    private boolean isSafeMove(ChessBoard board, Action action) {

        return true;
    }
}
