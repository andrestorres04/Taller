package view.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import dao.ClientDAO;
import dao.VehicleDAO;

import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SalesClientDetailsView {

	private JFrame frame;
	private JButton backButton;
	private JLabel lblCodResult;
	private JLabel lblNameResult;
	private JLabel lblSurnameResult;
	private JLabel lblDniResult;
	private JLabel lblTelephoneResult;
	private JTable clientTable;
	private ClientDAO clientDAO;
	private VehicleDAO vehicleDAO;
	private String Dni;

	/**
	 * Create the application.
	 */
	public SalesClientDetailsView(String dni) {
		clientDAO = new ClientDAO();
		vehicleDAO = new VehicleDAO();
		this.Dni = dni;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 735, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUIComponents();
		setControllers();

	}

	/**
	 * Contiene los controladores
	 */
	private void setControllers() {
		var tableModel = (DefaultTableModel) clientTable.getModel();
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				var cliente = clientDAO.searchClient(Dni);
				lblCodResult.setText("" + cliente.getClientCod());
				lblNameResult.setText(cliente.getNombre());
				lblSurnameResult.setText(cliente.getApellidos());
				lblDniResult.setText(cliente.getDni());
				lblTelephoneResult.setText(cliente.getTelefono());

				// Rellenar tabla si existen vehículos del cliente
				var clienList = vehicleDAO.getVehicleByClient(Dni);
				if (clienList != null) {
					for (var i = 0; i < clienList.size(); ++i) {
						tableModel.addRow(new Object[] { clienList.get(i).getNum_bastidor(),
								clienList.get(i).getMarca(), clienList.get(i).getModelo() });
					}
				}
			}
		});

		// Botón cerrar
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
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

		JLabel lblSpace = new JLabel(" ");
		lblSpace.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bottomPanel.add(lblSpace);

		/*
		 * mainPanel. Dentro se crean otros paneles para añadir los distintos
		 * componentes.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_mainPanel.rowHeights = new int[] { 64, 262, 50 };
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
		JLabel lblClientData = new JLabel("Ficha del cliente");
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
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblCod, 23, SpringLayout.NORTH, clientDataPanelLeft);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblCod, 10, SpringLayout.WEST, clientDataPanelLeft);
		lblCod.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelLeft.add(lblCod);

		lblCodResult = new JLabel("");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblCodResult, 0, SpringLayout.NORTH, lblCod);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblCodResult, 30, SpringLayout.EAST, lblCod);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.EAST, lblCodResult, 167, SpringLayout.EAST, lblCod);
		lblCodResult.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblCodResult);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblNombreCliente = new JLabel("Nombre:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblNombreCliente, 26, SpringLayout.SOUTH, lblCod);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblNombreCliente, 0, SpringLayout.WEST, lblCod);
		lblNombreCliente.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelLeft.add(lblNombreCliente);

		lblNameResult = new JLabel("");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblNameResult, 0, SpringLayout.NORTH,
				lblNombreCliente);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblNameResult, 0, SpringLayout.WEST, lblCodResult);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.EAST, lblNameResult, 0, SpringLayout.EAST, lblCodResult);
		lblNameResult.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblNameResult);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblSurnames = new JLabel("Apellidos:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblSurnames, 26, SpringLayout.SOUTH,
				lblNombreCliente);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblSurnames, 0, SpringLayout.WEST, lblCod);
		lblSurnames.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelLeft.add(lblSurnames);

		lblSurnameResult = new JLabel("");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblSurnameResult, 0, SpringLayout.NORTH,
				lblSurnames);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblSurnameResult, 0, SpringLayout.WEST, lblCodResult);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.EAST, lblSurnameResult, 0, SpringLayout.EAST, lblCodResult);
		lblSurnameResult.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblSurnameResult);

		JLabel lblDni = new JLabel("DNI:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblDni, 26, SpringLayout.SOUTH, lblSurnames);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblDni, 0, SpringLayout.WEST, lblCod);
		lblDni.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelLeft.add(lblDni);

		lblDniResult = new JLabel("");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblDniResult, 0, SpringLayout.NORTH, lblDni);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblDniResult, 0, SpringLayout.WEST, lblCodResult);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.EAST, lblDniResult, 0, SpringLayout.EAST, lblCodResult);
		lblDniResult.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblDniResult);

		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblTelephone = new JLabel("Teléfono:");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblTelephone, 26, SpringLayout.SOUTH, lblDni);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblTelephone, 0, SpringLayout.WEST, lblCod);
		lblTelephone.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelLeft.add(lblTelephone);

		lblTelephoneResult = new JLabel("");
		sl_datosClientePanelLeft.putConstraint(SpringLayout.NORTH, lblTelephoneResult, 0, SpringLayout.NORTH,
				lblTelephone);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.WEST, lblTelephoneResult, 0, SpringLayout.WEST,
				lblCodResult);
		sl_datosClientePanelLeft.putConstraint(SpringLayout.EAST, lblTelephoneResult, 0, SpringLayout.EAST,
				lblCodResult);
		lblTelephoneResult.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelLeft.add(lblTelephoneResult);

		JPanel clientDataPanelRight = new JPanel();
		clientDataPanel.add(clientDataPanelRight);

		JLabel lblVehiclesClient = new JLabel("Vehículos del cliente");
		lblVehiclesClient.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientDataPanelRight.add(lblVehiclesClient);

		clientTable = new JTable();
		clientTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		clientTable.getTableHeader().setForeground(Color.WHITE);
		clientTable.getTableHeader().setBackground(new Color(244, 162, 97));
		clientTable.setPreferredScrollableViewportSize(new Dimension(950, 400));
		clientTable.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientTable.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Número bastidor", "Marca", "Modelo" }) {
					private static final long serialVersionUID = 1L;
					Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class,
							String.class };

					public Class<?> getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		clientTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		clientTable.getColumnModel().getColumn(0).setMaxWidth(150);
		clientTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		clientTable.getColumnModel().getColumn(1).setMaxWidth(100);
		clientTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		clientTable.getColumnModel().getColumn(2).setMaxWidth(100);

		JScrollPane tableScrollPane = new JScrollPane(clientTable);
		tableScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableScrollPane.setPreferredSize(new Dimension(325, 200));
		tableScrollPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		clientDataPanelRight.add(tableScrollPane);

		JPanel bottonPanel = new JPanel();
		GridBagConstraints gbc_botonPanel = new GridBagConstraints();
		gbc_botonPanel.fill = GridBagConstraints.BOTH;
		gbc_botonPanel.gridx = 0;
		gbc_botonPanel.gridy = 2;
		mainPanel.add(bottonPanel, gbc_botonPanel);
		bottonPanel.setLayout(new FlowLayout(1, 300, 50));

		// Botones
		backButton = new JButton("Volver");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		backButton.setBackground(new Color(244, 162, 97));
		backButton.setForeground(Color.WHITE);
		bottonPanel.add(backButton);

	}

	public JFrame getFrame() {
		return frame;
	}

}
