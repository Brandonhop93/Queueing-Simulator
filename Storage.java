/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.LinkedList;
import java.util.Queue;

public abstract class Storage extends ProductionObject
{
    //FIFO storage queue.
    protected Queue<Item> storage;

    /** Class Constructor */
    Storage()
    {
        //Initialise variables.
        this.storage = new LinkedList<Item>();
    }
}
