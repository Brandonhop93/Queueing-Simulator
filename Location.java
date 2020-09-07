/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class Location
{
    //Location name.
    private String locationName;

    //Location time statistics.
    private double entryTime;
    private double exitTime;

    /** Class Constructor */
    Location(String locationName, double entryTime, double exitTime)
    {
        //Initialise variables.
        this.locationName = locationName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    /** Set name for location. */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /** Get name for location. */
    public String getLocationName()
    {
        return locationName;
    }

    /** Set entry time for location. */
    public void setEntryTime(double entryTime)
    {
        this.entryTime = entryTime;
    }

    /** Get entry time for location. */
    public double getEntryTime()
    {
        return entryTime;
    }

    /** Set exit time for location. */
    public void setExitTime(double exitTime)
    {
        this.exitTime = exitTime;
    }

    /** Get exit time for location. */
    public double getExitTime()
    {
        return exitTime;
    }
}
