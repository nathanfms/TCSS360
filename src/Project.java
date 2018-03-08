import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is designed to be used with the Material and ProjectList class.
 * Projects hold a list of materials and have a total cost.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class Project {

    /**
     * The name of the project
     */
    private String projectName;

    /**
     * The list of materials used for this project
     */
    private List<Material> projectMaterials;

    /**
     * Constructor to make a project just from a name. Initializes other values to empty
     * @author
     * @param theName Name of the project
     */
    public Project(String theName)  {
        projectName = theName;
        //this.projectId = 0; //We weill have to calculate this in the project list class or keep track of it in the database
        projectMaterials = new ArrayList<Material>();
    }

    /**
     * Updates the project name
     * @author
     * @param theName the new project name
     */
    public void setProjectName(String theName) {
        projectName = theName;
    }

    /**
     * Adds a new material to the current project
     * @author
     * @param name name of the material
     * @param theQuantity quantity of the material
     * @param theCost cost of the materail
     */
    public void addMaterial(String name, int theQuantity, BigDecimal theCost)    {
        Material toAdd = new Material(name);
        toAdd.setQuantity(theQuantity);
        toAdd.setCostPerOne(theCost);
        projectMaterials.add(toAdd);
    }

    /**
     * Same as other addMaterial(), but uses a string instead of BigDecimal for cost
     * @author
     */
    public void addMaterial(String theName, int theQuantity, String theCost)   {
        addMaterial(theName, theQuantity, new BigDecimal(theCost));
    }

    /**
     * Removes a material from a project. **************************************************************************************NEEDS IMPLEMENTATION IN GUI
     * @author
     * @param theMaterial the material to remove
     */
    public void removeMaterial(Material theMaterial)  {
        int idx = 0;
        for(int i = 0; i < projectMaterials.size(); i++)    {
            if (projectMaterials.get(i).hashCode() == theMaterial.hashCode())  {
                projectMaterials.remove(i);
                return;
            }
        }
    }

    /**
     * Gets the total cost of the project (adds all material costs up)
     * @author
     * @return total dollar cost of the project
     */
    public String getTotalCost()    {
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 0; i < projectMaterials.size(); i++)    {
            total = total.add(new BigDecimal(projectMaterials.get(i).getTotalCost()));
        }
        return total.toString();
    }

    /**
     * Gets the project name
     * @author
     * @return project name
     */
    public String getProjectName()  {
        return projectName;
    }

    /**
     * ToString method for project. This is used for our save file. Do not change this!!!
     * Materials separated by "%%%"
     * @author Nathan
     * @return a ToString of the Project in this format: "{Name}$$${matName1},{matQuant1},{matCost1}%%%{matName2}..."
     */
    @Override
    public String toString()    {
        String toReturn = getProjectName() + "$$$";
        for(int i = 0; i < projectMaterials.size(); i++)    {
            toReturn += projectMaterials.get(i).getMaterialName() + ",";
            toReturn += projectMaterials.get(i).getQuantity() + ",";
            toReturn += projectMaterials.get(i).getCostPerOne().toString() + "%%%";
        }
        return toReturn;
    }

    /**
     * Returns a pretty string version of the project cost, with a dollar sign and decimal.
     * @return Currency string version of the cost
     */
    public String getDollarCost()   {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(getTotalCost()));
        return money;
    }

}
