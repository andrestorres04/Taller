package view.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import model.Sales;
import view.LoginView;
import view.mechanical.MechanicalLandingView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SalesLandingView {

	private JFrame frame;
	private JButton btnLogOut;
	private JLabel lblUser;
	private JComboBox<Object> customerComboBox;
	private JComboBox<Object> vehicleComboBox;
	private JComboBox<Object> salesComboBox;
	
	private Sales user; // TODO: mostrar usuario en la vista

	/**
	 * Crea la aplicación
	 */
	public SalesLandingView(Sales user) {
		this.user = user;
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
		// Al abrir la ventana cargan los datos de usuario
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				lblUser.setText("Bienvenido/a: " + user.getNombre() + " " + user.getApellidos());
			}
		});
		
		
		
		// Combobox de clientes
		customerComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (customerComboBox.getSelectedItem().toString().equalsIgnoreCase("Alta cliente")) {
					new SalesAddClientView(user).getFrame().setVisible(true);
					frame.dispose();
				}
				if (customerComboBox.getSelectedItem().toString().equalsIgnoreCase("Listar cliente")) {
					new SalesSearchAndListClientView(user).getFrame().setVisible(true);
					frame.dispose();
				}
			}
		});

		// Combobox de vehículos
		vehicleComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (vehicleComboBox.getSelectedItem().toString().equalsIgnoreCase("Alta vehículo")) {
					new SalesAddVehicleView(user).getFrame().setVisible(true);
					frame.dispose();
				}
				if (vehicleComboBox.getSelectedItem().toString().equalsIgnoreCase("Listar vehículo")) {
					new SalesSearchAndListVehiclesView(user).getFrame().setVisible(true);
					frame.dispose();
				}
			}
		});

		// Combobox de propuesta de venta
		salesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (salesComboBox.getSelectedItem().toString().equalsIgnoreCase("Añadir propuesta")) {
					new SalesAddSellingPropositionView(user).getFrame().setVisible(true);
					frame.dispose();
				}
				if (salesComboBox.getSelectedItem().toString().equalsIgnoreCase("Listar propuestas")) {
					new SalesSearchAndListSellingPropositionView(user).getFrame().setVisible(true);
					frame.dispose();
				}
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

		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setBackground(new Color(233, 196, 106));
		topPanel.setBounds(100, 100, 100, 100);
		
		JLabel lblIconUser = new JLabel("");
		lblIconUser.setIcon(new ImageIcon(MechanicalLandingView.class.getResource("/assets/img/icon_user.png")));
		lblIconUser.setAlignmentY(0.0f);
		lblIconUser.setLocation(5, 0);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		topPanel.add(lblIconUser);
		
		lblUser = new JLabel("");
		lblUser.setFont(new Font("SansSerif", Font.BOLD, 15));
		topPanel.add(lblUser);

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
		mainPanel.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel headerPanel = new JPanel();
		mainPanel.add(headerPanel);

		JLabel lblNewLabel = new JLabel("Menú Principal");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		headerPanel.add(lblNewLabel);

		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		mainPanel.add(customerPanel);
		customerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 80));

		JLabel lblCustomers = new JLabel("Gestión de clientes");
		lblCustomers.setFont(new Font("SansSerif", Font.BOLD, 18));
		customerPanel.add(lblCustomers);

		customerComboBox = new JComboBox<>();
		customerComboBox
				.setModel(new DefaultComboBoxModel<>(new String[] { "Elige una", "Alta cliente", "Listar cliente" }));
		customerComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		customerPanel.add(customerComboBox);

		JPanel vehiclesPanel = new JPanel();
		vehiclesPanel.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		mainPanel.add(vehiclesPanel);
		vehiclesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 80));

		JLabel lblGestinDeVehculos = new JLabel("Gestión de vehículos");
		lblGestinDeVehculos.setFont(new Font("SansSerif", Font.BOLD, 18));
		vehiclesPanel.add(lblGestinDeVehculos);

		vehicleComboBox = new JComboBox<>();
		vehicleComboBox
				.setModel(new DefaultComboBoxModel<>(new String[] { "Elige una", "Alta vehículo", "Listar vehículo" }));
		vehicleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		vehiclesPanel.add(vehicleComboBox);

		JPanel salesPanel = new JPanel();
		salesPanel.setBorder(new MatteBorder(1, 1, 0, 0, (Color) new Color(0, 0, 0)));
		mainPanel.add(salesPanel);
		salesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 80));

		JLabel lblSales = new JLabel("Propuestas de venta");
		lblSales.setFont(new Font("SansSerif", Font.BOLD, 18));
		salesPanel.add(lblSales);

		salesComboBox = new JComboBox<>();
		salesComboBox.setModel(
				new DefaultComboBoxModel<>(new String[] { "Elige una", "Añadir propuesta", "Listar propuestas" }));
		salesComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		salesPanel.add(salesComboBox);

	}

	public JFrame getFrame() {
		return frame;
	}

}
