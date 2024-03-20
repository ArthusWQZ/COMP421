import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class AssignTicket {

    public static void assignTicket(Statement pStatement, long barcode, int cID) throws SQLException {

        String query = "UPDATE ticket SET cid = " + cID + " WHERE barcode = " + barcode + ";";
        int rs = pStatement.executeUpdate(query);
        if (rs > 0) {
            System.out.println("The ticket "+ barcode + " has been assigned to the customer with ID: " + cID);
        } else {
            System.out.println("There has been an error while trying to assign the ticket");
        }
    }

    public static int checkBarcode(Statement pStatement, long barcode) throws SQLException {


        String query = "SELECT COUNT(*) FROM ticket WHERE barcode = " + barcode;
        ResultSet rs = pStatement.executeQuery(query);
        int result = rs.getInt(1);
        return result;
    }

    public static void execute(Statement pStatement) {
        System.out.println("Please enter the ticket's barcode: ");
        String option;
        Scanner input = new Scanner(System.in);
        option = input.nextLine();

        if (option.equalsIgnoreCase("q")) return;

        long code;
        try {
            code = Long.parseLong(option);
            if (checkBarcode(pStatement, code) == 0) {
                System.out.println("Ticket barcode invalid!");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("Invalid ticket barcode!");
            System.out.println(e);
            return;
        }

        System.out.println("Please enter the customer id: ");
        option = input.nextLine();
        if (option.equalsIgnoreCase("q")) return;

        int cust;
        try {
            cust = Integer.parseInt(option);
        }
        catch (Exception e) {
            System.out.println("Invalid customer ID!");
            return;
        }

        try {
            assignTicket(pStatement, code, cust);
        }
        catch (SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE

            System.out.println("There was an error updating the price");
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
    }
}
