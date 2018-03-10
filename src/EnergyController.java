import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A controller class for the energy screen of the application.
 * Controls all the buttons and what data is displayed to the user.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class EnergyController {

    /**
     * A drop down selector box for choosing the month
     */
    @FXML
    private ComboBox<String> monthSelector;

    /**
     * An entry box for entering the cost of the energy bill
     */
    @FXML
    private TextField energyCost;

    /**
     * A button to return to the main screen
     */
    @FXML
    private Button goBackToMain;

    /**
     * Holds all the entered energy bills
     */
    @FXML
    private List<EnergyBill> myBills;

    /**
     * A table to display all the energy bills to the user
     */
    @FXML
    private TableView<EnergyBill> energyView = new TableView<EnergyBill>();

    /**
     * Used for the chart.
     */
    @FXML
    private  CategoryAxis xAxis = new CategoryAxis();

    /**
     * Used for the chart.
     */
    @FXML
    private NumberAxis yAxis = new NumberAxis();

    /**
     * Used for the chart.
     */
    private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

    /**
     * Used for the chart.
     */
    private XYChart.Data<String, Number> data;

    /**
     * A chart displaying energy bill data to the user.
     */
    @FXML
    private LineChart<String, Number> dataChart = new LineChart<String, Number>(xAxis, yAxis);

    /**
     * Method called upon switching to the Energy screen.
     * Loads drop down box, reads energy save file and updates view accordingly
     * @author
     * @throws IOException beccause loadEnergyState() is called
     */
    public void initialize() throws IOException {
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct",
                "Nov", "Dec" };
        monthSelector.getItems().addAll(months);
        myBills = new ArrayList<EnergyBill>();
        dataChart.setData(FXCollections.observableArrayList());
        dataChart.getData().add(series);
        loadEnergyState();
        energyView.getItems().addAll(myBills);
    }

    /**
     * Updates the chart view according from data added/removed
     * @author
     */
    private void updateChart()  {
        series.getData().clear();
        for(EnergyBill eb : myBills)    {
            data = new XYChart.Data<String, Number>(eb.getMonth(), eb.getCost().intValue());
            series.getData().add(data);
        }
    }

    /**
     * Menu "go back" button handler
     * @author
     * @throws IOException if fxml file is not found
     */
    @FXML
    private void handleButtonAction() throws IOException {
        saveEnergyState();
        Stage stage = (Stage) goBackToMain.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds a energy bill to our data (table, chart, save file)
     * @author
     * @throws IOException because saveEnergyState() is called
     */
    @FXML
    private void addBillButton() throws IOException {
        if(monthSelector.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setHeaderText(null);
            alert.setContentText("Please select a month from the drop down box.");
            alert.showAndWait();
            return;
        }
        try{
            new BigDecimal(energyCost.getText());
        } catch (Exception e)   {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setHeaderText(null);
            alert.setContentText("Invalid entry. Make sure you are entering a number/decimal for cost.");
            alert.showAndWait();
            return;
        }
        EnergyBill toAdd = new EnergyBill(monthSelector.getSelectionModel().getSelectedItem(), energyCost.getText());
        myBills.add(toAdd);
        monthSelector.getItems().remove(toAdd.getMonth());
        energyView.getItems().add(toAdd);
        updateChart();
        saveEnergyState();
        energyCost.clear();
    }

    /**
     * Removes a bill from the data
     * @author
     * @throws IOException because saveEnergyState() is called
     */
    @FXML
    private void removeSelected() throws IOException {
        if(energyView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EnergyBill toRemove = energyView.getSelectionModel().getSelectedItem();
        energyView.getItems().remove(toRemove);
        myBills.remove(toRemove);
        monthSelector.getItems().add(toRemove.getMonth());
        updateChart();
        saveEnergyState();
    }

    /**
     * Displays an about screen. Same as the other controllers.
     * @author
     */
    @FXML
    private void aboutScreen()  {
        new AboutScreen(goBackToMain.getScene().getWindow());
    }

    /**
     * exits the app. used for file -> close button
     * @author
     * @throws IOException via Platform.exit()
     */
    @FXML
    private void exitApp() {
        Platform.exit();
    }

    /**
     * Writes the users data to a save file (saveEnergy.txt) which is stored in the root directory
     * @author
     * @throws IOException for file not found
     */
    public void saveEnergyState() throws IOException {
        File f = new File("saveEnergy.txt");
        BufferedWriter w = new BufferedWriter(new FileWriter(f));
        w.write(""); //Clear file
        for(EnergyBill e : myBills) {
            w.append(e.toString() + "\n");
        }
        w.flush();
        w.close();
    }

    /**
     * Loads the save file. Updates view accordingly
     * @author
     * @throws IOException save file not found
     */
    private void loadEnergyState() throws IOException {
        File f = new File("saveEnergy.txt");
        if(f.exists())  {
            Scanner s = new Scanner(f);
            String line;
            while(s.hasNextLine())  {
                line = s.nextLine();
                if(line.contains("%$%"))    {
                    EnergyBill loadMe = new EnergyBill(line.substring(0, line.indexOf("%$%")));
                    line = line.substring(line.indexOf("%$%") + 3);
                    loadMe.setCost(new BigDecimal(line));
                    myBills.add(loadMe);
                }
            }
            updateChart();
        }
        for(EnergyBill eb : myBills)    {
            monthSelector.getItems().remove(eb.getMonth());
        }
    }

}
