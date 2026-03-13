package exercicio2;

import java.util.*;

public class Principal {
	
	public static void main(String[] args) {
		JogoDAO dao = new JogoDAO();
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        
        dao.conectar();

        do {
            System.out.println("==== MENU CRUD ====");
            System.out.println("1) Listar");
            System.out.println("2) Inserir");
            System.out.println("3) Excluir");
            System.out.println("4) Atualizar");
            System.out.println("5) Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1: // LISTAR
                    System.out.println("==== Lista de Jogos ====");
                    Jogo[] lista = dao.getJogos();
                    if (lista != null) {
                        for (int i = 0; i < lista.length; i++) {
                            System.out.println(lista[i].toString());
                        }
                    }
                    break;

                case 2: // INSERIR
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Plataforma: ");
                    String plat = sc.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    
                    Jogo novo = new Jogo(0, nome, plat, preco);
                    if (dao.inserirJogo(novo)) {
                        System.out.println("Inserido!");
                    }
                    break;

                case 3: // EXCLUIR
                    System.out.print("Digite o ID do jogo para excluir: ");
                    int idExcluir = sc.nextInt();
                    if (dao.excluirJogo(idExcluir)) {
                        System.out.println("Jogo excluído!");
                    }
                    break;

                case 4: // ATUALIZAR 
                    System.out.print("Digite o ID do jogo para atualizar: ");
                    int idAtu = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo Nome: ");
                    String novoNome = sc.nextLine();
                    System.out.print("Nova Plataforma: ");
                    String novaPlat = sc.nextLine();
                    System.out.print("Novo Preço: ");
                    double novoPreco = sc.nextDouble();
                    
                    Jogo jogoAtu = new Jogo(idAtu, novoNome, novaPlat, novoPreco);
                    if (dao.atualizarJogo(jogoAtu)) {
                        System.out.println("Atualizado!");
                    }
                    break;
            }
        } while (opcao != 5);
        
        System.out.println("Finalizando...");
        dao.fechar();
        sc.close();
    }
}