/*
 *  Brandon Hopkins - C3290146
 *  Assignment 3
 */

public class PA3
{
    public static void main(String[] args)
    {
        double m = Double.valueOf(args[0]);
        double r = Double.valueOf(args[1]);
        int q = Integer.valueOf(args[2]);

        //Custom line design.
        String[] lineDesign =
        {"SS",
         "S-"+1+"-"+m+"-"+r,
         "IS-"+q,
         "S-"+1+"-"+m+"-"+r,
         "IS-"+q,
         "S-"+2+"-"+m*2+"-"+r*2,
         "IS-"+q,
         "S-"+1+"-"+m+"-"+r,
         "IS-"+q,
         "S-"+2+"-"+m*2+"-"+r*2,
         "IS-"+q,
         "S-"+1+"-"+m+"-"+r,
         "DS"};

        ProductionLine productionLine = new ProductionLine(lineDesign);
        productionLine.startProduction(10000000);
        productionLine.printStatistics();
    }
}

