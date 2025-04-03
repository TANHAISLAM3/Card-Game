package game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestCard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Card object, for example: Ace of Hearts
        Card card = new Card("Hearts", "Ace", 1);

        // Try to load the image
        try {
            // Create an ImageView to display the image
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitWidth(200);  // Set the width of the image
            imageView.setPreserveRatio(true);  // Maintain aspect ratio

            // Add the ImageView to a StackPane
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, 400, 400);  // Set the scene size

            // Set up the stage and show it
            primaryStage.setTitle("Test Card Image Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
