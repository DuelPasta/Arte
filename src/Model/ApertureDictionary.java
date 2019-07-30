package Model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApertureDictionary {

    ArrayList<Shape> aperturesList;
    private static final Pattern REGEX_FIND_PADS = Pattern.compile("ADD(\\d*)([RCO]),(\\d*[.]\\d*)*X?(\\d[.]\\d*)?");


    public void addToAperturesList(ArrayList<Macro> macroApertures) {
        aperturesList.addAll(macroApertures);
    }

    public ApertureDictionary() {
        aperturesList = new ArrayList<>();
    }

    public void addApertures(ArrayList<String> apertures) {
        for (String command : apertures) {

            Matcher matcher = REGEX_FIND_PADS.matcher(command);
            int dCode;
            double x;
            double y = 0;

            if (matcher.find()) {
                dCode = Integer.parseInt(matcher.group(1));
                x = Double.parseDouble(matcher.group(3));
                if (matcher.group(4) != null) {
                    y = Double.parseDouble(matcher.group(4));
                }

                switch (matcher.group(2)) {
                    case "R":
                        Rectangle rectangle;
                        rectangle = new Rectangle(dCode, x, y, "Rectangle");
                        aperturesList.add(rectangle);
                        break;
                    case "O":
                        Obround obround;
                        obround = new Obround(dCode, x, y, "Oblong");
                        aperturesList.add(obround);
                        break;
                    case "C":
                        x /= 2; //Need radius for calculations
                        Circle circle;
                        circle = new Circle(dCode, x, y, "Circle");
                        aperturesList.add(circle);
                        break;
                }
            }
        }
    }

    public void showApertures() {
        System.out.println(aperturesList);
        for (Shape aperture : aperturesList) {
            System.out.println(aperture.getOutput());
        }
    }

    public void addPolygons(ArrayList<Polygon> polygonCommand) {
        aperturesList.addAll(polygonCommand);
    }
}


