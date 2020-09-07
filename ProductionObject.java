/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.LinkedList;

public abstract class ProductionObject
{
    //Name for this production object.
    protected String name;

    //Production line for this production stage.
    protected ProductionLine productionLine;

    //Inputs for this production stage.
    protected LinkedList<ProductionObject> inputs;

    //Outputs for this production stage.
    protected LinkedList<ProductionObject> outputs;

    /** Class Constructor */
    ProductionObject()
    {
        //Initialise variables.
        inputs = new LinkedList<ProductionObject>();
        outputs = new LinkedList<ProductionObject>();
    }

    /** Return whether this production object can have an item deposited. (Called from inputs) */
    public abstract boolean canDeposit();
    /** Return whether this production object can have an item withdrawn. (Called from outputs) */
    public abstract boolean canWithdraw();
    /** Deposit an item into this production object. (Called from inputs) */
    public abstract void depositItem(Item item);
    /** Withdraw an item from this production object. (Called from outputs) */
    public abstract Item withdrawItem();

    /** Adds an input production object to this production object. */
    public void addInput(ProductionObject productionObject)
    {
        //Add passed production object to inputs.
        inputs.add(productionObject);
    }

    /** Adds an output production object to this production object. */
    public void addOutput(ProductionObject productionObject)
    {
        //Add passed production object to outputs.
        outputs.add(productionObject);
    }

    /** Set production object name. */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Return production object name. */
    public String getName()
    {
        return name;
    }
}
