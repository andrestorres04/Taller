package view.mechanical;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import model.Client;
import model.Mechanical;
import model.Vehicle;
import view.LoginView;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dao.ClientDAO;
import dao.VehicleDAO;

import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MechanicalAddVehicleView {

	private JFrame frame;
	private JComboBox<Object> vehicleTypeComboBox;
	private JComboBox<Object> fuelVehicleComboBox;
	private JComboBox<Object> concessionaireComboBox;
	private JComboBox<Object> clientComboBox;
	private JButton returnButton;
	private JButton addVehicleButton;
	private JButton btnLogOut;
	private JTextField vehicleLicenseTxt;
	private JTextField frameNumberTxt;
	private JTextField brandTxt;
	private JTextField modelTxt;

	private VehicleDAO vehicleDAO;
	private ClientDAO clientDAO;

	private Mechanical user;
	private boolean isBoss;

	/**
	 * Crea la aplicación
	 */
	public MechanicalAddVehicleView(Mechanical user, boolean isBoss) {
		this.user = user;
		this.isBoss = isBoss;
		this.vehicleDAO = new VehicleDAO();
		this.clientDAO = new ClientDAO();
		initialize();
	}

	/**
	 * Inicializa el contenido del frame, los componentes de la interfaz de usuario
	 * y los controladores
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
		var comboboxModel = new DefaultComboBoxModel<>();
		
		// Obtener datos DAO
		var vehicles = vehicleDAO.getVehicles();
		var clients = clientDAO.getClients();
		
		// Al abrir carga los clientes en el comboBox
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				clients.stream().forEach(client -> comboboxModel.addElement(client.getNombre() + " " + client.getApellidos()));
				
				// Inserta el modelo del comboBox con los datos
				clientComboBox.setModel(comboboxModel);
			}
		});

		// Añadir vehículo
		addVehicleButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var vehicle = createVehicle(vehicles, clients);
				if (vehicle != null) {
					vehicleDAO.addVehicle(vehicle, vehicleLicenseTxt.getText(), false);
					JOptionPane.showMessageDialog(frame, "Vehículo añadido", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Volver al menú principal
		returnButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new MechanicalLandingView(user, isBoss).getFrame().setVisible(true);
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

		// Controlar número de carácteres en los textFields
		frameNumberTxt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				correctNumberOfCharacters(frameNumberTxt, e, 10);
			}
		});
		
		vehicleLicenseTxt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				correctNumberOfCharacters(vehicleLicenseTxt, e, 7);
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

		JPanel vehiclesPanel = new JPanel();
		GridBagConstraints gbc_vehiclesPanel = new GridBagConstraints();
		gbc_vehiclesPanel.fill = GridBagConstraints.BOTH;
		gbc_vehiclesPanel.insets = new Insets(0, 0, 5, 0);
		gbc_vehiclesPanel.gridx = 0;
		gbc_vehiclesPanel.gridy = 0;
		mainPanel.add(vehiclesPanel, gbc_vehiclesPanel);
		vehiclesPanel.setLayout(new BorderLayout());
		vehiclesPanel.setPreferredSize(new Dimension(10, 50));

		// Añadir Jlabel a clientesPanel
		JLabel mainLbl = new JLabel("Registrar vehículo");
		mainLbl.setHorizontalAlignment(SwingConstants.CENTER);
		mainLbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		vehiclesPanel.add(mainLbl, BorderLayout.CENTER);

		// Panel para rellenar datos de cliente.
		JPanel vehiclesDatesPanel = new JPanel();
		GridBagConstraints gbc_vehiclesDatesPanel = new GridBagConstraints();
		gbc_vehiclesDatesPanel.fill = GridBagConstraints.BOTH;
		gbc_vehiclesDatesPanel.insets = new Insets(0, 0, 5, 0);
		gbc_vehiclesDatesPanel.gridx = 0;
		gbc_vehiclesDatesPanel.gridy = 1;
		mainPanel.add(vehiclesDatesPanel, gbc_vehiclesDatesPanel);
		vehiclesDatesPanel.setLayout(new GridLayout(1, 2));

		JPanel vehiclesDatesPanelLeft = new JPanel();
		vehiclesDatesPanel.add(vehiclesDatesPanelLeft);
		SpringLayout sl_vehiclesDatesPanelLeft = new SpringLayout();
		vehiclesDatesPanelLeft.setLayout(sl_vehiclesDatesPanelLeft);

		// Componentes panel derecho
		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel vehicleTypeLbl = new JLabel("Tipo de vehículo:");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, vehicleTypeLbl, 26, SpringLayout.NORTH,
				vehiclesDatesPanelLeft);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, vehicleTypeLbl, 70, SpringLayout.WEST,
				vehiclesDatesPanelLeft);
		vehicleTypeLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(vehicleTypeLbl);

		vehicleTypeComboBox = new JComboBox<>();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, vehicleTypeComboBox, 69, SpringLayout.EAST,
				vehicleTypeLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, vehicleTypeComboBox, -106, SpringLayout.EAST,
				vehiclesDatesPanelLeft);
		vehicleTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Coche", "Motocicleta", "Ciclomotor" }));
		vehicleTypeComboBox.setSelectedIndex(0);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, vehicleTypeComboBox, -3, SpringLayout.NORTH,
				vehicleTypeLbl);
		vehicleTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(vehicleTypeComboBox);

		JLabel vehicleLicenseLbl = new JLabel("*Matrícula:");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, vehicleLicenseLbl, 26, SpringLayout.SOUTH,
				vehicleTypeLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, vehicleLicenseLbl, 0, SpringLayout.WEST,
				vehicleTypeLbl);
		vehicleLicenseLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(vehicleLicenseLbl);

		vehicleLicenseTxt = new JTextField();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, vehicleLicenseTxt, -106, SpringLayout.EAST,
				vehiclesDatesPanelLeft);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, vehicleLicenseTxt, -3, SpringLayout.NORTH,
				vehicleLicenseLbl);
		vehicleLicenseTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehicleLicenseTxt.setColumns(10);
		vehiclesDatesPanelLeft.add(vehicleLicenseTxt);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel frameNumberLbl = new JLabel("*Número de bastidor:");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, frameNumberLbl, 26, SpringLayout.SOUTH,
				vehicleLicenseLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, frameNumberLbl, 0, SpringLayout.WEST,
				vehicleTypeLbl);
		frameNumberLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(frameNumberLbl);

		frameNumberTxt = new JTextField();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, vehicleLicenseTxt, 0, SpringLayout.WEST,
				frameNumberTxt);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, frameNumberTxt, -3, SpringLayout.NORTH,
				frameNumberLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, frameNumberTxt, -106, SpringLayout.EAST,
				vehiclesDatesPanelLeft);
		frameNumberTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		frameNumberTxt.setColumns(10);
		vehiclesDatesPanelLeft.add(frameNumberTxt);

		JLabel brandLbl = new JLabel("Marca:");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, brandLbl, 26, SpringLayout.SOUTH, frameNumberLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, brandLbl, 0, SpringLayout.WEST, vehicleTypeLbl);
		brandLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(brandLbl);

		brandTxt = new JTextField();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, frameNumberTxt, 0, SpringLayout.WEST, brandTxt);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, brandTxt, 146, SpringLayout.EAST, brandLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, brandTxt, -106, SpringLayout.EAST,
				vehiclesDatesPanelLeft);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, brandTxt, -3, SpringLayout.NORTH, brandLbl);
		brandTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		brandTxt.setColumns(10);
		vehiclesDatesPanelLeft.add(brandTxt);

		JLabel modelLbl = new JLabel("Modelo:");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, modelLbl, 26, SpringLayout.SOUTH, brandLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, modelLbl, 0, SpringLayout.WEST, vehicleTypeLbl);
		modelLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelLeft.add(modelLbl);

		modelTxt = new JTextField();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, modelTxt, -3, SpringLayout.NORTH, modelLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, modelTxt, 137, SpringLayout.EAST, modelLbl);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, modelTxt, -106, SpringLayout.EAST,
				vehiclesDatesPanelLeft);
		modelTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		modelTxt.setColumns(10);
		vehiclesDatesPanelLeft.add(modelTxt);

		JPanel vehiclesDatesPanelRight = new JPanel();
		vehiclesDatesPanel.add(vehiclesDatesPanelRight);
		SpringLayout sl_vehiclesDatesPanelRight = new SpringLayout();
		vehiclesDatesPanelRight.setLayout(sl_vehiclesDatesPanelRight);

		JLabel fuelVehicleLbl = new JLabel("Tipo de combustible:");
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, fuelVehicleLbl, 26, SpringLayout.NORTH,
				vehiclesDatesPanelRight);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, fuelVehicleLbl, 70, SpringLayout.WEST,
				vehiclesDatesPanelRight);
		fuelVehicleLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(fuelVehicleLbl);

		fuelVehicleComboBox = new JComboBox<>();
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, fuelVehicleComboBox, -3, SpringLayout.NORTH,
				fuelVehicleLbl);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, fuelVehicleComboBox, 69, SpringLayout.EAST,
				fuelVehicleLbl);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.EAST, fuelVehicleComboBox, -81, SpringLayout.EAST,
				vehiclesDatesPanelRight);
		fuelVehicleComboBox
				.setModel(new DefaultComboBoxModel<>(new String[] { "Diesel", "Gasolina", "Eléctrico", "Híbrido" }));
		fuelVehicleComboBox.setSelectedIndex(0);
		fuelVehicleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(fuelVehicleComboBox);

		JLabel concessionaireLbl = new JLabel("Concesionario:");
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, concessionaireLbl, 23, SpringLayout.SOUTH,
				fuelVehicleComboBox);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, concessionaireLbl, 0, SpringLayout.WEST,
				fuelVehicleLbl);
		concessionaireLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(concessionaireLbl);

		concessionaireComboBox = new JComboBox<>();
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, concessionaireComboBox, -3, SpringLayout.NORTH,
				concessionaireLbl);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, concessionaireComboBox, 0, SpringLayout.WEST,
				fuelVehicleComboBox);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.EAST, concessionaireComboBox, 0, SpringLayout.EAST,
				fuelVehicleComboBox);
		concessionaireComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Todo Ruedas", "H&N Customs" }));
		concessionaireComboBox.setSelectedIndex(0);
		concessionaireComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(concessionaireComboBox);
		
		clientComboBox = new JComboBox<Object>();
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, clientComboBox, 17, SpringLayout.SOUTH, concessionaireComboBox);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, clientComboBox, 0, SpringLayout.WEST, fuelVehicleComboBox);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.EAST, clientComboBox, 0, SpringLayout.EAST, fuelVehicleComboBox);
		clientComboBox.setSelectedIndex(-1);
		clientComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(clientComboBox);
		
		JLabel lblClient = new JLabel("Propietario:");
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.NORTH, lblClient, 4, SpringLayout.NORTH, clientComboBox);
		sl_vehiclesDatesPanelRight.putConstraint(SpringLayout.WEST, lblClient, 0, SpringLayout.WEST, fuelVehicleLbl);
		lblClient.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesDatesPanelRight.add(lblClient);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_botonPanel = new GridBagConstraints();
		gbc_botonPanel.fill = GridBagConstraints.BOTH;
		gbc_botonPanel.gridx = 0;
		gbc_botonPanel.gridy = 2;
		mainPanel.add(buttonPanel, gbc_botonPanel);
		buttonPanel.setLayout(new FlowLayout(1, 250, 100));

		// Botones
		returnButton = new JButton("Volver al menú");
		returnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		returnButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		returnButton.setBackground(new Color(244, 162, 97));
		returnButton.setForeground(Color.WHITE);
		buttonPanel.add(returnButton);

		addVehicleButton = new JButton("Añadir vehículo");
		addVehicleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addVehicleButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		addVehicleButton.setBackground(new Color(231, 111, 81));
		addVehicleButton.setForeground(Color.WHITE);
		buttonPanel.add(addVehicleButton);

	}

	/**
	 * Crea un vehículo con los datos de los campos de texto, comboBox y comprueba
	 * si ya existe
	 * 
	 * @return Si no existe devuelve un vehículo, sino null
	 */
	private Vehicle createVehicle(List<Vehicle> vehicles, List<Client> clients) {
		Vehicle vehicle = null;

		var numFrameCar = frameNumberTxt.getText();
		var brand = brandTxt.getText();
		var model = modelTxt.getText();
		var fuel = fuelVehicleComboBox.getSelectedItem().toString();
		var concessionaire = concessionaireComboBox.getSelectedIndex() + 1;
		var vehicleType = vehicleTypeComboBox.getSelectedItem().toString();
		var clientName = clientComboBox.getSelectedItem().toString();
		
		// Obtener cliente propietario
		var selectedClient = clients.stream().filter(client -> clientName.equalsIgnoreCase(client.getNombre() + " " + client.getApellidos()))
				.collect(Collectors.toList());

		if (frameNumberTxt.getText().isBlank() || vehicleLicenseTxt.getText().isBlank()) {
			showErrorMessage("Error, los campos número de bastidor y matrícula no pueden estar vacios, ni contener solo espacios");
		} else {
			// Comprobar si el vehículo ya existe 
			var existVehicle = vehicles.stream().filter(v -> v.getNum_bastidor().equalsIgnoreCase(numFrameCar))
					.collect(Collectors.toList());

			if (existVehicle.size() > 0) {
				showErrorMessage("Error, ya existe el número de bastidor del vehículo introducido");
			} else {
				vehicle = new Vehicle(numFrameCar, brand, model, fuel, null, 0, selectedClient.get(0).getClientCod(), concessionaire, vehicleType);
			}
		}
		return vehicle;
	}
	
	/**
	 * Limíta el número de carácteres introducidos
	 * 
	 * @param txt   campo de texto
	 * @param e     evento
	 * @param limit número máximo de carácteres
	 */
	private void correctNumberOfCharacters(JTextField txt, KeyEvent e, int limit) {
		if (txt.getText().length() >= limit) {
			e.consume();
		}
	}
	
	/**
	 * Muestra un mensaje de error por pantalla
	 * @param message
	 */
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "Warning!", JOptionPane.ERROR_MESSAGE);
	}

	public JFrame getFrame() {
		return frame;
	}

}
