package view.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JCalendar;

import dao.ClientDAO;
import dao.SellingPropositionDAO;
import dao.VehicleDAO;
import model.Client;
import model.Sales;
import model.SellingProposition;
import model.Vehicle;
import view.LoginView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Cursor;

public class SalesAddSellingPropositionView {

	private JFrame frame;
	private JComboBox<Object> clientsComboBox;
	private JButton backMenuBtn;
	private JButton addPropositionBtn;
	private JButton btnLogOut;
	private JTextField frameNumberTxt;
	private JTextField priceTxt;
	private JCalendar calendar;

	private VehicleDAO vehicleDAO;
	private ClientDAO clientDAO;
	private SellingPropositionDAO sellingPropositionDAO;

	private Sales user;

	/**
	 * Create the application.
	 */
	public SalesAddSellingPropositionView(Sales user) {
		this.user = user;
		vehicleDAO = new VehicleDAO();
		clientDAO = new ClientDAO();
		sellingPropositionDAO = new SellingPropositionDAO();
		initialize();
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
		// Obtener datos del DAO
		var clients = clientDAO.getClients();
		var vehicles = vehicleDAO.getVehicles();

		// Al abrir la ventana se rellena el combobox con los clientes
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				var comboboxModel = new DefaultComboBoxModel<>();

				for (var client : clients) {
					comboboxModel.addElement(client.getNombre() + " " + client.getApellidos());
				}
				clientsComboBox.setModel(comboboxModel);
			}
		});

		// Añadir propuesta
		addPropositionBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var proposition = createProposition(clients, vehicles);
				if (proposition != null) {
					sellingPropositionDAO.addSellingProposition(proposition);
					JOptionPane.showMessageDialog(frame, "Propuesta de venta añadida", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Volver al menú principal
		backMenuBtn.addMouseListener(new MouseAdapter() {
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

		// Controlar número de carácteres en los textFields
		frameNumberTxt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				correctNumberOfCharacters(frameNumberTxt, e, 10);
			}
		});

		priceTxt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				correctNumberOfCharacters(priceTxt, e, 6);
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

		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		mainPanel.add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setPreferredSize(new Dimension(10, 50));

		// Añadir Jlabel a clientesPanel
		JLabel MainLbl = new JLabel("Añadir propuesta de venta");
		MainLbl.setHorizontalAlignment(SwingConstants.CENTER);
		MainLbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		headerPanel.add(MainLbl, BorderLayout.CENTER);

		// Panel para rellenar datos de cliente.
		JPanel bodyPanel = new JPanel();
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 1;
		mainPanel.add(bodyPanel, gbc_bodyPanel);
		bodyPanel.setLayout(new GridLayout(1, 2));

		JPanel sellingPropositionPanelLeft = new JPanel();
		bodyPanel.add(sellingPropositionPanelLeft);
		SpringLayout sl_sellingPropositionPanelLeft = new SpringLayout();
		sellingPropositionPanelLeft.setLayout(sl_sellingPropositionPanelLeft);

		// Componentes panel derecho
		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel clientLbl = new JLabel("Cliente:");
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, clientLbl, 26, SpringLayout.NORTH,
				sellingPropositionPanelLeft);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.WEST, clientLbl, 70, SpringLayout.WEST,
				sellingPropositionPanelLeft);
		clientLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		sellingPropositionPanelLeft.add(clientLbl);

		clientsComboBox = new JComboBox<>();
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.WEST, clientsComboBox, 140, SpringLayout.EAST,
				clientLbl);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.EAST, clientsComboBox, -106, SpringLayout.EAST,
				sellingPropositionPanelLeft);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, clientsComboBox, -3, SpringLayout.NORTH,
				clientLbl);
		clientsComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		sellingPropositionPanelLeft.add(clientsComboBox);

		JLabel frameNumberLbl = new JLabel("Número de bastidor:");
		frameNumberLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		sellingPropositionPanelLeft.add(frameNumberLbl);

		frameNumberTxt = new JTextField();
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, frameNumberLbl, 3, SpringLayout.NORTH,
				frameNumberTxt);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.EAST, frameNumberLbl, -52, SpringLayout.WEST,
				frameNumberTxt);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, frameNumberTxt, 26, SpringLayout.SOUTH,
				clientsComboBox);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.WEST, frameNumberTxt, 0, SpringLayout.WEST,
				clientsComboBox);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.EAST, frameNumberTxt, -106, SpringLayout.EAST,
				sellingPropositionPanelLeft);
		frameNumberTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		frameNumberTxt.setColumns(10);
		sellingPropositionPanelLeft.add(frameNumberTxt);

		JLabel priceLbl = new JLabel("Precio:");
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.WEST, priceLbl, 0, SpringLayout.WEST, clientLbl);
		priceLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		sellingPropositionPanelLeft.add(priceLbl);

		priceTxt = new JTextField();
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, priceLbl, 3, SpringLayout.NORTH, priceTxt);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.NORTH, priceTxt, 24, SpringLayout.SOUTH,
				frameNumberTxt);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.WEST, priceTxt, 0, SpringLayout.WEST,
				clientsComboBox);
		sl_sellingPropositionPanelLeft.putConstraint(SpringLayout.EAST, priceTxt, 0, SpringLayout.EAST,
				clientsComboBox);
		priceTxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		priceTxt.setColumns(10);
		sellingPropositionPanelLeft.add(priceTxt);

		JPanel sellingPropositionPanelRight = new JPanel();
		bodyPanel.add(sellingPropositionPanelRight);
		GridBagLayout gbl_sellingPropositionPanelRight = new GridBagLayout();
		gbl_sellingPropositionPanelRight.columnWidths = new int[] { 220, 0, 265, 0 };
		gbl_sellingPropositionPanelRight.rowHeights = new int[] { 173, 0 };
		gbl_sellingPropositionPanelRight.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_sellingPropositionPanelRight.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		sellingPropositionPanelRight.setLayout(gbl_sellingPropositionPanelRight);

		JLabel dateLbl = new JLabel("Válido hasta:");
		dateLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_dateLbl = new GridBagConstraints();
		gbc_dateLbl.insets = new Insets(0, 0, 0, 5);
		gbc_dateLbl.gridx = 0;
		gbc_dateLbl.gridy = 0;
		sellingPropositionPanelRight.add(dateLbl, gbc_dateLbl);

		calendar = new JCalendar();
		GridBagConstraints gbc_calendar = new GridBagConstraints();
		gbc_calendar.anchor = GridBagConstraints.NORTHWEST;
		gbc_calendar.gridx = 2;
		gbc_calendar.gridy = 0;
		calendar.setMinSelectableDate(new Date());
		sellingPropositionPanelRight.add(calendar, gbc_calendar);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_botonPanel = new GridBagConstraints();
		gbc_botonPanel.fill = GridBagConstraints.BOTH;
		gbc_botonPanel.gridx = 0;
		gbc_botonPanel.gridy = 2;
		mainPanel.add(buttonPanel, gbc_botonPanel);
		buttonPanel.setLayout(new FlowLayout(1, 250, 100));

		// Botones
		backMenuBtn = new JButton("Volver al menú");
		backMenuBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backMenuBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
		backMenuBtn.setBackground(new Color(244, 162, 97));
		backMenuBtn.setForeground(Color.WHITE);
		buttonPanel.add(backMenuBtn);

		addPropositionBtn = new JButton("Añadir propuesta");
		addPropositionBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addPropositionBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
		addPropositionBtn.setBackground(new Color(231, 111, 81));
		addPropositionBtn.setForeground(Color.WHITE);
		buttonPanel.add(addPropositionBtn);

	}

	/**
	 * Crea una propuesta de venta con los datos de la vista
	 * 
	 * @param clients Lista
	 * 
	 * @return Objeto propuesta de venta
	 */
	private SellingProposition createProposition(List<Client> clients, List<Vehicle> vehicles) {
		SellingProposition sellingProposition = null;

		// Obtener los datos de la vista y obtener datos de clientes mediante su código
		var selectedClient = clients.stream()
				.filter(client -> clientsComboBox.getSelectedItem().toString()
						.equalsIgnoreCase(client.getNombre() + " " + client.getApellidos()))
				.collect(Collectors.toList());
		var frameNumber = frameNumberTxt.getText();
		var price = priceTxt.getText();

		// Cambiar formato de la fecha introducida
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		var dateString = dateFormat.format(calendar.getDate());

		// Comprobar que existe el número de bastidor introducido
		var frameNumberExist = vehicles.stream()
				.filter(vehicle -> vehicle.getNum_bastidor().equalsIgnoreCase(frameNumber))
				.collect(Collectors.toList());
		if (frameNumberExist.size() == 0) {
			JOptionPane.showMessageDialog(frame, "Error, no existe el número de bastidor introducido", "Warning!",
					JOptionPane.ERROR_MESSAGE);
		} else if (!isNumber(price)) {
			JOptionPane.showMessageDialog(frame, "Error, el precio debe ser un número", "Warning!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			sellingProposition = new SellingProposition(0, selectedClient.get(0).getClientCod(), user.getCod_ventas(),
					frameNumber, dateString, price);
		}

		return sellingProposition;
	}

	/**
	 * Comprueba si el String es un numero
	 * 
	 * @param number String
	 * @return true si es un número, false si no lo es
	 */
	private boolean isNumber(String number) {
		boolean numeric;
		try {
			Integer.parseInt(number);
			numeric = true;
		} catch (NumberFormatException e) {
			numeric = false;
		}
		return numeric;
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

	public JFrame getFrame() {
		return frame;
	}

}
