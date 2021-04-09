import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/**
 * Represents post published by user.
 */
@JsonDeserialize(using = PostsDeserializer.class)
public class Posts {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Posts(int userID, int ID, String title, String body){
        this.userId = userID;
        this.id = ID;
        this.title = title;
        this.body = body;
    }

    //getters
    public int getUserID() {
        return userId;
    }
    public String getTitle() {
        return title;
    }
}
