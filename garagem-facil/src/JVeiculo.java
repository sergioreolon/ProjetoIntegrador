import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class JVeiculo extends JFrame {
	
	public static final char INSERIR_VEICULO='I';
	public static final char ALTERAR_VEICULO='A';
	public static final char VISUALIZAR_VEICULO='V';
	public static final char DELETAR_VEICULO='D';
	
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox cbClientes;
	private JTextField txtModelo,txtCor,txtPlaca,
					   txtPreco,txtAno,txtMotor,txtLucro,
					   txtKilometragem,txtPrecoAquisicao;
	private ActionListener alInserir;
	
	private Object[][] manutencao = new Object[0][0];
	private int[] idsManutencao = new int[0];
	
	private JScrollPane scrollPane = new JScrollPane();
	private JPanel pnManutencao;
	private JTable table;
	private JButton btnAcao;
	
	private JPanel p = new JPanel();
	private JPanel panel = new JPanel();
	private String[] nomeColunas = {"Ajustes","Valor","Horas Gastas"};
	
	private Object[] infos = new Object[0];
	private String[] clientes = new String[0];
	private int[] idsClientes = new int[0];
	private int codigoVeiculo = -1;
	
	private JTable tbManutencao;
	private JButton btnAdd,btnDelete;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JVeiculo frame = new JVeiculo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void salvarTabela() {
		
		for (int i=0;i<manutencao.length;i++) {

			int id = idsManutencao[i];
			int idVeiculo = codigoVeiculo;
			String ajustes = String.valueOf(tbManutencao.getValueAt(i, 0));
			String valor = String.valueOf(tbManutencao.getValueAt(i, 1));
			String horasgastas = String.valueOf(tbManutencao.getValueAt(i, 2));
			if (!Conexao.editarManutencao(id,idVeiculo,ajustes,valor,horasgastas)) {
				JOptionPane.showMessageDialog(null,"Erro ao salvar linha");
			}
		}
	}
	
	private void adicionarLinha(int codVeiculo) {
		Conexao.inserirManutencao(codVeiculo);
		salvarTabela();
		preencherManutencao();
		salvarTabela();
	}
	
	private void deletarLinha(int linha) {
		Conexao.apagarManutencao(idsManutencao[linha]);
		preencherManutencao();
		salvarTabela();
	}
	
	private boolean validaCampos() {
		if (cbClientes.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Campo Cliente não pode ser vazio");
			return false;
		}
		return true;
	}

	private void preencherManutencao() {
		DefaultTableModel tablemodel = new DefaultTableModel(dadosTabela(codigoVeiculo), nomeColunas){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		tbManutencao.setModel(tablemodel);
	}
	
	private boolean inserirVeiculo() {
		try {
			if (!validaCampos()) return false;
			int idCliente = (cbClientes.getSelectedIndex() >= 0) ? idsClientes[cbClientes.getSelectedIndex()] : -1;
			return Conexao.inserirVeiculo(idCliente,txtModelo.getText(),txtCor.getText(),
					txtPlaca.getText(),txtPreco.getText(),txtAno.getText(),
					txtMotor.getText(), txtLucro.getText(), txtKilometragem.getText(), 
					txtPrecoAquisicao.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao inserir registro");
		return false;
	}

	private boolean editarVeiculo(int id) {
		try {
			if (!validaCampos()) return false;
			int idCliente = (cbClientes.getSelectedIndex() >= 0) ? idsClientes[cbClientes.getSelectedIndex()] : -1;
			return Conexao.editarVeiculo(id,idCliente,txtModelo.getText(),txtCor.getText(),
					txtPlaca.getText(),txtPreco.getText(),txtAno.getText(),
					txtMotor.getText(), txtLucro.getText(), txtKilometragem.getText(), 
					txtPrecoAquisicao.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Erro ao editar cadastro");
		return false;
	}
	
	private void preencherDados(int codigo) {
		infos = Conexao.buscaCarro(codigo);
		int idCliente = -1;
		for (int i=0;i<idsClientes.length;i++) {
			if (infos[1] != null) {
				if (idsClientes[i] == (int)Integer.parseInt(String.valueOf(infos[1])) ){
					idCliente = i;
				}
			}
		}
		cbClientes.setSelectedIndex(idCliente);
		txtModelo.setText((String.valueOf(infos[2]) != "null")?String.valueOf(infos[2]):"");
		txtCor.setText((String.valueOf(infos[3]) != "null")?String.valueOf(infos[3]):"");
		txtAno.setText((String.valueOf(infos[4]) != "null")?String.valueOf(infos[4]):"");
		txtPlaca.setText((String.valueOf(infos[5]) != "null")?String.valueOf(infos[5]):"");
		txtPreco.setText((String.valueOf(infos[6]) != "null")?String.valueOf(infos[6]):"");
		txtMotor.setText((String.valueOf(infos[7]) != "null")?String.valueOf(infos[7]):"");
		txtLucro.setText((String.valueOf(infos[8]) != "null")?String.valueOf(infos[8]):"");
		txtKilometragem.setText((String.valueOf(infos[9]) != "null")?String.valueOf(infos[9]):"");
		txtPrecoAquisicao.setText((String.valueOf(infos[10]) != "null")?String.valueOf(infos[10]):"");
	}
	
	private Object[][] dadosTabela(int codVeiculo){
		manutencao = Conexao.buscaManutencoes(codVeiculo);
		Object[][] resposta = new Object[manutencao.length][3];
		idsManutencao = new int[manutencao.length];
		for (int i=0;i<manutencao.length;i++) {
			idsManutencao[i] = (int)(manutencao[i][0]);
			resposta[i][0] = manutencao[i][1];
			resposta[i][1] = manutencao[i][2];
			resposta[i][2] = manutencao[i][3];
		}
		return resposta;
	}
	
	private void desabilitarCampos() {
		btnAcao.setVisible(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preencherClientes() {
		Object [][] busca = Conexao.buscaComboClientes();
		clientes = new String[busca[0].length];
		for (int i=0;i<busca[0].length;i++) clientes[i] = (String) busca[0][i];
		
		idsClientes = new int[busca[1].length];
		for (int i=0;i<busca[1].length;i++) idsClientes[i] = (int) busca[1][i];
		cbClientes = new JComboBox(clientes);
		cbClientes.setBounds(150, 44, 150, 20);
		cbClientes.setVisible(true);
		cbClientes.setSelectedIndex(-1);
	}

	public JVeiculo(int codVeiculo, char modo) {
		this();
		
		codigoVeiculo = codVeiculo;
		preencherDados(codVeiculo);
		
		switch(modo) {
		case ALTERAR_VEICULO:
			setTitle("Alterar Veículo");
			preencherDados(codVeiculo);
			btnAcao.removeActionListener(alInserir);
			
			/*
			 * Se conseguiu alterar registro com sucesso, salva no banco a tabela
			 * de manutenções e fecha a tela.
			 */
			btnAcao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					salvarTabela();
					if (editarVeiculo(codVeiculo)) {
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao editar registro");
					}
				}
			});
			btnAcao.setText("Alterar");
			break;
		case VISUALIZAR_VEICULO:
			setTitle("Visualizar Veículo");
			preencherDados(codVeiculo);
			desabilitarCampos();
			break;
		default:
			return;			
		}
		
		tbManutencao.setModel(new DefaultTableModel(dadosTabela(codVeiculo), nomeColunas){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        });
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public JVeiculo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 538);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		cbClientes = new JComboBox();
		cbClientes.setBounds(150, 43, 150, 22);
		preencherClientes();
		contentPane.setLayout(null);
		contentPane.add(cbClientes);
		
		JLabel lblNewLabel = new JLabel("Cliente");
		lblNewLabel.setBounds(75, 47, 46, 14);
		
		txtModelo = new JTextField();
		txtModelo.setBounds(150, 75, 150, 20);
		txtModelo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Modelo");
		lblNewLabel_1.setBounds(75, 78, 46, 14);
		
		txtCor = new JTextField();
		txtCor.setBounds(112, 106, 75, 20);
		txtCor.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Cor");
		lblNewLabel_2.setBounds(50, 109, 46, 14);
		
		txtPlaca = new JTextField();
		txtPlaca.setBounds(251, 106, 75, 20);
		txtPlaca.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Placa");
		lblNewLabel_3.setBounds(205, 109, 46, 14);
		
		txtPreco = new JTextField();
		txtPreco.setBounds(112, 137, 75, 20);
		txtPreco.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Preco");
		lblNewLabel_4.setBounds(50, 140, 46, 14);
		
		txtAno = new JTextField();
		txtAno.setBounds(251, 137, 75, 20);
		txtAno.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Ano");
		lblNewLabel_5.setBounds(205, 140, 46, 14);
		lblNewLabel_5.setVisible(true);
		
		txtMotor = new JTextField();
		txtMotor.setBounds(112, 168, 75, 20);
		txtMotor.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Motor");
		lblNewLabel_6.setBounds(50, 171, 46, 14);
		
		txtLucro = new JTextField();
		txtLucro.setBounds(251, 168, 75, 20);
		txtLucro.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Lucro");
		lblNewLabel_7.setBounds(205, 171, 46, 14);
		
		txtKilometragem = new JTextField();
		txtKilometragem.setBounds(112, 199, 75, 20);
		txtKilometragem.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Kilometragem");
		lblNewLabel_8.setBounds(50, 202, 46, 14);
		lblNewLabel_8.setToolTipText("Kilometragem");
		
		txtPrecoAquisicao = new JTextField();
		txtPrecoAquisicao.setBounds(251, 199, 75, 20);
		txtPrecoAquisicao.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Preço Aquisição");
		lblNewLabel_9.setBounds(205, 202, 46, 14);
		lblNewLabel_9.setToolTipText("Preço Aquisição");
		contentPane.add(lblNewLabel);
		contentPane.add(txtModelo);
		contentPane.add(lblNewLabel_1);
		contentPane.add(txtCor);
		contentPane.add(lblNewLabel_2);
		contentPane.add(txtPlaca);
		contentPane.add(lblNewLabel_3);
		contentPane.add(txtPreco);
		contentPane.add(lblNewLabel_4);
		contentPane.add(txtAno);
		contentPane.add(lblNewLabel_5);
		contentPane.add(txtMotor);
		contentPane.add(lblNewLabel_6);
		contentPane.add(txtLucro);
		contentPane.add(lblNewLabel_7);
		contentPane.add(txtKilometragem);
		contentPane.add(lblNewLabel_8);
		contentPane.add(txtPrecoAquisicao);
		contentPane.add(lblNewLabel_9);
		
		pnManutencao = new JPanel();
		pnManutencao.setBounds(29, 230, 380, 201);
		contentPane.add(pnManutencao);
		String title = "Manutenções do Veículo";
		Border border = BorderFactory.createTitledBorder(title);
		pnManutencao.setBorder(border);
		panel.setBounds(10, 5, 751, 437);		
		
		panel.add(new JScrollPane(table));
		panel.setVisible(true);
		p.setLayout(null);
		p.add(panel);
		p.setVisible(true);
		
		pnManutencao.setLayout(null);

		DefaultTableModel modelClientes = new DefaultTableModel(dadosTabela(-1), nomeColunas){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
			@SuppressWarnings("unchecked")
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        

        
		tbManutencao = new JTable(modelClientes);
		tbManutencao.setBounds(10, 15, 360, 137);
		tbManutencao.setToolTipText("Clique duas vezes para editar e Enter para confirmar");
		
		scrollPane = new JScrollPane(tbManutencao);
		scrollPane.setBounds(10, 18, 360, 134);
		pnManutencao.add(scrollPane);
		btnAdd = new JButton();
		btnAdd.setBounds(295, 154, 36, 36);
		btnAdd.setToolTipText("Adiciona linha editável na tabela");
		ImageIcon imgAdd = new ImageIcon("img/add36.png");
		btnAdd.setIcon(imgAdd);
		
		/*
		 * Adiciona funcionalidade para o botão de adicionar manutenção.
		 */
		btnAdd.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent me) {
		    	  adicionarLinha(codigoVeiculo);
		      }
		    });
		
		pnManutencao.add(btnAdd);
		
		btnDelete = new JButton();
		btnDelete.setBounds(334, 154, 36, 36);
		btnDelete.setToolTipText("Deleta linha selecionada");
		ImageIcon imgDelete = new ImageIcon("img/delete36.png");
		btnDelete.setIcon(imgDelete);
		
		/*
		 * Adiciona funcionalidade para o botão de deletar manutenção.
		 */
		btnDelete.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent me) {
		    	  if (tbManutencao.getSelectedRow() == -1) {
		    		  JOptionPane.showMessageDialog(null,"Selecione uma linha na tabela");
		    	  } else {
		    		  Object[] options = { "Sim", "Não" };
		    			int resposta = JOptionPane.showOptionDialog(null, "Apagar registro?", "Aviso",
		    						   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		    						   null, options, options[0]);
		    			if (resposta == 0) {
		    				deletarLinha((tbManutencao.getSelectedRow()));
		    			}
		    	  }
		    	  
		      }
		    });
		
		pnManutencao.add(btnDelete);
		
		pnManutencao.setVisible(true);

		btnAcao = new JButton();
		btnAcao.setBounds(320, 442, 89, 23);
		
		/*
		 * Se conseguiu alterar registro com sucesso, salva no banco a tabela
		 * de manutenções e fecha a tela.
		 */
		alInserir = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarTabela();
				if (inserirVeiculo()) {
					dispose();
				}
			}
		};
		btnAcao.addActionListener(alInserir);
		contentPane.add(btnAcao);

		btnAcao.setText("Inserir");
		
		/*
		 * Salva a tabela de manutenções quando o usuário fecha a tela.
		 */
		this.addWindowListener(new WindowListener() {
			@Override public void windowClosed(WindowEvent e) {}
			@Override public void windowOpened(WindowEvent e) {}
			@Override public void windowIconified(WindowEvent e) {}
			@Override public void windowDeiconified(WindowEvent e) {}			
			@Override public void windowActivated(WindowEvent e) {}
			@Override public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				salvarTabela();
				Conexao.limparManutencao();
			}

		    }
		);
		
		setVisible(true);
		setTitle("Inserir Veículo");
		setIconImage(new ImageIcon("img/g.png").getImage());
	}
}
