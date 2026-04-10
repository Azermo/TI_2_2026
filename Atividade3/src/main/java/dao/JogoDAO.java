package dao;

import java.sql.*;
import java.util.*;
import model.Jogo;

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

    public Jogo getJogo(int id) {
        Jogo jogo = null;
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM jogo WHERE id=" + id);
            if(rs.next()){            
                 jogo = new Jogo(rs.getInt("id"), rs.getString("nome"), rs.getString("plataforma"), rs.getDouble("preco"));
            }
            st.close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return jogo;
    }
    
    public List<Jogo> get() { return get(""); }
    public List<Jogo> getOrderByID() { return get("id"); }
    public List<Jogo> getOrderByNome() { return get("nome"); }
    public List<Jogo> getOrderByPreco() { return get("preco"); }
    
    private List<Jogo> get(String orderBy) {
        List<Jogo> jogos = new ArrayList<Jogo>();
        try {
            Statement st = conexao.createStatement();
            String sql = "SELECT * FROM jogo" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);            
            while(rs.next()) {              
                Jogo j = new Jogo(rs.getInt("id"), rs.getString("nome"), rs.getString("plataforma"), rs.getDouble("preco"));
                jogos.add(j);
            }
            st.close();
        } catch (Exception e) { System.err.println(e.getMessage()); }
        return jogos;
    }
}