package game;
import javafx.scene.image.Image;

public class Card {
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
    public Image getImage() {
    	return new Image(getClass().getResourceAsStream(imagePath));
    }
    
    @Override
    public String toString() {
    	return rank + "of" + suit;
    }
}
