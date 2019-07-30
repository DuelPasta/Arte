package Model;

public class Polygon extends Shape {

    private static int counter;

    public Polygon(int dCode, double x, double y, String shape) {
        super(dCode, x, y, shape);
        counter++;
    }

    public static int getCounter() {
        return counter;
    }
}

