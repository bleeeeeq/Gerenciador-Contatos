package meuprojeto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCTest {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/u-listacontatos?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "1234";

        try {
            // Carregue o driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabeleça a conexão
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

            // Execute uma consulta
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            if (resultSet.next()) {
                System.out.println("Conexão bem-sucedida! Resultado: " + resultSet.getInt(1));
            }

            // Feche a conexão
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
