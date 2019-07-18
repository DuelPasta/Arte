package Model;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

public class Circle extends Shape {

    public Circle(int dCode, double x, double y, String shape) {
        super(dCode,x,y,shape);
    }

    @Override
    public double getArea() {
        return Math.PI * power(getX(),2);
    }

    @Override
    public double getAreaRatio() {

        return getArea() / (Math.PI * (getX()*2) * getThickness());

    }

    @Override
    public double getTransferEffeciency() {
        return getX() / getThickness();

    }

    @Override
    public String getOutput() {
        return String.format("DCode: %-7.0f  \"%-9s\" \t - %5.3fmm - \t \t \t Area: %6.3fmm² \t Area Ratio: %5.2f \t Transfer Effeciency: %2.1f \t Number of apertures: %-5.0f"
                , (double) getdCode()
                , getShape()
                , getX() * 2
                , getArea()
                , getAreaRatio()
                , getTransferEffeciency()
                , (double) getNumbOfApertures());
    }
}