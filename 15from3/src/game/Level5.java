package game;

import java.util.*;

public class Level5 {
	private Player player;
	
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();

        System.out.println("Welcome to Level 5 - The Full Game!");

        // 1. Ask how many rounds to play
        int rounds = 0;
        while (rounds < 1 || rounds > 5) {
            System.out.print("Enter number of rounds to play (1 to 5): ");
            try {
                rounds = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number between 1 and 5.");
            }
        }

        // 2. Initialize player
        Level4 player = new Level4("Player 1");

        int totalScore = 0;

        // 3. Loop through rounds
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n--- Round " + round + " ---");
            player.clearHand();
            // Clear deck and reset for fairness (optional: reshuffle or recreate deck)
            deck = new Deck(); // Recreate a new shuffled deck

            // Deal 3 cards
            for (int i = 0; i < 3; i++) {
                player.receiveCard(deck.dealcards());
            }

            player.displayHand();

            // Ask which cards to replace
            Set<Integer> indexesToRemove = new HashSet<>();
            while (indexesToRemove.size() < 1) {
                System.out.println("Enter at least 1 card index to REMOVE (0, 1, or 2):");
                String[] inputs = scanner.nextLine().trim().split(" ");
                indexesToRemove.clear();

                for (String input : inputs) {
                    try {
                        int index = Integer.parseInt(input);
                        if (index >= 0 && index < 3) {
                            indexesToRemove.add(index);
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid inputs
                    }
                }

                if (indexesToRemove.size() < 1) {
                    System.out.println("You must remove at least 1 card!");
                }
            }

            // Remove & top up
            player.selectcards(deck, indexesToRemove);

            System.out.println("Final hand for this round:");
            player.displayHand();

            // Calculate score
            int roundScore = player.playerBonus();
            totalScore += roundScore;
            System.out.println("Round Score: " + roundScore);
        }

        // 4. Show total score
        System.out.println("\n--- Game Over ---");
        System.out.println("Total Score after " + rounds + " rounds: " + totalScore);
        scanner.close();
    }
}
