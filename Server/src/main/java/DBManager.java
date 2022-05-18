import java.sql.*;

public class DBManager {
    private Connection connection=null;

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден!");
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg/studs");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
