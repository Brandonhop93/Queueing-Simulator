/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class DestinationStorage extends Storage
{
    /** Class Constructor */
    DestinationStorage(ProductionLine productionLine)
    {
        //Initialise parent variables.
        super();

        //Initialise variables.
        this.productionLine = productionLine;
    }

    /** Deposit an item into this storage. (Called from inputs) */
    public void depositItem(Item item)
    {
        //Add a location entry to item.
        item.addLocationEntry(name, productionLine.getRunningTime());

        //Add item to storage queue.
        storage.add(item);
    }

    /** Withdraw an item from this storage. (Called by Production Line) */
    public Item withdrawItem()
    {
        //Return storage item.
        return storage.remove();
    }

    /** Return whether this storage can have an item deposited. (Called from inputs) */
    public boolean canDeposit()
    {
        //Always return true as destination storage type.
        return true;
    }

    /** Return whether this storage can have an item withdrawn. (Called by Production Line) */
    public boolean canWithdraw()
    {
        //Withdraw if not empty.
        return !storage.isEmpty();
    }
}
