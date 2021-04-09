/**
 * Represents user address.
 */
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    public Address(String street, String suite, String city, String zipcode, Geo geo){
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    //getters
    public double getLat(){
        return Double.parseDouble(geo.getLat());
    }
    public double getLng(){
        return Double.parseDouble(geo.getLng());
    }

}

/**
 * Represents geographical coordinates
 */
class Geo{
    private String lat;
    private String lng;

    public Geo(String lat, String lng){
        this.lat = lat;
        this.lng = lng;
    }

    //getters
    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }
}