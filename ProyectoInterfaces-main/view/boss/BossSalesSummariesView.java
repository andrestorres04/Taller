package view.boss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import dao.VehicleDAO;
import model.Boss;
import view.LoginView;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BossSalesSummariesView {

	private Boss user;
	private JFrame frame;
	
	private JTextField filtertxt;
	private JLabel lbltotal;
	
	private JButton btnLogOut;
	private JButton backButton;
	private JButton btnSearch;
	
	private JTable salesSummariesTable;
	
	private VehicleDAO vehicleDAO;


	/**
	 * Create the application.
	 */
	public BossSalesSummariesView(Boss user) {
		this.user = user;
		initialize();
		vehicleDAO = new VehicleDAO();
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

	private void setControllers() {
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
				
				var tableModel = (DefaultTableModel) salesSummariesTable.getModel();
				
				
				frame.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						if (vehicleDAO.getVehiclesSold() != null) {
							for (var i = 0; i < vehicleDAO.getVehiclesSold().size(); ++i) {
								tableModel.addRow(new Object[] { vehicleDAO.getVehiclesSold().get(i).getCod_empleado(), vehicleDAO.getVehiclesSold().get(i).getNombre(),
										vehicleDAO.getVehiclesSold().get(i).getApellidos(), vehicleDAO.getVehiclesSold().get(i).getMarca(),
										vehicleDAO.getVehiclesSold().get(i).getModelo(), vehicleDAO.getVehiclesSold().get(i).getPrecio(), vehicleDAO.getVehiclesSold().get(i).getTipoVehiculo()});
								
							}
							lbltotal.setText(totalVentas().toString()+"€");
						}
					}
				});
				
				btnSearch.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// Reiniciar el contenido de la tabla clientes
						var rows = salesSummariesTable.getRowCount();
						// Si hay filas las elimina
						if (rows > 0) {
							for (var i = (rows - 1); i > -1; --i) {
								tableModel.removeRow(i);
							}
						}

						// Buscar vehiculo vendidp por dni empleado
						var dni = filtertxt.getText();

						// Insertar el cliente devuelto en la tabla clientes
						if (vehicleDAO.searchVehicleByDni(dni) != null) {
							for (var i = 0; i < vehicleDAO.searchVehicleByDni(dni).size(); ++i) {
								tableModel.addRow(new Object[] { vehicleDAO.searchVehicleByDni(dni).get(i).getCod_empleado(), vehicleDAO.searchVehicleByDni(dni).get(i).getNombre(),
										vehicleDAO.searchVehicleByDni(dni).get(i).getApellidos(), vehicleDAO.searchVehicleByDni(dni).get(i).getMarca(),
										vehicleDAO.searchVehicleByDni(dni).get(i).getModelo(), vehicleDAO.searchVehicleByDni(dni).get(i).getPrecio(), vehicleDAO.searchVehicleByDni(dni).get(i).getTipoVehiculo()});
								
							}
							lbltotal.setText(totalVentas().toString()+"€");
						} else {
							JOptionPane.showMessageDialog(frame, "Empleado no encontrado, revise el DNI", "Warning!",
							JOptionPane.ERROR_MESSAGE);
						}
						
					}
					
				});
				
				
				
	}

	private void setUIComponents() {
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
		gbl_mainPanel.rowHeights = new int[]{106, 73, 409, 93};
		gbl_mainPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		mainPanel.setLayout(gbl_mainPanel);
				
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20 ,20));
		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.fill = GridBagConstraints.BOTH;
		gbc_panel1.insets = new Insets(0, 0, 5, 0);
		gbc_panel1.gridx = 0;
		gbc_panel1.gridy = 0;
		mainPanel.add(panel1, gbc_panel1);
				
		JLabel vehiclesLbl = new JLabel("Resúmenes de ventas");
		vehiclesLbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		panel1.add(vehiclesLbl);
		
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 30));
		GridBagConstraints gbc_filterPanel = new GridBagConstraints();
		gbc_filterPanel.fill = GridBagConstraints.BOTH;
		gbc_filterPanel.insets = new Insets(0, 0, 5, 0);
		gbc_filterPanel.gridx = 0;
		gbc_filterPanel.gridy = 1;
		mainPanel.add(filterPanel, gbc_filterPanel);
		
		JLabel filterlbl = new JLabel("Filtrar por DNI:");
		filterlbl.setFont(new Font("SansSerif", Font.BOLD, 18));
		filterPanel.add(filterlbl);
		
		filtertxt = new JTextField();
		filtertxt.setColumns(10);
		filtertxt.setFont(new Font("SansSerif", Font.PLAIN, 15));
		filterPanel.add(filtertxt);
		
		btnSearch = new JButton("Buscar empleado");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSearch.setBorderPainted(false);
		btnSearch.setBackground(new Color(231, 111, 81));
		filterPanel.add(btnSearch);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20 ,20));
		GridBagConstraints gbc_tablePanel = new GridBagConstraints();
		gbc_tablePanel.fill = GridBagConstraints.BOTH;
		gbc_tablePanel.gridx = 0;
		gbc_tablePanel.gridy = 2;
		mainPanel.add(tablePanel, gbc_tablePanel);
		
		salesSummariesTable = new JTable();
		salesSummariesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		salesSummariesTable.getTableHeader().setForeground(Color.WHITE);
		salesSummariesTable.getTableHeader().setBackground(new Color(244, 162, 97));
		salesSummariesTable.setPreferredScrollableViewportSize(new Dimension(950, 400));
		salesSummariesTable.setFont(new Font("SansSerif", Font.BOLD, 15));
		salesSummariesTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] {"Código empleado", "Nombre", "Apellidos", "Marca", "Modelo", "Precio", "Tipo"}) {
					private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class, String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		salesSummariesTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		salesSummariesTable.getColumnModel().getColumn(0).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		salesSummariesTable.getColumnModel().getColumn(1).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		salesSummariesTable.getColumnModel().getColumn(2).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		salesSummariesTable.getColumnModel().getColumn(3).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		salesSummariesTable.getColumnModel().getColumn(4).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(5).setPreferredWidth(80);
		salesSummariesTable.getColumnModel().getColumn(5).setMaxWidth(555);
		salesSummariesTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		salesSummariesTable.getColumnModel().getColumn(6).setMaxWidth(555);
		
		JScrollPane tableScrollPane = new JScrollPane(salesSummariesTable);
		tableScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableScrollPane.setPreferredSize(new Dimension(1000, 402));
		tableScrollPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tablePanel.add(tableScrollPane);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(0, 2));
		GridBagConstraints gbc_panel3 = new GridBagConstraints();
		gbc_panel3.fill = GridBagConstraints.BOTH;
		gbc_panel3.gridx = 0;
		gbc_panel3.gridy = 3;
		mainPanel.add(panel3, gbc_panel3);
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		panel3.add(panel4);
		
		backButton = new JButton("Volver");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setForeground(Color.WHITE);
		backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		backButton.setBackground(new Color(244, 162, 97));
		panel4.add(backButton);
		
		JPanel panel5 = new JPanel();
		panel5.setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 20));
		panel3.add(panel5);
		
		JLabel total = new JLabel("Total ventas:");
		total.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel5.add(total);
		
		lbltotal = new JLabel();
		lbltotal.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel5.add(lbltotal);
		
	}
	
	public Integer totalVentas() {
		int total = 0;
		int valor = 0;
		if(salesSummariesTable.getRowCount() > 0) {
			for(int i = 0; i<salesSummariesTable.getRowCount(); i++) {
			valor = Integer.parseInt(salesSummariesTable.getValueAt(i, 5).toString());
			total += valor;
			}
		}
		return total;
	}

	public JFrame getFrame() {
		return frame;
	}

}
