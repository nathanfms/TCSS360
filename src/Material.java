import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * A class that is designed to be used with the Project class. Projects will be made
 * up of multiple materials, each which have a name, quanitity, individual cost, and total cost.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class Material {
    /**
     * Name of the material
     */
    private String materialName;

    /**
     * Quantity of the material
     */
    private int quantity;

    /**
     * Individual cost of the material
     */
    private BigDecimal costPerOne;

    /**
     * Total cost of the material (Quantity * costPerOne)
     */
    private BigDecimal totalCost;

    /**
     * Constructor that allows assigning the name.
     * Sets all other attributes of materials to zero.
     * @author
     * @param theName The name of the material.
     */
    public Material(String theName) {
        materialName = theName;
        quantity = 0;
        costPerOne = BigDecimal.ZERO;
        totalCost = BigDecimal.ZERO;
    }

    /**
     * Alternative constructor for assigning everything.
     * @author
     * @param theName name of the material
     * @param theQuantity quantity of the material
     * @param theEachCost individual cost of the material
     */
    public Material(String theName, String theQuantity, String theEachCost)    {
        materialName = theName;
        quantity = Integer.parseInt(theQuantity);
        costPerOne = new BigDecimal(theEachCost);
        updateTotal();
    }

    /**
     * Gets the current materials name.
     * @author
     * @return The name of the material.
     */
    public String getMaterialName()   {
        return materialName;
    }

    /**
     * Gets the quantity of the selected material.
     * @author
     * @return The quantity of the selected material.
     */
    public int getQuantity()   {
        return quantity;
    }

    /**
     * Gets the individual cost of the material.
     * @author
     * @return The individual cost of the selected material; the cost for/per one.
     */
    public String getCostPerOne()  {
        return costPerOne.toString();
    }

    /**
     * Returns the total cost of the material: costPerOne * quantity
     * @author
     * @return total cost of the material
     */
    public String getTotalCost()   {
//        BigDecimal total = new BigDecimal(getCostPerOne());
//        total.multiply(new BigDecimal(getQuantity()));
        return totalCost.toString();
    }

    /**
     * Updates the name of the Material
     * @author
     * @param theName The new name of the material
     */
    public void setMaterialName(String theName) {
        materialName = theName;
    }

    /**
     * Updates the quantity of the selected material
     * @author
     * @param theQuantity the new quantity
     */
    public void setQuantity(int theQuantity)   {
        quantity = theQuantity;
        updateTotal();
    }

    /**
     * @param theCost new individual cost
     * @author
     * Updates the individual cost of the material
     */
    public void setCostPerOne(BigDecimal theCost)  {
        costPerOne = theCost;
        updateTotal();
    }

    /**
     * Internal function to update the total cost of the material (quantity * costPerOne)
     * It is not necessary for the user to be able to edit this value.
     * @author
     */
    private void updateTotal()  {
        totalCost = costPerOne.multiply(new BigDecimal(quantity));
    }

    /**
     * Returns a string representation of a material
     * @author
     * @return string representation of a material
     */
    public String toString()    {
        String str = getMaterialName();
        str += "\t" + getQuantity() + "\t" + getCostPerOne().toString();
        str += "\t" + getTotalCost().toString();
        return str;
    }

    /**
     * Returns the hashcode of this material
     * @author
     * @return material hashcode
     */
    @Override
    public int hashCode()   {
        return getMaterialName().hashCode() + getCostPerOne().hashCode() + getQuantity() + getTotalCost().hashCode();
    }

    /**
     * Returns a pretty string version of the material cost, with a dollar sign and decimal.
     * @author
     * @return Currency string version of the cost
     */
    public String getDollarCost() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(getCostPerOne()));
        return money;
    }

    /**
     * Returns a pretty string version of the total material cost, with a dollar sign and decimal.
     * @author
     * @return Currency string version of the total cost
     */
    public String getDollarTotalCost()  {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(getTotalCost()));
        return money;
    }

}
