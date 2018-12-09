/**
 * <h1>Database Manager</h1>
 * <p>
 * Used to locally save and retrieve data.
 * <p>
 *
 * @author Yolanda Sung, Haoyi Ding, lab 814
 * @version December 5, 2018
 */

import java.io.*;
import java.util.ArrayList;

public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String[] vehicles;

            String str = br.readLine();
            while (str != null) {
                vehicles = str.split(",");

                if (vehicles[0].equals("Truck")) {
                    vehicle.add(new Truck(vehicles[1], Double.parseDouble(vehicles[2])));
                } else if (vehicles[0].equals("Cargo Plane")) {
                    vehicle.add(new CargoPlane(vehicles[1], Double.parseDouble(vehicles[2])));
                } else if (vehicles[0].equals("Drone")) {
                    vehicle.add(new Drone(vehicles[1], Double.parseDouble(vehicles[2])));
                }

                str = br.readLine();
            }

            br.close();
            fr.close();
            return vehicle;
        } catch (IOException e) {
            System.out.println("hi");
            return vehicle;
        }

    }


    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * <p>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
        ArrayList<Package> packages = new ArrayList<Package>();
        String[] packageParameters;
        if (!file.exists()) {
            return packages;
        } else {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String str = br.readLine();
                while (str != null) {
                    try {
                        packageParameters = str.split(",");
                        ShippingAddress sa = new ShippingAddress(packageParameters[4], packageParameters[5],
                                packageParameters[6], packageParameters[7], Integer.parseInt(packageParameters[8]));
                        packages.add(new Package(packageParameters[0], packageParameters[1],
                                Double.parseDouble(packageParameters[2]), Double.parseDouble(packageParameters[3]), sa));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("ArrayIndexOutOfBoundsException was caught in the loadPackages method.");
                    }
                    str = br.readLine();
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
                System.out.println("Exception " + e + " was caught");
                return packages;
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Exception " + e + " was caught");
                return packages;
            }
            return packages;
        }
    }


    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     *
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        Double profit = 0.0;
        if (!file.exists()) {
            return 0;
        } else {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                profit = Double.parseDouble(br.readLine());
                br.close();
                fr.close();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Exception " + e + " was caught");
            }
        }
        return profit;
    }

    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     *
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        int packages = 0;
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                packages = Integer.parseInt(br.readLine());
                br.close();
                fr.close();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Exception " + e + " was caught");
            }
        }
        return packages;
    }


    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     *
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String prime = br.readLine();
                br.close();
                fr.close();
                return prime.equals("1");
            } catch (IOException e) {
                System.out.println("Exception " + e + " was caught");
            }
        }
        return false;
    }


    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     *
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        String type = "";
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i) instanceof CargoPlane) {
                    type = "Cargo Plane";
                } else if (vehicles.get(i) instanceof Drone) {
                    type = "Drone";
                } else if (vehicles.get(i) instanceof Truck) {
                    type = "Truck";
                }

                bw.write(type + "," + vehicles.get(i).getLicensePlate() + "," + vehicles.get(i).getMaxWeight());
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID (String)</li>
     * <li>Product Name (String)</li>
     * <li>Weight (double)</li>
     * <li>Price (double)</li>
     * <li>Address Name (String)</li> (Or Buyer name?)
     * <li>Address (String)</li>
     * <li>City (String)</li>
     * <li>State (String)</li>
     * <li>ZIP Code (Integer)</li>
     * </ol>
     *
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        String id;
        String productName;
        double weight;
        double price;
        String name;
        String addressName;
        String state;
        String city;
        int zip;

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < packages.size(); i++) {
                id = packages.get(i).getID();
                productName = packages.get(i).getProduct();
                weight = packages.get(i).getWeight();
                price = packages.get(i).getPrice();
                name = packages.get(i).getDestination().getName();
                addressName = packages.get(i).getDestination().getAddress();
                city = packages.get(i).getDestination().getCity();
                state = packages.get(i).getDestination().getState();
                zip = packages.get(i).getDestination().getZipCode();

                bw.write(id + "," + productName + "," + weight + "," + price + "," + name + "," +
                        addressName + "," + city + "," + state + "," + zip);
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exception " + e + " was caught");
        }
    }


    /**
     * Saves profit to text file.
     *
     * @param file   File to write profits to
     * @param profit Total profits
     */
    public static void saveProfit(File file, double profit) {
        try {
            FileWriter fw = new FileWriter(file); // Overwrites file
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("" + profit);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exception " + e + " was caught");
        }
    }


    /**
     * Saves number of packages shipped to text file.
     *
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        try {
            FileWriter fw = new FileWriter(file, false); // Overwrites file
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("" + nPackages);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exception " + e + " was caught");
        }
    }


    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * written, otherwise "0" will be written.
     *
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        try {
            FileWriter fw = new FileWriter(file, false); // Overwrites file
            BufferedWriter bw = new BufferedWriter(fw);
            if (primeDay) {
                bw.write("1");
            } else {
                bw.write("0");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exception " + e + " was caught");
        }
    }
}