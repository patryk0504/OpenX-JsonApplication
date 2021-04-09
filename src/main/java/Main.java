import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main class contains
 */
public class Main {
    public final static double RADIUS_OF_EARTH= 6371;//km

    List<Posts> listPost;
    List<Users> listUser;

    //setters
    public void setListUser(List<Users> listUser) {
        this.listUser = listUser;
    }
    public void setListPost(List<Posts> listPost) {
        this.listPost = listPost;
    }

    public static void main(String[] args) {
        String pathPosts = "https://jsonplaceholder.typicode.com/posts";
        String pathUsers = "https://jsonplaceholder.typicode.com/users";
        new Main().runProgram(pathPosts,pathUsers);
    }

    /**
     * Main procedure
     * @param pathPosts URL path to Posts Json
     * @param pathUsers URL path to User Json
     */
    public void runProgram(String pathPosts, String pathUsers){
        ObjectMapper mapper = new ObjectMapper();

        String jsonStringPosts = null;
        String jsonStringUsers = null;
        try{
            jsonStringPosts = readStringFromUrl(pathPosts);
            jsonStringUsers = readStringFromUrl(pathUsers);

            setListPost(mapper.readValue(jsonStringPosts, new TypeReference<List<Posts>>(){}));
            setListUser(mapper.readValue(jsonStringUsers, new TypeReference<List<Users>>(){}));

            //ZADANIA
            //liczba postow
            var numberOfPosts = getNumberOfPosts();
            System.out.println("Lista postow ->");
            numberOfPosts.forEach(x -> System.out.print(x + "\n"));
            System.out.println("-----------------------------------");
            //czy tytuly postów są unikalne
            System.out.println("Czy tytuły postów są unikalne? -> ");
            var setOfDuplicates = getDuplicateTitles();
            if(setOfDuplicates.isEmpty())
                System.out.println("Tytuły są unikalne");
            else
                System.out.println(setOfDuplicates);
            System.out.println("-----------------------------------");
            //szukanie uzytkownika w najblizszej odleglosci
            int id = 1;
            System.out.println("Najblizszy \"sąsiad\" uzytkownika o id = " + id +" -> ");
            searchUser(id);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns list of users and number of posts written by a specific user
     * @return List of String
     */
    public List<String> getNumberOfPosts(){
        List<String> numberOfPosts = new LinkedList<>();
        String message = "$1 napisał(a) $2 postów";
        for(var users : listUser){
            int counter = 0;
            for(var posts:listPost){
                if(posts.getUserID() == users.getId())
                    counter +=1;
            }
            numberOfPosts.add(message.replace("$1",users.getUsername()).replace("$2",String.valueOf(counter)));
        }
        return numberOfPosts;
    }

    /**
     * Returns Set of titles that are duplicated
     * @return Set of String
     */
    public Set<String> getDuplicateTitles(){
        Set<String> setOfDuplicates = new HashSet<String>();
        Set<String> setHelper = new HashSet<String>();
        for(var posts : listPost){
            if(!setHelper.add(posts.getTitle())){
                setOfDuplicates.add(posts.getTitle());
            }
        }
        return setOfDuplicates;
    }

    /**
     * Calculates distance between two points using Haversine formula.
     * @param user1Lat First user latitude
     * @param user1Lng  First user longitude
     * @param user2Lat Second user latitude
     * @param user2Lng  Second user latitude
     * @return distance between two users
     */
    public double calculateDistance(double user1Lat, double user1Lng, double user2Lat, double user2Lng){
        double latDistance = Math.toRadians(user1Lat - user2Lat);
        double lngDistance = Math.toRadians(user1Lng - user2Lng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(user1Lat)) * Math.cos(Math.toRadians(user2Lat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (Math.round(RADIUS_OF_EARTH * c));
    }

    /**
     * Returns User object with a specific ID
     * @param id id parameter
     * @return null if user not exist, User object if exist
     */
    public Users getUserByID(int id){
        for(var user : listUser){
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    /**
     * Searches nearest user to a specific user by id
     * @param userId id of the user that we want to return the nearest user
     */
    public void searchUser(int userId){
        Users foundUser = getUserByID(userId);
        if(foundUser == null){
            System.out.println("Brak uzytkownika o podanym ID");
        }else{
            double min = Double.MAX_VALUE;
            int id = -1;
            for(var user : listUser){
                if(user.getId() != userId){
                    double newMin = calculateDistance(foundUser.getAddress().getLat(),foundUser.getAddress().getLng(),
                                                        user.getAddress().getLat(),user.getAddress().getLng());
                    if(min > newMin) {
                        min = newMin;
                        id = user.getId();
                    }
                }
            }
            Users newUser = getUserByID(id);
            if(newUser != null)
                System.out.println("Dla uzytkownika o id: " + foundUser.getId() + " GEO( "+ foundUser.getAddress().getLat() +
                    ", " + foundUser.getAddress().getLng() + " ) najblizszym uzytkownikiem jest ten o id: " + id + " GEO( " +
                    newUser.getAddress().getLat() + ", " + newUser.getAddress().getLng() + " )");
            else
                System.out.println("Nie znaleziono uzytkownika");
        }
    }

    /**
     * Read String from file
     * @param url URL path to file
     * @return String
     * @throws IOException
     */
    public static String readStringFromUrl(String url) throws IOException {
        InputStream in = new URL(url).openStream();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String jsonString = "";
            jsonString = readFile(reader);
            return jsonString;
        }finally {
            in.close();
        }
    }

    /**
     * Helper to read all characters from String
     * @param reader BufferedReader object
     * @return file String
     * @throws IOException
     */
    private static String readFile(Reader reader) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        int helper;
        while((helper = reader.read())!=-1) {
            stringBuilder.append((char) helper);
        }
        return stringBuilder.toString();
    }
}
