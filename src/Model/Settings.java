package Model;

public class Settings {

    private int precisionX;
    private int precisionY;
    private String unit;

    public int getPrecisionX() {
        return precisionX;
    }
    public int getPrecisionY() {
        return precisionY;
    }
    public String getUnit() {
        return unit;
    }

    public void setPrecisionX(int number) {
        precisionX = 1;
        for (int i = 0; i < number; i++) {
            precisionX *= 10;
        }
    }
    public void setPrecisionY(int number) {
        precisionY = 1;
        for (int i = 0; i < number; i++) {
            precisionY *= 10;
        }
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
