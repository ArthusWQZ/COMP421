import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class RemoveSession {

    private static void printTheaterList(Statement pStatement) throws SQLException {
        String querySQL = "SELECT tid, name FROM theater ORDER BY tid";
        java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
        System.out.println("=========Theater List=========");
        while (rs.next()) {
            int tid = rs.getInt(1);
            String name = rs.getString(2);
            System.out.println(tid + ". " + name);
        }
    }

    private static void printProjectionRoomList(Statement pStatement, int pTid) throws SQLException {
        String querySQL = "SELECT room_number FROM projection_room WHERE tid = " + pTid + " ORDER BY room_number";
        java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
        System.out.println("=========Projection Room List=========");
        while (rs.next()) {
            int room_number = rs.getInt(1);
            System.out.println("Projection Room #" + room_number);
        }
    }

    private static HashMap loadMovieList(Statement pStatement) throws SQLException {
        String querySQL = "SELECT mid, movie_name FROM movie";
        ResultSet rs = pStatement.executeQuery(querySQL);
        HashMap<Integer, String> movieList = new HashMap<Integer, String>();
        while (rs.next()) {
            movieList.put(rs.getInt(1), rs.getString(2));
        }
        return movieList;
    }
    private static void printSessionsForRoom(Statement pStatement, int pTid, int pRoomNumber) throws SQLException {
        HashMap<Integer, String> movieList = loadMovieList(pStatement);
        String querySQL = "SELECT sid, start_date, start_time, mid FROM sessions WHERE tid = " + pTid + " AND room_number = " + pRoomNumber +" ORDER BY start_date,start_time";
        java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
        System.out.println("=========Session List=========");
        while (rs.next()) {
            int sid = rs.getInt(1);
            Date start_date = rs.getDate(2);
            Time start_time = rs.getTime(3);
            int mid = rs.getInt("mid");
            System.out.println(sid + ". " + movieList.get(mid) + " - On " + start_date + " at " + start_time);
        }
    }

    private static void deleteSession(Statement pStatement, int pSid) throws SQLException
    {
        String deleteSQL = "DELETE FROM sessions WHERE sid = " + pSid;
        pStatement.executeUpdate(deleteSQL);
    }

    private static void deleteTicketsForSession(Statement pStatement, int pSid) throws SQLException
    {
        String deleteSQL = "DELETE FROM ticket WHERE sid = " + pSid;
        pStatement.executeUpdate(deleteSQL);
    }

    public static void execute(Statement pStatement)
    {

        try {
            printTheaterList(pStatement);
        }
        catch (SQLException e)
        {
            System.out.println("ERROR: An error occurred when fetching the theater list.");
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return;
        }

        System.out.print("\nPlease choose the ID of the target theater (or 'Q' to quit):\n> ");
        String option;
        Scanner input = new Scanner(System.in);
        option = input.nextLine();
        if (option.toLowerCase().equals("q")) return;

        int tid;
        try {
            tid = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid format for the theater id.");
            return;
        }

        try {
            printProjectionRoomList(pStatement, tid);
        }
        catch (SQLException e)
        {
            System.out.println("ERROR: An error occurred when fetching the projection room list.");
            System.out.println("Double check the given theater id.");
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return;
        }

        System.out.print("\nPlease enter the target room number (or 'Q' to quit):\n> ");
        option = input.nextLine();
        if (option.toLowerCase().equals("q")) return;

        int room_number;
        try {
            room_number = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid format for the room number.");
            return;
        }

        try {
            printSessionsForRoom(pStatement, tid, room_number);
        }
        catch (SQLException e)
        {
            System.out.println("ERROR: An error occurred when fetching the session list.");
            System.out.println("Double check the room number.");
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return;
        }

        System.out.print("\nPlease enter the ID of the session you want to remove (or 'Q' to quit):\n> ");
        option = input.nextLine();
        if (option.toLowerCase().equals("q")) return;

        int sid;
        try {
            sid = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid format for the session ID.");
            return;
        }

        try {
            deleteTicketsForSession(pStatement, sid);
        }
        catch (SQLException e)
        {
            System.out.println("ERROR: An error occurred when removing the tickets associated to this session.");
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return;
        }

        try {
            deleteSession(pStatement, sid);
        }
        catch (SQLException e)
        {
            System.out.println("ERROR: An error occurred when removing the session.");
            System.out.println("Double check the session ID.");
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return;
        }

        System.out.println("Success!");

    }


}
