import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * A simple about screen for this application.
 * This class was created to avoid having the "About Us" dialog copy
 * and pasted three times throughout the program (one time per controller)
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class AboutScreen {

    /**
     * Opens a screen displaying information about the team that developed this application.
     */
    public AboutScreen(Window theWindow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(theWindow);
        alert.setTitle("About Us!");
        alert.setHeaderText("The Smallest Team");
        alert.setContentText("We are the Smallest Team, a team that is all about making software. " +
                "We want to enable people to improve their lives and give them the opportunity " +
                "to save some money through the wonders of DIY.\n\nThis application was created to enable" +
                " people to easily calculate the total cost of a project and all its included materials." +
                " Users will also be able to track projects they are currently working on as well as " +
                "input monthly energy bills to track savings.\n\nAuthors:\nNathan Rueschenberg\n" +
                "Hui Ting Cai\nMaryia Shautsova\nHien Doan\n\nVersion 1.01");
        alert.showAndWait();
    }
}
