import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//Logic and javafx visuals for three-card monte game.
public class ThreeCardMonte {

    private Random rng = new Random();

    private Button ace1 = new Button();
    private Button ace2 = new Button();
    private Button queen = new Button();
    private Button startButton = new Button("Start");

    private ChoiceBox<SpeedSetting> speedSelector = new ChoiceBox<SpeedSetting>();

    private ImageView aceFace1 = new ImageView("images/ace.png");
    private ImageView aceFace2 = new ImageView("images/ace.png");
    private ImageView queenFace = new ImageView("images/queen.png");
    private ImageView cardBack1 = new ImageView("images/card_back.png");
    private ImageView cardBack2 = new ImageView("images/card_back.png");
    private ImageView cardBack3 = new ImageView("images/card_back.png");

    private Label winLabel = new Label("Follow the queen of hearts. Press start to begin.");

    private Pane cardPane = new Pane(ace1, ace2, queen);

    private Slider swapSlider = new Slider(1, 25, 10);

    private SpeedSetting slow = new SpeedSetting(1, "Slow");
    private SpeedSetting fast = new SpeedSetting(10, "Fast");
    private SpeedSetting medium = new SpeedSetting(5, "Medium");
    private SpeedSetting superfast = new SpeedSetting(20, "Superfast");

    private HBox settingsBox = new HBox(startButton,
                                        new VBox(new Label("Swap number:"),
                                                 swapSlider),
                                        new VBox(new Label("Shuffle speed:"),
                                                 speedSelector));

    public VBox container = new VBox(settingsBox, winLabel, cardPane);

    public ThreeCardMonte() {

        ace1.setDisable(true);
        ace2.setDisable(true);
        queen.setDisable(true);
        ace1.setGraphic(aceFace1);
        ace2.setGraphic(aceFace2);
        queen.setGraphic(queenFace);

        ace1.relocate(0, 20);
        ace2.relocate(200, 20);
        queen.relocate(100, 20);

        swapSlider.setMajorTickUnit(1);
        swapSlider.setMinorTickCount(0);
        swapSlider.setSnapToTicks(true);
        swapSlider.setShowTickLabels(true);

        speedSelector.getItems().add(slow);
        speedSelector.getItems().add(medium);
        speedSelector.getItems().add(fast);
        speedSelector.getItems().add(superfast);
        speedSelector.setValue(medium);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ace1.setDisable(true);
                ace2.setDisable(true);
                queen.setDisable(true);
                ace1.setGraphic(cardBack1);
                ace2.setGraphic(cardBack2);
                queen.setGraphic(cardBack3);
                startButton.setDisable(true);
                startButton.setText("Retry");
                shuffle((int)swapSlider.getValue(), speedSelector.getSelectionModel()
                                                                 .getSelectedItem()
                                                                 .getSpeed());
            }
        });

        queen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ace1.setDisable(true);
                ace2.setDisable(true);
                queen.setDisable(true);
                ace1.setGraphic(aceFace1);
                ace2.setGraphic(aceFace2);
                queen.setGraphic(queenFace);
                winLabel.setText("You win! Press retry to try again.");
            }
        });

        EventHandler<ActionEvent> loseClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ace1.setDisable(true);
                ace2.setDisable(true);
                queen.setDisable(true);
                ace1.setGraphic(aceFace1);
                ace2.setGraphic(aceFace2);
                queen.setGraphic(queenFace);
                winLabel.setText("You Lose. Press retry to try again.");
            }
        };

        ace1.setOnAction(loseClick);
        ace2.setOnAction(loseClick);
    }

    private void shuffle(int numSwaps, int speed) {

        int index1, index2;
        double x1, x2, add1, add2;

        index1 = rng.nextInt(3);
        while((index2=rng.nextInt(3))==index1);
        Button card1 = (Button) cardPane.getChildren().get(index1);
        Button card2 = (Button) cardPane.getChildren().get(index2);

        if((x1=card1.getLayoutX())>(x2=card2.getLayoutX())) {
            add1 = -speed;
            add2 = speed;
        }else{
            add1 = speed;
            add2 = -speed;
        }

        AnimationTimer timer = new AnimationTimer() {
            int n = numSwaps;
            @Override
            public void handle(long now) {
                card1.setLayoutX(card1.getLayoutX()+add1);
                card2.setLayoutX(card2.getLayoutX()+add2);
                if(card1.getLayoutX()==x2) {
                    this.stop();
                    if(--n>0) shuffle(n, speed);
                    else {
                        ace1.setDisable(false);
                        ace2.setDisable(false);
                        queen.setDisable(false);
                        startButton.setDisable(false);
                    }
                }
            }
        };
        timer.start();
    }
}
