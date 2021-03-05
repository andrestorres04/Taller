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
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.SellingPropositionDAO;
import model.Sales;
import model.SellingProposition;
import view.LoginView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SalesSearchAndListSellingPropositionView {

	private JFrame frame;
	private JButton btnLogOut;
	private JButton btnBackToMenu;
	private JTextField textFieldSearch;
	private JTable sellingPropositionTable;
	private JButton btnSearch;
	private JButton btnSell;

	private SellingPropositionDAO sellingPropositionDAO;

	private Sales user;

	/**
	 * Crea la aplicación
	 */
	public SalesSearchAndListSellingPropositionView(Sales user) {
		this.user = user;
		sellingPropositionDAO = new SellingPropositionDAO();
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
		var tableModel = (DefaultTableModel) sellingPropositionTable.getModel();
		


		// Al abrir la ventana se rellena la tabla con las propuestas de venta
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				// Insertar los clientes en la tabla clientes
				var propositionList = sellingPropositionDAO.getSellingProposition();
				if (propositionList != null) {
					for (var i = 0; i < propositionList.size(); ++i) {
						tableModel.addRow(new Object[] {
								sellingPropositionDAO.getDni_cliente(propositionList.get(i).getCod_cliente()),
								propositionList.get(i).getCod_propuesta(), propositionList.get(i).getCod_cliente(),
								propositionList.get(i).getCod_ventas(), propositionList.get(i).getNum_bastidor(),
								propositionList.get(i).getFecha_validez(), propositionList.get(i).getPrecio() });
					}
				}
			}
		});

		// Buscar propuesta por dni
		btnSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Reiniciar el contenido de la tabla propuestas
				clearTable(tableModel);

				// Buscar cliente por dni
				var dni = textFieldSearch.getText();
				var propositionResult = sellingPropositionDAO.searchProposition(dni);

				// Insertar el cliente devuelto en la tabla clientes
				if (propositionResult != null) {
					tableModel.addRow(
							new Object[] { sellingPropositionDAO.getDni_cliente(propositionResult.getCod_cliente()),
									propositionResult.getCod_cliente(), propositionResult.getCod_propuesta(),
									propositionResult.getCod_ventas(), propositionResult.getNum_bastidor(),
									propositionResult.getFecha_validez(), propositionResult.getPrecio() });
				} else {
					JOptionPane.showMessageDialog(frame, "Cliente no encontrado, revise el DNI", "Warning!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Confirmar propuesta de venta
		btnSell.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (sellingPropositionTable.getSelectedRow() != -1) {
					// Último aviso
					var lastCheck = JOptionPane.showConfirmDialog(frame,
							"¿Está seguro de que desea completar esta venta? Se avisará al cliente para que pase a recogerlo",
							"¡Atención!", 0, 1);
					// 0 sí, 1 no
					if (lastCheck == 0) {
						var sellingProposition = createSellingProposition(tableModel);
						sellingPropositionDAO.finishSellingProposition(sellingProposition);
						JOptionPane.showMessageDialog(frame, "Venta completada, prepara las llaves el cliente está en camino", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						// Actualizar vista
						new SalesSearchAndListSellingPropositionView(user).getFrame().setVisible(true);
						frame.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Seleccione un elemento de la tabla para finalizar la venta",
							"Warning!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Volver atrás
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
		gbl_mainPanel.rowHeights = new int[] { 47, 52, 73, 383 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.anchor = GridBagConstraints.WEST;
		gbc_headerPanel.fill = GridBagConstraints.VERTICAL;
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		mainPanel.add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 20));

		JPanel headerPanel2 = new JPanel();
		GridBagConstraints gbc_headerPanel2 = new GridBagConstraints();
		gbc_headerPanel2.anchor = GridBagConstraints.WEST;
		gbc_headerPanel2.fill = GridBagConstraints.BOTH;
		gbc_headerPanel2.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel2.gridx = 0;
		gbc_headerPanel2.gridy = 1;
		mainPanel.add(headerPanel2, gbc_headerPanel2);
		headerPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 0));

		btnBackToMenu = new JButton("Volver al menú");
		btnBackToMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBackToMenu.setForeground(Color.WHITE);
		btnBackToMenu.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnBackToMenu.setBackground(new Color(244, 162, 97));
		headerPanel.add(btnBackToMenu);

		JLabel lblMainMenu = new JLabel("Listado de propuestas de ventas");
		lblMainMenu.setFont(new Font("SansSerif", Font.BOLD, 25));
		headerPanel2.add(lblMainMenu);

		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_searchPanel = new GridBagConstraints();
		gbc_searchPanel.fill = GridBagConstraints.BOTH;
		gbc_searchPanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchPanel.gridx = 0;
		gbc_searchPanel.gridy = 2;
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

		btnSearch = new JButton("Buscar propuesta de venta");
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
		gbc_listPanel.gridy = 3;
		mainPanel.add(listPanel, gbc_listPanel);
		listPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

		sellingPropositionTable = new JTable();
		sellingPropositionTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		sellingPropositionTable.getTableHeader().setForeground(Color.WHITE);
		sellingPropositionTable.getTableHeader().setBackground(new Color(244, 162, 97));
		sellingPropositionTable.setPreferredScrollableViewportSize(new Dimension(950, 400));
		sellingPropositionTable.setFont(new Font("SansSerif", Font.BOLD, 15));
		sellingPropositionTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "DNI Cliente",
				"Código propuesta", "Código cliente", "Código venta", "Número de bastidor", "Fecha de validez", "Precio" }) {
					private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class, String.class,
					String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		sellingPropositionTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		sellingPropositionTable.getColumnModel().getColumn(0).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		sellingPropositionTable.getColumnModel().getColumn(1).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		sellingPropositionTable.getColumnModel().getColumn(2).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		sellingPropositionTable.getColumnModel().getColumn(3).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		sellingPropositionTable.getColumnModel().getColumn(4).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		sellingPropositionTable.getColumnModel().getColumn(5).setMaxWidth(555);
		sellingPropositionTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		sellingPropositionTable.getColumnModel().getColumn(6).setMaxWidth(555);

		btnSell = new JButton("Confirmar venta");
		btnSell.setForeground(Color.WHITE);
		btnSell.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSell.setBorderPainted(false);
		btnSell.setBackground(new Color(231, 111, 81));
		listPanel.add(btnSell);

		JScrollPane tableScrollPane = new JScrollPane(sellingPropositionTable);
		tableScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableScrollPane.setPreferredSize(new Dimension(1000, 402));
		tableScrollPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		listPanel.add(tableScrollPane);

	}

	/**
	 * Crea una propuesta de venta con los datos de la vista
	 * 
	 * @param tableModel
	 * @return
	 */
	private SellingProposition createSellingProposition(DefaultTableModel tableModel) {
		
		// Asignación de valores de los datos de la vista
		var propositionId = (int) tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 1);
		var clientId = (int) tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 2);
		var salesId = (int) tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 3);
		var frameNumber = String.valueOf(tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 4));
		var validDate = String.valueOf(tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 5));
		var price = String.valueOf(tableModel.getValueAt(sellingPropositionTable.getSelectedRow(), 6));
		
		return new SellingProposition(propositionId, clientId, salesId, frameNumber, validDate, price);
	}
	
	private void clearTable(DefaultTableModel tableModel) {
		var rows = sellingPropositionTable.getRowCount();
		// Si hay filas las elimina
		if (rows > 0) {
			for (var i = (rows - 1); i > -1; --i) {
				tableModel.removeRow(i);
			}
		}
	}

	public JFrame getFrame() {
		return frame;
	}

}
