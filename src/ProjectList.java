import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used to hold Projects and Energy Bills
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class ProjectList {

    /**
     * The list of projects in this ProjectList
     */
    private List<Project> myProjects;

    /**
     * The list of energy bills in this ProjectList
     */
    private List<EnergyBill> myBills;

    /**
     * Default ProjectList constructor
     * @author
     */
    public ProjectList()    {
        myProjects = new ArrayList<Project>();
        myBills = new ArrayList<EnergyBill>();
    }

    /**
     * Returns the size of the project array
     * @author
     * @return size of project array
     */
    public int getProjectListSize()    {
        return myProjects.size();
    }

    /**
     * Default getProject method. Returns the most recently added Project
     * @author
     * @return most recently added project
     */
    public Project getProject() {
        return myProjects.get(myProjects.size() - 1);
    }

    /**
     * Returns the project at the given index ***************************************************************************************needs input validation
     * @author
     * @param theIndex the index of the project you want returned
     * @return the project at the index specified
     */
    public Project getProject(int theIndex) {
        if(theIndex >= 0 && theIndex < getProjectListSize()) {
            return myProjects.get(theIndex);
        }
        return null;
    }

    /**
     * Returns the cost of all projects combined.
     * @author
     * @return cost of all projects combined
     */
    public BigDecimal getProjectTotal()    {
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 0; i < myProjects.size(); i++)  {
            total = total.add(new BigDecimal(myProjects.get(i).getTotalCost()));
        }
        return total;
    }

    /**
     * Returns the cost of all energy bills combined
     * @author
     * @return all energy bills combined
     */
    public BigDecimal getEnergyTotal()  {
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 0; i < myBills.size(); i++) {
            total.add(myBills.get(i).getCost());
        }
        return total;
    }

    /**
     * Adds a project to this ProjectList
     * @author
     * @param theProject the project to add
     * @return success code, -1 for fail, 0 for success
     */
    public int addProject(Project theProject)  {
        for(int i = 0; i < myProjects.size(); i++)  {
            if(theProject.equals(myProjects.get(i)))   {
                return -1;
            }
        }
        myProjects.add(theProject);
        return 0;
    }

    /**
     * Adds a energy bill to this ProjectList
     * @author
     * @param theBill the energy bill to add
     */
    public void addBill(EnergyBill theBill)  {
        myBills.add(theBill);
    }

    /**
     * Deletes a project with the given name************************************************************************************This actually isn't used and can be removed
     * @author
     * @param theProject the project to delete from the list
     * @return 0 for success, -1 for failure
     */
    public int deleteProject(Project theProject)   {
        for(int i = 0; i < myProjects.size(); i++)  {
            if(myProjects.get(i).hashCode() == theProject.hashCode())  {
                myProjects.remove(i);
                return 0;
            }
        }
        return -1;
    }

    //Delete me after all testing is done*********************************************************************************************************************************
    public void printMyList()   {
        for(int i = 0; i < myProjects.size(); i++)  {
            System.out.println(myProjects.get(i).toString());
        }
    }




}

