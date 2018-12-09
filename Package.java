import java.text.NumberFormat;
import java.util.Locale;

/**
 * <h1>Package</h1> Represents a package
 */

public class Package {
    private String id;
    private String product;
    private double weight;
    private double price;
    private ShippingAddress destination;

    public Package() { //default constructor
        this.id = "";
        this.product = "";
        this.weight = 0;
        this.price = 0;
        this.destination = new ShippingAddress();
    }

    public Package(String id, String product, double weight, double price, ShippingAddress destination) {
        this.id = id; //id number of product
        this.product = product; //name of product
        this.weight = weight; //weight of package
        this.price = price; //price of product
        this.destination = destination; //destination of package
    }


    /**
     * @return id of package
     */
    public String getID() {
        return this.id;
    }

    /**
     * @return Name of product in package
     */
    public String getProduct() {
        return this.product;
    }

    /**
     * @param product the product name to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return price of product in package
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return Package weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return The shipping address of package
     */
    public ShippingAddress getDestination() {
        return this.destination;
    }

    /**
     * @param destination the shipping address to set
     */
    public void setDestination(ShippingAddress destination) {
        this.destination = destination;
    }

    /**
     * @return The package's shipping label.
     */

    public String shippingLabel() {
        String dash = "====================";
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        String price = nf.format(this.price);
        String weight = String.format("%.2f", this.weight);
        String longDash = "==============================";

        String label = (dash + "\n" + "TO: " + destination.getName() + "\n" +
                destination.getAddress() + "\n" +
                destination.getCity() + ", " + destination.getState() + ", " + destination.getZipCode() + "\n" +
                "Weight: " + "         " + weight + "\n" +
                "Price: " + "        " + price + "\n" +
                "Product: " + this.product + "\n" + dash + "\n" + longDash + "\n");
        return label;
    }
}