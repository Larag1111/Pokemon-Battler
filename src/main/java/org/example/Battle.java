package org.example;

public class Battle {

    public static void start(Pokemon player, Pokemon opponent) {
        System.out.println("\n=== POKEMON BATTLE ===");
        System.out.println(player.getName() + " VS " + opponent.getName());
    }
}