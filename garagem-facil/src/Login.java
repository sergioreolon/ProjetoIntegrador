import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textLogin;
	private JPasswordField textSenha;
	private int w=360;	// Width (Largura) da tela 
	private int h=210;	// Height (Altura) da tela

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, w, h);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(w/6, h/6, 46, 20);
		contentPane.add(lblLogin);
		
		textLogin = new JTextField();
		textLogin.setBounds(w/3, h/6, w/3, 20);
		contentPane.add(textLogin);
		textLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(w/6, h/3, 46, 20);
		contentPane.add(lblSenha);
		
		textSenha = new JPasswordField();
		textSenha.setBounds(w/3, h/3, w/3, 20);
		contentPane.add(textSenha);
		textSenha.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Conexao.logar(textLogin.getText(), String.valueOf(textSenha.getPassword()))) {
					new Principal();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,"Usuário e/ou senha incorretos");
					textSenha.setText("");
				}
			}
		});
		btnEntrar.setBounds(w/3, h/2, w/3, 20);
		contentPane.add(btnEntrar);
		
		setTitle("Garagem Fácil");
		setIconImage(new ImageIcon("img/g.png").getImage());
	}

}
