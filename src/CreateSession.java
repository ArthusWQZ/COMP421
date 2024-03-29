import java.sql.Connection;
import java.sql.Statement;
import java.sql.* ;
import java.util.Scanner;

public class CreateSession {
    public static void insertSessions(Connection pCon, int sid, Date date, Time time, String lang, String sub, int tid, int roomNum, int mid) throws SQLException {
        String queryString = "INSERT INTO sessions VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = pCon.prepareStatement(queryString);
        preparedStatement.setInt(1, sid);
        preparedStatement.setDate(2, date);
        preparedStatement.setTime(3, time);
        preparedStatement.setString(4, lang);
        preparedStatement.setString(5, sub);
        preparedStatement.setInt(6, tid);
        preparedStatement.setInt(7, roomNum);
        preparedStatement.setInt(8, mid);
        preparedStatement.executeUpdate();
    }

    public static void execute(Connection pCon, Statement pStatement){
        Scanner input = new Scanner(System.in);
        Date date;
        Time time;
        String lang;
        String sub;
        int tid;
        int roomNum;
        int mid;

        // get input
        try{
            System.out.println("Please enter the start date (YYYY-MM-DD)");
            date = Date.valueOf(input.nextLine());
            System.out.println("Please enter the start time (HH:MM:SS)");
            time = Time.valueOf(input.nextLine());
            System.out.println("Please enter the language of the movie");
            lang = input.nextLine();
            System.out.println("Does the movie has subtitle? (yes/no)");
            sub = input.nextLine();
            System.out.println("Please enter tid");
            tid = Integer.parseInt(input.nextLine());
            System.out.println("Please enter the room number");
            roomNum = Integer.parseInt(input.nextLine());
            System.out.println("Please enter mid");
            mid = Integer.parseInt(input.nextLine());
        } catch (IllegalArgumentException e){
            System.out.println("ERROR: Invalid input");
            return;
        }

        // confirm input
        System.out.println("Create a new session for: ");
        System.out.println("date: " + date + ", time: " + time + ", lang: " + lang + ", sub: " + sub + ", tid: " + tid + ", room number: " + roomNum + ", mid: " + mid);
        System.out.println("Confirm? (yes/no) ");
        String confirm = input.nextLine();

        // execute
        if(confirm.equals("yes")){
            int sid = 1 + GetColumnMaxValue.execute(pStatement, "sid", "sessions");
            try {
                insertSessions(pCon, sid, date, time, lang, sub, tid, roomNum, mid);
            } catch (SQLException e) {
                System.out.println("ERROR: An error occurred when creating the session.");
                int sqlCode = e.getErrorCode();
                String sqlState = e.getSQLState();
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                return;
            }
            System.out.println("Success! - sid of the created session: " + sid);
        }
    }
}
