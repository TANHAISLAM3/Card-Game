package game;

import java.io.*;
import java.util.*;

public class Level9 {

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
        List<RoundData> roundHistory = new ArrayList<>();

        System.out.println("🎮 Welcome to Level 9 - High Scores + Replay!");

        // Player input
        int numPlayers = 0;
        while (numPlayers < 1 || numPlayers > 6) {
            System.out.print("Enter number of players (1 to 6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            Level4 player = new Level4(name);
            players.add(player);
            totalScores.put(player, 0);
        }

        int rounds = 0;
        while (rounds < 1 || rounds > 5) {
            System.out.print("Enter number of rounds (1 to 5): ");
            rounds = scanner.nextInt();
            scanner.nextLine();
        }

        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n🔁 --- Round " + round + " ---");

            for (Level4 player : players) {
                System.out.println("\n🎲 " + player.getName() + "'s turn:");

                player.clearHand();
                for (int i = 0; i < 3; i++) {
                    player.receiveCard(deck.dealcards());
                }

                List<Card> hand = player.getHand();
                player.displayHand();
                Set<Integer> toRemove = new HashSet<>();

                if (player.getName().equalsIgnoreCase("Computer")) {
                    toRemove = computerSmartSelectCards(player, deck.copy());
                    System.out.println("🧠 Computer chose to remove: " + toRemove);
                } else {
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
                                // Ignore
                            }
                        }
                        if (toRemove.size() < 1) {
                            System.out.println("❗ You must remove at least 1 card.");
                        }
                    }
                }

                player.selectcards(deck, toRemove);
                System.out.println("Updated hand:");
                player.displayHand();

                int roundScore = player.playerBonus();
                totalScores.put(player, totalScores.get(player) + roundScore);
                System.out.println("✅ " + player.getName() + " scored: " + roundScore);

                roundHistory.add(new RoundData(hand, toRemove, player.copyHand(), roundScore));
            }
        }

        // Final Scores
        System.out.println("\n🏁 --- Final Scores ---");
        for (Level4 player : players) {
            int total = totalScores.get(player);
            double average = Math.round(((double) total / rounds) * 100.0) / 100.0;
            System.out.println(player.getName() + ": Total = " + total + ", Avg = " + average);

            // 🔽 Save high score to file
            String fileName = "src/game/highscores.txt";
            System.out.println("Saving to: " + new File(fileName).getAbsolutePath());

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(player.getName() + ": " + average + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Offer replay
        System.out.print("Do you want to view the replay of the game? (yes/no): ");
        String replayChoice = scanner.nextLine().toLowerCase();

        if (replayChoice.equals("yes")) {
            System.out.println("\n🎬 --- Replay ---");
            for (int round = 1; round <= roundHistory.size(); round++) {
                System.out.println("\n🔁 Round " + round + ":");
                RoundData data = roundHistory.get(round - 1);

                System.out.println("🃏 Initial Hand:");
                for (Card card : data.initialHand) System.out.println(card);

                System.out.println("❌ Cards Removed:");
                for (Integer idx : data.removedCards) System.out.println(data.initialHand.get(idx));

                System.out.println("🃏 Final Hand:");
                for (Card card : data.finalHand) System.out.println(card);

                System.out.println("✅ Round Score: " + data.score);
            }
        }

        scanner.close();
    }
}
