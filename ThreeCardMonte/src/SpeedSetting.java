//Class for speed settings. Used with javafx ChoiceBox.
public class SpeedSetting {

    private int speed;
    private String name;

    public SpeedSetting(int speed, String name) {
        this.speed = speed;
        this.name = name;
    }

    public int getSpeed() {return speed;}

    @Override
    public String toString() {return name;}
}
