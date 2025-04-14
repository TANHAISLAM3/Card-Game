package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Level4 {
	private Player player;
	
	public Level4 (String name) {
		this.player = new Player(name);
	}
	public String getName() {
	    return player.name; // or player.getName() if name is private in Player
	}
    public void receiveCard(Card card) {
        player.receiveCard(card);
    }

    public List<Card> getHand() {
        return player.getHand(); // Use getter to access the hand
    }
    public void displayHand() {
        player.displayHand();
    }
    public int handSize() {
        return player.hand.size(); // Method of the player class is mention with a dot
    }
    public Card getCard(int index) {
        return player.hand.get(index);
    }

    public List<Card> copyHand() {
        return new ArrayList<>(player.hand);
    }

    public void setHand(List<Card> newHand) {
        player.hand = newHand;
    }
    public void selectcards(Deck deck1, Set<Integer> select ) {
		List <Card> newHand = new ArrayList<>();
		for (int i =0; i<player.hand.size(); i++) {
			if (!select.contains(i)){
				newHand.add(player.hand.get(i));
			}
		}
	
		player.hand = newHand;
		  while (player.hand.size() < 3) {
		        Card newCard = deck1.dealcards();  // Corrected: Get the next card from the 'deck1' instance
		        if (newCard != null) {
		            player.receiveCard(newCard);  // Add the card to the player's hand
		        }
		    }
    }
    
    
    public int playerBonus() {
        List<Card> hand = new ArrayList<>(player.hand);
        int totalValue = 0;

        for (Card card : hand) {
            totalValue += card.getValue(); // assumes getValue() or value is public
        }

        String firstSuit = hand.get(0).getSuit();
        boolean sameSuit = true;
        for (Card card : hand) {
            if (!card.getSuit().equals(firstSuit)) {
                sameSuit = false;
                break;
            }
        }

        String firstColour = colourofSuit(firstSuit);
        boolean sameColour = true;
        for (Card card : hand) {
            if (!colourofSuit(card.getSuit()).equals(firstColour)) {
                sameColour = false;
                break;
            }
        }

        // Base score is (15 - totalValue)
        int score = 15 - totalValue;

        if (sameColour) {
            score -= 1; // bonus
        }
        if (sameSuit) {
            score -= 2; // extra bonus
        }

        return score;
    }

    public String colourofSuit(String suit) {
        if (suit.equals("Hearts") || suit.equals("Diamonds")) {
            return "Red";
        } else {
            return "Black";
        }
    }
    public void clearHand() {
	    player.hand.clear();
	}
    
	public static void main(String[] args) {
		Deck deck = new Deck();
	    Level4 player = new Level4("Player 1");

	    // Deal 3 cards
	    for (int i = 0; i < 3; i++) {
	        player.receiveCard(deck.dealcards());
	    }

	    player.displayHand();

	    int score = player.playerBonus();
	    System.out.println("Final score (with bonus if any): " + score);

		// TODO Auto-generated method stub

	}
	

}
