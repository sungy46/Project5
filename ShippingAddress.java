/**
 * <h1>Shipping Address</h1> Represents a shipping address
 * @author Yolanda Sung, Haoyi Ding, lab 814
 * @version December 5, 2018
 */
public class ShippingAddress {
    private String name;
    private String address;
    private String city;
    private String state;
    private int zipCode;

    public ShippingAddress() {
        this.name = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipCode = 0;
    }

    public ShippingAddress(String name, String address, String city, String state, int zipCode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}