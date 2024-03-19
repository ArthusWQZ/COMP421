import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUnsoldTickets {
    public static void execute(Statement pStatement) throws SQLException{
        Scanner input = new Scanner(System.in);
        int sessionID = 0;

        while (sessionID==0) {
            printSessionIDs(pStatement);
            String option = input.nextLine();
            if (option.equalsIgnoreCase("q")) return;
            try {
                sessionID = Integer.parseInt(option);
                if(!checkSessionExist(pStatement, sessionID)){
                    System.out.println("ERROR: Session ID " + sessionID + " does not exist");
                    sessionID = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid format for the session id.");
            }
        }

        try {
            String updateSQL = "DELETE FROM Ticket WHERE sId = " + sessionID + " AND cId IS NULL";
            System.out.println(updateSQL);
            pStatement.executeUpdate(updateSQL);

            System.out.println();

            printRemainingTickets(pStatement, sessionID);
            System.out.println("Unsold tickets removed\n\n");

        } catch (SQLException e) {
            System.out.println("ERROR: An error occurred when deleting the tickets from session " + sessionID);
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }


    }

    private static boolean checkSessionExist(Statement pStatement, int sessionID) throws SQLException{
        int rowCount = 0;
        boolean sessionExists = false;
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
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
            return sessionExists;
        }
    }

    private static void printRemainingTickets(Statement pStatement, int sessionID) throws SQLException {
        try {
            String querySQL = "SELECT barcode, sID, cID FROM Ticket WHERE sID = " + sessionID;
            java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
            System.out.println("\n=========Tickets sold for session " + sessionID + "=========\n");
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
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
    }

    private static void printSessionIDs(Statement pStatement) throws SQLException {
        try {
            String querySQL = "SELECT DISTINCT sId FROM ticket ORDER BY sId";
            java.sql.ResultSet rs = pStatement.executeQuery(querySQL);
            System.out.println("\n=========Session IDs=========");
            String id = "| ";
            while (rs.next()) {
                int sid = rs.getInt(1);
                id = id.concat(sid + " | ");
            }
            System.out.println(id);
            if (id.equals("| ")){
                System.out.println("\nNo sessions exist");
            }
            System.out.println("\nPlease enter the ID of the session you want to remove the unsold tickets from (or 'Q' to quit):");
        }
        catch (SQLException e){
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
    }
}
