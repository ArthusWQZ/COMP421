import java.sql.Connection;
import java.sql.Statement;
import java.sql.* ;
import java.util.Scanner;

public class CreateSession {
//    public static void execute(Connection pCon, int sid, String date, String time, String lang, String sub, int tId, int roomNum, int mId){
    public static void insertSessions(Connection pCon, int sid, Date date, String time, String lang, String sub, int tid, int roomNum, int mid) throws SQLException {
        String queryString = "INSERT INTO sessions VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = pCon.prepareStatement(queryString);
        preparedStatement.setInt(1, sid);
        preparedStatement.setDate(2, date);
        preparedStatement.setString(3, time);
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
        String time;
        String lang;
        String sub;
        int tid;
        int roomNum;
        int mid;

        // get input
        try{
            System.out.println("Please enter the start date (YYYY-MM-DD)");
            date = Date.valueOf(input.nextLine());
            System.out.println("Please enter the start time (HH:MM)");
            time = input.nextLine();
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
        System.out.println("Create new session for: ");
        System.out.println("date: " + date + ", time: " + time + ", lang: " + lang + ", sub: " + sub + ", tid: " + tid + ", room number: " + roomNum + ", mid: " + mid);
        System.out.println("Confirm? (yes/no) ");
        String confirm = input.nextLine();

        // execute
        if(confirm.equals("yes")){
            int sid = GetColumnMaxValue.execute(pStatement, "sId", "sessions");
            try {
                insertSessions(pCon, sid+1, date, time, lang, sub, tid, roomNum, mid);
            } catch (SQLException e) {
                System.out.println("ERROR: An error occurred when creating the session.");
                return;
            }
            System.out.println("Success!");
        }
    }
}
