package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.robots.RobotPlayer;
import fr.pantheonsorbonne.miage.utils.ColorUtil;

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
        pause(); // Pause avant le début du tour

        // Vérifier si le roi du joueur est en échec
        if (board.isKingInCheck(currentPlayer.getColor())) {
            System.out.println("Le roi du joueur " + currentPlayer.getColor() + " est en échec !");

            // Vérifier s'il y a des coups légaux
            List<String> legalMoves = board.getLegalMovesForPlayer(currentPlayer.getColor());
            if (legalMoves.isEmpty()) {
                System.out.println("Le joueur " + currentPlayer.getColor() + " est en échec et mat !");
                gameState.eliminatePlayer(currentPlayer.getColor());
                board.removePlayerPieces(currentPlayer.getColor());
                nextTurn();
                return;
            } else {
                System.out.println("Coups légaux disponibles pour sortir de l'échec :");
                for (String move : legalMoves) {
                    System.out.println(move);
                }

            }
            
        }

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
                pause(); // Pause après l'affichage du mouvement

                // Vérifier si un roi adverse est en échec
                for (String opponentColor : gameState.getActivePlayers()) {
                    if (!opponentColor.equals(currentPlayer.getColor()) && board.isKingInCheck(opponentColor)) {
                        String colorizedPlayer = ColorUtil.colorize("Joueur " + opponentColor, getColorCode(opponentColor));
                        String message = ColorUtil.colorize(" est en échec !", ColorUtil.PURPLE);
                        System.out.println(colorizedPlayer + message);
                        pause(); // Pause après un échec
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Mouvement invalide : " + e.getMessage());
                pause();
            }
        } else {
            System.out.println("Joueur " + currentPlayer.getColor() + " n'a pas de mouvement disponible.");
            pause();
        }

        // Vérifier si le joueur actuel est en pat
        if (board.isPlayerInPat(currentPlayer.getColor())) {
            String patMessage = ColorUtil.colorize("Le joueur " + currentPlayer.getColor() + " est en pat !", ColorUtil.PURPLE);
            System.out.println(patMessage);
            pause();

            // Éliminer le joueur et supprimer ses pièces
            gameState.eliminatePlayer(currentPlayer.getColor());
            board.removePlayerPieces(currentPlayer.getColor());
            pause();
        }

        // Passer au prochain joueur
        nextTurn();
        pause(); // Pause avant le prochain tour
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private String getColorCode(String playerColor) {
        return switch (playerColor) {
            case "1" -> ColorUtil.RED;    // Rouge
            case "2" -> ColorUtil.GREEN;  // Vert
            case "3" -> ColorUtil.YELLOW; // Jaune
            case "4" -> ColorUtil.BLUE;   // Bleu
            default -> ColorUtil.RESET;   // Par défaut
        };
    }

    private void pause() {
        try {
            Thread.sleep(1); // Pause de 100 millisecondes = 0,1 seconde
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Pause interrompue : " + e.getMessage());
        }
    }
}
