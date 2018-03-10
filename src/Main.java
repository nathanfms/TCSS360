import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        boolean b = new File("LOAD_ME.txt").delete();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("TCSS 360");
        primaryStage.setScene(new Scene(root, 596, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Called when the application exits.
     * We override this to delete our temp save/load files in case they're still present.
     * @author
     * @throws Exception from something in the super.stop() method
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if(new File("LOAD_ME.txt").exists())    {
            boolean b = new File("LOAD_ME.txt").delete();
        }
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
