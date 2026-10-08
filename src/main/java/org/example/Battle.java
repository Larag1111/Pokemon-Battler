
package org.example;

import java.util.Scanner;

public class Battle {

    public static void start(Pokemon player, Pokemon opponent) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== POKEMON BATTLE ===");
        System.out.println(player.getName() + " VS " + opponent.getName());

        if (player.getAttacks().isEmpty()) {
            System.out.println("Your Pokemon has no attacks!");
            return;
        }

        System.out.println("\nChoose an attack:");

        for (int i = 0; i < player.getAttacks().size(); i++) {
            Attack attack = player.getAttacks().get(i);
            System.out.println((i + 1) + ". " + attack.name);
        }

        int choice;
        while (true) {
            System.out.print("Choose: ");

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

        opponent.takeDamage(chosenAttack.baseDamage);

        System.out.println(player.getName() + " used " + chosenAttack.name + "!");
        System.out.println(opponent.getName() + " HP: "
                + opponent.getCurrentHp() + "/" + opponent.getMaxHp());

        if (opponent.isDefeated()) {
            System.out.println(opponent.getName() + " is defeated!");
            System.out.println("You win!");
            return;
        }

        if (!opponent.getAttacks().isEmpty()) {
            int randomIndex = (int) (Math.random() * opponent.getAttacks().size());
            Attack cpuAttack = opponent.getAttacks().get(randomIndex);

            player.takeDamage(cpuAttack.baseDamage);

            System.out.println(opponent.getName() + " used " + cpuAttack.name + "!");
            System.out.println(player.getName() + " HP: "
                    + player.getCurrentHp() + "/" + player.getMaxHp());

            if (player.isDefeated()) {
                System.out.println(player.getName() + " is defeated!");
                System.out.println("You lose!");
            }
        }

        System.out.println("\nHP after this round:");
        System.out.println(player.getName() + ": " + player.getCurrentHp());
        System.out.println(opponent.getName() + ": " + opponent.getCurrentHp());
    }
}
