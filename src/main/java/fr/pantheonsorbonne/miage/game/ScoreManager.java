package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class ScoreManager {
    private final GameState gameState;

    public ScoreManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void updateScoreForCapture(String playerColor, ChessPiece capturedPiece) {
        if (capturedPiece != null) {
            int points = 0;
            String pieceName = capturedPiece.getClass().getSimpleName();
    
            if (pieceName.equals("Pawn")) {
                points = 1;
            } else if (pieceName.equals("Knight") || pieceName.equals("Bishop")) {
                points = 3;
            } else if (pieceName.equals("Rook")) {
                points = 5;
            } else if (pieceName.equals("Queen")) {
                points = 9;
            } else if (pieceName.equals("King")) { // Exemple, si le roi est capturé
                points = 20;
            }
    
            // Met à jour le score pour la couleur donnée
            gameState.updateScore(playerColor, points);
        }
    }
    

    public void updateScoreForSpecialMove(String playerColor, int points) {
        gameState.updateScore(playerColor, points);
    }
}
