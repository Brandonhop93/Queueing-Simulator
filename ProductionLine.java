/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

import java.util.LinkedList;

public class ProductionLine
{
    //Simulator for production line.
    private Simulator simulator;

    //Stages and storages in production line.
    private LinkedList<Stage> stages;
    private LinkedList<Storage> storages;

    /** Class Constructor */
    public ProductionLine(String[] lineDesign)
    {
        //Initialise variables.
        simulator = new Simulator();
        stages = new LinkedList<>();
        storages = new LinkedList<>();

        //Build production line.
        initialiseProductionLine(lineDesign);
    }

    /** Start the production line. */
    public void startProduction(double runTime)
    {
        //Create a new item to start production with.
        Item item = new Item();
        //Add a location entry to item.
        item.addLocationEntry(storages.getFirst().getName(), 0);

        //Deposit item to create intial production event.
        stages.get(0).depositItem(item);

        //Start the simulator
        simulator.start(runTime);

        //Add final time intervals to stages.
        for (Stage stage: stages)
        {
            stage.updateStatus(0);
        }
    }

    /** Prints out production statistics for the production line. */
    public void printStatistics()
    {
        //Print stage statistics.
        System.out.println("Production Stations:");
        System.out.println("----------------------------------------------------");
        System.out.println("Stage:       Work[%]       Starve[t]        Block[t]");
        for (Stage stage: stages)
        {
            System.out.print(String.format("%-8s", stage.getName()));
            System.out.print(String.format("%1$12s", String.format("%.2f", (stage.getProductionTime() / simulator.getSimulationTime()) * 100) + "%"));
            System.out.print(String.format("%1$16s", String.format("%,.2f", stage.getStarvingTime())));
            System.out.print(String.format("%1$16s", String.format("%,.2f", stage.getBlockingTime())));
            System.out.print("\n");
        }

        System.out.print("\n");

        //Queue statistics, temporary storage for calculations.
        double[] queueArrivalTimeSums     = new double[storages.size()];
        double[] queueArrivalTimeSumsTemp = new double[storages.size()];
        double[] queueItemWaitingTimeSums = new double[storages.size()];
        int[]    queueItemCountSums       = new int   [storages.size()];

        //Production paths, temporary storage for calculations.
        LinkedList<String> pathNames = new LinkedList<String>();
        LinkedList<Integer> pathCounts = new LinkedList<Integer>();

        //Withdraw from destination storage until empty.
        while (storages.getLast().canWithdraw())
        {
            //List of locations visited on an item.
            LinkedList<Location> locations = storages.getLast().withdrawItem().getPath();

            //Initialise new path for item.
            String pathway = "";

            //Loop over all visited locations.
            for (Location location: locations)
            {
                //Location is a storage/queue type.
                if (location.getLocationName().charAt(0) == 'Q')
                {
                    //Index of queue from location name.
                    int index = Integer.valueOf(location.getLocationName().substring(1));

                    //Increase waiting time in queue.
                    queueItemWaitingTimeSums[index] += location.getExitTime() - location.getEntryTime();
                    //Increase item count in queue.
                    queueItemCountSums[index] += 1;
                    //Increase arrival time in queue.
                    queueArrivalTimeSums[index] += location.getEntryTime() - queueArrivalTimeSumsTemp[index];
                    //Assign arrival time to temporary storage.
                    queueArrivalTimeSumsTemp[index] = location.getEntryTime();
                }

                //Location is a stage.
                else
                {
                    if (pathway.equalsIgnoreCase(""))
                    {
                        //Add location to pathway.
                        pathway += location.getLocationName();
                    }

                    else
                    {
                        //Add location to pathway with arrow.
                        pathway += "->" + location.getLocationName();
                    }
                }
            }

            //Index for path counts, returns -1 if doesn't exist.
            int index = pathNames.indexOf(pathway);

            //Path doesn't exist.
            if (index == -1)
            {
                //Add a new path to statistics and set path count to one.
                pathNames.addLast(pathway);
                pathCounts.addLast(1);
            }

            //Path exists.
            else
            {
                //Increase path count by one.
                pathCounts.set(index, pathCounts.get(index) + 1);
            }
        }

        //Print storage/queue statistics.
        System.out.println("Storage Queues");
        System.out.println("-------------------------------------------");
        System.out.println("Store:        AvgTime[t]        AvgItems[t]");
        for (int i = 1; i < storages.size() - 1; i++)
        {
            //Averaged statistics.
            double averageWaitingTime = queueItemWaitingTimeSums[i] / queueItemCountSums[i];
            double averageArrivalRate = 1 / ((queueArrivalTimeSums[i] / queueItemCountSums[i]));

            System.out.print(String.format("%-8s", storages.get(i).getName()));
            System.out.print(String.format("%1$16s", String.format("%,.2f", averageWaitingTime)));
            System.out.print(String.format("%1$19s", String.format("%,.2f", averageWaitingTime * averageArrivalRate)));
            System.out.print("\n");
        }

        System.out.print("\n");

        //Print item paths.
        System.out.println("Production Paths:");
        System.out.println("------------------------------");
        for (int i = 0; i < pathNames.size(); i++)
        {
            System.out.println(pathNames.get(i) + ": " + pathCounts.get(i));
        }
    }

