import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUnsoldTickets {
    public static void execute(Statement pStatement, int sessionID) throws SQLException{
        int sqlCode=0;
        String sqlState="00000";
        try{
            String updateSQL = "DELETE FROM Ticket WHERE sId = " +  sessionID + " AND cId IS NULL";
            System.out.println(updateSQL);
            pStatement.executeUpdate(updateSQL);
            System.out.println();
            System.out.println("DONE");
        }
        catch (SQLException e){
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
        finally {
            pStatement.close();
        }
    }


}
