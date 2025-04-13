package game;

public class Level3 {
	private Player player;
	
	public Level3(String name) {
		this.player = new Player(name);
	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
    
	
	public void receivecard (Card card) {
		player.receiveCard(card);
	}
	
	public void displayhand() {
		player.displayHand();
	}
	public int calculateHandValue() {
        int total = 0;
        for (Card card : player.hand) {
            total += card.getValue(); // assumes Card class has getValue(). Need it to follow proper OOP principles
        }
        return total;
    }
	
	public int totalvaluehand() {
		int handvalue = calculateHandValue();
		return 15 - handvalue;
		
	}
	
	public void showScore() {
        int score = totalvaluehand();
        System.out.println("Your score (15 - hand value): " + score);
    }

	
	
	public static void main(String[] args) {
		Level3 player = new Level3 ("Tanha");
		Deck deck = new Deck();
		
		for (int i=0; i<3; i++) {
			player.receivecard(deck.dealcards());
		}
		player.displayhand();
		
		player.showScore();
	}}

