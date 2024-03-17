public class QueryString {
    public static String insertSessions = "INSERT INTO sessions VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static String getLatestPk = "SELECT MAX(?) FROM ?";
}
