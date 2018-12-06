import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * <h1>Truck</h1> Represents a Truck
 */

public class Truck extends Vehicle {

    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;
    private final double GAS_RATE = 1.66;
    private int maxRange;

    /**
     * Default Constructor
     */

    public Truck () { //check
        super ();
    }

    /**
     * Constructor
     * 
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight that the vehicle can hold
     */

    public Truck (String licensePlate, double maxWeight) {
        super (licensePlate, maxWeight);
    }


    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getCurrentWeight() {
        return this.currentWeight;
    }

    public int getZipDest() {
        return this.zipDest;
    }

    public void setZipDest(int zipDest) {
        this.zipDest = zipDest;
    }

    public ArrayList <Package> getPackages() {
        return this.packages;
    }

    /**
     * Returns the profits generated by the packages currently in the Vehicle.
     * <p>
     * &sum;p<sub>price</sub> - (range<sub>max</sub> &times; 1.66)
     * </p>
     */

    NumberFormat nf = NumberFormat.getInstance(Locale.US);

    @Override
    public double getProfit() { //check this
        double profit = 0.0;
        double priceOfPackage = 0.0;


        for (int i = 0; i <packages.size(); i++) {
            priceOfPackage = packages.get(i).getPrice();
            profit = Double.parseDouble(nf.format(profit + ((priceOfPackage) - (getMaxRange() * GAS_RATE))));
        }
    	return profit;
    }

    public boolean addPackage(Package pkg) {
        if (this.currentWeight <= this.maxWeight) {
            packages.add(pkg);
            this.currentWeight = this.currentWeight + pkg.getWeight(); //add weight of pkg to current weight
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (this.currentWeight >= this.maxWeight) { //equal to because if it's the max weight, you can't put more pkg
            return true;
        }
        return false;
    }

    public void fill(ArrayList<Package> warehousePackages) {

        while (!isFull() && warehousePackages.size() != 0) { //if the vehicle isn't full and if there are still packages in the warehouse
            for (int i = 0; i <warehousePackages.size(); i++) { //go through the items in the warehouse
                int difference = Math.abs(this.zipDest - warehousePackages.get(i).getDestination().getZipCode());
                //line above is getting the closest zip code first...comparing the this zip code with the package's zip code
                if (((difference == range))) { //so the range is 0 at first, so it'll find all the packages with 0 difference/range (closest)
                    addPackage(warehousePackages.get(i)); //adds the package into the vehicle
                    System.out.println("The package was added"); //Kelly said that we needed this :) Do we though??
                    warehousePackages.remove(i); //removes the package from the warehouse
                    maxRange = range; //the max range is the farthest the vehicle delivers
                }
                //      i = i - 1; ...don't remember what this was for
            }

            range = range + 1; //since it's in an arraylist, you need to do this because the size decreases

        }
    }

    public int getMaxRange() {
        return this.maxRange;
    }

    /**
     * Generates a String of the truck report. Truck report includes:
     * <ul>
     * <li>License Plate No.</li>
     * <li>Destination</li>
     * <li>Current Weight/Maximum Weight</li>
     * <li>Net Profit</li>
     * <li>Shipping labels of all packages in truck</li>
     * </ul>
     * 
     * @return Truck Report
     */
    @Override
    public String report() {
        String endResult = "";
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        String netProf = nf.format(getProfit());

        /////truck report

    	String truck = "==========Truck Report==========";
    	String license = "License Plate No.: " + this.licensePlate;
    	String destination = "Destination: " + getZipDest();
    	String weightLoad = String.format("Weight load: %.2f", getCurrentWeight() / getMaxWeight());
    	String netProfit = "Net Profit: $%.2f" + netProf;
    	String shippingLabels = "=====Shipping Labels=====";
    	String dash = "====================";


    	///////shipping labels

        for (int i = 0; i <packages.size(); i++) {
            endResult += dash + "\n" + packages.get(i).toString() + "\n" + dash + "\n";
        }

        return truck + "\n" + license + "\n" + destination + "\n" + weightLoad + "\n" + netProfit
                + "\n" + shippingLabels + "\n" + endResult;
    }

}