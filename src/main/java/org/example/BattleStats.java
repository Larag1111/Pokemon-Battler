
package org.example;

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
}
