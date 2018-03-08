import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

/**
 * A controller class for the project screen of the application.
 * Controls all the buttons and what data is displayed to the user.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class NewProjectController {

    /**
     * The ProjectList containing all the user created projects
     */
    private ProjectList myList = new ProjectList();

    /**
     * A button to go back to the main screen
     */
    @FXML
    private Button goBackToMain;

    /**
     * A TextArea for showing the cost of a project
     */
    @FXML
    private TextArea projectCost;

    /**
     * A TextField that allows the user to enter a project name
     */
    @FXML
    private TextField projectName;

    /**
     * A TextField that allows the user to enter a material name
     */
    @FXML
    private TextField materialName;

    /**
     * A TextField that allows the user to enter a material quantity
     */
    @FXML
    private TextField materialQuantity;

    /**
     * A TextField that allows the user to enter a material price
     */
    @FXML
    private TextField materialPrice;

    /**
     * A TableView used for displaying all materials in the open project
     */
    @FXML
    private TableView<Material> materials = new TableView<Material>();

    /**
     * The current project being worked on while in the "New Project" screen
     */
    private Project currentProject = new Project("");

    /**
     * A button used for removing materials from a project
     */
    @FXML
    private Button removeButton;

    /**
     * Boolean that tells the program if this is a loaded project or not
     */
    private boolean isLoaded = false;

    /**
     * Used if opening a project via double click in the main menu
     * @author
     * @throws FileNotFoundException if the load file was not found
     */
    public void initialize() throws FileNotFoundException {
        File load = new File("LOAD_ME.txt");
        if(load.exists())   {
            isLoaded = true;
            Scanner s = new Scanner(load);
            String toLoad = "";
            while(!toLoad.contains("$$$"))  {
                toLoad = s.nextLine();
            }
            currentProject = currentProject.loadProject(toLoad);
            materials.getItems().clear();
            List<Material> list = currentProject.getProjectMaterials();
            materials.getItems().addAll(list);
            projectName.setText(currentProject.getProjectName());
        } else {
            System.err.println("Failed to load!");
        }
        boolean b = load.delete();
        System.out.println("Load was deleted: " + b);
    }


    /**
     * Method executes if the user hits the "go back" button. Takes the user back to the main menu screen.
     * @author
     * @throws IOException if FXMLLoader fails
     */
    @FXML
    private void handleButtonAction() throws IOException {
        Stage stage = (Stage) goBackToMain.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method is called when the user hits the "add" button. It grabs the data from the user entry box,
     * creates a material from it, and adds it to the current project.
     * @author
     */
    @FXML
    protected void handleAddMaterialButton()   {
        try {
            int num = Integer.parseInt(materialQuantity.getText());
        } catch (Exception e)   {
            inputValidationMessage();
            return;
        }
        try{
            new BigDecimal(materialPrice.getText());
        } catch (Exception e)   {
            inputValidationMessage();
            return;
        }
        currentProject.addMaterial(materialName.getText(), Integer.parseInt(materialQuantity.getText()), new BigDecimal(materialPrice.getText()));
        materials.getItems().add(new Material(materialName.getText(), materialQuantity.getText(), materialPrice.getText()));
        materialName.clear();
        materialQuantity.clear();
        materialPrice.clear();
       // updateTotal();
        projectCost.setText(currentProject.getDollarCost());
    }
//
//    /**
//     * Updates the total project cost shown
//     * @author
//     */
//    private void updateTotal() {
//        NumberFormat fmt = NumberFormat.getCurrencyInstance();
//        String money = fmt.format(Double.parseDouble(currentProject.getTotalCost()));
//        projectCost.setText(currentProject.getDollarCost());
//    }

    /**
     * Displays an error message indicating invalid input has been entered.
     * @author
     */
    private void inputValidationMessage()  {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An error has occurred");
        alert.setHeaderText(null);
        alert.setContentText("Invalid entries. Please assure you are entering a number " +
                "for quantity and a number/decimal for cost.");
        alert.showAndWait();
    }

    /**
     * Method executed when the user hits the "done" button. What this does is saves the current project to the
     * save file, adds it to the ProjectList, and goes back to the main menu
     * @author
     * @throws IOException if FXMLLoader fails
     */
    @FXML
    private void handleProjectCompleteButton() throws IOException {
        if(materials.getItems().size() != 0)    {
            currentProject.setProjectName(projectName.getText());
            int success = myList.addProject(currentProject);
            System.err.println(success);
            if (success == -1)   {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occurred");
                alert.setHeaderText(null);
                alert.setContentText("A project with those attributes already exists. " +
                        "Try changing materials or using a different name.");
                alert.showAndWait();
            } else {
                saveProjectState();
                Stage stage = (Stage) goBackToMain.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setHeaderText(null);
            alert.setContentText("Please add at least one material before trying to save your project.");
            alert.showAndWait();
        }
    }

    /**
     * Saves all projects to the save file. Should be called before switching scenes.
     * @author
     * @throws IOException if the save file is not found
     */
    private void saveProjectState() throws IOException   {
        File file = new File("saveProject.txt");
        FileWriter wr = new FileWriter(file, true);
        wr.write("\n");
        wr.write(myList.getProject().toString());
        wr.flush();
        wr.close();
    }

    /**
     * Exits the application. Used for "File -> Close"
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
                "Hui Ting Cai\nMaryia Shautsova\nHien Doan\n\nVersion 1.01");
        alert.showAndWait();
    }

    /**
     * Removes a material from the project.
     * @author
     */
    @FXML
    private void removeSelected()   {
        Material mat = materials.getSelectionModel().getSelectedItem();
        currentProject.removeMaterial(mat);
        materials.getItems().remove(mat);
        //updateTotal();
        projectCost.setText(currentProject.getDollarCost());
    }

}
