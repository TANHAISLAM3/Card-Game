package game;
import java.util.*;

public class Level2 {
	private Player player;
	
	
    public Level2(String name) {
        this.player = new Player(name);
    }

	public void receivecard(Card card) {
		player.receiveCard(card);
	}
	
	public void displayHand() {
		player.displayHand();
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


	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Level2 player = new Level2 ("Player 1");

        // Deal 5 cards
        for (int i = 0; i < 5; i++) {
            player.receivecard(deck.dealcards());
        }

        // Show hand
        player.displayHand();

        // Ask for at least two card indexes to remove
        Set<Integer> indexesToRemove = new HashSet<>();
        while (indexesToRemove.size() < 2) {
            System.out.println("Enter at least 2 card indexes to REMOVE (space separated, between 0-4):");
            String[] inputs = scanner.nextLine().trim().split(" ");
            indexesToRemove.clear();

            for (String input : inputs) {
                try {
                    int index = Integer.parseInt(input);
                    if (index >= 0 && index < 5) {
                        indexesToRemove.add(index);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            }

            if (indexesToRemove.size() < 2) {
                System.out.println("You must remove at least 2 cards!");
            }
        }

        // Remove and top up
        player.selectcards(deck, indexesToRemove);

        // Final hand
        System.out.println("\nFinal hand (after removing and topping up):");
        player.displayHand();

        scanner.close();
    }
}

