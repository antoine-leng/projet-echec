package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.robots.RobotPlayer;

import java.util.List;

public class TurnManager {
    private final List<RobotPlayer> players; // Liste des joueurs
    private int currentPlayerIndex = 0;     // Index du joueur courant

    public TurnManager(List<RobotPlayer> players) {
        this.players = players;
    }

    public void playTurn(ChessBoard board, GameState gameState) {
        RobotPlayer currentPlayer = players.get(currentPlayerIndex);

        // Passer les joueurs éliminés
        while (gameState.getEliminatedPlayers().contains(currentPlayer.getColor())) {
            nextTurn();
            currentPlayer = players.get(currentPlayerIndex);
        }

        System.out.println("Tour du joueur : " + currentPlayer.getColor());

        // Exécuter un mouvement
        Action action = currentPlayer.playTurn(board);
        if (action != null) {
            try {
                // Déplacer la pièce et vérifier les règles
                board.movePiece(action.getStartRow(), action.getStartCol(),
                        action.getTargetRow(), action.getTargetCol());
                System.out.println("Joueur " + currentPlayer.getColor() + " a joué : " +
                        "(" + action.getStartRow() + ", " + action.getStartCol() + ") -> (" +
                        action.getTargetRow() + ", " + action.getTargetCol() + ")");

                // Vérifier si un roi adverse est en échec
                for (String opponentColor : gameState.getActivePlayers()) {
                    if (!opponentColor.equals(currentPlayer.getColor()) && board.isKingInCheck(opponentColor)) {
                        System.out.println("Le joueur " + opponentColor + " est en échec !");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Mouvement invalide : " + e.getMessage());
            }
        } else {
            System.out.println("Joueur " + currentPlayer.getColor() + " n'a pas de mouvement disponible.");
        }

        // Vérifier le pat
        if (board.isPlayerInPat(currentPlayer.getColor())) {
            System.out.println("Le joueur " + currentPlayer.getColor() + " est en pat !");
            gameState.eliminatePlayer(currentPlayer.getColor());
        }

        // Passer au prochain joueur
        nextTurn();
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}