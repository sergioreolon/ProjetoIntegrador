import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Concentra todos os acessos ao banco de dados.
 */
public class Conexao {
	public static final boolean TOTAL=true;
	public static final boolean PARCIAL=false;

	/*
	 * Informações e links para conexão com banco.
	 */
	private static String driver = "org.postgresql.Driver";
	private static String url = "jdbc:postgresql://localhost:5432/DB_GARAGEM_FACIL";
	private static String usuarioBanco = "postgres";
	private static String senhaBanco = "postgres";
	
	/**
	 * Recebe os dados de login e checa se a combinação usuário e senha é válida.
	 * @return True se o login for bem-sucedido e false se não for.
	 */
	public static boolean logar(String login, String password) {
		try {
			ResultSet rs = consultaQuery("select usuario from Funcionarios where usuario='"+login+"' and senha='"+password+"'");
			String msg = "";
			while (rs.next()) msg += rs.getString("USUARIO");
			return !msg.isEmpty();
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Busca as informações do carro referente ao código informado.
	 * @return Todas as informações do banco sobre o registro em questão.
	 */
	public static Object[] buscaCarro(int codigo) {
		String query  = "Select * from Carros ca ";
			   query += "where ca.id='"+codigo+"'";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String cliente = rs.getString("cliente");
				String modelo = rs.getString("modelo");
				String cor = rs.getString("cor");
				String ano = rs.getString("ano");
				String placa = rs.getString("placa");
				String preco = rs.getString("preco");
				String motor = rs.getString("motor");
				String lucro = rs.getString("lucro");
				int kilometragem = rs.getInt("kilometragem");
				String precoAquisicao = rs.getString("precoAquisicao");
				Object[] linha = {id,cliente,modelo,cor,ano,placa,preco,motor,lucro,kilometragem,precoAquisicao};
				return linha;
			}
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return new Object[0];
	}
	
	/**
	 * Busca as informações do cliente referente ao código informado.
	 * @return Todas as informações do banco sobre o registro em questão.
	 */
	public static Object[] buscaCliente(int codigo) {
		String query  = "Select * from Clientes ";
			   query += "where id='"+codigo+"'";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");
				String telefone = rs.getString("telefone");
				String email = rs.getString("email");
				String endereco = rs.getString("endereco");
				String numero = rs.getString("numero");
				String bairro = rs.getString("bairro");
				String cep = rs.getString("cep");
				String cidade = rs.getString("cidade");
				String estado = rs.getString("estado");
				Object[] linha = {id,nome,cpf,telefone,email,endereco,numero,bairro,cep,cidade,estado};
				return linha;
			}
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return new Object[0];
	}

