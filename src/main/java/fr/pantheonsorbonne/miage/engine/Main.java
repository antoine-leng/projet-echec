package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.board.BoardInitializer;
import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.robots.RandomBot;
import fr.pantheonsorbonne.miage.robots.RobotPlayer;
import fr.pantheonsorbonne.miage.game.TurnManager;
import fr.pantheonsorbonne.miage.game.GameState;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ChessBoard board = BoardInitializer.initialize();


        List<RobotPlayer> robots = new ArrayList<>();
        robots.add(new RandomBot("1")); // Rouge
        robots.add(new RandomBot("2")); // Vert
        robots.add(new RandomBot("3")); // Jaune
        robots.add(new RandomBot("4")); // Bleu

        GameState gameState = new GameState();
        gameState.initializePlayers("1", "2", "3", "4");

 
        TurnManager turnManager = new TurnManager(robots);


        System.out.println("Début de la partie !");
        while (!gameState.isGameOver()) {
            System.out.println("\nPlateau actuel :");
            board.display();

            turnManager.playTurn(board, gameState);

            System.out.println("\nScores actuels :");
            gameState.displayScores();

            System.out.println("\nJoueurs actifs :");
            gameState.getActivePlayers().forEach(
                    player -> System.out.println("Joueur " + player + ": " + gameState.getScore(player) + " points"));

            System.out.println("\nJoueurs éliminés :");
            gameState.getEliminatedPlayers().forEach(System.out::println);

            gameState.checkEndConditions();
        }

        System.out.println("\nFin de la partie !");
        System.out.println("Joueur gagnant : " + gameState.getLeadingPlayer());
    }
}   
