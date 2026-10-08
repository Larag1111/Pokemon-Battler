
package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BattleStats {

    private int wins;
    private int losses;

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public void showStats() {
        System.out.println("\n=== BATTLE STATS ===");
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
    }

    public void saveStats() {
        try {
            Files.writeString(Path.of("battle_stats.txt"), wins + "\n" + losses);
        } catch (IOException e) {
            System.out.println("Could not save battle stats.");
        }
    }

    public void loadStats() {
        Path path = Path.of("battle_stats.txt");

        if (Files.exists(path)) {
            try {
                String[] lines = Files.readString(path).split("\n");
                wins = Integer.parseInt(lines[0].trim());
                losses = Integer.parseInt(lines[1].trim());
            } catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Could not load battle stats.");
            }
        }
    }
}