	/**
	 * Busca as informações do funcionário referente ao login informado.
	 * @return Todas as informações do banco sobre o registro em questão.
	 */
	public static Object[] buscaFuncionario(String usuario) {
		String query  = "Select * from Funcionarios ";
			   query += "where usuario='"+usuario+"'";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				String nome = rs.getString("nome");
				String senha = rs.getString("senha");
				int funcao = (rs.getObject("funcao") != null) ? rs.getInt("funcao") : -1;
				String cpf = rs.getString("cpf");
				String telefone = rs.getString("telefone");
				String email = rs.getString("email");
				String dataAdmissao = rs.getString("dataAdmissao");
				String dataCadastro = rs.getString("dataCadastro");
				Object[] linha = {usuario,nome,senha,funcao,cpf,telefone,email,dataAdmissao,dataCadastro};
				return linha;
			}
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return new Object[0];
	}

	/**
	 * Busca as informações parciais do veículo no formato para a demonstração na tabela.
	 * @return Matriz de Objects em que cada linha é um registro de veículo com
	 * id, nome do cliente, modelo, cor, ano e placa.
	 */	
	public static Object[][] buscaCarros() {
		ArrayList<Object[]> resposta = new ArrayList<Object[]>(); 
		
		String query  = "Select ca.id,cl.Nome as Cliente,Modelo,Cor,Ano,Placa ";
			   query += "from Carros ca ";
			   query += "left join Clientes cl on cl.id=ca.cliente ";
			   query += "order by ca.id ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String cliente = rs.getString("cliente");
				String modelo = rs.getString("modelo");
				String cor = rs.getString("cor");
				String ano = rs.getString("ano");
				String placa = rs.getString("placa");
				Object[] linha = {id,cliente,modelo,cor,ano,placa};
				resposta.add(linha);
			}
			
			return convertToObjectMatrix(resposta);
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca as informações parciais do cliente no formato para a demonstração na tabela.
	 * @return Matriz de Objects em que cada linha é um registro de cliente com
	 * id, nome, telefone e e-mail.
	 */	
	public static Object[][] buscaClientes() {
		ArrayList<Object[]> resposta = new ArrayList<Object[]>(); 
		
		String query = "select id,nome,cidade,telefone,email from Clientes order by id ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cidade = rs.getString("cidade");
				String telefone = rs.getString("telefone");
				String email = rs.getString("email");
				Object[] linha = {id,nome,cidade,telefone,email};
				resposta.add(linha);
			}
			
			return convertToObjectMatrix(resposta);
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca as informações parciais do funcionário no formato para a demonstração na tabela.
	 * @return Matriz de Objects em que cada linha é um registro de funcionário com
	 * usuario, nome, telefone e e-mail.
	 */	
	public static Object[][] buscaFuncionarios() {
		ArrayList<Object[]> resposta = new ArrayList<Object[]>(); 
		
		String query = "select nome,usuario,telefone,email from Funcionarios order by usuario ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				String usuario = rs.getString("usuario");
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String email = rs.getString("email");
				Object[] linha = {usuario,nome,telefone,email};
				resposta.add(linha);
			}
			
			return convertToObjectMatrix(resposta);
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca a lista de manutenções associadas ao veículo do qual se passa o código 
	 * e a entrega no formato correto para a demonstração na tabela.
	 * @return Matriz de Objects em que cada linha é um registro de manutenção com
	 * id, nome do cliente, modelo, cor, ano e placa.
	 */	
	public static Object[][] buscaManutencoes(int codigo) {
		ArrayList<Object[]> resposta = new ArrayList<Object[]>(); 
		
		String query  = "select m.id,m.ajustes,m.valor,m.horasgastas from manutencao m ";
			   query += "where idCarro='"+codigo+"' order by id ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String ajustes = rs.getString("ajustes");
				String valor = rs.getString("valor");
				String horasgastas = rs.getString("horasgastas");
				Object[] linha = {id,ajustes,valor,horasgastas};
				resposta.add(linha);
			}
			
			return convertToObjectMatrix(resposta);
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca a lista de clientes para preencher o combo box.
	 * @return Uma matriz de Objects em que a primeira linha é a lista de todos os nomes 
	 * de clientes e a segunda é a de seus IDs
	 */
	public static Object[][] buscaComboClientes() {
		ArrayList<String> nomes = new ArrayList<String>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		String query  = "Select id,nome from Clientes ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				ids.add(rs.getInt("id"));
				nomes.add(rs.getString("nome"));
			}
			
			return new Object[][] {nomes.toArray(),ids.toArray()};
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Busca a lista de funções.
	 * @return Uma matriz de Object em que a primeira coluna é a lista de ids de todos os clientes
	 * e a segunda é a de seus IDs
	 */
	public static Object[][] buscaFuncoes() {
		ArrayList<String> descricoes = new ArrayList<String>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		String query  = "Select * from Funcoes ";
		
		try {
			ResultSet rs = consultaQuery(query);
			while (rs.next()) {
				ids.add(rs.getInt("id"));
				descricoes.add(rs.getString("descricao"));
			}
			
			return new Object[][] {descricoes.toArray(),ids.toArray()};
			
		} catch (Exception e) { 
			JOptionPane.showMessageDialog(null,e.getStackTrace().toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return Caso o parâmetro esteja vazio, retorna "NULL". 
	 * Caso não esteja retorna o parâmetro cercado por "'".
	 */
	private static String setNull(String s) {
		return (s.length() > 0)?"'"+s+"'":"NULL";
	}
	
	/**
	 * Insere o usuário no banco.
	 * @return False caso haja um erro do banco.
	 */
	public static boolean inserirCliente(String nome,String cpf,String telefone, 
										 String email,String endereco,String numero,
										 String bairro,String cep,String cidade,String estado) {
		try {
			ResultSet rs = consultaQuery("select id from Clientes order by id desc limit 1");
			int id = (rs.next()) ? rs.getInt("id") : -1;
			id++;
			String query =  "INSERT INTO CLIENTES (id,nome,cpf,telefone,email,endereco,";
				   query +=					  		  "numero,bairro,cep,cidade,estado)";
				   query +=					  						  " VALUES ("+id+",";
				   query += setNull(nome)+",";
				   query += setNull(cpf)+",";
				   query += setNull(telefone)+",";
				   query +=	setNull(email)+",";
				   query +=	setNull(endereco)+",";
				   query +=	setNull(numero)+",";
				   query += setNull(bairro)+",";
				   query +=	setNull(cep)+",";	
				   query +=	setNull(cidade)+","; 
				   query +=	setNull(estado)+");";

		    return executaQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Verifica a presença do usuário no banco e retorna a resposta em boolean.
	 */
	public static boolean existeFuncionario(String usuario) {
		try {
			ResultSet rs = consultaQuery("select usuario from FUNCIONARIOS where usuario='"+usuario+"' ");
			
			if (rs.next()) {
				return true;
			}
			
			return false;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Insere o funcionário no banco.
	 * @return False caso haja um erro.
	 */
	public static boolean inserirFuncionario(String nome,String usuario,String senha,String cpf,String telefone, 
			 String email,int funcao, String dataAdmissao,String dataCadastro) {

		try {
			String query =  "INSERT INTO FUNCIONARIOS (nome,usuario,senha,cpf,telefone,email,";
			query +=					  		   "funcao,dataAdmissao,dataCadastro)";
			query +=					  						   		  " VALUES (";
			query += setNull(nome)+",";
			query +=	setNull(usuario)+",";
			query +=	setNull(senha)+",";
			query += setNull(cpf)+",";
			query += setNull(telefone)+",";
			query +=	setNull(email)+",";
			query +=			funcao+",";
			query += setNull(dataAdmissao)+",";
			query +=	setNull(dataCadastro)+");";
			
			return executaQuery(query);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Insere o veículo no banco.
	 * @return False caso haja um erro.
	 */
	public static boolean inserirVeiculo(int idCliente,String modelo,String cor,
			String placa,String preco,String ano,String motor,String lucro,String kilometragem,
			String precoAquisicao) {
			
		
		try {
			ResultSet rs = consultaQuery("select id from Carros order by id desc limit 1");
			int id = (rs.next()) ? rs.getInt("id") : -1;
			id++;

			String query = 	    "INSERT INTO CARROS (id,cliente,modelo,cor,ano,placa,preco,";
			query +=					  		  "motor,lucro,kilometragem,precoAquisicao)";
			query +=					  							    		" VALUES (";
			query +=	id+",";
			query +=	idCliente+",";
			query += 	setNull(modelo)+",";
			query += 	setNull(cor)+",";
			query +=	setNull(ano)+",";
			query += 	setNull(placa)+",";
			query +=	setNull(preco)+",";
			query +=	setNull(motor)+",";	
			query +=	setNull(lucro)+","; 
			query +=	setNull(kilometragem)+",";
			query +=	setNull(precoAquisicao)+"); ";
			
			executaQuery("update Manutencao set idCarro='"+id+"' where idCarro=-1 ");
		
		System.out.println(query);
		return executaQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Insere a manutenção vazia no banco.
	 * @return False caso haja um erro.
	 */
	public static int inserirManutencao(int idCarro) {

		try {

			ResultSet rs = consultaQuery("select id from MANUTENCAO order by id desc limit 1");
			int id = (rs.next()) ? rs.getInt("id") : -1;
			id++;
			String query = "INSERT INTO MANUTENCAO (id,idCarro,ajustes,valor,horasgastas) VALUES ("+id+","+idCarro+",'','',''); ";
			
			return (executaQuery(query)) ? id : -1;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Edita o cliente no banco.
	 * @return False caso haja um erro.
	 */
	public static boolean editarCliente(int id, String nome,String cpf,String telefone, 
										 String email,String endereco,String numero,
										 String bairro,String cep,String cidade,String estado) {
		try {
			String query =  "UPDATE CLIENTES SET ";
				   query +=	"nome = "+setNull(nome)+",";
				   query += "cpf = "+setNull(cpf)+",";
				   query += "telefone = "+setNull(telefone)+",";
				   query +=	"email = "+setNull(email)+",";
				   query +=	"endereco = "+setNull(endereco)+",";
				   query +=	"numero = "+setNull(numero)+",";
				   query += "bairro = "+setNull(bairro)+",";
				   query +=	"cep = "+setNull(cep)+",";	
				   query +=	"cidade = "+setNull(cidade)+","; 
				   query +=	"estado = "+setNull(estado)+" ";
				   query +=	"where id="+id+";";

			System.out.println(query);
		    return executaQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Edita o funcionário no banco.
	 * @return False caso haja um erro.
	 */
	public static boolean editarFuncionario(String nome,String usuario,String senha,
			String cpf,String telefone,String email,int funcao,String dataAdmissao,String dataCadastro) {
		
			try {
			String query = "UPDATE FUNCIONARIOS SET ";
			query +=	"nome = "+setNull(nome)+",";
			query +=	"senha = "+setNull(senha)+",";
			query += 	"cpf = "+setNull(cpf)+",";
			query += 	"telefone = "+setNull(telefone)+",";
			query +=	"email = "+setNull(email)+",";
			query +=	"funcao = "+funcao+",";	
			query +=	"dataAdmissao = "+setNull(dataAdmissao)+","; 
			query +=	"dataCadastro = "+setNull(dataCadastro)+" ";
			query +=	"where usuario='"+usuario+"';";
			
			System.out.println(query);
		return executaQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Edita o veículo no banco.
	 * @return False caso haja um erro.
	 */
	public static boolean editarVeiculo(int id,int idCliente,String modelo,String cor,
			String placa,String preco,String ano,String motor,String lucro,String kilometragem,
			String precoAquisicao) {
			
		
		try {
			String query = "UPDATE CARROS SET ";
			query +=	"cliente = "+idCliente+",";
			query += 	"modelo = "+setNull(modelo)+",";
			query += 	"cor = "+setNull(cor)+",";
			query +=	"ano = "+setNull(ano)+",";
			query += 	"placa = "+setNull(placa)+",";
			query +=	"preco = "+setNull(preco)+",";
			query +=	"motor = "+setNull(motor)+",";	
			query +=	"lucro = "+setNull(lucro)+","; 
			query +=	"kilometragem = "+setNull(kilometragem)+",";
			query +=	"precoAquisicao = "+setNull(precoAquisicao)+" ";
			query +=	"where id='"+id+"';";
		
		System.out.println(query);
		return executaQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Apaga o veículo do banco.
	 * @return False caso haja um erro.
	 */
	public static boolean apagarCarro(int codigo) {
		String query = "delete from Carros where id='"+codigo+"'";
		
		try {
			boolean rs = executaQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Apaga o cliente do banco.
	 * @return False caso haja um erro.
	 */
	public static boolean apagarCliente(int codigo) {
		String query = "delete from Clientes where id='"+codigo+"'";
		
		try {
			boolean rs = executaQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Atualiza o registro do funcionário do banco.
	 * @return False caso haja um erro.
	 */
	public static boolean apagarFuncionario(String usuario) {
		String query = "delete from Funcionarios where usuario='"+usuario+"'";
		
		try {
			boolean rs = executaQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Atualiza o registro da manutenção do banco.
	 * @return False caso haja um erro.
	 */
	public static boolean apagarManutencao(int id) {
		String query = "delete from Manutencao where id='"+id+"'";
		
		try {
			boolean rs = executaQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Atualiza o registro da manutenção do banco.
	 * @return False caso haja um erro.
	 */
	public static boolean editarManutencao(int id,int idCarro,String ajustes,String valor,String horasgastas) {

		try {
			String query =  "UPDATE MANUTENCAO SET ";
			query += "idCarro = "+idCarro+",";
			query += "ajustes = "+setNull(ajustes)+",";
			query += "valor = "+setNull(valor)+",";
			query += "horasgastas = "+setNull(horasgastas)+" ";
			query += "WHERE id="+id+";";
			
			return executaQuery(query);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Remove os registros no banco com ID negativo, ou seja, não relacionado a nenhum
	 * usuário ativo
	 * @throws Exception 
	 */
	public static boolean limparManutencao() {
		String query = "delete from Manutencao where idCarro < 0 ";
		
		try {
			boolean rs = executaQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Converte um ArrayList de Object[] para uma matriz de Objects de forma a os nódulos
	 * manterem uma relação de x e y equivalente à que eles tinham no parâmetro mas com   
	 * formato mais facilmente legível por elementos visuais.
	 * @throws Exception 
	 */
	private static Object[][] convertToObjectMatrix(ArrayList<Object[]> lista){
		if (lista.isEmpty()) return new Object[0][0];
		if (lista.get(0).length==0) return new Object[0][0];
		int y = lista.size();
		int x = lista.get(0).length; 
		Object[][] resposta = new Object[y][x];
		for (int i=0;i<y;i++) {
			for (int j=0;j<x;j++) {
				resposta[i][j] = (lista.get(i)[j] != null) ? lista.get(i)[j] : "";
			}
		}
		
		return resposta;
	}
	
	
	/**
	 * Realiza as consultas ao banco e retorna o resultado em um ResultSet.
	 * Em caso de erro dispara mensagem e retorna nulo.
	 * @throws Exception 
	 */
	private static ResultSet consultaQuery(String query) throws Exception {
		try {
			Class.forName(driver);
			Connection db = DriverManager.getConnection(url, usuarioBanco, senhaBanco);
			Statement stmt = db.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (Exception e) { 
			e.printStackTrace();
			throw (e);
		}
	}
	
	/**
	 * Realiza as consultas ao banco e retorna o resultado em um ResultSet.
	 * Em caso de erro dispara mensagem e retorna nulo.
	 * @throws Exception 
	 */
	private static boolean executaQuery(String query) throws Exception {
		try {
			Class.forName(driver);
			Connection db = DriverManager.getConnection(url, usuarioBanco, senhaBanco);
			Statement stmt = db.createStatement();
			int resposta = stmt.executeUpdate(query);
			return (resposta > 0) ? true : false;
		} catch (Exception e) { 
			e.printStackTrace();
			throw (e);
		}
	}
	

}
