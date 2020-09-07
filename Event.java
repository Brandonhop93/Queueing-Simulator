/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class Event implements Comparable<Event>
{
    //Process to run on event occurrence.
    private Process process;

    //Time the event is scheduled to occur.
    private double occurrenceTime;

    //Event ID for comparison.
    private long eventID;

    /** Class Constructor */
    Event(Process process, double occurrenceTime, long eventID)
    {
        //Initialise variables.
        this.process = process;
        this.occurrenceTime = occurrenceTime;
        this.eventID = eventID;
    }

    /** Execute the event. */
    public void execute()
    {
        process.execute();
    }

    /** Compare to method for */
    public int compareTo(Event other)
    {
        //Compare occurrence time of both events.
        int time = Double.compare(this.getOccurrenceTime(), other.getOccurrenceTime());

        //Times are equal, compare by event ID.
        if (time == 0)
        {
            //Returns: 1 if positive, 0 if equal, -1 if negative.
            return Long.signum(eventID - other.getEventID());
        }

        return time;
    }

    /** Return the events occurrence time. */
    public double getOccurrenceTime()
    {
        return occurrenceTime;
    }

    /** Return the events ID. */
    public long getEventID()
    {
        return eventID;
    }
}
