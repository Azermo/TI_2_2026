package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;

import dao.JogoDAO; 
import model.Jogo;   

import spark.Request;
import spark.Response;

public class JogoService {

	private JogoDAO jogoDAO = new JogoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	public JogoService() {
		makeForm();
	}
	
	public void conectarDAO() {
        jogoDAO.conectar();
    }

	public void makeForm() {
		makeForm(FORM_INSERT, new Jogo(), FORM_ORDERBY_NOME);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Jogo(), orderBy);
	}

	public void makeForm(int tipo, Jogo jogo, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umJogo = "";
		if(tipo != FORM_INSERT) {
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/jogo/list/1\">Novo Jogo</a></b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";
			umJogo += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/jogo/";
			String name, nomeJogo, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Jogo";
				nomeJogo = "Título do Jogo";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + jogo.getId();
				name = "Atualizar Jogo (ID " + jogo.getId() + ")";
				nomeJogo = jogo.getNome();
				buttonLabel = "Atualizar";
			}
			umJogo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nomeJogo +"\"></td>";
			umJogo += "\t\t\t<td>Plataforma: <input class=\"input--register\" type=\"text\" name=\"plataforma\" value=\""+ (jogo.getPlataforma() != null ? jogo.getPlataforma() : "") +"\"></td>";
			umJogo += "\t\t\t<td>Preço: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ jogo.getPreco() +"\"></td>";
			umJogo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";
			umJogo += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Jogo (ID " + jogo.getId() + ")</b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td>&nbsp;Nome: "+ jogo.getNome() +"</td>";
			umJogo += "\t\t\t<td>Plataforma: "+ jogo.getPlataforma() +"</td>";
			umJogo += "\t\t\t<td>Preço: R$ "+ String.format("%.2f", jogo.getPreco()) +"</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replace("<UM-PRODUTO>", umJogo);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Jogos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><b>Plataforma</b></td>\n" +
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Jogo> jogos;
		if (orderBy == FORM_ORDERBY_ID) {                 jogos = jogoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {        jogos = jogoDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_PRECO) {       jogos = jogoDAO.getOrderByPreco();
		} else {                                          jogos = jogoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Jogo j : jogos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
			          "\t<td>" + j.getId() + "</td>\n" +
			          "\t<td>" + j.getNome() + "</td>\n" +
			          "\t<td>" + j.getPlataforma() + "</td>\n" +
			          "\t<td>R$ " + String.format("%.2f", j.getPreco()) + "</td>\n" +			          
			          "\t<td align=\"center\"><a href=\"/jogo/" + j.getId() + "\" class=\"btn-action btn-detail\">🔍</a></td>\n" +
			          "\t<td align=\"center\"><a href=\"/jogo/update/" + j.getId() + "\" class=\"btn-action btn-update\">✏️</a></td>\n" +
			          "\t<td align=\"center\"><a href=\"javascript:confirmarDeleteJogo('" + j.getId() + "', '" + j.getNome() + "', '" + j.getPreco() + "');\" class=\"btn-action btn-delete\">🗑️</a></td>\n" +
			          "</tr>\n";
		}
		list += "</table>";		
		form = form.replace("<LISTAR-PRODUTO>", list);				
	}
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String plataforma = request.queryParams("plataforma");
		double preco = Double.parseDouble(request.queryParams("preco"));
		
		String resp = "";
		Jogo jogo = new Jogo(0, nome, plataforma, preco);
		
		if(jogoDAO.inserirJogo(jogo) == true) {
            resp = "Jogo (" + nome + ") inserido!";
            response.status(201); 
		} else {
			resp = "Jogo (" + nome + ") não inserido!";
			response.status(404); 
		}
			
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogoDAO.getJogo(id);
		
		if (jogo != null) {
			response.status(200); 
			makeForm(FORM_DETAIL, jogo, FORM_ORDERBY_NOME);
        } else {
            response.status(404); 
            String resp = "Jogo " + id + " não encontrado.";
    		makeForm();
    		form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");      
        }

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogoDAO.getJogo(id);
		
		if (jogo != null) {
			response.status(200); 
			makeForm(FORM_UPDATE, jogo, FORM_ORDERBY_NOME);
        } else {
            response.status(404); 
            String resp = "Jogo " + id + " não encontrado.";
    		makeForm();
    		form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");      
        }

		return form;
	}
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Jogo jogo = jogoDAO.getJogo(id);
        String resp = "";       

        if (jogo != null) {
        	jogo.setNome(request.queryParams("nome"));
        	jogo.setPlataforma(request.queryParams("plataforma"));
        	jogo.setPreco(Double.parseDouble(request.queryParams("preco")));
        	jogoDAO.atualizarJogo(jogo);
        	response.status(200); 
            resp = "Jogo (ID " + jogo.getId() + ") atualizado!";
        } else {
            response.status(404); 
            resp = "Jogo (ID " + id + ") não encontrado!";
        }
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogoDAO.getJogo(id);
        String resp = "";       

        if (jogo != null) {
            jogoDAO.excluirJogo(id);
            response.status(200); 
            resp = "Jogo (" + id + ") excluído!";
        } else {
            response.status(404); 
            resp = "Jogo (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}

//Adaptação dos codigos de exemplo das video aulas disponibilizadas no canvas de TI2 e do exercicio 2.