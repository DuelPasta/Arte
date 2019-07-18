package Controller;

import Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private Scanner scan;
    private String line;

    private ArrayList<String> ADCommand = new ArrayList<>();
    private ArrayList<String> AMCommand = new ArrayList<>();
    private ArrayList<String> PolygonCommand = new ArrayList<>();


    private final static String blockCommand = "%";
    private List<String> templine = new ArrayList<>();

    private Settings settings = new Settings();
    private File file;
    private String filelocation;

    public Parser(File file) {
        this.file = file;
        this.filelocation = file.getParent();
    }

    public void parse() {
        ApertureDictionary aperture = new ApertureDictionary();
        ApertureTemplateDictionary apertureTemplate = new ApertureTemplateDictionary();

        try {
            scan = new Scanner(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded the following file " + file + " and start parsing ");

        while (scan.hasNext()) {
            line = scan.next();
            System.out.println(line);

            if (line.substring(0, 1).equals(blockCommand) && line.substring(line.length() - 1).equals(blockCommand)) {
                parseAdCommand();
            } else if (line.substring(0, 1).equals(blockCommand) && !line.substring(line.length() - 1).equals(blockCommand)) {
                parseAmCommand();
            } else if (line.contains("G36")) {
                parsePolygons();
            }
        }
        aperture.addApertures(ADCommand);
        apertureTemplate.addApertures(AMCommand);
        aperture.addToAperturesList(apertureTemplate.getMacros());
        aperture.showApertures();
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

    }

    private void parsePolygons() {

    }

    private void parseFlashes() {

    }
}
