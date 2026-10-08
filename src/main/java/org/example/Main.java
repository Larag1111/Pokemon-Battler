package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of("pokedex.txt");
        List<Pokemon> pokedex = createSeedData();
        BattleStats stats = new BattleStats();


        if (Files.exists(path)) {
            loadPokedex(path, pokedex);
        }

        boolean running = true;

        while (running) {
            System.out.println("\n=== POKEDEX ===");
            System.out.println("1. Show all Pokemon");
            System.out.println("2. Add Pokemon");
            System.out.println("3. Edit Pokemon");
            System.out.println("4. Delete Pokemon");
            System.out.println("5. Save");
            System.out.println("6. Load");
            System.out.println("7. Reset");
            System.out.println("8. Exit");
            System.out.println("9. Start battle");
            System.out.println("10. Battle stats");

            int choice = readIntInRange(scanner, "Choose: ", 1, 10);

            switch (choice) {
                case 1 -> showAllPokemon(pokedex);
                case 2 -> addPokemon(scanner, pokedex);
                case 3 -> editPokemon(scanner, pokedex);
                case 4 -> deletePokemon(scanner, pokedex);
                case 5 -> savePokedex(path, pokedex);
                case 6 -> loadPokedex(path, pokedex);
                case 7 -> {
                    pokedex.clear();
                    pokedex.addAll(createSeedData());
                    System.out.println("Pokedex reset.");
                }
                case 8 -> {
                    savePokedex(path, pokedex);
                    running = false;
                }
                case 9 -> {
                    if (pokedex.size() >= 2) {
                        showAllPokemon(pokedex);

                        int number = readIntInRange(scanner, "Choose your Pokemon: ", 1, pokedex.size());
                        Pokemon player = pokedex.get(number - 1);

                        Pokemon opponent;
                        do {
                            int randomIndex = (int) (Math.random() * pokedex.size());
                            opponent = pokedex.get(randomIndex);
                        } while (opponent == player);

                        Battle.start(player, opponent, stats);
                    } else {
                        System.out.println("Not enough Pokemon to start a battle.");
                    }
                }
                case 10 -> stats.showStats();
            }
        }
    }

    public static List<Pokemon> createSeedData() {
        List<Pokemon> pokedex = new ArrayList<>();
        pokedex.add(createPokemon("Pikachu", Type.ELECTRIC, 100, "Thunder Shock", 40, 100, Type.ELECTRIC));
        pokedex.add(createPokemon("Charmander", Type.FIRE, 90, "Ember", 40, 100, Type.FIRE));
        pokedex.add(createPokemon("Squirtle", Type.WATER, 95, "Water Gun", 40, 100, Type.WATER));
        pokedex.add(createPokemon("Bulbasaur", Type.GRASS, 100, "Vine Whip", 45, 100, Type.GRASS));
        pokedex.add(createPokemon("Meowth", Type.NORMAL, 90, "Scratch", 40, 100, Type.NORMAL));
        pokedex.add(createPokemon("Psyduck", Type.WATER, 100, "Confusion", 50, 100, Type.WATER));
        return pokedex;
    }

    public static Pokemon createPokemon(String name, Type type, int hp, String attackName,
                                        int damage, int accuracy, Type attackType) {
        List<Attack> attacks = new ArrayList<>();
        attacks.add(new Attack(attackName, damage, accuracy, attackType));
        return new Pokemon(name, type, hp, hp, attacks);
    }

    public static void showAllPokemon(List<Pokemon> pokedex) {
        for (int i = 0; i < pokedex.size(); i++) {
            Pokemon pokemon = pokedex.get(i);
            System.out.println((i + 1) + ". " + pokemon.getName() + " - " + pokemon.getType()
                    + " - HP " + pokemon.getCurrentHp() + "/" + pokemon.getMaxHp());
        }
    }

    public static void addPokemon(Scanner scanner, List<Pokemon> pokedex) {
        String name = readName(scanner, "Pokemon name: ");
        Type type = readType(scanner, "Pokemon type");
        int maxHp = readIntInRange(scanner, "Max HP: ", 1, Integer.MAX_VALUE);

        Attack attack = readAttack(scanner);
        List<Attack> attacks = new ArrayList<>();
        attacks.add(attack);

        pokedex.add(new Pokemon(name, type, maxHp, maxHp, attacks));
        System.out.println(name + " added to Pokedex!");
    }

    public static void editPokemon(Scanner scanner, List<Pokemon> pokedex) {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokemon to edit.");
            return;
        }

        showAllPokemon(pokedex);
        int number = readIntInRange(scanner, "Choose Pokemon to edit: ", 1, pokedex.size());
        Pokemon pokemon = pokedex.get(number - 1);

        pokemon.setName(readName(scanner, "New name: "));

        int newMaxHp = readIntInRange(scanner, "New max HP: ", 1, Integer.MAX_VALUE);
        pokemon.setMaxHp(newMaxHp);
        pokemon.setCurrentHp(newMaxHp);

        pokemon.setType(readType(scanner, "New type"));

        boolean editingAttacks = true;
        while (editingAttacks) {
            System.out.println("1. Add attack");
            System.out.println("2. Remove attack");
            System.out.println("3. Done");
            int choice = readIntInRange(scanner, "Choose: ", 1, 3);

            switch (choice) {
                case 1 -> {
                    if (pokemon.getAttacks().size() >= 4) {
                        System.out.println("Pokemon already has 4 attacks.");
                    } else {
                        pokemon.getAttacks().add(readAttack(scanner));
                        System.out.println("Attack added.");
                    }
                }
                case 2 -> removeAttack(scanner, pokemon);
                case 3 -> editingAttacks = false;
            }
        }

        System.out.println("Pokemon updated.");
    }

    public static void removeAttack(Scanner scanner, Pokemon pokemon) {
        if (pokemon.getAttacks().size() <= 1) {
            System.out.println("Pokemon must have at least 1 attack.");
            return;
        }

        for (int i = 0; i < pokemon.getAttacks().size(); i++) {
            System.out.println((i + 1) + ". " + pokemon.getAttacks().get(i).name);
        }

        int number = readIntInRange(scanner, "Choose attack to remove: ", 1, pokemon.getAttacks().size());
        pokemon.getAttacks().remove(number - 1);
        System.out.println("Attack removed.");
    }

    public static void deletePokemon(Scanner scanner, List<Pokemon> pokedex) {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokemon to delete.");
            return;
        }

        showAllPokemon(pokedex);
        int number = readIntInRange(scanner, "Choose Pokemon to delete: ", 1, pokedex.size());
        Pokemon deleted = pokedex.remove(number - 1);
        System.out.println(deleted.getName() + " deleted.");
    }

    public static Attack readAttack(Scanner scanner) {
        String name = readName(scanner, "Attack name: ");
        int damage = readIntInRange(scanner, "Attack damage: ", 0, Integer.MAX_VALUE);
        int accuracy = readIntInRange(scanner, "Attack accuracy (0-100): ", 0, 100);
        Type type = readType(scanner, "Attack type");
        return new Attack(name, damage, accuracy, type);
    }

    public static String readName(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();

            if (!name.isEmpty() && name.length() <= 50 && !name.contains("|") && !name.contains(",")) {
                return name;
            }

            System.out.println("Name must be 1-50 characters and cannot contain | or ,");
        }
    }

    public static Type readType(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");
            String input = scanner.nextLine().trim().toUpperCase();

            try {
                return Type.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type.");
            }
        }
    }

    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public static void savePokedex(Path path, List<Pokemon> pokedex) {
        StringBuilder data = new StringBuilder();

        for (Pokemon pokemon : pokedex) {
            data.append(pokemon.getName()).append("|")
                    .append(pokemon.getType()).append("|")
                    .append(pokemon.getMaxHp()).append("|")
                    .append(pokemon.getCurrentHp());

            for (Attack attack : pokemon.getAttacks()) {
                data.append("|").append(attack.name).append(",")
                        .append(attack.baseDamage).append(",")
                        .append(attack.accuracy).append(",")
                        .append(attack.type);
            }
            data.append(System.lineSeparator());
        }

        try {
            Files.writeString(path, data.toString());
            System.out.println("Pokedex saved.");
        } catch (IOException e) {
            System.out.println("Could not save Pokedex.");
        }
    }

    public static void loadPokedex(Path path, List<Pokemon> pokedex) {
        if (!Files.exists(path)) {
            System.out.println("No saved Pokedex found.");
            return;
        }

        List<Pokemon> loaded = new ArrayList<>();

        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank()) {
                    continue;
                }

                Pokemon pokemon = parsePokemon(line);
                if (pokemon == null) {
                    System.out.println("Could not load Pokedex. Saved file contains invalid data.");
                    return;
                }
                loaded.add(pokemon);
            }
        } catch (IOException e) {
            System.out.println("Could not load Pokedex.");
            return;
        }

        if (loaded.isEmpty()) {
            System.out.println("Could not load Pokedex. Saved file is empty.");
            return;
        }

        pokedex.clear();
        pokedex.addAll(loaded);
        System.out.println("Pokedex loaded.");
    }

    public static Pokemon parsePokemon(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5 || parts.length > 8) {
            return null;
        }

        try {
            String name = parts[0].trim();
            Type type = Type.valueOf(parts[1]);
            int maxHp = Integer.parseInt(parts[2]);
            int currentHp = Integer.parseInt(parts[3]);

            if (!validSavedName(name) || maxHp <= 0 || currentHp < 0 || currentHp > maxHp) {
                return null;
            }

            List<Attack> attacks = new ArrayList<>();
            for (int i = 4; i < parts.length; i++) {
                String[] attackParts = parts[i].split(",", -1);
                if (attackParts.length != 4) {
                    return null;
                }

                String attackName = attackParts[0].trim();
                int damage = Integer.parseInt(attackParts[1]);
                int accuracy = Integer.parseInt(attackParts[2]);
                Type attackType = Type.valueOf(attackParts[3]);

                if (!validSavedName(attackName) || damage < 0 || accuracy < 0 || accuracy > 100) {
                    return null;
                }

                attacks.add(new Attack(attackName, damage, accuracy, attackType));
            }

            return new Pokemon(name, type, maxHp, currentHp, attacks);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean validSavedName(String name) {
        return !name.isEmpty() && name.length() <= 50 && !name.contains("|") && !name.contains(",");
    }
}
