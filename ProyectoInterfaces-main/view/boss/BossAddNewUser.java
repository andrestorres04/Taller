package view.boss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import dao.EmployeeDAO;
import model.Boss;
import model.Employee;
import view.LoginView;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class BossAddNewUser {

	private JFrame frame;
	private Boss user;
	
	private JLabel nombrelbl;
	private JLabel apellidolbl;
	private JLabel telefonolbl;
	private JLabel dnilbl;
	private JLabel rollbl;
	private JLabel passlbl;
	private JLabel userlbl;
	private JLabel especialidad;
	
	private JTextField nombretxt;
	private JTextField apellidotxt;
	private JTextField telefonotxt;
	private JTextField dnitxt;
	private JTextField usertxt;
	
	private JPasswordField passtxt;
	
	private JComboBox<Object> rolComboBox;
	private JComboBox<Object> concesionarioComboBox;
	private JComboBox<Object> especialidadCB;
	
	private JButton backButton;
	private JButton registerButton;
	

	private JButton btnLogOut;
	
	private EmployeeDAO employeeDAO;
	
	/**
	 * Create the application.
	 */
	public BossAddNewUser(Boss user) {
		this.user = user;
		initialize();
		employeeDAO = new EmployeeDAO();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUIComponents();
		setControllers();
	}
	
	public void setControllers() {
		// Añadir cliente
		registerButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var employee = createEmployee();
				if (employee != null) {
					employeeDAO.addEmployee(employee);
					JOptionPane.showMessageDialog(frame, "Empleado añadido", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		});
		
		// Volver al menú principal
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossLandingView(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		// Volver al login
		btnLogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new LoginView().getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
	}
	
	public void setUIComponents() {
		frame.setTitle("Jefe");
		frame.setMinimumSize(new Dimension(700, 500));

		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setBackground(new Color(233, 196, 106));
		topPanel.setBounds(100, 100, 100, 100);

		JPanel leftPanel = new JPanel();
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setBackground(new Color(233, 196, 106));

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setBackground(new Color(233, 196, 106));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		btnLogOut = new JButton("Cerrar Sesión");

		btnLogOut.setBorder(null);
		btnLogOut.setBackground(new Color(233, 196, 106));
		btnLogOut.setFont(new Font("SansSerif", Font.BOLD, 12));
		bottomPanel.add(btnLogOut);

		JLabel lblLogOut = new JLabel("X");
		lblLogOut.setForeground(new Color(220, 20, 60));
		lblLogOut.setFont(new Font("SansSerif", Font.BOLD, 13));
		bottomPanel.add(lblLogOut);

		JPanel rightPanel = new JPanel();
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setBackground(new Color(233, 196, 106));

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{1061, 0};
		gbl_mainPanel.rowHeights = new int[]{84, 281, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JPanel cabeceraPanel = new JPanel();
		cabeceraPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,50));
		GridBagConstraints gbc_cabeceraPanel = new GridBagConstraints();
		gbc_cabeceraPanel.fill = GridBagConstraints.BOTH;
		gbc_cabeceraPanel.insets = new Insets(0, 0, 5, 0);
		gbc_cabeceraPanel.gridx = 0;
		gbc_cabeceraPanel.gridy = 0;
		mainPanel.add(cabeceraPanel, gbc_cabeceraPanel);
		
		JLabel datoslbl = new JLabel("Datos del nuevo empleado");
		datoslbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		cabeceraPanel.add(datoslbl);
		
		JPanel datesPanel = new JPanel();
		datesPanel.setLayout(new GridLayout(1,2));
		GridBagConstraints gbc_datesPanel = new GridBagConstraints();
		gbc_datesPanel.fill = GridBagConstraints.BOTH;
		gbc_datesPanel.gridx = 0;
		gbc_datesPanel.gridy = 1;
		mainPanel.add(datesPanel, gbc_datesPanel);
		
		JPanel datesPanelLeft = new JPanel();
		datesPanel.add(datesPanelLeft);
		SpringLayout sl_datesPanelLeft = new SpringLayout();
		datesPanelLeft.setLayout(sl_datesPanelLeft);
		
		nombrelbl = new JLabel("Nombre");
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, nombrelbl, 29, SpringLayout.NORTH, datesPanelLeft);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, nombrelbl, 59, SpringLayout.WEST, datesPanelLeft);
		nombrelbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(nombrelbl);
		
		nombretxt = new JTextField();
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, nombretxt, -3, SpringLayout.NORTH, nombrelbl);
		nombretxt.setColumns(10);
		nombretxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(nombretxt);
		
		apellidolbl = new JLabel("Apellidos");
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, apellidolbl, 30, SpringLayout.SOUTH, nombrelbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, apellidolbl, 0, SpringLayout.WEST, nombrelbl);
		apellidolbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(apellidolbl);
		
		apellidotxt = new JTextField();
		sl_datesPanelLeft.putConstraint(SpringLayout.EAST, nombretxt, 0, SpringLayout.EAST, apellidotxt);
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, apellidotxt, -3, SpringLayout.NORTH, apellidolbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.EAST, apellidotxt, -134, SpringLayout.EAST, datesPanelLeft);
		apellidotxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		apellidotxt.setColumns(10);
		datesPanelLeft.add(apellidotxt);
		
		telefonolbl = new JLabel("Teléfono");
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, telefonolbl, 30, SpringLayout.SOUTH, apellidolbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, telefonolbl, 0, SpringLayout.WEST, nombrelbl);
		telefonolbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(telefonolbl);
		
		telefonotxt = new JTextField();
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, telefonotxt, -3, SpringLayout.NORTH, telefonolbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, telefonotxt, 0, SpringLayout.WEST, apellidotxt);
		telefonotxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		telefonotxt.setColumns(10);
		datesPanelLeft.add(telefonotxt);
		
		rollbl = new JLabel("Rol");
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, rollbl, 30, SpringLayout.SOUTH, telefonolbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, rollbl, 0, SpringLayout.WEST, nombrelbl);
		rollbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(rollbl);
		
		rolComboBox = new JComboBox<>();
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, rolComboBox, -3, SpringLayout.NORTH, rollbl);
		rolComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Ventas", "Mecánico" }));
		rolComboBox.setSelectedIndex(0);
		rolComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(rolComboBox);
		
		especialidad = new JLabel("Especialidad mecánicos");
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, especialidad, 30, SpringLayout.SOUTH, rollbl);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, especialidad, 0, SpringLayout.WEST, nombrelbl);
		especialidad.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(especialidad);
		
		especialidadCB = new JComboBox<>();
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, especialidadCB, 42, SpringLayout.EAST, especialidad);
		sl_datesPanelLeft.putConstraint(SpringLayout.EAST, especialidadCB, -134, SpringLayout.EAST, datesPanelLeft);
		sl_datesPanelLeft.putConstraint(SpringLayout.WEST, rolComboBox, 0, SpringLayout.WEST, especialidadCB);
		sl_datesPanelLeft.putConstraint(SpringLayout.EAST, rolComboBox, 0, SpringLayout.EAST, especialidadCB);
		sl_datesPanelLeft.putConstraint(SpringLayout.NORTH, especialidadCB, -3, SpringLayout.NORTH, especialidad);
		especialidadCB.setModel(new DefaultComboBoxModel<>(new String[] { "Coches", "Motocicletas", "Ciclomotores"}));
		especialidadCB.setSelectedIndex(0);
		especialidadCB.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelLeft.add(especialidadCB);
		
		
		
		JPanel datesPanelRight = new JPanel();
		datesPanel.add(datesPanelRight);
		SpringLayout sl_datesPanelRight = new SpringLayout();
		datesPanelRight.setLayout(sl_datesPanelRight);
		
		dnilbl = new JLabel("DNI");
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, dnilbl, 29, SpringLayout.NORTH, datesPanelRight);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, dnilbl, 59, SpringLayout.WEST, datesPanelRight);
		dnilbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(dnilbl);
		
		dnitxt = new JTextField();
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, dnitxt, -3, SpringLayout.NORTH, dnilbl);
		dnitxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dnitxt.setColumns(10);
		datesPanelRight.add(dnitxt);
		
		userlbl = new JLabel("Nombre de usuario");
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, userlbl, 30, SpringLayout.SOUTH, dnilbl);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, userlbl, 59, SpringLayout.WEST, datesPanelRight);
		userlbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(userlbl);
		
		usertxt = new JTextField();
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, dnitxt, 0, SpringLayout.WEST, usertxt);
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, usertxt, -3, SpringLayout.NORTH, userlbl);
		usertxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		usertxt.setColumns(10);
		datesPanelRight.add(usertxt);
		
		passlbl = new JLabel("Contraseña");
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, passlbl, 30, SpringLayout.SOUTH, userlbl);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, passlbl, 0, SpringLayout.WEST, dnilbl);
		passlbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(passlbl);
		
		passtxt = new JPasswordField();
		sl_datesPanelRight.putConstraint(SpringLayout.EAST, passtxt, -135, SpringLayout.EAST, datesPanelRight);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, usertxt, 0, SpringLayout.WEST, passtxt);
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, passtxt, -3, SpringLayout.NORTH, passlbl);
		passtxt.setColumns(10);
		passtxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(passtxt);
		
		JLabel concelbl = new JLabel("Concesionario");
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, concelbl, 30, SpringLayout.SOUTH, passlbl);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, concelbl, 0, SpringLayout.WEST, dnilbl);
		concelbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(concelbl);
		
		concesionarioComboBox = new JComboBox<>();
		sl_datesPanelRight.putConstraint(SpringLayout.NORTH, concesionarioComboBox, -3, SpringLayout.NORTH, concelbl);
		sl_datesPanelRight.putConstraint(SpringLayout.WEST, concesionarioComboBox, 106, SpringLayout.EAST, concelbl);
		sl_datesPanelRight.putConstraint(SpringLayout.EAST, concesionarioComboBox, -135, SpringLayout.EAST, datesPanelRight);
		concesionarioComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Todo Ruedas", "H&N Customs" }));
		concesionarioComboBox.setSelectedIndex(0);
		concesionarioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		datesPanelRight.add(concesionarioComboBox);
		
		JPanel bottonPanel = new JPanel();
		GridBagConstraints gbc_botonPanel = new GridBagConstraints();
		gbc_botonPanel.fill = GridBagConstraints.BOTH;
		gbc_botonPanel.gridx = 0;
		gbc_botonPanel.gridy = 2;
		mainPanel.add(bottonPanel, gbc_botonPanel);
		bottonPanel.setLayout(new FlowLayout(1, 250, 100));

		// Botones
		backButton = new JButton("Volver al menú");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		backButton.setBackground(new Color(244, 162, 97));
		backButton.setForeground(Color.WHITE);
		bottonPanel.add(backButton);

		registerButton = new JButton("Añadir empleado");
		registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		registerButton.setBackground(new Color(231, 111, 81));
		registerButton.setForeground(Color.WHITE);
		bottonPanel.add(registerButton);
		
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	private Employee createEmployee() {
		Employee employee = null;
		var employeeList = employeeDAO.getEmployees();
		var name = nombretxt.getText();
		var surnames = apellidotxt.getText();
		var dni = dnitxt.getText();
		var telephone = telefonotxt.getText();
		var user = usertxt.getText();
		var pass = new String(passtxt.getPassword());
		var rol = rolComboBox.getSelectedItem();
		var cod_conce = concesionarioComboBox.getSelectedItem();
		var cod_especialidad = especialidadCB.getSelectedItem();

		if (dnitxt.getText().isBlank() || nombretxt.getText().isBlank() || apellidotxt.getText().isBlank()
				|| telefonotxt.getText().isBlank() || usertxt.getText().isBlank() || pass.isBlank()) {
			JOptionPane.showMessageDialog(frame, "Error, los campos no pueden estar vacios, ni contener solo espacios",
					"Warning!", JOptionPane.ERROR_MESSAGE);
		} else {
			// Comprobar si el empleado ya existe
			var exist = false;
			for (int i = 0; i < employeeList.size(); ++i) {
				if (dni.equalsIgnoreCase(employeeList.get(i).getDni())) {
					exist = true;
					JOptionPane.showMessageDialog(frame, "Error, ya existe el DNI introducido", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if (!exist) {
				if(cod_conce.equals("Todo Ruedas")) {
					cod_conce = 1;
				} else if(cod_conce.equals("H&N Customs")) {
					cod_conce = 2;
				}
				
				if(cod_especialidad.equals("Coches")) {
					cod_especialidad = 1;
				} else if(cod_especialidad.equals("Motocicletas")) {
					cod_especialidad = 2;
				} else if(cod_especialidad.equals("Ciclomotores")) {
					cod_especialidad = 3;
				}
				
				employee = new Employee(dni, name, surnames, telephone, rol.toString(), (int) cod_conce, (int) cod_especialidad, user, pass);
			}
		}

		return employee;
	}
}
