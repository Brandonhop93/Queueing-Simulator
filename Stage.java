/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.Random;

public class Stage extends ProductionObject implements Process
{
    //0 Starving, 1 Production, 2 Blocking
    private int status;

    //Location time statistics.
    private double totalStarvingTime;
    private double totalProductionTime;
    private double totalBlockingTime;

    //Stage production properties.
    private double mean;
    private double range;

    //Current item inside the station.
    private Item item;

    /** Class Constructor */
    public Stage(ProductionLine productionLine, double mean, double range)
    {
        //Initialise default variables.
        super();

        //Initialise variables.
        this.productionLine = productionLine;
        this.status = 0;
        this.totalStarvingTime = 0;
        this.totalProductionTime = 0;
        this.totalBlockingTime = 0;
        this.mean = mean;
        this.range = range;
    }

    /** Updates the status of this stage. */
    public void updateStatus(int status)
    {
        //Get time difference since last change.
        double interval = productionLine.getRunningTime() - totalStarvingTime - totalProductionTime - totalBlockingTime;

        //Switch via previous status.
        switch (this.status)
        {
            case 0:
                //Status was starving.
                totalStarvingTime += interval;
                break;
            case 1:
                //Status was production.
                totalProductionTime += interval;
                break;
            case 2:
                //Status was blocking.
                totalBlockingTime += interval;
                break;
        }

        //Assign new status.
        this.status = status;
    }

    /** Generates a new executable event to run when processing the current item is completed. */
    private void processItem()
    {
        //Add a location entry to item.
        item.addLocationEntry(name, productionLine.getRunningTime());

        //Create a new production line event.
        productionLine.createProductionEvent(this, (mean + range * (new Random().nextDouble() - 0.5)));

        //Set status to production.
        updateStatus(1);
    }

    /** Processing of an item has finished. */
    public void execute()
    {
        //Loop over all outputs.
        for (ProductionObject output: outputs)
        {
            //Output is not full.
            if (output.canDeposit())
            {
                //Pass item forward to output.
                output.depositItem(item);
                item = null;

                //Loop over all inputs.
                for (ProductionObject input: inputs)
                {
                    //Input isn't empty.
                    if (input.canWithdraw())
                    {
                        //Remove item from input.
                        item = input.withdrawItem();

                        //Add a processing event for newly arrived item.
                        processItem();
                        return;
                    }
                }

                //No available inputs, set status to starving.
                updateStatus(0);
                return;
            }
        }

        //No available outputs, set status to blocking.
        updateStatus(2);
    }

    /** Deposit an item into this station. (Called from inputs) */
    public void depositItem(Item item)
    {
        //Add item to this station.
        this.item = item;

        //Add a processing event for newly arrived item.
        processItem();
    }

    /** Withdraw an item from this station. (Called from outputs) */
    public Item withdrawItem()
    {
        //Remove and temporarily store item.
        Item item = this.item;
        this.item = null;

        //Loop over all inputs.
        for (ProductionObject input: inputs)
        {
            //Input is not empty.
            if (input.canWithdraw())
            {
                //Remove item from input.
                this.item = input.withdrawItem();

                //Add a processing event for newly arrived item.
                processItem();

                //Return production item.
                return item;
            }
        }

        //No available inputs, set status to starving.
        updateStatus(0);

        //Return production item.
        return item;
    }

    /** Return whether this stage can have an item deposited. (Called from inputs) */
    public boolean canDeposit()
    {
        //Only deposit if the stage is starving.
        return status == 0;
    }

    /** Return whether this stage can have an item withdrawn. (Called from outputs) */
    public boolean canWithdraw()
    {
        //Only withdraw if the stage is blocking.
        return status == 2;
    }

    /** Return starving time for this stage. */
    public double getStarvingTime()
    {
        return totalStarvingTime;
    }

    /** Return production time for this stage. */
    public double getProductionTime()
    {
        return totalProductionTime;
    }

    /** Return blocking time for this stage. */
    public double getBlockingTime()
    {
        return totalBlockingTime;
    }
}
