package fr.pantheonsorbonne.miage.robots;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.game.Action;
import fr.pantheonsorbonne.miage.pieces.normal.King;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FusionBot extends RobotPlayer {

    private Random random = new Random();

    public FusionBot(String color) {
        super(color);
    }

    @Override
    public Action playTurn(ChessBoard board) {
        List<Action> fusionActions = new ArrayList<>();
        List<Action> legalMoves = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                ChessPiece piece = board.getPiece(row, col);

                if (piece != null && piece.getColor().equals(this.color)) {
                    List<int[]> possibleMoves = piece.getPossibleActions(board);

                    for (int[] move : possibleMoves) {
                        int targetRow = move[0];
                        int targetCol = move[1];
                        ChessPiece targetPiece = board.getPiece(targetRow, targetCol);

                        if (targetPiece != null &&
                                targetPiece.getColor().equals(this.color) &&
                                canFusion(piece, targetPiece)) {
                            fusionActions.add(new Action(row, col, targetRow, targetCol));
                        } else {
                            legalMoves.add(new Action(row, col, targetRow, targetCol));
                        }
                    }
                }
            }
        }

        if (!fusionActions.isEmpty()) {
            Action fusionAction = fusionActions.get(random.nextInt(fusionActions.size()));
            System.out.println("FusionBot prépare une fusion !");
            return fusionAction;
        }

        if (!legalMoves.isEmpty()) {
            return legalMoves.get(random.nextInt(legalMoves.size()));
        }

        return null;
    }

    private boolean canFusion(ChessPiece piece1, ChessPiece piece2) {
        return (piece1.getClass().equals(piece2.getClass()) &&
                !(piece1 instanceof King));
    }
}
