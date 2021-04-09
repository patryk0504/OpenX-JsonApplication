import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

/**
 * Helps to deserialize User object
 */
public class UsersDeserializer extends StdDeserializer<Users> {

    public UsersDeserializer() {
        this(null);
    }

    public UsersDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Users deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        int id = (Integer) node.get("id").asInt();
        String name = node.get("name").asText();
        String username = node.get("name").asText();
        String email = node.get("email").asText();
        String phone = node.get("phone").asText();
        String website = node.get("website").asText();

        //Company
        String nameComp = node.get("company").get("name").asText();
        String catchComp = node.get("company").get("catchPhrase").asText();
        String bs = node.get("company").get("bs").asText();
        Company company = new Company(nameComp, catchComp, bs);

        //Address
        String street = node.get("address").get("street").asText();
        String suite = node.get("address").get("suite").asText();
        String city = node.get("address").get("city").asText();
        String zipcode = node.get("address").get("zipcode").asText();
        //Geo
        String lat = node.get("address").get("geo").get("lat").asText();
        String lng = node.get("address").get("geo").get("lng").asText();

        Geo geo = new Geo(lat,lng);
        Address address = new Address(street,suite,city,zipcode,geo);


        return new Users(id, name, username, email, address, phone, website, company);
    }
}