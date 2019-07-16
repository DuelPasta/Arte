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
}