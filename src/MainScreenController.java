import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * A controller class for the main (home) screen of the application.
 * Controls all the buttons and what data is displayed to the user.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class MainScreenController {

    /**
     * A button to take the user to the energy calculator screen
     * TO BE IMPLEMENTED***************************************************************************************************************************
     */
    @FXML
    private Button goToEnergy;

    /**
     * A button to go to the "New Project" screen
     */
    @FXML
    private Button goToNewProject;

    /**
     * A tableview that displays all the projects loaded from the internal save file
     */
    @FXML
    private TableView<Project> projects = new TableView<Project>();

    /**
     * TextArea to display the total cost of all projects
     */
    @FXML
    private TextArea totalSpent;

    /**
     * Drop down menu to select a project to compare
     */
    @FXML
    private ComboBox<Project> proToCompare1;

    /**
     * Drop down menu to select a project to compare
     */
    @FXML
    private ComboBox<Project> proToCompare2;

    /**
     * A TextArea displaying the difference between the two compared projects
     */
    @FXML
    private TextArea compareResult;

    /**
     * Method that is called upon first loading the MainScreen FXML file
     * @author
     * @throws FileNotFoundException if the save file is not located
     */
    public void initialize() throws FileNotFoundException {
        ProjectList populate = loadState();
        for(int i = 0; i < populate.getProjectListSize(); i++) {
            projects.getItems().add(populate.getProject(i));
            proToCompare1.getItems().add(populate.getProject(i));
            proToCompare2.getItems().add(populate.getProject(i));
        }
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(populate.getProjectTotal().toString()));
        totalSpent.setText(money);
        ComboBox[] arr = {proToCompare1, proToCompare2};
        updateComparators(arr);
    }

    /**
     * Function to tell our drop down menus how we want them to display project data. Help received from
     * from https://stackoverflow.com/questions/19242747/javafx-editable-combobox-showing-tostring-on-item-selection
     * @author
     * @param theComboBoxes an array of combo boxes you want to override the display method of
     */
    private void updateComparators(ComboBox[] theComboBoxes)  {
        for (ComboBox<Project> cb : theComboBoxes)  {
            cb.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>()  {
               public ListCell<Project> call(ListView<Project> pro) {
                   ListCell cell = new ListCell<Project>()  {
                       protected void updateItem(Project project, boolean empty)    {
                           super.updateItem(project, empty);
                           if (empty)   {
                               setText("");
                           } else {
                               setText(project.getProjectName());
                           }
                       }
                   };
                   return cell;
               }
            });
            cb.setButtonCell(new ListCell<Project>() {
               protected void updateItem(Project p, boolean bln)    {
                   super.updateItem(p, bln);
                   if (bln) {
                       setText("");
                   } else {
                       setText(p.getProjectName());
                   }
               }
            });
        }
    }

    /**
     * Compare function that will update the compareResult TextArea.
     * This is called by the Compare button.
     * @author
     */
    @FXML
    private void compareFunction()  {
        Project choice1 = proToCompare1.getValue();
        Project choice2 = proToCompare2.getValue();
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        if(choice1 != null && choice2 != null)  {
            BigDecimal val1 = new BigDecimal(choice1.getTotalCost());
            BigDecimal val2 = new BigDecimal(choice2.getTotalCost());
            if(val1.compareTo(val2) == -1)  {
                String money = fmt.format(Math.abs(Double.parseDouble(val1.subtract(val2).toString())));
                compareResult.setText(choice1.getProjectName() + " is " + money + " cheaper!");
            } else if (val1.compareTo(val2) == 1) {
                String money = fmt.format(Math.abs(Double.parseDouble(val2.subtract(val1).toString())));
                compareResult.setText(choice2.getProjectName() + " is " + money + " cheaper!");
            } else {
                compareResult.setText("No cost difference.");
            }
        }
    }

    /**
     * Reads the save file in order to display all projects on the TableView in the home screen.
     * @author
     * @return a ProjectList containing all loaded projects
     * @throws FileNotFoundException if the save file is not found
     */
    private ProjectList loadState() throws FileNotFoundException {
        File f = new File("saveProject.txt");
        Scanner s = new Scanner(f);
        ProjectList myList = new ProjectList();
        while(s.hasNextLine())  {
            String line = s.nextLine();
            if(line.contains("$$$")) {
                String name = line.substring(0, line.indexOf("$$$"));
                line = line.substring(line.indexOf("$$$"));
                String[] mats = line.split("%%%");
                Project toAdd = new Project(name);
                //System.out.println(name);
                for(int i = 0; i < mats.length; i++)    {
                    mats[i] = mats[i].replace("$$$","");
                    toAdd.addMaterial(mats[i].substring(0, mats[i].indexOf(",")),
                            Integer.parseInt(mats[i].substring(mats[i].indexOf(",")+1,
                                    mats[i].lastIndexOf(","))),
                            mats[i].substring(mats[i].lastIndexOf(",")+1));
                }
                myList.addProject(toAdd);
                System.out.println(toAdd.getProjectName() + ", " + toAdd.getTotalCost());
            }
        }
        return myList;
    }

    /**
     * Handles changing scenes. Determines what button was pushed and from that chooses what scene to load.
     * Help received from http://www.javafxtutorials.com/tutorials/switching-to-different-screens-in-javafx-and-fxml/****************************CAN BE SIMPLIFIED????**************************
     * @author
     * @param event The action that happened
     * @throws IOException if FXMLLoader fails
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = null;
        if (event.getSource() == goToNewProject) {
            stage = (Stage) goToNewProject.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ProjectScreen.fxml"));
        } else {
            stage = (Stage) goToNewProject.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes the application. Used in "File -> Close"
     * @author
     */
    @FXML
    private void exitApp() {
        Platform.exit();
    }

    /**
     * Opens a screen displaying information about the team that developed this application.
     * @author
     */
    @FXML
    private void aboutScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Us!");
        alert.setHeaderText("The Smallest Team");
        alert.setContentText("We are the Smallest Team, a team that is all about making software. " +
                "We want to enable people to improve their lives and give them the opportunity " +
                "to save some money through the wonders of DIY.\n\nThis application was created to enable" +
                " people to easily calculate the total cost of a project and all its included materials." +
                " Users will also be able to track projects they are currently working on as well as " +
                "input monthly energy bills to track savings.\n\nAuthors:\nNathan Rueschenberg\n" +
                "Hui Ting Cai\nMaryia Shautsova\nHien Doan\n\nVersion 1.0");
        alert.showAndWait();
    }
}
