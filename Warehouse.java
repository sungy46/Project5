import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 * @author Yolanda Sung, Haoyi Ding, lab 814
 * @version December 5, 2018
 */

public class Warehouse {
    final static String folderPath = "files/";
    final static File VEHICLE_FILE = new File(folderPath + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(folderPath + "PackageList.csv");
    final static File PROFIT_FILE = new File(folderPath + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(folderPath + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(folderPath + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .85;
    private static ArrayList<Vehicle> vehicles;
    private static ArrayList<Package> packages;
    private static double profits;
    private static int nPackagesShipped;
    private static boolean primeday;
    private static Scanner console = new Scanner(System.in);

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        //TODO

        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        packages = DatabaseManager.loadPackages(PACKAGE_FILE);
        profits = DatabaseManager.loadProfit(PROFIT_FILE);
        nPackagesShipped = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        primeday = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);

        //2) Show menu and handle user inputs
        while (true) {
            System.out.println("==========Options==========");
            System.out.println("1) Add Package");
            System.out.println("2) Add Vehicle");

            if (primeday) {
                System.out.println("3) Deactivate Prime Day");
            } else {
                System.out.println("3) Activate Prime Day");
            }

            System.out.println("4) Send Vehicle");
            System.out.println("5) Print Statistics");
            System.out.println("6) Exit");
            System.out.println("===========================");

            int input = console.nextInt();  //user selected option

            switch (input) {
                case 1:
                    addPackages();
                    break;
                case 2:
                    addVehicle();
                    break;
                case 3:
                    primeday = !primeday;
                    break;
                case 4: // how do you check if vehicle is full?
                    break;
                case 5: // how do you get number of packages in warehouse?
                    printStatisticsReport(profits, nPackagesShipped,
                            packages.size() - nPackagesShipped);
                    break;
                case 6:
                    break;
            }

            break;
        }


        //3) save data (vehicle, packages, profits, packages shipped and primeday)
        // to files (overwriting them) using DatabaseManager
        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);
        DatabaseManager.savePackages(N_PACKAGES_FILE, packages);
        DatabaseManager.saveProfit(PROFIT_FILE, profits);
        DatabaseManager.savePackagesShipped(PACKAGE_FILE, nPackagesShipped);
        DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeday);


    }

    public static void addPackages() {
        String id;
        String product;
        double weight;
        double price;
        String buyerName;
        String address;
        String city;
        String state;
        int zip;
        ShippingAddress destination;

        System.out.println("Enter Package ID:");
        id = console.nextLine();
        System.out.println("Enter Product Name:");
        product = console.nextLine();
        System.out.println("Enter Weight:");
        weight = console.nextDouble();
        console.nextLine();
        System.out.println("Enter Price:");
        price = console.nextDouble();
        console.nextLine();
        System.out.println("Enter Buyer Name:");
        buyerName = console.nextLine();
        System.out.println("Enter Address:");
        address = console.nextLine();
        System.out.println("Enter City:");
        city = console.nextLine();
        System.out.println("Enter State:");
        state = console.nextLine();
        System.out.println("Enter ZIP Code:");
        zip = console.nextInt();
        console.nextLine();

        destination = new ShippingAddress(buyerName, address, city, state, zip);
        if (primeday) {
            price *= PRIME_DAY_DISCOUNT;
        }
        Package temp = new Package(id, product, weight, price, destination);
        packages.add(temp);
        nPackagesShipped++;


        System.out.println();
        temp.shippingLabel();
    }

    public static void addVehicle() {
        System.out.println("Vehicle Options:");
        System.out.println("1) Truck");
        System.out.println("2) Drone");
        System.out.println("3) Cargo Plane");

        int type = console.nextInt();
        console.nextLine();

        System.out.println("Enter License Plate No.:");
        String licensePlate = console.nextLine();

        System.out.println("Enter Maximum Carry Weight:");
        double maxWeight = console.nextDouble();

        if (type == 1) {
            vehicles.add(new Truck(licensePlate, maxWeight));
        } else if (type == 2) {
            vehicles.add(new Drone(licensePlate, maxWeight));
        } else if (type == 3) {
            vehicles.add(new CargoPlane(licensePlate, maxWeight));
        }
    }

    public static void printStatisticsReport(double profits, int packagesShipped, int numberOfPackages) {
        System.out.println("==========Statistics==========");
        System.out.printf("Profits: " + "%16.2f\n", profits);
        System.out.printf("Packages Shipped: " + "%16d\n", packagesShipped);
        System.out.printf("Packages in Warehouse: " + "%16d\n", numberOfPackages);
        System.out.println("==============================");
    }


}