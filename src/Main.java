import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Main class of our DIY calculator application.
 * Starts up the GUI and displays it to the user.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class Main extends Application {

    /**
     * Overrided start method to load FXML (Java FX) document and display it
     * @author
     * @param primaryStage Stage where the FXML will be displayed
     * @throws Exception if the fxml file is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("TCSS 360");
        primaryStage.setScene(new Scene(root, 596, 435));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Launches the GUI.
     * @author
     * @param args command line arguments
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
}
