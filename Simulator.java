/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.PriorityQueue;

public class Simulator
{
    //Scheduled event priority queue.
    private PriorityQueue<Event> scheduledEvents;

    //Current simulation time.
    private double simulationTime;

    //Event ID
    private long nextEventID;

    /** Class Constructor */
    Simulator()
    {
        //Initialise variables.
        this.scheduledEvents = new PriorityQueue<Event>();
        this.simulationTime = 0;
        this.nextEventID = 0;
    }

    /** Start the simulator. */
    public void start(double runTime)
    {
        //Get first event in queue.
        Event event = scheduledEvents.poll();

        //Simulate until no events left in queue or runTime is reached.
        while (event != null && (simulationTime < runTime))
        {
            //Update the simulation time to next occurring event.
            simulationTime = event.getOccurrenceTime();

            //Execute the event.
            event.execute();

            //Get the next event in the event queue.
            event = scheduledEvents.poll();
        }
    }

    /** Add a new event to the event queue. */
    public void addEvent(Process process, double duration)
    {
        //Store and increment event ID.
        long newEventId = nextEventID;
        nextEventID ++;

        //Schedule new event with target process, time to execute and event ID.
        scheduledEvents.add(new Event(process, simulationTime + duration, newEventId));
    }

    /** Return the current simulation time. */
    public double getSimulationTime()
    {
        //Return the current time of the simulation.
        return simulationTime;
    }
}
