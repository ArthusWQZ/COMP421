import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class CreateMovie {
    public static void insertMovie(Connection pCon, int mid, String name, int duration, String genre, float rating, Date date) throws SQLException {
        String queryString = "INSERT INTO movie VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = pCon.prepareStatement(queryString);
        preparedStatement.setInt(1, mid);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, duration);
        preparedStatement.setString(4, genre);
        preparedStatement.setFloat(5, rating);
        preparedStatement.setDate(6, date);
        preparedStatement.executeUpdate();
    }
    public static void execute(Connection pCon, Statement pStatement){
        Scanner input = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        String name;
        int duration;
        String genre;
        float rating;
        Date date;

        // get input
        try{
            System.out.println("Please enter the movie name");
            name = input.nextLine();
            System.out.println("Please enter the duration in minute");
            duration = Integer.parseInt(input.nextLine());
            System.out.println("Please enter the genre (separate each genre by comma)");
            genre = input.nextLine();
            System.out.println("Please enter the movie rating");
            rating = Float.parseFloat(input.nextLine());
            System.out.println("Please enter the release date (YYYY-MM-DD)");
            date = Date.valueOf(input.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Invalid input");
            return;
        }

        // confirm input
        rating = Float.parseFloat(df.format(rating));
        System.out.println("Create a new movie for: ");
        System.out.println("name: " + name + ", duration: " + duration + ", genre: " + genre + ", rating: " + rating + ", release date: " + date);
        System.out.println("Confirm? (yes/no) ");
        String confirm = input.nextLine();

        // execute
        if(confirm.equals("yes")){
            int mid = 1 + GetColumnMaxValue.execute(pStatement, "mid", "movie");
            try {
                insertMovie(pCon, mid, name, duration, genre, rating, date);
            }  catch (SQLException e) {
                System.out.println("ERROR: An error occurred when creating the movie.");
                return;
            }
            System.out.println("Success! - mid of the created movie: " + mid);
        }
    }
}
