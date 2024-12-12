package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class ScoreManager {
    private final GameState gameState;

    public ScoreManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void updateScoreForCapture(String playerColor, ChessPiece capturedPiece) {
        if (capturedPiece != null) {
            int points = switch (capturedPiece.getClass().getSimpleName()) {
                case "Pawn" -> 1;
                case "Knight", "Bishop" -> 3;
                case "Rook" -> 5;
                case "Queen" -> 9;
                case "King" -> 20; // Exemple, si le roi est capturé
                default -> 0;
            };
            gameState.updateScore(playerColor, points);
        }
    }

    public void updateScoreForSpecialMove(String playerColor, int points) {
        gameState.updateScore(playerColor, points);
    }
}
