import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import java.io.IOException;

/**
 * Helps to deserialize Post object
 */
public class PostsDeserializer extends StdDeserializer<Posts> {

    public PostsDeserializer() {
        this(null);
    }

    public PostsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Posts deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        int userId = (Integer) ((IntNode) node.get("userId")).numberValue();
        String title = node.get("title").asText();
        String body = node.get("body").asText();


        return new Posts(userId, id, title, body);
    }
}