import java.sql.*;
import java.util.Scanner;

public class PriceIncrease {

    private static void increasePrice(Statement pStatement, int priceIncrease) throws SQLException {


        String update = "UPDATE product set price=price + " + priceIncrease + "from consumable where product.barcode=consumable.barcode;";
        int updateResult = pStatement.executeUpdate(update);
        if (updateResult > 0) {
            System.out.println("The price has been updated successfully!");
        }
        else {
            System.out.println("There has been an error while trying to update the prices");
        }
    }

    public static void execute(Statement pStatemnet) {

        System.out.println("By how much should the price for consumables be increased? \n>");
        String option;
        Scanner input = new Scanner(System.in);
        option = input.nextLine();

        if (option.equalsIgnoreCase("q")) return;

        int pi;
        try {
            pi = Integer.parseInt(option);
        }
        catch (Exception e) {
            System.out.println("Invalid price increase");
            return;
        }

        try {
            increasePrice(pStatement, pi);
        }
        catch (SQLException e) {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            System.out.println("There was an error updating the price");
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
    }
}
