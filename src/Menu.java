import java.util.Scanner;
import java.sql.*;

public class Menu {

  Connection aCon;
  Statement aStatement;

  public Menu(Connection pCon, Statement pStatement)
  {
    aCon = pCon;
    aStatement = pStatement;
  }

  public void enterMenuLoop() {
    String option;
    Scanner input = new Scanner(System.in);

    do {
      System.out.println("=========CineFlex Admin Menu=========");
      System.out.println("1. Create a new session");
      System.out.println("2. Delete unsold tickets from a session");
      System.out.println("3. Add a new movie");
      System.out.println("4. Remove session");
      System.out.println("5. Assign a ticket to a customer");
      System.out.println("6. Increase consumables price");
      System.out.println("7. Quit\n");
      System.out.print("> ");
      option = input.nextLine();
      switch (option) {
        case "1":
          break;
        case "2":
          break;
        case "3":
          break;
        case "4":
          break;
        case "5":
          break;
        case "6":
          break;
      }

    } while (!option.equals("7"));
  }
}
