package fr.pantheonsorbonne.miage.robots;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.game.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBot extends RobotPlayer {

    private Random random = new Random();

    public RandomBot(String color) {
        super(color);
    }

    @Override
    public Action playTurn(ChessBoard board) {
        List<Action> legalMoves = new ArrayList<>();


        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {

                if (!board.isValidCell(row, col)) {
                    continue;
                }

                ChessPiece piece = board.getPiece(row, col);

                if (piece != null && piece.getColor().equals(this.color)) {
                    List<int[]> possibleMoves = piece.getPossibleMoves(board);
                    for (int[] move : possibleMoves) {
                        if (board.isValidCell(move[0], move[1])) {
                            legalMoves.add(new Action(row, col, move[0], move[1]));
                        }
                    }
                }
            }
        }

        if (legalMoves.isEmpty()) {
            return null;
        }


        return legalMoves.get(random.nextInt(legalMoves.size()));
    }
}
