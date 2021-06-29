import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class JFuncionario extends JFrame {

	public static final char INSERIR_FUNCIONARIOS='I';
	public static final char ALTERAR_FUNCIONARIOS='A';
	public static final char VISUALIZAR_FUNCIONARIOS='V';
	public static final char DELETAR_FUNCIONARIOS='D';
	
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	JComboBox cbFuncoes = new JComboBox();
	private JButton btnAcao;
	
	private JPanel p = new JPanel();
	private JPanel panel = new JPanel();
	private JTextField txtNome,txtCPF,txtTelefone,txtEmail,txtUsuario,
					   txtDataAdmissao,txtDataCadastro;
	private JPasswordField txtSenha;
	private ActionListener alInserir;
	
	private int w=421;	// Width (Largura) da tela 
	
	private Object[] dados = new Object[0];
	private String[] funcoes = new String[0];
	private int[] idFuncoes = new int[0];
	int id = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFuncionario frame = new JFuncionario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private boolean validaCampos() {
		if ((txtNome.getText().length() == 0)||(txtUsuario.getText().length() == 0)
						   ||(String.valueOf(txtSenha.getPassword()).length() == 0)) {
			JOptionPane.showMessageDialog(null, "Campos Nome, Usuário e Senha não podem ser vazios");
			return false;
		}
		return true;
	}

	private boolean inserirFuncionario() {
		try {
			if (!validaCampos()) return false;
			if (Conexao.existeFuncionario(txtUsuario.getText())) {
				JOptionPane.showMessageDialog(null, "Usuário já existente");
				return false;
			}
			int funcao = (cbFuncoes.getSelectedIndex() >= 0) ? idFuncoes[cbFuncoes.getSelectedIndex()] : -1;
			return Conexao.inserirFuncionario(txtNome.getText(),txtUsuario.getText(),String.valueOf(txtSenha.getPassword()),
					txtCPF.getText(),txtTelefone.getText(),txtEmail.getText(), funcao, txtDataAdmissao.getText(),txtDataCadastro.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao inserir registro");
		return false;
	}

	private boolean editarFuncionario(String usuario) {
		try {
			if (!validaCampos()) return false;
			int funcao = (cbFuncoes.getSelectedIndex() >= 0) ? idFuncoes[cbFuncoes.getSelectedIndex()] : -1;
			return Conexao.editarFuncionario(txtNome.getText(),usuario,String.valueOf(txtSenha.getPassword()),
					txtCPF.getText(),txtTelefone.getText(),txtEmail.getText(), funcao, txtDataAdmissao.getText(),txtDataCadastro.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao editar cadastro");
		return false;
	}
	
	private void preencherDados(String usuario) {
		dados = Conexao.buscaFuncionario(usuario);
		txtUsuario.setText((String.valueOf(dados[0]) != "null")?String.valueOf(dados[0]):"");
		txtNome.setText((String.valueOf(dados[1]) != "null")?String.valueOf(dados[1]):"");
		txtSenha.setText((String.valueOf(dados[2]) != "null")?String.valueOf(dados[2]):"");
		
		if (dados[3] != null) selecionaFuncao((int) dados[3]);
						else  cbFuncoes.setSelectedIndex(-1);
		txtCPF.setText((String.valueOf(dados[4]) != "null")?String.valueOf(dados[4]):"");
		txtTelefone.setText((String.valueOf(dados[5]) != "null")?String.valueOf(dados[5]):"");
		txtEmail.setText((String.valueOf(dados[6]) != "null")?String.valueOf(dados[6]):"");
		txtDataAdmissao.setText((String.valueOf(dados[7]) != "null")?String.valueOf(dados[7]):"");
		txtDataCadastro.setText((String.valueOf(dados[8]) != "null")?String.valueOf(dados[8]):"");
	}

	
	/**
	 * Prenche o combo box com as funções de funcionário disponíveis.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preencherFuncoes() {
		funcoes = new String[Conexao.buscaFuncoes()[0].length];
		for (int i=0;i<Conexao.buscaFuncoes()[0].length;i++) funcoes[i] = (String) Conexao.buscaFuncoes()[0][i];
		idFuncoes = new int[Conexao.buscaFuncoes()[1].length];
		for (int i=0;i<Conexao.buscaFuncoes()[1].length;i++) idFuncoes[i] = (int) Conexao.buscaFuncoes()[1][i];
		cbFuncoes = new JComboBox(funcoes);
		cbFuncoes.setVisible(true);
		cbFuncoes.setSelectedIndex(-1);
	}
	
	private void selecionaFuncao(int funcao) {
		if (funcao < 0 || idFuncoes.length == 0) {
			cbFuncoes.setSelectedIndex(-1);
			return; 
		}
		for (int i=0;i<idFuncoes.length;i++) {
			if (idFuncoes[i] == funcao) {
				cbFuncoes.setSelectedIndex(i);
				return;
			}
		}
		cbFuncoes.setSelectedIndex(-1);
	}

	public JFuncionario(String usuario, char modo) {
		this();
		
		//infos = Conexao.buscaFuncionario(usuario);
		preencherDados(usuario);
		
		switch(modo) {
		case ALTERAR_FUNCIONARIOS:
			setTitle("Alterar Funcionário");
			preencherDados(usuario);
			txtUsuario.setEditable(false);
			btnAcao.setText("Alterar");
			btnAcao.removeActionListener(alInserir);
			
			/*
			 * Se conseguiu alterar registro com sucesso, fecha a tela.
			 */
			btnAcao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (editarFuncionario(usuario)) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao editar registro");
					}
				}
			});
			break;
		case VISUALIZAR_FUNCIONARIOS:
			setTitle("Visualizar Funcionário");
			preencherDados(usuario);
			btnAcao.setVisible(false);
			break;
		default:
			return;			
		}
	}

	/**
	 * Create the frame.
	 */
	public JFuncionario() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 421, 430);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtNome = new JTextField();
		txtNome.setBounds(w/4, 44, w/2, 20);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(42, 47, 46, 14);
		
		txtCPF = new JTextField();
		txtCPF.setBounds(105, 168, 105, 20);
		txtCPF.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setBounds(42, 171, 46, 14);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(105, 199, 105, 20);
		txtTelefone.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Telefone");
		lblNewLabel_2.setBounds(42, 202, 74, 14);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(105, 230, 210, 20);
		txtEmail.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(42, 233, 58, 14);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(105, 75, 210, 20);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Usuário");
		lblNewLabel_4.setBounds(42, 78, 90, 14);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(105, 106, 140, 20);
		txtSenha.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Senha");
		lblNewLabel_5.setBounds(42, 109, 74, 14);
		lblNewLabel_5.setVisible(true);
		
		preencherFuncoes();
		cbFuncoes.setBounds(105, 137, 140, 22);
		
		JLabel lblNewLabel_6 = new JLabel("Função");
		lblNewLabel_6.setBounds(42, 141, 58, 14);
		
		txtDataAdmissao = new JTextField();
		txtDataAdmissao.setBounds(105, 261, 105, 20);
		txtDataAdmissao.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Admissão");
		lblNewLabel_7.setBounds(w/10, 264, 74, 14);
		lblNewLabel_7.setToolTipText("Data de Admissão");
		
		txtDataCadastro = new JTextField();
		txtDataCadastro.setBounds(105, 292, w/4, 20);
		txtDataCadastro.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Cadastro");
		lblNewLabel_8.setBounds(42, 295, w/4, 14);
		lblNewLabel_8.setToolTipText("Data de Cadastro");

		contentPane.setLayout(null);
		contentPane.add(txtNome);
		contentPane.add(lblNewLabel);
		contentPane.add(txtCPF);
		contentPane.add(lblNewLabel_1);
		contentPane.add(txtTelefone);
		contentPane.add(lblNewLabel_2);
		contentPane.add(txtEmail);
		contentPane.add(lblNewLabel_3);
		contentPane.add(txtUsuario);
		contentPane.add(lblNewLabel_4);
		contentPane.add(txtSenha);
		contentPane.add(lblNewLabel_5);
		contentPane.add(cbFuncoes);
		contentPane.add(lblNewLabel_6);
		contentPane.add(txtDataAdmissao);
		contentPane.add(lblNewLabel_7);
		contentPane.add(txtDataCadastro);
		contentPane.add(lblNewLabel_8);
		
		panel.setVisible(true);
		p.setLayout(null);
		p.add(panel);
		p.setVisible(true);

		btnAcao = new JButton();
		
		/*
		 * Se conseguiu salvar registro com sucesso, fecha a tela.
		 */
		alInserir = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inserirFuncionario()) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao inserir registro");
				}
			}
		}; 
		btnAcao.addActionListener(alInserir);
		btnAcao.setBounds(w/2, 323, w/4, 23);
		contentPane.add(btnAcao);

		btnAcao.setText("Inserir");
		
		setVisible(true);
		setTitle("Inserir Funcionário");
		setIconImage(new ImageIcon("img/g.png").getImage());
	}
}
