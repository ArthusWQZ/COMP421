import java.sql.*;
import java.util.Scanner;

public class PriceIncrease {

    private static void increasePrice(Statement pStatement, float priceIncrease) throws SQLException {


        String update = "UPDATE product SET price=price + " + priceIncrease + " FROM consumable WHERE product.barcode=consumable.barcode;";
        int updateResult = pStatement.executeUpdate(update);
        if (updateResult > 0) {
            System.out.println("The price has been updated successfully!");
        }
        else {
            System.out.println("There has been an error while trying to update the prices");
        }
    }

    public static void execute(Statement pStatement) {

        System.out.println("By how much should the price for consumables be increased?");
        String option;
        Scanner input = new Scanner(System.in);
        option = input.nextLine();

        if (option.equalsIgnoreCase("q")) return;

        float pi;
        try {
            pi = Float.parseFloat(option);
        }
        catch (Exception e) {
            System.out.println("Invalid price increase");
            return;
        }

        try {
            increasePrice(pStatement, pi);
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
