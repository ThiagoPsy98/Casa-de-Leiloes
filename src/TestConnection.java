import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false&allowPublicKeyRetrieval=true";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "Mineclash2395");

        try {
            Connection conn = DriverManager.getConnection(url, properties);
            System.out.println("Conexão estabelecida com sucesso!");

            // Executa um comando SQL para listar tabelas
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES");

            System.out.println("Tabelas no banco de dados 'uc11':");
            boolean tableExists = false;
            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println(tableName);
                if (tableName.equalsIgnoreCase("produtos")) {
                    tableExists = true;
                }
            }

            if (tableExists) {
                // Verifica se a tabela 'produtos' existe e lista seus dados
                rs = stmt.executeQuery("SELECT * FROM produtos");
                System.out.println("\nDados na tabela 'produtos':");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Valor: " + rs.getInt("valor") + ", Status: " + rs.getString("status"));
                }
            } else {
                System.out.println("Tabela 'produtos' não encontrada.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

