# Pokemon Battler

A console-based Pokemon game built in Java. This project extends my previous Pokedex application by adding turn-based battles against wild Pokemon.

## Features

- Show all Pokemon in the Pokedex
- Add, edit and delete Pokemon
- Add and remove attacks
- Save and load Pokedex data
- Reset the Pokedex to the default Pokemon
- Choose a Pokemon and battle against a randomly selected wild Pokemon
- Choose an attack each turn while the computer selects its attacks randomly
- View battle results, including total wins and losses

## How to run

1. Open the project in IntelliJ IDEA.
2. Make sure Java 21 is installed and selected.
3. Run `Main.java` in the `org.example` package.
4. Choose an option from the console menu.

The project uses Java 21 and Maven.

## Menu

1. Show all Pokemon
2. Add Pokemon
3. Edit Pokemon
4. Delete Pokemon
5. Save
6. Load
7. Reset
8. Exit
9. Start battle
10. Battle stats

## How battles work

The player selects a Pokemon from the Pokedex. The computer randomly selects an opponent from a separate list of wild Pokemon.

Each round, the player chooses an attack and the computer randomly selects one of its attacks.

**Turn order:** The Pokemon with the highest maximum HP attacks first. If both Pokemon have the same maximum HP, the player's Pokemon attacks first.

**Damage calculation:**

- Damage starts with the attack's base damage.
- Some type matchups deal double damage: GRASS against WATER, WATER against FIRE, FIRE against GRASS, and ELECTRIC against WATER.
- A random multiplier between 0.85 and 1.0 is applied.
- The result is converted to an integer.

Formula: `damage = (int) (baseDamage * typeMultiplier * randomMultiplier)`

An attack only deals damage if it passes its accuracy check. Otherwise, the attack misses.

A Pokemon is defeated when its HP reaches 0. The battle ends when either Pokemon is defeated. Wins and losses are recorded.

## Saving data

The Pokedex is saved to `pokedex.txt`.

Battle results are saved to `battle_stats.txt`. The program loads saved battle statistics when it starts.

Both files use a simple text format.

## Object-oriented design

The project uses separate classes with different responsibilities:

- `Pokemon` stores a Pokemon's name, type, HP and attacks. It also has methods for taking damage and checking whether the Pokemon is defeated.
- `Attack` represents an attack with a name, base damage, accuracy and type.
- `Battle` handles the battle rounds, attack choices, damage and results.
- `BattleStats` stores the total number of wins and losses and handles saving and loading those results.
- `Main` handles the console menu and Pokedex operations.
- `Type` is an enum containing the available Pokemon types.

The project uses encapsulation through private fields and methods in classes such as `Pokemon` and `BattleStats`. A Pokemon also contains a list of `Attack` objects.

The project does not currently use class inheritance or interfaces.

## VG requirements

This project focuses on meeting the G requirements. No additional VG requirements have been implemented intentionally.

## Known limitations

- The type-effectiveness system only includes selected double-damage matchups, not a complete Pokemon type chart.
- Turn order is based on maximum HP instead of a separate speed attribute.
- Battle statistics store total wins and losses, not detailed results for individual Pokemon.
- Saved data uses text files rather than JSON.