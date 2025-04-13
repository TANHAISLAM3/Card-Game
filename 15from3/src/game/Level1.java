package game;
import java.util.*;
import javafx.scene.image.Image;

class Card {
	public String suit;
    public String rank;
    public int value;
    public String imagePath;
    
    public Card(
    	String suit, String rank, int value) {
    	this.suit = suit;
    	this.rank = rank;
    	this.value = value;
    	this.imagePath ="/images/" + rank.toLowerCase() + "_of_"+ suit.toLowerCase()+".png";
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
    private Stack<Card> cards = new Stack<>();
    
    private static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    
    public Deck() {
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                String rank = ranks[i];
                int value = values[i];
                cards.push(new Card(suit, rank, value));  // Create new card and add to stack
            }
        }
        Collections.shuffle(cards);  // Shuffle the cards for randomness
    }
    
    public Card dealcards() {
        if (!cards.empty()) {
            return cards.pop();  // Return the card that was popped from the stack
        }
        return null;  // If the stack is empty, return null
    }
}

class Player {
    public String name;
    public List<Card> hand = new ArrayList<>();

    // Constructor to initialize the player with a name
    public Player(String name) {
        this.name = name;
    }

    // Receive a card from the deck
    public void receiveCard(Card card) {
        if (card != null) {
            hand.add(card);  // Add the card to the player's hand
        }
    }
    public String getName() {
        return this.name;
    }

    // Display the player's hand
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
    }
}

	