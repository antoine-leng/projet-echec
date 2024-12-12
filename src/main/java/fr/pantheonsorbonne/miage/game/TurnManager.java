package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.robots.RobotPlayer;

import java.util.List;

public class TurnManager {
    private final List<RobotPlayer> players;
    private int currentPlayerIndex = 0;

    public TurnManager(List<RobotPlayer> players) {
        this.players = players;
    }

    public void playTurn(ChessBoard board, GameState gameState) {
        RobotPlayer currentPlayer = players.get(currentPlayerIndex);


        while (gameState.getEliminatedPlayers().contains(currentPlayer.getColor())) {

            currentPlayer = players.get(currentPlayerIndex);
        }


        Action action = currentPlayer.playTurn(board);
        if (action != null) {
            board.movePiece(action.getStartRow(), action.getStartCol(),
                    action.getTargetRow(), action.getTargetCol());
            System.out.println("Joueur " + currentPlayer.getColor() + " a joué : " +
                    "(" + action.getStartRow() + ", " + action.getStartCol() + ") -> (" +
                    action.getTargetRow() + ", " + action.getTargetCol() + ")");
        } else {
            System.out.println("Joueur " + currentPlayer.getColor() + " n'a pas de mouvement disponible.");
        }


        nextTurn();
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
