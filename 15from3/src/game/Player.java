package game;
import java.util.List;
import java.util.ArrayList;

public class Player {
	String name;
	List<Card> hand;
	int score;
	
	public Player (String name) {
		this.name = name;
		this.hand = new ArrayList<>();
		this.score = 0;
	}
	
	public void storeCard(Card card) {
		if (card != null) {
			hand.add(card);
		}
	}
    
	public int Scorecalculator(List<Card> selectedCards) {
		int total = 0;
		for(Card card: selectedCards) {
			total += card.value;
		}
		
		return Math.abs(15 - total);
	}
	
	public String displayHand() {
		return hand.toString();
	}
}
