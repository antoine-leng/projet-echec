package fr.pantheonsorbonne.miage.robots;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.game.Action;

public class AggressiveBot extends RobotPlayer {

    private Random random = new Random();

    public AggressiveBot(String color) {
        super(color);
    }

    @Override
    public Action playTurn(ChessBoard board) {
        List<Action> captureMoves = new ArrayList<>();
        List<Action> legalMoves = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor().equals(this.color)) {
                    List<int[]> possibleMoves = piece.getPossibleActions(board);
                    for (int[] move : possibleMoves) {
                        Action action = new Action(row, col, move[0], move[1]);
                        ChessPiece target = board.getPiece(move[0], move[1]);

                        if (target != null && !target.getColor().equals(this.color)) {
                            captureMoves.add(action);
                        } else {
                            legalMoves.add(action);
                        }
                    }
                }
            }
        }

        if (!captureMoves.isEmpty()) {
            return captureMoves.get(random.nextInt(captureMoves.size()));
        } else if (!legalMoves.isEmpty()) {
            return legalMoves.get(random.nextInt(legalMoves.size()));
        }
        return null;
    }
}
