import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public int cadastrarProduto (ProdutosDTO produto){
       
      conn = new conectaDAO().connectDB();
        int status;
        try {
            //preparando a string sql com o código de inserção para o banco de dados
            prep = conn.prepareStatement("INSERT INTO produtos(nome, valor, status)"
                    + "VALUES(?,?,?)");
            //setando os parâmetros
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            //executando a query
            status = prep.executeUpdate();
            JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso");
            //retornando o valor da query
            return status;
        } catch (SQLException ex) {
            //mensagem de erro caso o programa não consiga se conectar com o banco de dados
            JOptionPane.showMessageDialog(null,"Erro ao cadastrar dados do filme: "
                    + ex.getMessage());
            return ex.getErrorCode();
        }

       
        
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM produtos");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));

                produtos.add(p);

            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos. ERRO: " + sqle.getMessage());

        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
            }
        }

        return produtos;
    }
    
    
    
        
}
