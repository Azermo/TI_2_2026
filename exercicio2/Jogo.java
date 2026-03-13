package exercicio2;

public class Jogo {

	private int id;
	private String nome;
	private String plataforma;
	private double preco;
	
	public Jogo() {
		
	}
	
	public Jogo(int id,String nome,String plataforma,double preco) {
		this.id = id;
		this.nome = nome;
		this.plataforma = plataforma;
		this.preco = preco;
	}
	
	
	
	public int getId() { return id; }
	public void setId() { this.id = id; }
	
	public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
	
    @Override
    public String toString() {
        return "Jogo: \nId = " + id + ", \nNome = " + nome + ", \nPlataforma = " + plataforma + ", \nPreço = " + preco + ".";
    }
}

//Adaptação do codigo de exemplo das video aulas disponibilizadas no canvas de TI2
