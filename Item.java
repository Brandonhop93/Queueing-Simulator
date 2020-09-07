/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.LinkedList;

public class Item
{
    //Locations the item has visited.
    private LinkedList<Location> path;

    /** Class Constructor */
    Item()
    {
        //Initialise variables.
        this.path = new LinkedList<Location>();
    }

    /** Add a new location entry on this item. */
    public void addLocationEntry(String locationName, double entryTime)
    {
        if (path.size() != 0)
        {
            //Add exit time to last entry.
            path.getLast().setExitTime(entryTime);
        }

        //Create a new path and add it to path list.
        path.addLast(new Location(locationName, entryTime, entryTime));
    }

    /** Returns path the object has taken. */
    public LinkedList<Location> getPath()
    {
        return path;
    }
}
