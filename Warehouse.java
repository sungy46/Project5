import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 *
 * @author Yolanda Sung, Haoyi Ding, lab 814
 * @version December 9, 2018
 */

public class Warehouse {
    final static String FOLDERPATH = "files/";
    //final static String folderPath = "";

    final static File VEHICLE_FILE = new File(FOLDERPATH + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(FOLDERPATH + "PackageList.csv");
    final static File PROFIT_FILE = new File(FOLDERPATH + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(FOLDERPATH + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(FOLDERPATH + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .85;
    private static ArrayList<Vehicle> vehicle = new ArrayList<>();
    private static ArrayList<Package> package2;
    private static double profits;
    private static int nPackagesShipped;
    private static boolean primeday;
    private static Scanner console = new Scanner(System.in);
    private static String id;
    private static Package temp;
    private static double weight;

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        boolean a = true;

        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        System.out.println("Calling loadVehicles method");
        vehicle = DatabaseManager.loadVehicles(VEHICLE_FILE); // Program fails over here; doesn't print the statement
        System.out.println("Warehouse class vehicles: " + vehicle.size()); // Checking to make sure load... works
        package2 = DatabaseManager.loadPackages(PACKAGE_FILE);
        profits = DatabaseManager.loadProfit(PROFIT_FILE);
        nPackagesShipped = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        primeday = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);

        //2) Show menu and handle user inputs
        while (a) {
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
            console.nextLine();
            System.out.println();

            switch (input) {
                case 1:
                    addPackages();
                    break;
                case 2:
                    addVehicle();
                    break;
                case 3:
                    if (primeday) {
                        for (int i = 0; i < package2.size(); i++) {
                            package2.get(i).setPrice(package2.get(i).getPrice() * PRIME_DAY_DISCOUNT);
                        }
                    } else {
                        for (int i = 0; i < package2.size(); i++) {
                            package2.get(i).setPrice(package2.get(i).getPrice() / PRIME_DAY_DISCOUNT);
                        }
                    }
                    primeday = !primeday;
                    break;
                case 4:
                    if (vehicle.size() == 0) {
                        System.out.println("Error: No vehicles available.");
                        break;
                    } else if (package2.size() == 0) {
                        System.out.println("Error: No packages available.");
                        break;
                    } else {
                        System.out.println("Options:");
                        System.out.println("1) Send Truck");
                        System.out.println("2) Send Drone");
                        System.out.println("3) Send Cargo Plane");
                        System.out.println("4) Send First Available");

                        int transportMode = console.nextInt();
                        console.nextLine();
                        boolean vehicleFound = false;
                        int vehicleIndex = -1;

                        if (transportMode == 1) {
                            for (int i = 0; i < vehicle.size(); i++) {
                                if (vehicle.get(i) instanceof Truck) {
                                    vehicleIndex = i;
                                    vehicleFound = true;
                                    //vehicles.get(i).addPackage(temp);
                                    break; //breaks out of for loop
                                }
                            }
                            if (!vehicleFound) {
                                System.out.println("Error: No vehicles of selected type are available.");
                                System.out.println();
                            }
                        } else if (transportMode == 2) {
                            for (int i = 0; i < vehicle.size(); i++) {
                                if (vehicle.get(i) instanceof Drone) {
                                    vehicleFound = true;
                                    vehicleIndex = i;
                                    break; //breaks out of for loop
                                }
                            }
                            if (!vehicleFound) {
                                System.out.println("Error: No vehicles of selected type are available.");
                                System.out.println();
                            }
                        } else if (transportMode == 3) {
                            for (int i = 0; i < vehicle.size(); i++) {
                                if (vehicle.get(i) instanceof CargoPlane) {
                                    vehicleIndex = i;
                                    vehicleFound = true;
                                    break; //breaks out of for loop
                                }
                            }
                            if (!vehicleFound) {
                                System.out.println("Error: No vehicles of selected type are available.");
                                System.out.println();
                            }
                        } else if (transportMode == 4) {
                            vehicleIndex = 0;
                        } else {
                            System.out.println("The option selected is not valid.");
                            System.out.println();
                            break;
                        }

                        if (vehicleFound) {
                            System.out.println("ZIP Code Options:");
                            System.out.println("1) Send to first ZIP Code");
                            System.out.println("2) Send to mode of ZIP Codes");

                            int zipOption = console.nextInt();
                            console.nextLine();
                            int zip = -1;

                            if (zipOption == 1) {
                                zip = package2.get(0).getDestination().getZipCode();
                            } else if (zipOption == 2) {
                                zip = zipMode(package2);
                            }
                            vehicle.get(vehicleIndex).setZipDest(zip);
                            vehicle.get(vehicleIndex).fill(package2);

                            System.out.println(id + " has been added.");
                            System.out.println(vehicle.get(vehicleIndex).report());

                            //System.out.println(packages.get(packages.size()-1).shippingLabel());
                            vehicle.remove(vehicleIndex);
                        }
                    }
                    break;
                case 5:
                    printStatisticsReport(profits, nPackagesShipped,
                            package2.size());
                    break;
                case 6:
                    a = false;
                    break;
                default:
                    System.out.println("Option selected is not valid.");
            }
        }

        //3) save data (vehicle, packages, profits, packages shipped and primeday)
        // to files (overwriting them) using DatabaseManager
        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicle);
        DatabaseManager.savePackages(PACKAGE_FILE, package2);
        DatabaseManager.saveProfit(PROFIT_FILE, profits);
        DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, nPackagesShipped);
        DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeday);
    }

    public static void addPackages() {
        String product;
//        double weight;
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

        temp = new Package(id, product, weight, price, destination);
        package2.add(temp);
        nPackagesShipped++;

        System.out.println();
        System.out.println(temp.shippingLabel());
        System.out.println();
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
            vehicle.add(new Truck(licensePlate, maxWeight));
        } else if (type == 2) {
            vehicle.add(new Drone(licensePlate, maxWeight));
        } else if (type == 3) {
            vehicle.add(new CargoPlane(licensePlate, maxWeight));
        }
    }

    public static int zipMode(ArrayList<Package> packages) {
        int[] mode = new int[packages.size()];
        int count = 0;

        for (int i = 0; i < packages.size(); i++) {
            for (int a = 0; a < packages.size(); a++) {
                if (a != i) {
                    if (packages.get(i).equals(packages.get(a))) {
                        count++;
                    }
                }
            }
            mode[i] = count;
            count = 0;
        }
        int max = mode[0];
        int index = 0;
        for (int b = 1; b < mode.length; b++) {
            if (max < mode[b]) {
                max = mode[b];
                index = b;
            }
        }
        return packages.get(index).getDestination().getZipCode();
    }

    public static void printStatisticsReport(double profit, int packagesShipped, int numberOfPackages) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("==========Statistics==========");
        System.out.println("Profits: " + nf.format(profit));
        //System.out.printf("Packages Shipped: " + "%10d\n", packagesShipped);
        System.out.printf("%s%10d", "Packages Shipped: ", packagesShipped);
        System.out.println();
        //System.out.printf("Packages in Warehouse: " + "%10d\n", numberOfPackages);
        System.out.printf("%s%5d", "Packages in Warehouse: ", numberOfPackages);
        System.out.println();
        System.out.println("==============================");
        System.out.println();
    }
}