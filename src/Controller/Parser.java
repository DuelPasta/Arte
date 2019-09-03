package Controller;

import Model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Scanner scan;
    private String line;
    private File tempFile;

    private ArrayList<String> ADCommand = new ArrayList<>();
    private ArrayList<String> AMCommand = new ArrayList<>();
    private ArrayList<Polygon> PolygonCommand = new ArrayList<>();
    ApertureDictionary aperture = new ApertureDictionary();
    ApertureTemplateDictionary apertureTemplate = new ApertureTemplateDictionary();

    private final static String blockCommand = "%";
    private List<String> templine = new ArrayList<>();

    private Settings settings = new Settings();
    private File file;
    private String filelocation;

    public Parser(File file) {
        this.file = file;
        this.filelocation = file.getParent();

        fileCleaner();
        commander();
        aperture.addApertures(ADCommand);
        aperture.addPolygons(PolygonCommand);
        apertureTemplate.addApertures(AMCommand);
        aperture.addToAperturesList(apertureTemplate.getMacros());
        aperture.showApertures();
    }

    private void fileCleaner() {

        //Run this to clean up the gerber file in order for regex to work on all different software exports.
        //Run through the file and enter newline after each (*) symbol. Removes % blocks.

        Path src = Paths.get(this.file.getAbsolutePath());
        Path dst = Paths.get(src + "-temp");
        tempFile = new File(dst.toString());
        BufferedReader reader;
        BufferedWriter writer;
        int ch;
        try {
            reader = Files.newBufferedReader(src, StandardCharsets.UTF_8);
            writer = Files.newBufferedWriter(dst, StandardCharsets.UTF_8);
            while ((ch = reader.read()) != -1) {
                switch (ch) {
                    default:
                        writer.write(ch);
                        break;
                    case 10:
                        break;
                    case 42:
                        writer.newLine();
                        break;
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Missing file");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Empty file");
            System.exit(0);
        }
    }

    private void commander() {
        try {
            scan = new Scanner(tempFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded the following file " + file + " and start parsing ");

        while (scan.hasNext()) {
            line = scan.next();
            System.out.println(line);

            if (line.contains("FSLA") || line.contains("MO")) {
                parseSettings();
            } else if (line.substring(0, 1).equals(blockCommand) && line.substring(line.length() - 1).equals(blockCommand)) {
                parseAdCommand();
            } else if (line.substring(0, 1).equals(blockCommand) && !line.substring(line.length() - 1).equals(blockCommand)) {
                parseAmCommand();
            } else if (line.contains("G36")) {
                parsePolygons();

            }
        }
    }

    private void parseAmCommand() {

        while (!line.substring(line.length() - 1).equals(blockCommand)) {
            templine.add(line);
            line = scan.next();
        }
        templine.add(line);
        AMCommand.add(templine.toString());
        templine.clear();

    }

    private void parseAdCommand() {
        ADCommand.add(line);
    }

    private void parseSettings() {
        if (line.contains("MOMM*")) {
            settings.setUnit("MM");
            System.out.println("Unit found: Milimeters");
        } else if (line.contains("MOIN*")) {
            settings.setUnit("INCH");
            System.out.println("Unit found: Inches");
        }

        Pattern pattern = Pattern.compile("FSLAX\\d(\\d)Y\\d(\\d)\\*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            settings.setPrecisionX(Integer.parseInt(matcher.group(1)));
            settings.setPrecisionY(Integer.parseInt(matcher.group(2)));
            System.out.println("Precission found: X = " + matcher.group(1) + "\t" + " Y = " + matcher.group(2));
        }
    }

    private void parsePolygons() {
        final String beginCode = "G36*";
        final String endCode = "G37*";
        final Pattern REGEX_FIND_POLYGONS = Pattern.compile("[^G\\d?\\d?]X?(-?\\d*)Y?(-?\\d*)[^*]");
        ArrayList<double[]> polygons = new ArrayList<>();

        ArrayList<Double> pointsX = new ArrayList<>();
        ArrayList<Double> pointsY = new ArrayList<>();
        double[] size;
        while (!line.contains(endCode)) {
            Matcher matcher = REGEX_FIND_POLYGONS.matcher(line);
            if (matcher.find()) {
                pointsX.add((Double.parseDouble(matcher.group(1)) / settings.getPrecisionX()));
                pointsY.add((Double.parseDouble(matcher.group(2)) / settings.getPrecisionY()));
                System.out.println(pointsX + " . " + pointsY);
                line = scan.next();
            } else {
                line = scan.next();
            }
        }

        size = findSize(pointsX, pointsY);

        polygons.add(size);
        Polygon polygon;
        polygon = new Polygon(9999, size[0], size[1], "Polygon");
        PolygonCommand.add(polygon);

    }

    private void parseFlashes() {

    }

    private double[] findSize(ArrayList<Double> x, ArrayList<Double> y) {

        double[] size = new double[2];

        // size[0] = Math.abs((Collections.max(x) - Collections.min(x)));
        // size[1] = Math.abs((Collections.max(y) - Collections.min(y)));

        size[0] = Collections.max(x) - Collections.min(x);
        size[1] = Collections.max(y) - Collections.min(y);
        return size;
    }
}


