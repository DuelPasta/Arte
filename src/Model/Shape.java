package Model;

public class Shape {
    private int dCode;
    private int numbOfApertures;
    private double area;
    private double x;
    private double y;
    private double thickness;
    private String shape;


    Shape(int dCode, double x, double y, String shape) {
        this.dCode = dCode;
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    /**
     * This method creates a formatted string to write to a report file.
     *
     * @return Returns formatted string with all attributes.
     */
    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - %5.3fmm x %5.3fmm - \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f \t Number of apertures: %-5.0f"
                , (double) dCode
                , shape
                , x
                , y
                , getArea()
                , getAreaRatio()
                , getTransferEffeciency()
                , (double) numbOfApertures);
    }

    /**
     * This method calculates the area of the shape.
     */
    public double getArea() {
        return x * y;
    }

    /**
     * This method calculates the Area Ratio of the shape.
     */
    public double getAreaRatio() {

        return getArea() / (2 * (getX() + getY()) * getThickness());
    }

    /**
     * This method calculates the Transfer Effeciency
     */
    public double getTransferEffeciency() {
        double w;
        w = (x < y ? x : y);
        return w / thickness;

    }


    // Setters
    public void setNumbOfApertures(int numbOfApertures) {
        this.numbOfApertures = numbOfApertures;
    }

    // Getters
    public int getdCode() {
        return dCode;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getThickness() {
        return thickness;
    }

    public String getShape() {
        return shape;
    }
    public int getNumbOfApertures() {
        return numbOfApertures;
    }
}
