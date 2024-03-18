import java.sql.Connection;
import java.sql.Statement;
import java.sql.* ;

public class CreateSession {
    public static void execute(Connection pCon, int sid, String date, String time, String lang, String sub, int tId, int roomNum, int mId){
        String queryString = "INSERT INTO sessions VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = pCon.prepareStatement(queryString);
            preparedStatement.setInt(1, sid);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, lang);
            preparedStatement.setString(5, sub);
            preparedStatement.setInt(6, tId);
            preparedStatement.setInt(7, roomNum);
            preparedStatement.setInt(8, mId);
        } catch (SQLException e) {
            System.out.println("ERROR: An error occurred when creating the session.");
            return;
        }
        System.out.println("Success!");
    }
}
