package org.example;

import java.util.Scanner;

public class Battle {

    public static void start(Pokemon player, Pokemon opponent, BattleStats stats) {
        Scanner scanner = new Scanner(System.in);

        player.setCurrentHp(player.getMaxHp());
        opponent.setCurrentHp(opponent.getMaxHp());

        System.out.println("\n=== POKEMON BATTLE ===");
        System.out.println(player.getName() + " VS " + opponent.getName());

        if (player.getAttacks().isEmpty()) {
            System.out.println("Your Pokemon has no attacks!");
            return;
        }

        if (opponent.getAttacks().isEmpty()) {
            System.out.println("Opponent has no attacks!");
            return;
        }

        while (!player.isDefeated() && !opponent.isDefeated()) {

            System.out.println("\nChoose an attack:");

            for (int i = 0; i < player.getAttacks().size(); i++) {
                Attack attack = player.getAttacks().get(i);
                System.out.println((i + 1) + ". " + attack.name);
            }

            int choice;
            while (true) {
                System.out.print("Choose: ");

                if (!scanner.hasNext()) {
                    System.out.println("\nBattle cancelled.");
                    return;
                }

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    if (choice >= 1 && choice <= player.getAttacks().size()) {
                        break;
                    }
                } else {
                    scanner.next();
                }

                System.out.println("Invalid attack. Try again.");
            }

            Attack chosenAttack = player.getAttacks().get(choice - 1);

            int chance = (int) (Math.random() * 100) + 1;

            if (chance <= chosenAttack.accuracy) {
                int damage = calculateDamage(chosenAttack, opponent);
                opponent.takeDamage(damage);
            } else {
                System.out.println(player.getName() + "'s attack missed!");
            }

            System.out.println(player.getName() + " used " + chosenAttack.name + "!");
            System.out.println(opponent.getName() + " HP: "
                    + opponent.getCurrentHp() + "/" + opponent.getMaxHp());

            if (opponent.isDefeated()) {
                System.out.println(opponent.getName() + " is defeated!");
                System.out.println("You win!");
                stats.addWin();
                break;
            }

            int randomIndex = (int) (Math.random() * opponent.getAttacks().size());
            Attack cpuAttack = opponent.getAttacks().get(randomIndex);

            int cpuChance = (int) (Math.random() * 100) + 1;

            if (cpuChance <= cpuAttack.accuracy) {
                int damage = calculateDamage(cpuAttack, player);
                player.takeDamage(damage);
            } else {
                System.out.println(opponent.getName() + "'s attack missed!");
            }

            System.out.println(opponent.getName() + " used " + cpuAttack.name + "!");
            System.out.println(player.getName() + " HP: "
                    + player.getCurrentHp() + "/" + player.getMaxHp());

            if (player.isDefeated()) {
                System.out.println(player.getName() + " is defeated!");
                System.out.println("You lose!");
                stats.addLoss();
                break;
            }

            System.out.println("\nHP after this round:");
            System.out.println(player.getName() + ": " + player.getCurrentHp());
            System.out.println(opponent.getName() + ": " + opponent.getCurrentHp());
        }

        player.setCurrentHp(player.getMaxHp());
        opponent.setCurrentHp(opponent.getMaxHp());
    }

    private static int calculateDamage(Attack attack, Pokemon defender) {
        int damage = attack.baseDamage;

        if (attack.type == Type.GRASS && defender.getType() == Type.WATER) {
            damage = damage * 2;
        } else if (attack.type == Type.WATER && defender.getType() == Type.FIRE) {
            damage = damage * 2;
        } else if (attack.type == Type.FIRE && defender.getType() == Type.GRASS) {
            damage = damage * 2;
        } else if (attack.type == Type.ELECTRIC && defender.getType() == Type.WATER) {
            damage = damage * 2;
        }
        damage = (int) (damage * (0.85 + Math.random() * 0.15));

        return damage;
    }
}
