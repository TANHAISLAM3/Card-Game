package game;

import java.util.*;

public class Level8 {

    // Store the round data for replay
    static class RoundData {
        List<Card> initialHand;
        Set<Integer> removedCards;
        List<Card> finalHand;
        int score;

        public RoundData(List<Card> initialHand, Set<Integer> removedCards, List<Card> finalHand, int score) {
            this.initialHand = new ArrayList<>(initialHand);
            this.removedCards = new HashSet<>(removedCards);
            this.finalHand = new ArrayList<>(finalHand);
            this.score = score;
        }
    }

    // Method for smart computer move (reusing Level7 logic)
    public static Set<Integer> computerSmartSelectCards(Level4 player, Deck realDeck) {
        Set<Integer> bestMove = new HashSet<>();
        int bestScore = Integer.MAX_VALUE;

        int n = player.handSize();

        for (int i = 0; i < n; i++) {
            for (int j = i; j <= n; j++) {
                Set<Integer> toRemove = new HashSet<>();
                toRemove.add(i);
                if (j < n && j != i) toRemove.add(j);

                List<Card> tempHand = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (!toRemove.contains(k)) {
                        tempHand.add(player.getCard(k));
                    }
                }

                // 🧪 Copy the deck for simulation
                Deck simulatedDeck = realDeck.copy();

                while (tempHand.size() < 3) {
                    Card newCard = simulatedDeck.dealcards();
                    if (newCard != null) {
                        tempHand.add(newCard);
                    }
                }

                List<Card> originalHand = player.copyHand();
                player.setHand(tempHand);
                int score = player.playerBonus();
                player.setHand(originalHand);

                if (score < bestScore) {
                    bestScore = score;
                    bestMove = toRemove;
                }
            }
        }

        return bestMove;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        List<Level4> players = new ArrayList<>();
        Map<Level4, Integer> totalScores = new HashMap<>();
        List<RoundData> roundHistory = new ArrayList<>();  // To store round data for replay

        System.out.println("🎮 Welcome to Level 8 - Replay Feature!");

        // Number of players
        int numPlayers = 0;
        while (numPlayers < 1 || numPlayers > 6) {
            System.out.print("Enter number of players (1 to 6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();  // consume newline
        }

        // Create players
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            Level4 player = new Level4(name);
            players.add(player);
            totalScores.put(player, 0);
        }

        // Number of rounds
        int rounds = 0;
        while (rounds < 1 || rounds > 5) {
            System.out.print("Enter number of rounds (1 to 5): ");
            rounds = scanner.nextInt();
            scanner.nextLine();
        }

        // Start playing rounds
     // Start playing rounds
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n🔁 --- Round " + round + " ---");

            for (Level4 player : players) {
                System.out.println("\n🎲 " + player.getName() + "'s turn:");

                player.clearHand();
                for (int i = 0; i < 3; i++) {
                    player.receiveCard(deck.dealcards());
                }

                // Record the player's initial hand
                List<Card> hand = player.getHand(); // player is an instance of Level4
                player.displayHand();

                Set<Integer> toRemove = new HashSet<>();

                if (player.getName().equalsIgnoreCase("Computer")) {
                    // Smart computer decision using simulated deck
                    toRemove = computerSmartSelectCards(player, deck.copy());
                    System.out.println("🧠 Computer chose to remove: " + toRemove);
                } else {
                    // Human input
                    while (toRemove.size() < 1) {
                        System.out.print("Enter at least 1 card index to REMOVE (0, 1, 2): ");
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
                }

                // Apply the selection (removal of cards and dealing new ones)
                player.selectcards(deck, toRemove);
                System.out.println("Updated hand:");
                player.displayHand();

                // Calculate the round score
                int roundScore = player.playerBonus();
                totalScores.put(player, totalScores.get(player) + roundScore);
                System.out.println("✅ " + player.getName() + " scored: " + roundScore);

                // Record the round data for replay
                roundHistory.add(new RoundData(hand, toRemove, player.copyHand(), roundScore)); // Changed initialHand to hand
            }
        }


        // Final Scores
        System.out.println("\n🏁 --- Final Scores ---");
        for (Level4 player : players) {
            System.out.println(player.getName() + ": " + totalScores.get(player) + " points");
        }

        // Offer replay after game ends
        System.out.print("Do you want to view the replay of the game? (yes/no): ");
        String replayChoice = scanner.nextLine().toLowerCase();

        if (replayChoice.equals("yes")) {
            System.out.println("\n🎬 --- Replay ---");
            for (int round = 1; round <= roundHistory.size(); round++) {
                System.out.println("\n🔁 Round " + round + ":");
                RoundData roundData = roundHistory.get(round - 1);

                // Display initial hand
                System.out.println("🃏 Initial Hand: ");
                for (Card card : roundData.initialHand) {
                    System.out.println(card);
                }

                // Display removal decision
                System.out.println("❌ Cards Removed: ");
                for (Integer index : roundData.removedCards) {
                    System.out.println(roundData.initialHand.get(index));
                }

                // Display final hand after selection
                System.out.println("🃏 Final Hand: ");
                for (Card card : roundData.finalHand) {
                    System.out.println(card);
                }

                // Display round score
                System.out.println("✅ Round Score: " + roundData.score);
            }
        }
    }
}
