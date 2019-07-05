package Controller;

import Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private Scanner scan;

    private String ADline;
    private String[] AMLines;
    private String blockCommand = "%";

    private Settings settings = new Settings();
    private File file;
    private String filelocation;

    public Parser(File file) {
        this.file = file;
        this.filelocation = file.getParent();

    }

    public void parse() {
        try {
            scan = new Scanner(file);
            scan.delimiter("%");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded the following file " + file + " and start parsing ");
        while (scan.hasNext()) {
            String line = scan.next();

            if (!line.substring(0,1).equals(blockCommand)) {

            }
            //Check for AD command

            //check for AM command

            //check for Settings command

            //check for polygons



            //getSettings(line);
            //parsePads(line);
            //parseMacro(line);
            //parsePolygons(line);
        }
    }
}
