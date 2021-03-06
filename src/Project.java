import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is designed to be used with the Material class.
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
     * Removes a material from a project.
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

    /**
     * Loads a project from its toString
     * @author
     * @param theProject the toString of a project
     * @return the project form of that toString
     */
    public Project loadProject(String theProject)    {
        Project pro = new Project(theProject.substring(0,theProject.indexOf("$$$")));
        theProject = theProject.substring(theProject.indexOf("$$$"));
        String[] mats = theProject.split("%%%");
        for(int i = 0; i < mats.length; i++)    {
            mats[i] = mats[i].replace("$$$", "");
            pro.addMaterial(mats[i].substring(0, mats[i].indexOf(",")),
                    Integer.parseInt(mats[i].substring(mats[i].indexOf(",")+1,
                            mats[i].lastIndexOf(","))),
                    mats[i].substring(mats[i].lastIndexOf(",")+1));
        }
        return pro;
    }

    /**
     * Returns the list of materials used for the project
     * @author
     * @return list of materials for the proejct
     */
    public List<Material> getProjectMaterials() {
        return projectMaterials;
    }

    /**
     * Equals method for checking if two projects are equal
     * @author
     * @param theOther the project to compare to
     * @return boolean for if the projects are equal
     */
    public boolean equals(Project theOther) {
        boolean equal = true;
        if(this.hashCode() != theOther.hashCode())  {
            equal = false;
        }
        if(this.getProjectMaterials().size() != theOther.getProjectMaterials().size())  {
            equal = false;
        } else {
            for(int i = 0; i < this.projectMaterials.size(); i++)   {
                if(!this.projectMaterials.get(i).equals(theOther.getProjectMaterials().get(i)))  {
                    equal = false;
                }
            }
        }
        return equal;
    }

    /**
     * Hashcode for use with the equals method.
     * @author
     * @return the hashcode of the project
     */
    @Override
    public int hashCode()   {
        int matHash = 0;
        for(Material m : projectMaterials)  {
            matHash += m.hashCode();
        }
        return (getProjectName().hashCode() + matHash + getDollarCost().hashCode()) / 1266;
    }

}
