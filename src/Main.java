import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.* ;

public class Main {
    public static QueryString q = new QueryString();
    public static DbConnection db = new DbConnection();
    public static Connection con;

    public static int getLatestPrimaryKey(String pkName, String tableName) throws SQLException {
        String s = q.getLatestPk;
        PreparedStatement statement = con.prepareStatement(s);
        statement.setString(1, pkName);
        statement.setString(2, tableName);
        java.sql.ResultSet rs = statement.executeQuery();
        System.out.println("getLatestPrimaryKey");
        System.out.println(statement);
        statement.close();
        return rs.getInt(pkName);
    }
//    public static void insertSessions(String date, String time, String lang, String sub, int tId, int roomNum, int mId){
//
//    }
    public static void main(String[] args) throws SQLException{
        con = db.getDbConnection();
        int i = getLatestPrimaryKey("sId", "sessions");
        System.out.println("latest: " + i);
        con.close();
//        Statement statement = con.createStatement ( ) ;
//        String s = q.insertSessions;
//        PreparedStatement statement = con.prepareStatement(s);
//        statement.setInt(1, 2);
//        statement.setInt(2, 5);
//        System.out.println(statement);



    }
}