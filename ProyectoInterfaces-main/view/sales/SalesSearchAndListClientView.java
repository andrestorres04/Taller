package view.sales;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ClientDAO;
import model.Sales;
import view.LoginView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.Cursor;

public class SalesSearchAndListClientView {

	private JFrame frame;
	private JButton btnLogOut;
	private JButton btnBackToMenu;
	private JTextField textFieldSearch;
	private JTable clientTable;
	private JButton btnSearch;
	private JButton btnDetallesDelCliente;
	
	private ClientDAO clientDAO;

	private Sales user;
	
	/**
	 * Crea la aplicación
	 */
	public SalesSearchAndListClientView(Sales user) {
		this.user = user;
		clientDAO = new ClientDAO();
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
		var tableModel = (DefaultTableModel) clientTable.getModel();
		
		// Obtener datos del DAO
		var clients = clientDAO.getClients();
		
		// Al abrir la ventana se rellena la tabla con TODOS los clientes
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				// Insertar los clientes en la tabla clientes
				if (clients != null) {
					for (var i = 0; i < clients.size(); ++i) {
						var tableModel = (DefaultTableModel) clientTable.getModel();
						tableModel.addRow(new Object[] { clients.get(i).getClientCod(), clients.get(i).getDni(),
								clients.get(i).getNombre(), clients.get(i).getApellidos(),
								clients.get(i).getTelefono() });
					}
				}
			}
		});

		// Botón buscar cliente
		btnSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Reiniciar el contenido de la tabla clientes
				var rows = clientTable.getRowCount();
				// Si hay filas las elimina
				if (rows > 0) {
					for (var i = (rows - 1); i > -1; --i) {
						tableModel.removeRow(i);
					}
				}

				// Buscar cliente por dni
				var dni = textFieldSearch.getText();
				var clientResult = clientDAO.searchClient(dni);

				// Insertar el cliente devuelto en la tabla clientes
				if (clientResult != null) {
					tableModel.addRow(new Object[] { clientResult.getClientCod(), clientResult.getDni(),
							clientResult.getNombre(), clientResult.getApellidos(), clientResult.getTelefono() });
				} else {
					JOptionPane.showMessageDialog(frame, "Cliente no encontrado, revise el DNI", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		// Botón ver detalles del cliente
		btnDetallesDelCliente.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(clientTable.getSelectedRow() != -1) {
					new SalesClientDetailsView(String.valueOf(tableModel.getValueAt(clientTable.getSelectedRow(), 1))).getFrame().setVisible(true);
				} else {
					JOptionPane.showMessageDialog(frame, "Haga clic en un cliente de la tabla para ver más detalles", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Volver al menú principal
		btnBackToMenu.addMouseListener(new MouseAdapter() {
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
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
		gbl_mainPanel.columnWidths = new int[] { 1063, 0 };
		gbl_mainPanel.rowHeights = new int[] { 145, 110, 420, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		mainPanel.add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 20));

		btnBackToMenu = new JButton("Volver al menú");
		btnBackToMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBackToMenu.setForeground(Color.WHITE);
		btnBackToMenu.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnBackToMenu.setBackground(new Color(244, 162, 97));
		headerPanel.add(btnBackToMenu);

		JLabel lblMainMenu = new JLabel("Listado de clientes");
		lblMainMenu.setFont(new Font("SansSerif", Font.BOLD, 25));
		headerPanel.add(lblMainMenu);

		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_searchPanel = new GridBagConstraints();
		gbc_searchPanel.fill = GridBagConstraints.BOTH;
		gbc_searchPanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchPanel.gridx = 0;
		gbc_searchPanel.gridy = 1;
		mainPanel.add(searchPanel, gbc_searchPanel);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 30));

		JLabel lblSearch = new JLabel("DNI");
		lblSearch.setFont(new Font("SansSerif", Font.BOLD, 18));
		searchPanel.add(lblSearch);

		textFieldSearch = new JTextField();
		textFieldSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
		textFieldSearch.setText("99999999X");
		searchPanel.add(textFieldSearch);
		textFieldSearch.setColumns(20);

		btnSearch = new JButton("Buscar cliente");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSearch.setBorderPainted(false);
		btnSearch.setBackground(new Color(231, 111, 81));
		searchPanel.add(btnSearch);

		JPanel listPanel = new JPanel();
		listPanel.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_listPanel = new GridBagConstraints();
		gbc_listPanel.fill = GridBagConstraints.BOTH;
		gbc_listPanel.gridx = 0;
		gbc_listPanel.gridy = 2;
		mainPanel.add(listPanel, gbc_listPanel);
		listPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

		clientTable = new JTable();
		clientTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		clientTable.getTableHeader().setForeground(Color.WHITE);
		clientTable.getTableHeader().setBackground(new Color(244, 162, 97));
		clientTable.setPreferredScrollableViewportSize(new Dimension(950, 400));
		clientTable.setFont(new Font("SansSerif", Font.BOLD, 15));
		clientTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "C\u00F3digo", "DNI", "Nombre", "Apellidos", "Tel\u00E9fono" }) {
					private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		clientTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		clientTable.getColumnModel().getColumn(0).setMaxWidth(555);
		clientTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		clientTable.getColumnModel().getColumn(1).setMaxWidth(555);
		clientTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		clientTable.getColumnModel().getColumn(2).setMaxWidth(555);
		clientTable.getColumnModel().getColumn(3).setPreferredWidth(280);
		clientTable.getColumnModel().getColumn(3).setMaxWidth(555);
		clientTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		clientTable.getColumnModel().getColumn(4).setMaxWidth(555);
		
		btnDetallesDelCliente = new JButton("Detalles cliente");
		btnDetallesDelCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDetallesDelCliente.setForeground(Color.WHITE);
		btnDetallesDelCliente.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDetallesDelCliente.setBorderPainted(false);
		btnDetallesDelCliente.setBackground(new Color(231, 111, 81));
		listPanel.add(btnDetallesDelCliente);

		JScrollPane tableScrollPane = new JScrollPane(clientTable);
		tableScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableScrollPane.setPreferredSize(new Dimension(1000, 402));
		tableScrollPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		listPanel.add(tableScrollPane);

	}

	public JFrame getFrame() {
		return frame;
	}

}
