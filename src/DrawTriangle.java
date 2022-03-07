import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class DrawTriangle {
    private double centerX;
    private double centerY;
    private double radius;
    private static final int POINT_SIZE = 5;
    private Circle circle1;
    private Circle circle2;
    private Circle circle3;

    DrawTriangle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

    }

    private double getAngleFromCenter(Vector vector) {
        return Math.toDegrees(Math.atan2(vector.getY() - centerY, vector.getX() - centerX));
    }
    private double getAngleFromGivenPoint(Vector vector, Vector pointOfOrigin) {
        return Math.toDegrees(Math.atan2(vector.getY() - pointOfOrigin.getY(), vector.getX() - pointOfOrigin.getX()));
    }
    private Vector getCoordinatesOfAngleFromGivenPoint(double angle, Vector vector) {
        double x = ((double) 30 *Math.cos(Math.toRadians(angle))) + vector.getX();
        double y = ((double) 30 *Math.sin(Math.toRadians(angle))) + vector.getY();
        return new Vector(x, y);
    }

    private Vector getCoordinatesOfAngle(double angle, double distance) {
        double x = (distance*Math.cos(Math.toRadians(angle))) + centerX;
        double y = (distance*Math.sin(Math.toRadians(angle))) + centerY;
        return new Vector(x, y);
    }

    private void drawTheTriangle(Vector vector1, Vector vector2, Vector vector3, Pane canvas) {
        Text text1 = new Text();
        Text text2 = new Text();
        Text text3 = new Text();
        circle1 = new Circle(vector1.getX(), vector1.getY(), POINT_SIZE, text1);
        circle2 = new Circle(vector2.getX(), vector2.getY(), POINT_SIZE, text2);
        circle3 = new Circle(vector3.getX(), vector3.getY(), POINT_SIZE, text3);
        Line line1 = new Line(circle1.getCenterX(), circle1.getCenterY(), circle2.getCenterX(), circle2.getCenterY());
        Line line2 = new Line(circle2.getCenterX(), circle2.getCenterY(), circle3.getCenterX(), circle3.getCenterY());
        Line line3 = new Line(circle3.getCenterX(), circle3.getCenterY(), circle1.getCenterX(), circle1.getCenterY());
        circle1.lineTo = line1;
        circle1.lineComing = line3;
        circle2.lineTo = line2;
        circle2.lineComing=line1;
        circle3.lineTo = line3;
        circle3.lineComing = line2;
        circle1.setFill(Color.RED);
        circle2.setFill(Color.RED);
        circle3.setFill(Color.RED);
        line1.setStroke(Color.GREEN);
        line2.setStroke(Color.GREEN);
        line3.setStroke(Color.GREEN);
        canvas.getChildren().addAll(line1, line2, line3, text1, text2, text3, circle1, circle2, circle3);
        updateSides();
    }

    private void updateSides() {
        double side1 = getDistanceBetweenTwoPoints(circle1.getCenterX(), circle1.getCenterY(), circle2.getCenterX(), circle2.getCenterY());
        double side2 = getDistanceBetweenTwoPoints(circle2.getCenterX(), circle2.getCenterY(), circle3.getCenterX(), circle3.getCenterY());
        double side3 = getDistanceBetweenTwoPoints(circle3.getCenterX(), circle3.getCenterY(), circle1.getCenterX(), circle1.getCenterY());
        double a;
        double b;
        double c;
        if (side1 == side2 && side2 == side1) {
            a = side1;
            b = side2;
            c = side3;
            computeAndDrawAngles(a, b, c, circle1, circle2, circle3);
            System.out.println(1);
        }else if (side1 > side2 && side1 > side3) {
            c = side1;
            b = side2;
            a = side3;
            computeAndDrawAngles(a, b, c, circle2, circle1, circle3);
            System.out.println(2);
        } else if (side2 > side1 && side2 > side3) {
            c = side2;
            b = side1;
            a = side3;
            computeAndDrawAngles(a, b, c, circle2, circle3, circle1);
            System.out.println(3);
        } else if (side3 > side1 && side3 > side2) {
            c = side3;
            b = side2;
            a = side1;
            computeAndDrawAngles(a, b, c, circle3, circle1, circle2);
            System.out.println(4);
        }
    }

    private void computeAndDrawAngles(double a, double b, double c, Circle circleA, Circle circleB, Circle circleC) {

        double angleA = Math.toDegrees(Math.acos(((b*b) + (c*c) - (a*a) )/(2*b*c)));
        double angleB = Math.toDegrees(Math.acos(((c*c) + (a*a) - (b*b))/(2*c*a)));
        double angleC = Math.toDegrees(Math.acos(((a*a) + (b*b) - (c*c))/(2*a*b)));
        circleC.angleText.setText(String.format("%.2f",angleC));
        circleB.angleText.setText(String.format("%.2f",angleB));
        circleA.angleText.setText(String.format("%.2f",angleA));
        System.out.printf("A:%.2f, B:%.2f, C%.2f %n ", angleA, angleB, angleC);
        circle1.setAngleTextCoords();
        circle2.setAngleTextCoords();
        circle3.setAngleTextCoords();
    }

    void drawRandomPointsOnTriangle(Pane canvas) {
        Vector vector1 = getCoordinatesOfAngle(Math.random()*360, radius);
        Vector vector2 = getCoordinatesOfAngle(Math.random()*360, radius);
        Vector vector3 = getCoordinatesOfAngle(Math.random()*360, radius);
        drawTheTriangle(vector1, vector2, vector3, canvas);
    }

    private double getDistanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private class Circle extends javafx.scene.shape.Circle {
        Line lineComing;
        Line lineTo;
        Text angleText;

        void setAngleTextCoords() {
            double xMiddle = (this.getCenterX() + lineTo.getEndX() + lineComing.getStartX())/3;
            double yMiddle = (this.getCenterY() + lineTo.getEndY() + lineComing.getStartY())/3;
            double angleFromGivenPoint = getAngleFromGivenPoint( new Vector(xMiddle, yMiddle), new Vector(getCenterX(), getCenterY()));
            Vector coordsForText = getCoordinatesOfAngleFromGivenPoint(angleFromGivenPoint, new Vector(getCenterX(), getCenterY()));
            angleText.setX(coordsForText.getX());
            angleText.setY(coordsForText.getY());
        }

        Circle (double x, double y, double r, Text text) {
            super(x, y, r);
            angleText = text;
            angleText.setText("0");
            setOnMouseDragged(e-> {
                double angle = getAngleFromCenter(new Vector(e.getX(), e.getY()));
                Vector coords = getCoordinatesOfAngle(angle, radius);
                setCenterX(coords.getX());
                setCenterY(coords.getY());
                lineComing.setEndX(coords.getX());
                lineComing.setEndY(coords.getY());
                lineTo.setStartX(coords.getX());
                lineTo.setStartY(coords.getY());
                updateSides();
            });

        }
    }

}
