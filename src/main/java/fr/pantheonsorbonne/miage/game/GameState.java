package fr.pantheonsorbonne.miage.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameState {
    private final Map<String, Integer> scores = new HashMap<>();
    private boolean gameOver = false;

    public void initializePlayers(String... playerColors) {
        for (String color : playerColors) {
            scores.put(color, 0); 
        }
    }

    public void updateScore(String color, int points) {
        scores.put(color, scores.getOrDefault(color, 0) + points);
    }

    public int getScore(String color) {
        return scores.getOrDefault(color, 0);
    }

    public String getLeadingPlayer() {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void displayScores() {
        System.out.println("Scores actuels :");
        scores.forEach((color, score) -> System.out.println("Joueur " + color + ": " + score + " points"));
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void checkEndConditions() {

        long activePlayers = scores.keySet().stream().filter(player -> !eliminatedPlayers.contains(player)).count();
        if (activePlayers <= 1) {
            setGameOver(true);
        }

        String leader = getLeadingPlayer();
        if (leader != null) {
            int leaderScore = getScore(leader);
            boolean canClaimVictory = scores.values().stream().allMatch(score -> leaderScore - score >= 21);
            if (canClaimVictory) {
                System.out.println("Le joueur " + leader + " revendique la victoire !");
                setGameOver(true);
            }
        }
    }

    private final Set<String> eliminatedPlayers = new HashSet<>();


    public void eliminatePlayer(String color) {
        eliminatedPlayers.add(color);
        System.out.println("Le joueur " + color + " est éliminé !");
    }
    
    public Set<String> getEliminatedPlayers() {
        return new HashSet<>(eliminatedPlayers);
    }

    public Set<String> getActivePlayers() {
        return scores.keySet().stream()
                .filter(player -> !eliminatedPlayers.contains(player))
                .collect(Collectors.toSet());
    }


    

}
