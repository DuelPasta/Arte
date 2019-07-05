import javafx.application.Application;
import javafx.stage.FileChooser;
import java.io.File;
import Controller.Parser;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {

        FileChooser configFileChooser = new FileChooser();
        File configFile = configFileChooser.showOpenDialog(null);
        Parser parser = new Parser(configFile);
        parser.parse();
        System.exit(1);

    }
}
