package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDAO;
import model.Employee;
import view.boss.BossLandingView;
import view.mechanical.MechanicalLandingView;
import view.sales.SalesLandingView;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class LoginView {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private UserDAO userDAO;

	/**
	 * Crea la aplicación
	 */
	public LoginView() {
		userDAO = new UserDAO();
		initialize();
	}

	/**
	 * Inicializa el contenido del frame, los componentes de la interfaz de usuario
	 * y los controladores
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		setUIComponents();
		setControllers();
	}

	/**
	 * Contiene los controladores
	 */
	private void setControllers() {
		btnLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var user = createEmployee();
				if (userDAO.login(user)) {
					// Comprobar que tipo de usuario entra (ventas, mecánico o jefe para cada nueva
					// view se le pasará un objeto con su modelo y sus datos)
					if (userDAO.isBoss(user)) {
						var bossUser = userDAO.getBossEmployee(user);
						new BossLandingView(bossUser).getFrame().setVisible(true);
						frame.dispose();
					} else if (userDAO.isSalesEmployee(user)) {
						var salesUser = userDAO.getSalesEmployee(user);
						new SalesLandingView(salesUser).getFrame().setVisible(true);
						frame.dispose();
					} else if (userDAO.isBossMechanical(user)) {
						// Se le pasa true para saber que es jefe
						var mechanicalBossUser = userDAO.getMechanicalEmployee(user);
						new MechanicalLandingView(mechanicalBossUser, true).getFrame().setVisible(true);
						frame.dispose();
					} else {
						// Si no es jefe false
						var mechanicalUser = userDAO.getMechanicalEmployee(user);
						new MechanicalLandingView(mechanicalUser, false).getFrame().setVisible(true);
						frame.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrecto", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Contiene los componentes de la interfaz de usuario
	 */
	private void setUIComponents() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(233, 196, 106));
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		txtUser = new JTextField();
		txtUser.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtUser.setText("Usuario");
		txtUser.setBounds(300, 327, 191, 30);
		mainPanel.add(txtUser);
		txtUser.setColumns(10);

		btnLogin = new JButton("Acceder");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnLogin.setBorderPainted(false);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(231, 111, 81));
		btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnLogin.setBounds(300, 440, 191, 30);
		mainPanel.add(btnLogin);

		txtPassword = new JPasswordField("usuario");
		txtPassword.setBounds(300, 376, 191, 30);
		mainPanel.add(txtPassword);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(LoginView.class.getResource("/assets/img/logo.png")));
		lblLogo.setBounds(-21, 10, 817, 307);
		mainPanel.add(lblLogo);
	}

	/**
	 * Crea un usuario con los datos de los textField
	 * 
	 * @return
	 */
	private Employee createEmployee() {
		var username = txtUser.getText();

		// Obtener hash MD5 de la contraseña introducida en el txtField
		var password = new String(txtPassword.getPassword());
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Obtención del hash
		md.update(password.getBytes());
		var digest = md.digest();

		// Casteo de bytes[] a String
		var bigInt = new BigInteger(1, digest);
		var passwordHashMD5 = bigInt.toString(16);

		// Si no tiene 32 carácteres rellena con 0 a la izquierda
		while (passwordHashMD5.length() < 32) {
			passwordHashMD5 = "0" + passwordHashMD5;
		}

		return new Employee(username, passwordHashMD5);
	}

	public JFrame getFrame() {
		return frame;
	}
}
