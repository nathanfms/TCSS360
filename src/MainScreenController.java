import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Optional;
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
     * A button used to remove a project
     */
    @FXML
    private Button removeButton;

    /**
     * Method that is called upon first loading the MainScreen FXML file. Help received from
     * https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx
     * @author
     * @throws FileNotFoundException if the save file is not located
     */
    public void initialize() throws FileNotFoundException {
        updateView();
        projects.setRowFactory(select ->    {
           TableRow<Project> row = new TableRow<>();
           row.setOnMouseClicked(event ->   {
               if(event.getClickCount() == 2 && (!row.isEmpty()))   {
                   //Project pr = row.getItem();
                   try {
                       openToProjectMenu(row.getItem());
                   } catch (IOException e) {}
               }
           });
           return row;
        });
    }

    /**
     * Handles if the user wants to open a project from the menu screen
     * @author
     * @param theProject the project to load into the project screen
     * @throws IOException if the file is not found
     */
    private void openToProjectMenu(Project theProject) throws IOException {
        File load = new File("LOAD_ME.txt");
        BufferedWriter w = new BufferedWriter(new FileWriter(load));
        String line;
        w.write(theProject.toString());
        w.close();
        Stage stage;
        Parent root;
        stage = (Stage) goToNewProject.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("ProjectScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates various view elements shown to the user
     * @author
     * @throws FileNotFoundException because loadState() is called
     */
    private void updateView() throws FileNotFoundException {
        ProjectList populate = loadState();
        projects.getItems().clear();
        proToCompare1.getItems().clear();
        proToCompare2.getItems().clear();
        for(int i = 0; i < populate.getProjectListSize(); i++) {
            if(populate.getProject(i) != null) {
                projects.getItems().add(populate.getProject(i));
                proToCompare1.getItems().add(populate.getProject(i));
                proToCompare2.getItems().add(populate.getProject(i));
            }
        }
        //populate.printMyList();
        updateTotalCost();
        ComboBox[] arr = {proToCompare1, proToCompare2};
        updateComparators(arr);
    }

    /**
     * Updates the total cost of all projects shown to the user
     * @author
     */
    private void updateTotalCost()  {
        Iterator<Project> pro = projects.getItems().iterator();
        BigDecimal total = BigDecimal.ZERO;
        while(pro.hasNext())    {
            total = total.add(new BigDecimal(pro.next().getTotalCost()));
        }
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(total.toString()));
        totalSpent.setText(money);
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
                for(int i = 0; i < mats.length; i++)    {
                    mats[i] = mats[i].replace("$$$","");
                    toAdd.addMaterial(mats[i].substring(0, mats[i].indexOf(",")),
                            Integer.parseInt(mats[i].substring(mats[i].indexOf(",")+1,
                                    mats[i].lastIndexOf(","))),
                            mats[i].substring(mats[i].lastIndexOf(",")+1));
                }
                myList.addProject(toAdd);
            }
        }
        return myList;
    }

    /**
     * Handles changing scenes. Determines what button was pushed and from that chooses what scene to load.
     * Help received from http://www.javafxtutorials.com/tutorials/switching-to-different-screens-in-javafx-and-fxml/
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
            boolean b = new File("LOAD_ME.txt").delete();
            System.err.println("Deleted on load project screen: " + b);
        } else {
            stage = (Stage) goToNewProject.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Removes a project from both the save file and the TableView the user sees.
     * @author
     * @throws IOException if the save file is not found (it will be found)
     */
    @FXML
    private void removeSelected() throws IOException {
        Project pro = projects.getSelectionModel().getSelectedItem();
        projects.getItems().remove(pro);
        proToCompare1.getItems().remove(pro);
        proToCompare2.getItems().remove(pro);
        File saveFile = new File("saveProject.txt");
        File newSave = new File("saveProject_Temp.txt");
        BufferedReader r = new BufferedReader(new FileReader(saveFile));
        BufferedWriter w = new BufferedWriter(new FileWriter(newSave));
        String line;
        while((line = r.readLine()) != null)    {
            String newLine = line.trim();
            if(!newLine.equals(pro.toString())) {
                w.write(line + "\n");
            }
        }
        w.close();
        r.close();
        saveFile.delete();
        boolean b = newSave.renameTo(new File("saveProject.txt"));
        updateTotalCost();
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
                "Hui Ting Cai\nMaryia Shautsova\nHien Doan\n\nVersion 1.0.1");
        alert.showAndWait();
    }

    /************************************************************************************************************************************Can add file verification so the file doesn't NEED to be named "saveProject.txt"
     * Allows the user to import a save file, loading all the projects it contains.
     * @author
     * @throws FileNotFoundException if the save file is not found (it will be, unless the user is trying to break the app)
     */
    @FXML
    private void importFile() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Save File");
        File save = fc.showOpenDialog(null);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("Loading a file will overwrite the current save file!" +
                " Are you sure you want to do this?");
        ButtonType okButton = new ButtonType("Continue to load");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == okButton) {
            if (save.getName().equals("saveProject.txt")) {
                File saveFile = new File("saveProject.txt");
                BufferedReader r = new BufferedReader(new FileReader(save));
                BufferedWriter w = new BufferedWriter(new FileWriter(saveFile));
                String line;
                w.write(""); //Clears file
                while((line = r.readLine()) != null)    {
                    String newLine = line.trim();
                    w.write(line + "\n");
                }
                w.close();
                r.close();
                updateView();
            }
        }
    }

    /**
     * Allows the user to export the save file to their desired directory
     * @author
     * @throws IOException if file read/write fails
     */
    @FXML
    private void exportFile() throws IOException {
        File save = new File("saveProject.txt");
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Choose a directory to save file to");
        File newSave = new File(choose.showDialog(null).getAbsolutePath()
                + "\\saveProject.txt");
        BufferedReader r = new BufferedReader(new FileReader(save));
        BufferedWriter w = new BufferedWriter(new FileWriter(newSave));
        String line;
        w.write("");
        while((line = r.readLine()) != null)    {
            String newLine = line.trim();
            w.write(line + "\n");
        }
        w.close();
        r.close();
    }
}
