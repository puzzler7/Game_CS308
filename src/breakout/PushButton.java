package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PushButton extends Rectangle {
    private Text text;
    private double maxHeight;
    private double maxSize;
    private double centerX;
    private double centerY;

    public PushButton(double x, double y, String msg) {
        this(x, y, Main.BUTTON_HEIGHT, msg);
    }


    public PushButton(double x, double y, double height, String msg) {
        super(x,y,height*Main.BUTTON_WIDTH_HEIGHT_RATIO*height, height);
        maxHeight = height;
        centerX = x+height/2;
        centerY = y+height/2*Main.BUTTON_WIDTH_HEIGHT_RATIO;
        setArcHeight(height/3);
        setArcWidth(height/3);//FIXME magic val
        text = new Text(msg);
        adjustFont();
    }

    public void onMouseover() {
        setHeight(maxHeight*Main.BUTTON_SHRINK);
        setWidth(maxHeight*Main.BUTTON_SHRINK*Main.BUTTON_WIDTH_HEIGHT_RATIO);
        adjustFont();
        center();
    }

    public void onMouseoff() {
        setHeight(maxHeight);
        setWidth(maxHeight*Main.BUTTON_WIDTH_HEIGHT_RATIO);
        adjustFont();
        center();
    }

    private void adjustFont(){
        Font font = new Font("Courier New", getHeight()*3/4);
        text.setFont(font);
    }

    private void center() {
        setX(centerX-getWidth()/2);
        setY(centerY-getHeight()/2);
        centerText();
    }

    private void centerText() {
        text.setX(centerX-text.getBoundsInLocal().getWidth()/2);
        text.setY(centerY+text.getBoundsInLocal().getHeight()/4);
    }

    public void setCenterX(double x) {
        setX(x-getWidth()/2);
        centerX = x;
    }

    public void setCenterY(double y) {
        setY(y-getWidth()/2);
        centerY = y;
    }

    public Text getText() {
        return text;
    }
}
