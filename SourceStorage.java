/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class SourceStorage extends Storage
{
    /** Class Constructor */
    SourceStorage(ProductionLine productionLine)
    {
        //Initialise parent variables.
        super();

        //Initialise variables.
        this.productionLine = productionLine;
    }

    /** Deposit an item into this storage. (Called from inputs) */
    public void depositItem(Item item)
    {
        throw new UnsupportedOperationException("Insert is not supported for Source Storage");
    }

    /** Withdraw an item from this storage. (Called from outputs) */
    public Item withdrawItem()
    {
        //Create a new item to withdraw.
        Item item = new Item();

        //Add a location entry to item.
        item.addLocationEntry(name, productionLine.getRunningTime());

        return item;
    }

    /** Return whether this storage can have an item deposited. (Called from inputs) */
    public boolean canDeposit()
    {
        //Source storage, always return false.
        return false;
    }

    /** Return whether this storage can have an item withdrawn. (Called from outputs) */
    public boolean canWithdraw()
    {
        //Source storage, always return true.
        return true;
    }
}
