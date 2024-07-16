import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    public int cadastrarProduto (ProdutosDTO produto){
        
        conn = new conectaDAO().connectDB();
        int status;
        try {
            
            prep = conn.prepareStatement("INSERT INTO produtos(nome, valor, status)"
           + "VALUES(?,?,?)");
           
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
           
            status = prep.executeUpdate();
            
            return status;
        } catch (SQLException ex) {
           
            JOptionPane.showMessageDialog(null,"Erro ao cadastrar dados do filme: "
                    + ex.getMessage());
            return ex.getErrorCode();
        }
        
        
    }
    
    public List<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";
        try {

            prep = this.conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            
            List<ProdutosDTO> listaProdutos = new ArrayList<>();

            while(rs.next()){
                ProdutosDTO produtos = new ProdutosDTO();
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("Status"));
                listaProdutos.add(produtos);
            }
            

            return listaProdutos;

            
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
    }
    
    
    
        
}
