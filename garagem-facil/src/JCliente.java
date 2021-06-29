import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class JCliente extends JFrame {

	public static final char INSERIR_CLIENTE='I';
	public static final char ALTERAR_CLIENTE='A';
	public static final char VISUALIZAR_CLIENTE='V';
	public static final char DELETAR_CLIENTE='D';
	
	private JPanel contentPane;
	private JButton btnAcao;
	
	private JPanel p = new JPanel();
	private JPanel panel = new JPanel();
	private JTextField txtNome,txtCPF,txtTelefone,txtEmail,txtEndereco,
					   txtNumero,txtBairro,txtCEP,txtCidade,txtEstado;
	private ActionListener alInserir;
	
	private Object[] infos = new Object[0];
	int id = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCliente frame = new JCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private boolean validaCampos() {
		if (txtNome.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Campo Nome não pode ser vazio");
			return false;
		}
		return true;
	}
	
	private boolean inserirCliente() {
		try {
			if (!validaCampos()) return false;
			return Conexao.inserirCliente(txtNome.getText(),txtCPF.getText(),txtTelefone.getText(),txtEmail.getText(), txtEndereco.getText(), txtNumero.getText(),txtBairro.getText(), txtCEP.getText(), txtCidade.getText(), txtEstado.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao inserir registro");
		return false;
	}

	private boolean editarCliente(int id) {
		try {
			if (!validaCampos()) return false;
			return Conexao.editarCliente(id,txtNome.getText(), txtCPF.getText(), txtTelefone.getText(),
					txtEmail.getText(), txtEndereco.getText(), txtNumero.getText(), 
					txtBairro.getText(), txtCEP.getText(), txtCidade.getText(), txtEstado.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao editar cadastro");
		return false;
	}
	
	private void preencherDados(int codigo) {
		infos = Conexao.buscaCliente(codigo);
		txtNome.setText((String.valueOf(infos[1]) != "null")?String.valueOf(infos[1]):"");
		txtCPF.setText((String.valueOf(infos[2]) != "null")?String.valueOf(infos[2]):"");
		txtTelefone.setText((String.valueOf(infos[3]) != "null")?String.valueOf(infos[3]):"");
		txtEmail.setText((String.valueOf(infos[4]) != "null")?String.valueOf(infos[4]):"");
		txtEndereco.setText((String.valueOf(infos[5]) != "null")?String.valueOf(infos[5]):"");
		txtNumero.setText((String.valueOf(infos[6]) != "null")?String.valueOf(infos[6]):"");
		txtBairro.setText((String.valueOf(infos[7]) != "null")?String.valueOf(infos[7]):"");
		txtCEP.setText((String.valueOf(infos[8]) != "null")?String.valueOf(infos[8]):"");
		txtCidade.setText((String.valueOf(infos[9]) != "null")?String.valueOf(infos[9]):"");
		txtEstado.setText((String.valueOf(infos[10]) != "null")?String.valueOf(infos[10]):"");
	}

	public JCliente(int id, char modo) {
		this();
		
		infos = Conexao.buscaCliente(id);
		
		switch(modo) {
		case ALTERAR_CLIENTE:
			setTitle("Alterar Cliente");
			preencherDados(id);
			btnAcao.setText("Alterar");
			btnAcao.removeActionListener(alInserir);
			
			/*
			 * Se conseguiu alterar registro com sucesso, fecha a tela.
			 */
			btnAcao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (editarCliente(id)) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao editar registro");
					}
				}
			});
			break;
		case VISUALIZAR_CLIENTE:
			setTitle("Visualizar Cliente");
			preencherDados(id);
			btnAcao.setVisible(false);
			break;
		default:
			return;			
		}
	}

	/**
	 * Create the frame.
	 */
	public JCliente() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 551, 364);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtNome = new JTextField();
		txtNome.setBounds(112, 44, 319, 20);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(38, 47, 46, 14);
		
		txtCPF = new JTextField();
		txtCPF.setBounds(112, 75, 119, 20);
		txtCPF.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setBounds(38, 78, 46, 14);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(297, 75, 134, 20);
		txtTelefone.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Telefone");
		lblNewLabel_2.setBounds(241, 78, 74, 14);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(112, 106, 319, 20);
		txtEmail.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(38, 109, 58, 14);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(112, 137, 319, 20);
		txtEndereco.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setBounds(38, 140, 90, 14);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(385, 168, 46, 20);
		txtNumero.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Número");
		lblNewLabel_5.setBounds(325, 171, 74, 14);
		lblNewLabel_5.setVisible(true);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(112, 168, 203, 20);
		txtBairro.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Bairro");
		lblNewLabel_6.setBounds(38, 171, 58, 14);
		
		txtCEP = new JTextField();
		txtCEP.setBounds(112, 230, 119, 20);
		txtCEP.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("CEP");
		lblNewLabel_7.setBounds(38, 233, 37, 14);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(112, 199, 319, 20);
		txtCidade.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Cidade");
		lblNewLabel_8.setBounds(38, 202, 51, 14);
		
		txtEstado = new JTextField();
		txtEstado.setBounds(394, 230, 37, 20);
		txtEstado.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Estado");
		lblNewLabel_9.setBounds(341, 233, 74, 14);
		
		contentPane.setLayout(null);
		contentPane.add(txtNome);
		contentPane.add(lblNewLabel);
		contentPane.add(txtCPF);
		contentPane.add(lblNewLabel_1);
		contentPane.add(txtTelefone);
		contentPane.add(lblNewLabel_2);
		contentPane.add(txtEmail);
		contentPane.add(lblNewLabel_3);
		contentPane.add(txtEndereco);
		contentPane.add(lblNewLabel_4);
		contentPane.add(txtNumero);
		contentPane.add(lblNewLabel_5);
		contentPane.add(txtBairro);
		contentPane.add(lblNewLabel_6);
		contentPane.add(txtCEP);
		contentPane.add(lblNewLabel_7);
		contentPane.add(txtCidade);
		contentPane.add(lblNewLabel_8);
		contentPane.add(txtEstado);
		contentPane.add(lblNewLabel_9);
		
		
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
				if (inserirCliente()) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao inserir registro");
				}
			}
		}; 
		btnAcao.addActionListener(alInserir);
		btnAcao.setText("Inserir");
		btnAcao.setBounds(341, 261, 90, 23);
		contentPane.add(btnAcao);
		
		setVisible(true);
		setTitle("Inserir Cliente");
		setIconImage(new ImageIcon("img/g.png").getImage());
	}

}
