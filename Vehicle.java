import java.util.ArrayList;

/**
 * <h1>Vehicle</h1> Represents a vehicle
 */


public class Vehicle implements Profitable {
    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;
    private int maxRange; //added this field variable


    /**
     * Default Constructor
     */
    public Vehicle () {
        this.licensePlate = "";
        this.maxWeight = 0;
        this.currentWeight = 0;
        this.zipDest = 0;
        packages = new ArrayList<Package>();
    }


    /**
     * Constructor
     * 
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight of vehicle
     */


    int range = 0;

    public Vehicle (String licensePlate, double maxWeight) {
        this.licensePlate = licensePlate;
        this.maxWeight = maxWeight;
    }

    
    /**
     * Returns the license plate of this vehicle
     * 
     * @return license plate of this vehicle
     */
    public String getLicensePlate() {
        return this.licensePlate;
    }

    
    /**
     * Updates the license plate of vehicle
     * 
     * @param licensePlate license plate to be updated to
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    

    /**
     * Returns the maximum weight this vehicle can carry
     * 
     * @return the maximum weight that this vehicle can carry
     */
    public double getMaxWeight() {
        return this.maxWeight;
    }
    
    /**
     * Updates the maximum weight of this vehicle
     * 
     * @param maxWeight max weight to be updated to
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    
    /**
     * Returns the current weight of all packages inside vehicle
     * 
     * @return current weight of all packages inside vehicle
     */
    public double getCurrentWeight() {
        return this.currentWeight;
    }

    /**
     * Returns the current ZIP code desitnation of the vehicle
     * 
     * @return current ZIP code destination of vehicle
     */
    public int getZipDest() {
        return this.zipDest;
    }


    /**
     * Updates the ZIP code destination of vehicle
     * 
     * @param zipDest ZIP code destination to be updated to
     */
    public void setZipDest(int zipDest) {
        this.zipDest = zipDest;
    }
    
    
    /**
     * Returns ArrayList of packages currently in Vehicle
     * 
     * @return ArrayList of packages in vehicle
     */
    public ArrayList<Package> getPackages() {
        return this.packages;
    }

    
    /**
     * Adds Package to the vehicle only if has room to carry it (adding it would not
     * cause it to go over its maximum carry weight).
     * 
     * @param pkg Package to add
     * @return whether or not it was successful in adding the package
     */
    public boolean addPackage(Package pkg) {
        if (this.currentWeight <= this.maxWeight) {
            packages.add(pkg);
            this.currentWeight = this.currentWeight + pkg.getWeight();

            return true;
        }
        return false;
    }

    
    /**
     * Clears vehicle of packages and resets its weight to zero
     */
    public void empty() {
        packages.clear();
        this.currentWeight = 0;
    }

    /**
     * Returns true if the Vehicle has reached its maximum weight load, false
     * otherwise.
     * 
     * @return whether or not Vehicle is full
     */
    public boolean isFull() {
        if (this.currentWeight >= this.maxWeight) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Fills vehicle with packages with preference of date added and range of its
     * destination zip code. It will iterate over the packages intially at a range
     * of zero and fill it with as many as it can within its range without going
     * over its maximum weight. The amount the range increases is dependent on the
     * vehicle that is using this. This range it increases by after each iteration
     * is by default one.
     * 
     * @param warehousePackages List of packages to add from
     */


    public void fill(ArrayList<Package> warehousePackages) {
        this.maxRange = 0;

        while (!isFull() && warehousePackages.size() != 0) {
            for (int i = 0; i <warehousePackages.size(); i++) {
                int difference = Math.abs(this.zipDest - warehousePackages.get(i).getDestination().getZipCode());
                if (((difference == range))) {
                    addPackage(warehousePackages.get(i));
                    System.out.println("The package was added"); //need this??
                    warehousePackages.remove(i);
                    maxRange = range;
                }
                i = i - 1;
            }

            range = range + 1; //since it's in an arraylist, you need to do this because the size decreases
        }
    }


    @Override
    public double getProfit() {
        double total = 0.0;
        for (int i = 0; i <packages.size() ; i++) {
            total = total + packages.get(i).getPrice();
        }
        return total;
    }

    @Override
    public String report() {
        return "";
    }

    //public getMaxRange

    // getMaxRange --> go through packages....and subtract like fill.... but set the highest one to that one


}
