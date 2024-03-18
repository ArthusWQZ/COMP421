import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.* ;

public class Main {
    public static QueryString q = new QueryString();
    public static DbConnection db = new DbConnection();
    public static Connection con;
    public static Statement statement;

    public static int getColumnMaxValue(String colName, String tableName) throws SQLException {
        String queryString = q.selectMaxValue;
        queryString = queryString.replace("colName", colName);
        queryString = queryString.replace("tableName", tableName);
        System.out.println("getLatestPrimaryKey");
        ResultSet rs = statement.executeQuery(queryString);
        if(rs.next()){
            return rs.getInt(1);
        }
        else {
            return -1;
        }
    }
    public static void insertSessions(String date, String time, String lang, String sub, int tId, int roomNum, int mId){
        try {
            String queryString = q.insertSessions;
            int sid = getColumnMaxValue("sId", "sessions");
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, sid + 1);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, lang);
            preparedStatement.setString(5, sub);
            preparedStatement.setInt(6, tId);
            preparedStatement.setInt(7, roomNum);
            preparedStatement.setInt(8, mId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) throws SQLException{
        con = db.getDbConnection();
        statement = con.createStatement();

        int i = getColumnMaxValue("sId", "sessions");
        System.out.println("latest: " + i);
        insertSessions("2024-02-15", "15:00", "FR", "yes", 1, 3, 15);


        statement.close();
        con.close();

//        Statement statement = con.createStatement ( ) ;
//        String s = q.insertSessions;
//        PreparedStatement statement = con.prepareStatement(s);
//        statement.setInt(1, 2);
//        statement.setInt(2, 5);
//        System.out.println(statement);



    }
}
