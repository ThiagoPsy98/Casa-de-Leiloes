import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProdutosDAO {
    
    private Connection conn;
    private PreparedStatement prep;
    private ResultSet resultSet;
    
    public int cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB(); // Corrigido o nome da classe 'conectaDAO' para 'ConectaDAO'
        int status;
        
        try {
            prep = conn.prepareStatement("INSERT INTO produtos(nome, valor, status) VALUES (?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            status = prep.executeUpdate();
            
            return status;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
            return ex.getErrorCode();
        } finally {
            closeResources();
        }
    }
    
    public List<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB(); // Corrigido o nome da classe 'conectaDAO' para 'ConectaDAO'
        String sql = "SELECT * FROM produtos";
        
        try {
            prep = conn.prepareStatement(sql);
            resultSet = prep.executeQuery();
            
            List<ProdutosDTO> listaProdutos = new ArrayList<>();
            
            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status")); // Corrigido para 'status'
                listaProdutos.add(produto);
            }
            
            return listaProdutos;
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
            return null;
        } finally {
            closeResources();
        }
    }
    
    private void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar recursos: " + ex.getMessage());
        }
    }
}
