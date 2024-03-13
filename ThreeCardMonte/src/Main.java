import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Entry point for running the three-card monte game.
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ThreeCardMonte game = new ThreeCardMonte();
        Scene scene = new Scene(game.container);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
