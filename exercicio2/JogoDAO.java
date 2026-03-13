package exercicio2;

import java.sql.*;

public class JogoDAO {
	private Connection conexao;
	
	
	private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost:5432/Exericio2";
    private String usuario = "postgres";
    private String senha = "umdiatroca"; 
    boolean status = false;
    
	public JogoDAO() {
		conexao = null;
	}
	
    public boolean conectar() {
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
			status = (conexao == null);
			System.out.println("Conexão Sucedida!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada  -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada -- " + e.getMessage());
		}

		return status;
	}
    public boolean fechar() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
    
    
   
    public boolean inserirJogo(Jogo jogo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO jogo (nome, plataforma, preco) VALUES ('" 
                + jogo.getNome() + "', '" 
            	+ jogo.getPlataforma() + "', " 
                + jogo.getPreco() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean atualizarJogo(Jogo jogo) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE jogo SET nome = '" + jogo.getNome() + "', plataforma = '"  
                       + jogo.getPlataforma() + "', preco = " + jogo.getPreco()
                       + " WHERE id = " + jogo.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean excluirJogo(int id) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();            
            st.executeUpdate("DELETE FROM jogo WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public Jogo[] getJogos() {
        Jogo[] jogos = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM jogo");		
            
            if(rs.next()){
                 rs.last(); 
                 jogos = new Jogo[rs.getRow()];
                 rs.beforeFirst(); 

                 for(int i = 0; rs.next(); i++) {
                    jogos[i] = new Jogo(rs.getInt("id"), rs.getString("nome"), 
                                         rs.getString("plataforma"), rs.getDouble("preco"));
                 }
              }
              st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return jogos;
    }
   
}