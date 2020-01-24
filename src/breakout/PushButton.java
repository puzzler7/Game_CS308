package breakout;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A button class for buttons with text, that collapse slightly on mouseover.
 *
 * Assumes that the text fits inside the button (will still work, but the text will be larger than the button).
 *
 * @author Maverick Chung mc608
 */
public class PushButton extends Rectangle {
    private Text text;
    private double maxHeight;
    private double centerX;
    private double centerY;

    public PushButton(double x, double y, double height, String msg) {
        super(x,y,height*Main.BUTTON_WIDTH_HEIGHT_RATIO, height);
        maxHeight = height;
        centerX = x+height/2;
        centerY = y+height/2*Main.BUTTON_WIDTH_HEIGHT_RATIO;
        setFill(Color.WHITE);
        setArcHeight(height/3);
        setArcWidth(height/3);//FIXME magic val
        text = new Text(msg);
        adjustFont();
    }

    /**
     * Shrinks the button and text slightly
     */
    public void onMouseover() {
        setHeight(maxHeight*Main.BUTTON_SHRINK);
        setWidth(maxHeight*Main.BUTTON_SHRINK*Main.BUTTON_WIDTH_HEIGHT_RATIO);
        adjustFont();
        center();
    }

    /**
     * Unshrinks the button and text;
     */
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

    /**
     * Centers the button and text after it gets shrunk/unshrunk
     */
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
        center();
    }

    public void setCenterY(double y) {
        setY(y-getWidth()/2);
        centerY = y;
        center();
    }

    public Text getText() {
        return text;
    }

    /**
     * Returns a list of Nodes for adding to the Group root
     * @return a list of Node to be added to root
     */
    public Collection<Node> getObjects() {
        ArrayList<Node> objs = new ArrayList<>();
        objs.add(this);
        objs.add(text);
        return objs;
    }
}
