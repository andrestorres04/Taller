package view.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import dao.ClientDAO;
import model.Client;
import model.Sales;
import view.LoginView;

import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class SalesAddClientView {

	private JFrame frame;
	private JButton backButton;
	private JButton btnLogOut;
	private JButton registerButton;
	private JTextField txtCod;
	private JTextField txtName;
	private JTextField txtSurnames;
	private JTextField txtDni;
	private JTextField txtTelephone;
	
	private ClientDAO clientDAO;

	private Sales user;
	
	/**
	 * Create the application.
	 */
	public SalesAddClientView(Sales user) {
		this.user = user;
		initialize();
		clientDAO = new ClientDAO();
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

	/**
	 * Contiene los controladores
	 */
	private void setControllers() {
		// Añadir cliente
		registerButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var client = createClient();
				if (client != null) {
					clientDAO.addClient(client);
					JOptionPane.showMessageDialog(frame, "Cliente añadido", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Volver al menú principal
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new SalesLandingView(user).getFrame().setVisible(true);
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

	/**
	 * Contiene los componentes de la interfaz de usuario
	 */
	private void setUIComponents() {
		frame.setTitle("Departamento de ventas");
		frame.setMinimumSize(new Dimension(700, 500));

		/*
		 * Paneles externos. Solo hay que añadir en el bottomPanel
		 */
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setBackground(new Color(233, 196, 106));
		topPanel.setBounds(100, 100, 100, 100);

		JPanel leftPanel = new JPanel();
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setBackground(new Color(233, 196, 106));

		JPanel rightPanel = new JPanel();
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setBackground(new Color(233, 196, 106));

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setBackground(new Color(233, 196, 106));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		// Se añaden los componentes al bottomPanel
		btnLogOut = new JButton("Cerrar Sesión");
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setBorder(null);
		btnLogOut.setBackground(new Color(233, 196, 106));
		btnLogOut.setFont(new Font("SansSerif", Font.BOLD, 12));
		bottomPanel.add(btnLogOut);

		JLabel lblLogOut = new JLabel("X");
		lblLogOut.setForeground(new Color(220, 20, 60));
		lblLogOut.setFont(new Font("SansSerif", Font.BOLD, 13));
		bottomPanel.add(lblLogOut);

		/*
		 * mainPanel. Dentro se crean otros paneles para añadir los distintos
		 * componentes.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 1061, 0 };
		gbl_mainPanel.rowHeights = new int[] { 137, 298, 328, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		// Paneles que componen el mainPanel

		JPanel clientPanel = new JPanel();
		GridBagConstraints gbc_clientesPanel = new GridBagConstraints();
		gbc_clientesPanel.fill = GridBagConstraints.BOTH;
		gbc_clientesPanel.insets = new Insets(0, 0, 5, 0);
		gbc_clientesPanel.gridx = 0;
		gbc_clientesPanel.gridy = 0;
		mainPanel.add(clientPanel, gbc_clientesPanel);
		clientPanel.setLayout(new BorderLayout());
		clientPanel.setPreferredSize(new Dimension(10, 50));

		// Añadir Jlabel a clientesPanel
		JLabel lblClientData = new JLabel("Datos del cliente");
		lblClientData.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientData.setFont(new Font("SansSerif", Font.BOLD, 25));
		clientPanel.add(lblClientData, BorderLayout.CENTER);

		// Panel para rellenar datos de cliente.
		JPanel clientDataPanel = new JPanel();
		GridBagConstraints gbc_datosClientePanel = new GridBagConstraints();
		gbc_datosClientePanel.fill = GridBagConstraints.BOTH;
		gbc_datosClientePanel.insets = new Insets(0, 0, 5, 0);
		gbc_datosClientePanel.gridx = 0;
		gbc_datosClientePanel.gridy = 1;
		mainPanel.add(clientDataPanel, gbc_datosClientePanel);
		clientDataPanel.setLayout(new GridLayout(1, 2));

		JPanel clientDataPanelLeft = new JPanel();
		clientDataPanel.add(clientDataPanelLeft);
		SpringLayout sl_datosClientePanelLeft = new SpringLayout();
		clientDataPanelLeft.setLayout(sl_datosClientePanelLeft);

		// Componentes panel derecho
		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblCod = new JLabel("Código de cliente:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblCod, 26, SpringLayout.NORTH, clientDataPanelLeft);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblCod, 70, SpringLayout.WEST, clientDataPanelLeft);
		lblCod.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblCod);

		txtCod = new JTextField();
		txtCod.setEnabled(false);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, txtCod, -3, SpringLayout.NORTH, lblCod);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, txtCod, 74, SpringLayout.EAST, lblCod);
		txtCod.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtCod.setColumns(10);
		clientDataPanelLeft.add(txtCod);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblNombreCliente = new JLabel("Nombre:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblNombreCliente, 26, SpringLayout.SOUTH, lblCod);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblNombreCliente, 0, SpringLayout.WEST, lblCod);
		lblNombreCliente.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblNombreCliente);

		txtName = new JTextField();
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, txtName, -3, SpringLayout.NORTH, lblNombreCliente);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtCod);
		txtName.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtName.setColumns(10);
		clientDataPanelLeft.add(txtName);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblSurnames = new JLabel("Apellidos:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblSurnames, 26, SpringLayout.SOUTH,
				lblNombreCliente);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblSurnames, 0, SpringLayout.WEST, lblCod);
		lblSurnames.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblSurnames);

		txtSurnames = new JTextField();
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, txtSurnames, -3, SpringLayout.NORTH, lblSurnames);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, txtSurnames, 0, SpringLayout.WEST, txtCod);
		txtSurnames.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSurnames.setColumns(10);
		clientDataPanelLeft.add(txtSurnames);

		JLabel lblDni = new JLabel("DNI:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblDni, 26, SpringLayout.SOUTH, lblSurnames);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblDni, 0, SpringLayout.WEST, lblCod);
		lblDni.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblDni);

		txtDni = new JTextField();
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, txtDni, -3, SpringLayout.NORTH, lblDni);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, txtDni, 0, SpringLayout.WEST, txtCod);
		txtDni.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDni.setColumns(10);
		clientDataPanelLeft.add(txtDni);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblTelephone = new JLabel("Teléfono:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblTelephone, 26, SpringLayout.SOUTH, lblDni);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblTelephone, 0, SpringLayout.WEST, lblCod);
		lblTelephone.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblTelephone);

		txtTelephone = new JTextField();
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, txtTelephone, -3, SpringLayout.NORTH, lblTelephone);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, txtTelephone, 0, SpringLayout.WEST, txtCod);
		txtTelephone.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTelephone.setColumns(10);
		clientDataPanelLeft.add(txtTelephone);

		JPanel clientDataPanelRight = new JPanel();
		clientDataPanel.add(clientDataPanelRight);

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

		registerButton = new JButton("Añadir cliente");
		registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		registerButton.setBackground(new Color(231, 111, 81));
		registerButton.setForeground(Color.WHITE);
		bottonPanel.add(registerButton);

	}

	/**
	 * Crea un usuario con los datos de los textField y comprueba si ya existe
	 * 
	 * @return Si no existe devuelve un cliente, sino null
	 */
	private Client createClient() {
		Client client = null;
		var clientsList = clientDAO.getClients();
		var name = txtName.getText();
		var surnames = txtSurnames.getText();
		var dni = txtDni.getText();
		var telephone = txtTelephone.getText();

		if (txtDni.getText().isBlank() || txtName.getText().isBlank() || txtSurnames.getText().isBlank()
				|| txtTelephone.getText().isBlank()) {
			JOptionPane.showMessageDialog(frame, "Error, los campos no pueden estar vacios, ni contener solo espacios",
					"Warning!", JOptionPane.ERROR_MESSAGE);
		} else {
			// Comprobar si el cliente ya existe
			var exist = false;
			for (int i = 0; i < clientsList.size(); ++i) {
				if (dni.equalsIgnoreCase(clientsList.get(i).getDni())) {
					exist = true;
					JOptionPane.showMessageDialog(frame, "Error, ya existe el DNI introducido", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if (!exist) {
				client = new Client(dni, name, surnames, telephone);
			}
		}

		return client;
	}

	public JFrame getFrame() {
		return frame;
	}

}