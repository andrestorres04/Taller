package view.boss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import dao.VehicleDAO;
import model.Boss;
import view.LoginView;

public class BossDetailsUnsoldVehiclesView {

	private JFrame frame;
	private Boss user;
	
	private JButton btnLogOut;
	private JButton btnVolver;
	
	private JTable vehiclesUnSoldTable; 
	
	private VehicleDAO vehicleDAO;


	/**
	 * Create the application.
	 */
	public BossDetailsUnsoldVehiclesView(Boss user) {
		this.user = user;
		vehicleDAO = new VehicleDAO();
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
	
	private void setControllers() {
		// Volver al login
		btnLogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new LoginView().getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossLandingView(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		var tableModel = (DefaultTableModel) vehiclesUnSoldTable.getModel();
		
		//var onSaleVehicles = vehicleDAO.getVehiclesUnsold();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				// Insertar los coches (por defecto) en la tabla
				if (vehicleDAO.getVehiclesUnsold() != null) {
					for (var i = 0; i < vehicleDAO.getVehiclesUnsold().size(); ++i) {
						tableModel.addRow(new Object[] { vehicleDAO.getVehiclesUnsold().get(i).getTipoVehiculo(), vehicleDAO.getVehiclesUnsold().get(i).getNum_bastidor(),
								vehicleDAO.getVehiclesUnsold().get(i).getMarca(), vehicleDAO.getVehiclesUnsold().get(i).getModelo(),
								vehicleDAO.getVehiclesUnsold().get(i).getCombustible(), vehicleDAO.getVehiclesUnsold().get(i).getPrecio()});
					}
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
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20 ,50));
		
		JLabel vehiclesLbl = new JLabel("Vehículos sin vender");
		vehiclesLbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		mainPanel.add(vehiclesLbl);

		vehiclesUnSoldTable = new JTable();
		vehiclesUnSoldTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		vehiclesUnSoldTable.getTableHeader().setForeground(Color.WHITE);
		vehiclesUnSoldTable.getTableHeader().setBackground(new Color(244, 162, 97));
		vehiclesUnSoldTable.setPreferredScrollableViewportSize(new Dimension(950, 400));
		vehiclesUnSoldTable.setFont(new Font("SansSerif", Font.BOLD, 15));
		vehiclesUnSoldTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] {"Tipo de vehículo", "Número de bastidor", "Marca", "Modelo", "Combustible", "Precio"}) {
					private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		vehiclesUnSoldTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		vehiclesUnSoldTable.getColumnModel().getColumn(0).setMaxWidth(555);
		vehiclesUnSoldTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		vehiclesUnSoldTable.getColumnModel().getColumn(1).setMaxWidth(555);
		vehiclesUnSoldTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		vehiclesUnSoldTable.getColumnModel().getColumn(2).setMaxWidth(555);
		vehiclesUnSoldTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		vehiclesUnSoldTable.getColumnModel().getColumn(3).setMaxWidth(555);
		vehiclesUnSoldTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		vehiclesUnSoldTable.getColumnModel().getColumn(4).setMaxWidth(555);
		vehiclesUnSoldTable.getColumnModel().getColumn(5).setPreferredWidth(80);
		vehiclesUnSoldTable.getColumnModel().getColumn(5).setMaxWidth(555);
		
		JScrollPane tableScrollPane = new JScrollPane(vehiclesUnSoldTable);
		tableScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableScrollPane.setPreferredSize(new Dimension(1000, 402));
		tableScrollPane.setFont(new Font("SansSerif", Font.PLAIN, 15));
		mainPanel.add(tableScrollPane);
		
		btnVolver = new JButton("Volver");
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnVolver.setBackground(new Color(244, 162, 97));
		mainPanel.add(btnVolver);
		
	}
	
	
	public JFrame getFrame() {
		return frame;
	}

}
