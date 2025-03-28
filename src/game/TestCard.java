package game;

public class TestCard {
    public static void main(String[] args) {
        // Create a test card (Ace of Hearts)
        Card testCard = new Card("Hearts", "Ace", 11);

        // Print details to check if it was created correctly
        System.out.println("Card Details:");
        System.out.println("Suit: " + testCard.suit);
        System.out.println("Rank: " + testCard.rank);
        System.out.println("Value: " + testCard.value);
        System.out.println("Image Path: " + testCard.imagePath);
    }
}