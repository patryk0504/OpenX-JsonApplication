import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/**
 * Represents an user
 */
@JsonDeserialize(using = UsersDeserializer.class)
public class Users {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    public Users(int id, String name, String username, String email, Address address, String phone, String website, Company company){
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Address getAddress() {
        return address;
    }
}

/**
 * Represents an company
 */
class Company{
    private String name;
    private String catchPhrase;
    private String bs;

    public Company(String name, String catchPhrase, String bs){
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
}

