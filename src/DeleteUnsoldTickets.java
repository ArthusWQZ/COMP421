import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUnsoldTickets {
    public static void execute(Statement pStatement) throws SQLException{

        int sessionID = 0;
        while (sessionID == 0) {
            sessionID = getSessionID(pStatement, sessionID);
        }

        if (checkSessionExist(pStatement, sessionID)) {
            try {
                String updateSQL = "DELETE FROM Ticket WHERE sId = " + sessionID + " AND cId IS NULL";
                System.out.println(updateSQL);
                pStatement.executeUpdate(updateSQL);

                System.out.println();
                System.out.println("Unsold tickets removed");

                printRemainingTickets(pStatement, sessionID);

            } catch (SQLException e) {
                System.out.println("ERROR: An error occurred when deleting the tickets from session " + sessionID);
            }
        } else {
            System.out.println("ERROR: Session ID " + sessionID + " does not exist");
        }
    }

    private static boolean checkSessionExist(Statement pStatement, int sessionID) throws SQLException{
        int rowCount = 0;
        boolean sessionExists = true;
        try {
            String selectSQL = "SELECT COUNT(*) FROM Ticket WHERE sId = " + sessionID;
            var resultSet = pStatement.executeQuery(selectSQL);
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
                sessionExists = rowCount > 0;
            }
            return sessionExists;
        }
        catch (SQLException e){
            System.out.println("ERROR: An error occurred when fetching the session ids.");
            return sessionExists;
        }
    }

    private static void printRemainingTickets(Statement pStatement, int sessionID) throws SQLException {
        try {
            String querySQL = "SELECT barcode, sID, cID FROM Ticket WHERE sID = " + sessionID;
            java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
            System.out.println("\n=========Session " + sessionID + " tickets sold=========\n");
            System.out.println("Barcode       |sID| cID \n________________________");
            while (rs.next()) {
                String barcode = rs.getString(1);
                int sid = rs.getInt(2);
                int cid = rs.getInt(3);

                System.out.println(barcode + " | " + sid + " | " + cid);
            }
        }
        catch (SQLException e){
            System.out.println("ERROR: An error occurred when fetching barcode, sID, cID from the Ticket table.");
            return;
        }
    }

    private static int getSessionID(Statement pStatement, int sessionID) throws SQLException {
        Scanner input = new Scanner(System.in);
        String querySQL = "SELECT DISTINCT sId FROM ticket ORDER BY sId";
        java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
        System.out.println("=========Session IDs=========");
        StringBuilder id = new StringBuilder("| ");
        while (rs.next()) {
            int sid = rs.getInt(1);
            id.append(sid + " | ");
        }
        System.out.println(id);
        System.out.println("Please enter the ID of the session you want to remove the unsold tickets from:");
        try {
            sessionID = input.nextInt();
            return sessionID;
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Invalid format for the session id.");
            return 0;
        }
   }
}
