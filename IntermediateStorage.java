/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class IntermediateStorage extends Storage
{
    //Max storage queue size.
    private int qMax;

    /** Class Constructor */
    IntermediateStorage(ProductionLine productionLine, int qMax)
    {
        //Initialise parent variables.
        super();

        //Initialise passed variables.
        this.productionLine = productionLine;
        this.qMax = qMax;
    }

    /** Deposit an item into this storage. (Called from inputs) */
    public void depositItem(Item item)
    {
        //Add a location entry to item.
        item.addLocationEntry(name, productionLine.getRunningTime());

        //Loop over all outputs.
        for (ProductionObject output: outputs)
        {
            //Output is not full.
            if (output.canDeposit())
            {
                //Forward item to output.
                output.depositItem(item);
                return;
            }
        }

        //No available outputs, add item to storage queue.
        storage.add(item);
    }

    /** Withdraw an item from this storage. (Called from outputs) */
    public Item withdrawItem()
    {
        //Remove and temporarily store item.
        Item item = storage.remove();

        //Loop over all inputs.
        for (ProductionObject input: inputs)
        {
            //Storage queue is full.
            if (storage.size() != qMax)
            {
                //Input is not empty.
                if (input.canWithdraw())
                {
                    //Withdraw item from input.
                    Item withdrawItem = input.withdrawItem();

                    //Add a location entry to item.
                    withdrawItem.addLocationEntry(name, productionLine.getRunningTime());

                    //Add withdrawn item to storage.
                    storage.add(withdrawItem);
                }
            }

            else
            {
                break;
            }
        }

        //Return front of storage queue.
        return item;
    }

    /** Return whether this storage can have an item deposited. (Called from inputs) */
    public boolean canDeposit()
    {
        //Only deposit if storage isn't full.
        return storage.size() != qMax;
    }

    /** Return whether this storage can have an item withdrawn. (Called from outputs) */
    public boolean canWithdraw()
    {
        //Withdraw if not empty.
        return !storage.isEmpty();
    }
}
