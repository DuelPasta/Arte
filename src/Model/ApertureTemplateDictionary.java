package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApertureTemplateDictionary {

    private static final Pattern REGEX_FIND_MACRO = Pattern.compile("AM(.*)\\*");
    private static final Pattern REGEX_FIND_MACRO_PARAMS = Pattern.compile("\\d*,\\d*,(-?\\d*\\.-?\\d*),(-?\\d*\\.-?\\d*).*");
    private Matcher matcher;
    ArrayList<Double> sizeX = new ArrayList<>();
    ArrayList<Double> sizeY = new ArrayList<>();
    double[] size;
    ArrayList<Macro> macros;
    ApertureDictionary AD;

    public ApertureTemplateDictionary() {
        macros = new ArrayList<>();
        ApertureDictionary AD = new ApertureDictionary();
    }

    public ArrayList<Macro> getMacros() {
        return macros;
    }

    public void addApertures(ArrayList<String> apertureTemplate) {

        String[][] array = new String[apertureTemplate.size()][];
        for (int i = 0; i < apertureTemplate.size(); i++)
            array[i] = apertureTemplate.get(i).split("\\*");
        for (String[] u : array) {
            for (String elem : u) {
                System.out.println(elem);
                if (!elem.contains("AM")) {
                    if (elem.substring(0, 1).equals(",")) {
                        elem = elem.substring(1, elem.length() - 1);
                    }
                    matcher = REGEX_FIND_MACRO_PARAMS.matcher(elem);
                    while (matcher.find()) {
                        sizeX.add(Double.parseDouble(elem.split(",")[2]));
                        sizeY.add(Double.parseDouble(elem.split(",")[3]));
                        System.out.println(elem);
                        size = highestSize(sizeX, sizeY);
                    }
                }
            }
            Macro macro = new Macro(0000, size[0], size[1], "Custom");
            macros.add(macro);
        }
    }

    private double[] highestSize(ArrayList<Double> x, ArrayList<Double> y) {
        double[] size = new double[2];
        size[0] = Collections.max(x);
        size[1] = Collections.max(y);
        return size;
    }
}