    /** Creates a timed production event via production line simulator. */
    public void createProductionEvent(Stage stage, double duration)
    {
        //Pass the provided stage and duration of item production.
        simulator.addEvent(stage, duration);
    }

    /** Returns the running time of the production line. */
    public double getRunningTime()
    {
        return simulator.getSimulationTime();
    }

    /** Initialises the production line from passed parameters. */
    private void initialiseProductionLine(String[] lineDesign)
    {
        int stageCount = 0;
        int storageCount = 0;
        int lastSize = 1;

        //Create stations and stages.
        for (int i = 0; i < lineDesign.length; i++)
        {
            //Source Storage 'SS'
            if (lineDesign[i].equalsIgnoreCase("SS"))
            {
                //Create source storage.
                SourceStorage sourceStorage = new SourceStorage(this);
                sourceStorage.setName("Q" + storageCount);

                //Add to storages.
                storages.add(sourceStorage);
                storageCount++;
            }

            //Intermediate Storage 'IS-X'
            else if(lineDesign[i].startsWith("IS"))
            {
                //Queue size.
                int size = Integer.valueOf(lineDesign[i].substring(3));

                //Create intermediate storage.
                IntermediateStorage intermediateStorage = new IntermediateStorage(this, size);
                intermediateStorage.setName("Q" + storageCount);

                //Add inputs and outputs.
                int stageSize = stages.size();
                for (int o = stageSize - 1; o >= stageSize - lastSize; o--)
                {
                    //System.out.println(o);
                    stages.get(o).addOutput(intermediateStorage);
                    intermediateStorage.addInput(stages.get(o));
                }

                //System.out.println("");

                //Add to storages.
                storages.add(intermediateStorage);
                storageCount++;
            }

            //Destination Storage 'DS'
            else if(lineDesign[i].equalsIgnoreCase("DS"))
            {
                //Create destination storage.
                DestinationStorage destinationStorage = new DestinationStorage(this);
                destinationStorage.setName("Q" + storageCount);

                //Add inputs and outputs.
                int stageSize = stages.size();
                for (int o = stageSize - 1; o >= stageSize - lastSize; o--)
                {
                    stages.get(o).addOutput(destinationStorage);
                    destinationStorage.addInput(stages.get(o));
                }

                //Add to storages.
                storages.add(destinationStorage);
                storageCount++;
            }

            //Stage 'S-stations-mean-range'
            else
            {
                //0 - Stations, 1 - Mean, 2 - Range.
                String[] symbols = lineDesign[i].substring(2).split("-");
                int stations = Integer.valueOf(symbols[0]);
                double mean = Double.valueOf(symbols[1]);
                double range = Double.valueOf(symbols[2]);
                lastSize = stations;

                if (stations == 1)
                {
                    //Create stage.
                    Stage stage = new Stage(this, mean, range);
                    stage.setName("S" + stageCount);

                    //Add input.
                    stage.addInput(storages.getLast());
                    storages.getLast().addOutput(stage);

                    //Add to stages.
                    stages.add(stage);
                    stageCount++;
                }

                else
                {
                    if (stations > 10)
                    {
                        //Throw runtime exception.
                        throw new RuntimeException("More than ten stations not currently implemented.");
                    }

                    else
                    {
                        //Station suffix.
                        String[] letters = {"a","b","c","d","e","f","g","h","i","j"};

                        for (int o = 0; o < stations; o++)
                        {
                            //Create multi stage.
                            Stage stage = new Stage(this, mean, range);
                            stage.setName("S" + stageCount + letters[o]);

                            //Add inputs and outputs.
                            stage.addInput(storages.getLast());
                            storages.getLast().addOutput(stage);

                            //Add to stages.
                            stages.add(stage);
                        }

                        //Increase stage count.
                        stageCount++;
                    }
                }
            }
        }
    }
}
