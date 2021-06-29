import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	private JPanel contentPane;
	private JTable tabVeiculos,tabClientes,tabFuncionarios;
	
	private JPanel pnVeiculo;
	
	private Object[][] veiculos,clientes,funcionarios;
	private String[] nomeColunasVeiculos = {"Ver Mais","Modelo","Cor","Ano","Placa","Cliente","Editar","Excluir"};
	private String[] nomeColunasClientes = {"Ver Mais","Nome","Cidade","Telefone","E-mail","Editar","Excluir"};
	private String[] nomeColunasFuncionarios = {"Ver Mais","Usuário","Nome","Telefone","E-mail","Editar","Excluir"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Preenche a aba de veículos e adiciona funcionalidades.
	 */
	private JPanel tabelaVeiculos() {
		JPanel p = new JPanel();
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 771, 455);
		
		
		DefaultTableModel tablemodel = new DefaultTableModel(dadosVeiculos(), nomeColunasVeiculos){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		
		tabVeiculos = new JTable(tablemodel);
		tabVeiculos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabVeiculos.setSize(new Dimension(900, 400));
		tabVeiculos.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				int row=tabVeiculos.rowAtPoint(e.getPoint());
				int col= tabVeiculos.columnAtPoint(e.getPoint());
				int idVeiculo = (int) veiculos[row][0];
				switch(col) {
					case 0: new JVeiculo(idVeiculo, JVeiculo.VISUALIZAR_VEICULO); break;
					case 6:  
						JVeiculo ve = new JVeiculo(idVeiculo, JVeiculo.ALTERAR_VEICULO);
						ve.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							updateVeiculos();
						}
					});
					break;
					case 7: apagarVeiculo(idVeiculo); break;
				};
			}
        });
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(tabVeiculos);
		scrollPane.setBounds(10, 11, 751, 438);
		panel.add(scrollPane);
		panel.setVisible(true);
		
		/*
		 * Trata o funcionamento do botão de adicionar
		 * 
		 */
		
		JButton btnNovoCarro = new JButton("Adicionar Carro");
		btnNovoCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JVeiculo ve = new JVeiculo();
				ve.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						updateVeiculos();
					}
				});
			}
		});
		btnNovoCarro.setBounds(637, 458, 124, 23);
		p.add(btnNovoCarro);
		
		p.setLayout(null);
		p.add(panel);
		p.setVisible(true);
		
		return p;
	}

	private void updateVeiculos() {
		DefaultTableModel tablemodel = new DefaultTableModel(dadosVeiculos(), nomeColunasVeiculos){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		tabVeiculos.setModel(tablemodel);
	}
	
	/**
	 * Preenche a aba de clientes e adiciona funcionalidades.
	 */
	private JPanel tabelaClientes() {
		JPanel p = new JPanel();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 771, 454);
		
		panel.setVisible(true);
		p.setLayout(null);
		p.add(panel);
		
		DefaultTableModel modelClientes = new DefaultTableModel(dadosClientes(), nomeColunasClientes){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		
		tabClientes = new JTable(modelClientes);
		tabClientes.setBounds(22, 24, 685, 352);
		tabClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabClientes.setSize(new Dimension(600, 400));
		tabClientes.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				int row=tabClientes.rowAtPoint(e.getPoint());
				int col=tabClientes.columnAtPoint(e.getPoint());
				int idCliente = (int) clientes[row][0];
				
				/*
				 * Trata o funcionamento do botão de ação, para o caso de 
				 * 
				 */
				
				switch(col) {
					case 0: new JCliente(idCliente, JCliente.VISUALIZAR_CLIENTE); break;
					case 5:
						JCliente cl = new JCliente(idCliente, JCliente.ALTERAR_CLIENTE);
						cl.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								updateClientes();
							}
						});
						break;
					case 6: apagarCliente(idCliente); break;
				};
			}
        });
		p.setVisible(true);
		panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tabClientes);
		scrollPane.setBounds(10, 11, 751, 438);
		panel.add(scrollPane);
		
		/*
		 * Trata o funcionamento do botão de adicionar
		 * 
		 */
		JButton btnNovoCliente = new JButton("Adicionar Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCliente cl = new JCliente();
				cl.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						updateClientes();
					}
				});
			}
		});
		btnNovoCliente.setBounds(621, 458, 140, 23);
		p.add(btnNovoCliente);
		
		return p;
	}

	private void updateClientes() {
		DefaultTableModel tablemodel = new DefaultTableModel(dadosClientes(), nomeColunasClientes){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		tabClientes.setModel(tablemodel);
	}
	
	/**
	 * Preenche a aba de funcionários e adiciona funcionalidades.
	 */
	private JPanel tabelaFuncionarios() {
		JPanel p = new JPanel();

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 771, 454);
		
		panel.setVisible(true);
		p.setLayout(null);
		p.add(panel);
		
		DefaultTableModel modelClientes = new DefaultTableModel(dadosFuncionarios(), nomeColunasFuncionarios){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            @SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		
		
        tabFuncionarios = new JTable(modelClientes);
        tabFuncionarios.setBounds(22, 24, 685, 352);
        tabFuncionarios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabFuncionarios.setSize(new Dimension(600, 400));
        tabFuncionarios.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				int row=tabFuncionarios.rowAtPoint(e.getPoint());
				int col=tabFuncionarios.columnAtPoint(e.getPoint());
				String usuario = (String) funcionarios[row][0];
				
				/*
				 * Trata o funcionamento dos botões da tabela
				 * 
				 */
				
				switch(col) {
					case 0: new JFuncionario(usuario, JFuncionario.VISUALIZAR_FUNCIONARIOS); break;
					case 5: JFuncionario jf = new JFuncionario(usuario, JFuncionario.ALTERAR_FUNCIONARIOS); 
						jf.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								updateFuncionarios();
							}
						});
						break;
					case 6: apagarFuncionario(usuario); break;
				};
			}
        });
		p.setVisible(true);
		panel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(tabFuncionarios);
		scrollPane.setBounds(10, 11, 751, 438);
        panel.add(scrollPane);
		
		panel.setVisible(true);
		p.add(panel);
		
		/*
		 * Trata o funcionamento do botão de adicionar
		 * 
		 */
		
        JButton btnNovoFuncionario = new JButton("Adicionar Funcionário");
        btnNovoFuncionario.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFuncionario jf = new JFuncionario(); 
				jf.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						updateFuncionarios();
					}
				});
        	}
        });
        btnNovoFuncionario.setBounds(595, 458, 166, 23);
        p.add(btnNovoFuncionario);	
		p.setVisible(true);
		
		return p;
	}
	
	private void updateFuncionarios() {
		DefaultTableModel tablemodel = new DefaultTableModel(dadosFuncionarios(), nomeColunasFuncionarios){
            @SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
		tabFuncionarios.setModel(tablemodel);
	}
	
	/**
	 * Confirma se o usuário quer mesmo apagar o registro e, em caso positivo,
	 * apaga e atualiza a tabela. 
	 */
	private void apagarVeiculo(int codigo) {
		Object[] options = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null, "Apagar veículo?", "Aviso",
					   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					   null, options, options[0]);
		if (resposta == 0) {
			if (Conexao.apagarCarro(codigo)) {
				updateVeiculos();
			}
		}
	}
	
	/**
	 * Confirma se o usuário quer mesmo apagar o registro e, em caso positivo,
	 * apaga e atualiza a tabela. 
	 */
	private void apagarCliente(int codigo) {
		Object[] options = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null, "Apagar cliente?", "Aviso",
					   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					   null, options, options[0]);
		if (resposta == 0) {
			if (Conexao.apagarCliente(codigo)) {
				updateClientes();
			}
		}
	}
	
	/**
	 * Confirma se o usuário quer mesmo apagar o registro e, em caso positivo,
	 * apaga e atualiza a tabela. 
	 */
	private void apagarFuncionario(String usuario) {
		Object[] options = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null, "Apagar funcionário?", "Aviso",
					   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					   null, options, options[0]);
		if (resposta == 0) {
			if (Conexao.apagarFuncionario(usuario)) {
				updateFuncionarios();
			}
		}
	}
	
	/**
	 * @return Um ImageIcon preenchido com a imagem relativa à ação que o usuário fará na tabela.
	 */
	private ImageIcon linkJVeiculo(char tipoJanela) {
		ImageIcon img;

		switch(tipoJanela) {
			case JVeiculo.VISUALIZAR_VEICULO:
				img = new ImageIcon("img/detalhes.png");
				break;
			case JVeiculo.ALTERAR_VEICULO:
				img = new ImageIcon("img/update.png");
				break;
			case JVeiculo.DELETAR_VEICULO:
				img = new ImageIcon("img/excluir.png");
				break;
			default:
				return new ImageIcon();
		};
		return img;
	}
	
	private Object[][] dadosVeiculos(){
		veiculos = Conexao.buscaCarros();
		Object[][] resposta = new Object[veiculos.length][8];
		for (int i=0;i<veiculos.length;i++) {
			resposta[i][0] = linkJVeiculo(JVeiculo.VISUALIZAR_VEICULO);
			resposta[i][1] = veiculos[i][2];
			resposta[i][2] = veiculos[i][3];
			resposta[i][3] = veiculos[i][4];
			resposta[i][4] = veiculos[i][5];
			resposta[i][5] = veiculos[i][1];
			resposta[i][6] = linkJVeiculo(JVeiculo.ALTERAR_VEICULO);
			resposta[i][7] = linkJVeiculo(JVeiculo.DELETAR_VEICULO);
		}
		return resposta;
	}
	
	private Object[][] dadosClientes(){
		clientes = Conexao.buscaClientes();
		Object[][] resposta = new Object[clientes.length][7];
		for (int i=0;i<clientes.length;i++) {
			resposta[i][0] = linkJVeiculo(JCliente.VISUALIZAR_CLIENTE);
			resposta[i][1] = clientes[i][1];
			resposta[i][2] = clientes[i][2];
			resposta[i][3] = clientes[i][3];
			resposta[i][4] = clientes[i][4];
			resposta[i][5] = linkJVeiculo(JCliente.ALTERAR_CLIENTE);
			resposta[i][6] = linkJVeiculo(JCliente.DELETAR_CLIENTE);
		}
		return resposta;
	}
	
	private Object[][] dadosFuncionarios(){
		funcionarios = Conexao.buscaFuncionarios();
		Object[][] resposta = new Object[funcionarios.length][7];
		for (int i=0;i<funcionarios.length;i++) {
			resposta[i][0] = linkJVeiculo(JFuncionario.VISUALIZAR_FUNCIONARIOS);
			resposta[i][1] = funcionarios[i][0];
			resposta[i][2] = funcionarios[i][1];
			resposta[i][3] = funcionarios[i][2];
			resposta[i][4] = funcionarios[i][3];
			resposta[i][5] = linkJVeiculo(JFuncionario.ALTERAR_FUNCIONARIOS);
			resposta[i][6] = linkJVeiculo(JFuncionario.DELETAR_FUNCIONARIOS);
		}
		return resposta;
	}

	/**
	 * Prepara a tela visualmente para receber os elementos.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 579);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 776, 520);
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		
		pnVeiculo = tabelaVeiculos();
		tabbedPane.addTab("Veículos", null, pnVeiculo);
		tabbedPane.addTab("Clientes", null, tabelaClientes());
		tabbedPane.addTab("Funcionários", null, tabelaFuncionarios());
		
		contentPane.add(tabbedPane);
		
		setVisible(true);		
		setTitle("Garagem Fácil");
		setIconImage(new ImageIcon("img/g.png").getImage());
	}
}
