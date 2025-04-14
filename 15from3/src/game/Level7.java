package game;

import java.util.*;

public class Level7 {
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
        Deck deck = new Deck();
        Level4 computerPlayer = new Level4("Computer");

        // Deal 3 cards to the computer player
        for (int i = 0; i < 3; i++) {
            computerPlayer.receiveCard(deck.dealcards());
        }

        System.out.println("🤖 Computer's initial hand:");
        computerPlayer.displayHand();

        // Simulate the computer making a smart move
        Set<Integer> move = computerSmartSelectCards(computerPlayer, deck.copy());

        System.out.println("💡 Computer decided to remove cards at indices: " + move);

        // Apply the move
        computerPlayer.selectcards(deck, move);
        System.out.println("🃏 Computer's new hand after applying smart selection:");
        computerPlayer.displayHand();

        // Show score
        int score = computerPlayer.playerBonus();
        System.out.println("📊 Final score after smart move: " + score);
    }
}
