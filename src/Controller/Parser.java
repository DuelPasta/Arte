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

    private List<String> ADcommand = new ArrayList<>();
    private List<String> AMCommand = new ArrayList<>();
    private List<List> commandList = new ArrayList<>();

    private final static String blockCommand = "%";
    private StringBuilder templine;

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
            }
        }
    }

    private void parseAmCommand() {
        while (!line.substring(line.length() - 1).equals(blockCommand)) {
            templine.append(line);
            line = scan.next();
        }
        AMCommand.add(line);
        System.out.println("AM COMMAND EXECUTED" + AMCommand);


    }

    private void parseAdCommand() {
        ADcommand.add(line);
        System.out.println("ADD COMMAND EXECUTED" + ADcommand);
    }
}
