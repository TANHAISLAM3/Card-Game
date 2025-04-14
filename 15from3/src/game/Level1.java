package game;

import java.util.*;
import javafx.scene.image.Image;

class Card {
    public String suit;
    public String rank;
    public int value;
    public String imagePath;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.imagePath = "/images/" + rank.toLowerCase() + "_of_" + suit.toLowerCase() + ".png";
    }

    public int getValue() {
        return this.value;
    }

    public String getSuit() {
        return this.suit;
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    @Override
    public String toString() {
        return rank + "of" + suit;
    }
}

class Deck {
    protected Stack<Card> cards = new Stack<>();

    private static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

    // Default constructor with shuffling
    public Deck() {
        this(true);
    }

    // Custom constructor for optional shuffling (used in copy)
    public Deck(boolean shuffle) {
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                String rank = ranks[i];
                int value = values[i];
                cards.push(new Card(suit, rank, value));
            }
        }
        if (shuffle) {
            Collections.shuffle(cards);
        }
    }

    // Deal a card
    public Card dealcards() {
        if (!cards.empty()) {
            return cards.pop();
        }
        return null;
    }

    // Clone/copy deck
    public Deck copy() {
        Deck newDeck = new Deck(false);         // Create a fresh ordered deck
        newDeck.cards.clear();                  // Clear it to avoid default fill
        newDeck.cards.addAll(this.cards);       // Copy card stack
        return newDeck;
    }
}

class Player {
    public String name;
    public List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void receiveCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public String getName() {
        return this.name;
    }

    public void displayHand() {
        System.out.println(name + "'s Hand:");
        for (Card card : hand) {
            System.out.println(card);
        }
    }
}

public class Level1 {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Player player = new Player("Player 1");

        for (int i = 0; i < 5; i++) {
            player.receiveCard(deck.dealcards());
        }

        player.displayHand();

        // Test deck copy
        Deck copiedDeck = deck.copy();
        System.out.println("✅ Deck copied successfully. Remaining cards in copy: " + copiedDeck.cards.size());
    }
}
