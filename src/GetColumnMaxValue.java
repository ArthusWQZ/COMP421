import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetColumnMaxValue {
    public static int execute(Statement pStatement, String colName, String tableName){
        String queryString = "SELECT MAX(colName) FROM tableName";
        queryString = queryString.replace("colName", colName);
        queryString = queryString.replace("tableName", tableName);
        try {
            ResultSet rs = pStatement.executeQuery(queryString);
            if(rs.next()){
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }
}
