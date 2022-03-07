import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Pane canvas;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double centerX = canvas.getPrefWidth()/2;
        double centerY = canvas.getPrefHeight()/2;
        double radius = canvas.getPrefWidth()/2-100;
        Circle circle = new Circle(centerX, centerY, radius);
        circle.setFill(null);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        canvas.getChildren().addAll(circle);
        DrawTriangle drawTriangle = new DrawTriangle(centerX, centerY, radius);
        drawTriangle.drawRandomPointsOnTriangle(canvas);
    }
}
