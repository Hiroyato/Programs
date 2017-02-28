package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@RestController
public class GreetingController {
    private static final String url = "jdbc:mysql://localhost:3306/cats";
    private static final String user = "root";
    private static final String password = "92405469a";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;



    //private static final String template = "Hello, %d!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/getAllCats")
    public Greeting getAllCats() {
        String query = "SELECT Name, Age, Weight, Photo FROM cats";

        String template = "%s, %d years old, weighs %d kilos; photo: %s";

        String name;
        int age;
        int weight;
        String photo;

        String answer = "";


        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();


            rs = stmt.executeQuery(query);

            while (rs.next()) {
                name = rs.getString(1);
                age = rs.getInt(2);
                weight = rs.getInt(3);
                photo = rs.getString(4);

                answer += String.format(template, name, age, weight, photo) + "\n";
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


        return new Greeting(counter.incrementAndGet(), answer);
    }


    @RequestMapping("/getByName")
    public Greeting getByName(@RequestParam(value="name", defaultValue="Barsik") String n) {
        String query = "SELECT Name, Age, Weight, Photo FROM cats";

        String template = "%s, %d years old, weighs %d kilos; photo: %s";

        String name;
        int age;
        int weight;
        String photo;

        String answer = "";


        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();


            rs = stmt.executeQuery(query);

            while (rs.next()) {
                name = rs.getString(1);
                age = rs.getInt(2);
                weight = rs.getInt(3);
                photo = rs.getString(4);

                if (name.equals(n)) {
                    answer += String.format(template, name, age, weight, photo) + "\n";
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


        return new Greeting(counter.incrementAndGet(), answer);
    }


    @RequestMapping("/deleteByName")
    public Greeting deleteByName(@RequestParam(value="name", defaultValue="Barsik") String n) {
        String query = "DELETE FROM cats WHERE Name='%s'";


        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();


            stmt.executeUpdate(String.format(query, n));

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


        return new Greeting(counter.incrementAndGet(), "Deleted");
    }

    @RequestMapping("/addNewCat")
    public Greeting addNewCat(@RequestParam(value="params", defaultValue="'Barsik',3,5,'None'") String n) {
        String query = "INSERT INTO cats (Name,Age,Weight,Photo) VALUES (%s)";


        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();


            stmt.executeUpdate(String.format(query, n));

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


        return new Greeting(counter.incrementAndGet(), "New cat added");
    }


}