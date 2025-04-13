package game;

import java.util.*;

public class Level6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();

        System.out.println("🎮 Welcome to Level 6 - Multiplayer Game!");

        // Step 1: Ask number of players
        int numPlayers = 0;
        while (numPlayers < 1 || numPlayers > 6) {
            System.out.print("Enter number of players (1 to 6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine(); // consume newline
        }

        // Step 2: Ask for player names and create Level4 players
        List<Level4> players = new ArrayList<>();
        Map<Level4, Integer> totalScores = new HashMap<>();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            Level4 player = new Level4(name);
            players.add(player);
            totalScores.put(player, 0);
        }

        // Step 3: Ask number of rounds
        int rounds = 0;
        while (rounds < 1 || rounds > 5) {
            System.out.print("Enter number of rounds to play (1 to 5): ");
            rounds = scanner.nextInt();
            scanner.nextLine();
        }

        // Step 4: Play rounds
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n🔁 --- Round " + round + " ---");

            for (Level4 player : players) {
                System.out.println("\n🎲 " + player.getName() + "'s turn:");

                // Clear and deal 3 cards
                player.clearHand();
                for (int i = 0; i < 3; i++) {
                    player.receiveCard(deck.dealcards());
                }

                player.displayHand();

                // Ask to remove 1+ cards
                Set<Integer> toRemove = new HashSet<>();
                while (toRemove.size() < 1) {
                    System.out.print("Enter at least 1 card index to REMOVE (0, 1, or 2): ");
                    String[] input = scanner.nextLine().split(" ");
                    toRemove.clear();
                    for (String val : input) {
                        try {
                            int idx = Integer.parseInt(val);
                            if (idx >= 0 && idx < 3) {
                                toRemove.add(idx);
                            }
                        } catch (NumberFormatException e) {
                            // ignore
                        }
                    }
                    if (toRemove.size() < 1) {
                        System.out.println("❗ You must remove at least 1 card.");
                    }
                }

                // Remove and top up
                player.selectcards(deck, toRemove);
                System.out.println("Updated hand:");
                player.displayHand();

                // Calculate score
                int roundScore = player.playerBonus();
                int currentTotal = totalScores.get(player);
                totalScores.put(player, currentTotal + roundScore);
                System.out.println("✅ " + player.getName() + "'s score this round: " + roundScore);
            }
        }

        // Step 5: Final Scores
        System.out.println("\n🏁 --- Final Scores ---");
        for (Level4 player : players) {
            System.out.println(player.getName() + ": " + totalScores.get(player) + " points");
        }

        scanner.close();
    }
}
