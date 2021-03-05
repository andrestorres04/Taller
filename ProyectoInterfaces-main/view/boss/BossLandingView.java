package view.boss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import dao.VehicleDAO;
import model.Boss;
import view.LoginView;
import view.mechanical.MechanicalLandingView;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BossLandingView {

	private JFrame frame;
	private JButton btnLogOut;
	private JButton detallesButton;
	private JButton detallesbtn;
	private JButton newdetallesbtn;
	private JButton registrarbtn;
	
	private JTextField txtTotal;
	private JTextField txtStock;
	
	private JLabel lblUser;

	private VehicleDAO vehicleDAO;

	private Boss user;

	/**
	 * Create the application.
	 */
	public BossLandingView(Boss user) {
		vehicleDAO = new VehicleDAO();
		this.user = user;
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
		// Al abrir la ventana se cargan los datos del usuario
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				// Cargar datos de usuario
				lblUser.setText("Bienvenido/a " + user.getNombre() + " " + user.getApellidos());
			}
		});

		// Volver al login
		btnLogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new LoginView().getFrame().setVisible(true);
				frame.dispose();
			}
		});

		// Boton detalles vehiculos sin vender
		detallesButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossDetailsUnsoldVehiclesView(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});

		// Boton detalles ventas
		detallesbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossSalesSummariesView(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});

		// Boton recaudaciones totales
		newdetallesbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossTotalCollectionsView(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});

		// Boton nuevo usuario
		registrarbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BossAddNewUser(user).getFrame().setVisible(true);
				frame.dispose();
			}
		});

		// Mostrar el número de vehiculos que no se han vendido en el JTextField
		txtStock.setText(vehicleDAO.getCountVehiclesUnsold().toString());
		txtStock.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Volver al login
		btnLogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new LoginView().getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		int total = 0;
		
		for(int i = 0; i<vehicleDAO.getVehiclesSold().size(); i++) {
			total += Integer.parseInt(vehicleDAO.getVehiclesSold().get(i).getPrecio());
		}
		
		txtTotal.setText(total+"€");
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void setUIComponents() {
		frame.setTitle("Jefe");
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
		mainPanel.setLayout(new GridLayout(5, 0, 0, 0));

		// Panel 1
		JPanel labelPanel = new JPanel();
		mainPanel.add(labelPanel);
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

		// Label panel 1
		JLabel lblmenu = new JLabel("Menú Principal");
		lblmenu.setFont(new Font("SansSerif", Font.BOLD, 25));
		labelPanel.add(lblmenu);

		// Panel 2
		JPanel stockPanel = new JPanel();
		mainPanel.add(stockPanel);
		SpringLayout sl_stockPanel = new SpringLayout();
		stockPanel.setLayout(sl_stockPanel);

		// Label panel 2
		JLabel lblstock = new JLabel("Stock de vehículos sin vender");
		lblstock.setFont(new Font("SansSerif", Font.PLAIN, 18));

		// TextField panel 2
		txtStock = new JTextField();
		sl_stockPanel.putConstraint(SpringLayout.NORTH, lblstock, 3, SpringLayout.NORTH, txtStock);
		sl_stockPanel.putConstraint(SpringLayout.EAST, lblstock, -34, SpringLayout.WEST, txtStock);
		sl_stockPanel.putConstraint(SpringLayout.NORTH, txtStock, 34, SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.EAST, txtStock, -449, SpringLayout.EAST, stockPanel);
		txtStock.setFont(new Font("SansSerif", Font.PLAIN, 18));
		txtStock.setEditable(false);
		txtStock.setPreferredSize(new Dimension(30, 30));

		// Button panel 2
		detallesButton = new JButton("Detalles");
		detallesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sl_stockPanel.putConstraint(SpringLayout.NORTH, detallesButton, 33, SpringLayout.NORTH, stockPanel);
		sl_stockPanel.putConstraint(SpringLayout.WEST, detallesButton, 31, SpringLayout.EAST, txtStock);
		detallesButton.setForeground(Color.WHITE);
		detallesButton.setBorderPainted(false);
		detallesButton.setBackground(new Color(231, 111, 81));
		detallesButton.setFont(new Font("SansSerif", Font.PLAIN, 18));

		stockPanel.add(lblstock);
		stockPanel.add(txtStock);
		stockPanel.add(detallesButton);

		// Panel 3
		JPanel resumenPanel = new JPanel();
		mainPanel.add(resumenPanel);
		SpringLayout sl_resumenPanel = new SpringLayout();
		resumenPanel.setLayout(sl_resumenPanel);

		// Label panel 3
		JLabel resumenLbl = new JLabel("Resúmenes de ventas");
		resumenLbl.setFont(new Font("SansSerif", Font.PLAIN, 18));

		// Button Panel 3
		detallesbtn = new JButton("Detalles");
		sl_resumenPanel.putConstraint(SpringLayout.NORTH, detallesbtn, 33, SpringLayout.NORTH, resumenPanel);
		sl_resumenPanel.putConstraint(SpringLayout.WEST, detallesbtn, 643, SpringLayout.WEST, resumenPanel);
		sl_resumenPanel.putConstraint(SpringLayout.NORTH, resumenLbl, 4, SpringLayout.NORTH, detallesbtn);
		sl_resumenPanel.putConstraint(SpringLayout.EAST, resumenLbl, -157, SpringLayout.WEST, detallesbtn);
		detallesbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		detallesbtn.setForeground(Color.WHITE);
		detallesbtn.setBorderPainted(false);
		detallesbtn.setBackground(new Color(231, 111, 81));
		detallesbtn.setFont(new Font("SansSerif", Font.PLAIN, 18));

		resumenPanel.add(resumenLbl);
		resumenPanel.add(detallesbtn);

		// Panel 4
		JPanel recaudacionPanel = new JPanel();
		mainPanel.add(recaudacionPanel);
		SpringLayout sl_recaudacionPanel = new SpringLayout();
		recaudacionPanel.setLayout(sl_recaudacionPanel);

		// Label panel 4
		JLabel lblTotal = new JLabel("Total recaudado");
		lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 18));

		// TextField panel 4
		txtTotal = new JTextField();
		sl_recaudacionPanel.putConstraint(SpringLayout.NORTH, lblTotal, 3, SpringLayout.NORTH, txtTotal);
		sl_recaudacionPanel.putConstraint(SpringLayout.EAST, lblTotal, -73, SpringLayout.WEST, txtTotal);
		sl_recaudacionPanel.putConstraint(SpringLayout.NORTH, txtTotal, 34, SpringLayout.NORTH, recaudacionPanel);
		sl_recaudacionPanel.putConstraint(SpringLayout.EAST, txtTotal, -449, SpringLayout.EAST, recaudacionPanel);
		txtTotal.setFont(new Font("SansSerif", Font.PLAIN, 18));
		txtTotal.setEditable(false);
		txtTotal.setPreferredSize(new Dimension(100, 30));

		// Button panel 4
		newdetallesbtn = new JButton("Detalles");
		sl_recaudacionPanel.putConstraint(SpringLayout.NORTH, newdetallesbtn, 33, SpringLayout.NORTH, recaudacionPanel);
		newdetallesbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sl_recaudacionPanel.putConstraint(SpringLayout.WEST, newdetallesbtn, 31, SpringLayout.EAST, txtTotal);
		newdetallesbtn.setForeground(Color.WHITE);
		newdetallesbtn.setBorderPainted(false);
		newdetallesbtn.setBackground(new Color(231, 111, 81));
		newdetallesbtn.setFont(new Font("SansSerif", Font.PLAIN, 18));

		recaudacionPanel.add(lblTotal);
		recaudacionPanel.add(txtTotal);
		recaudacionPanel.add(newdetallesbtn);

		// Panel 5
		JPanel registroPanel = new JPanel();
		mainPanel.add(registroPanel);
		registroPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

		// Button panel 5
		registrarbtn = new JButton("Registro nuevo usuario");
		registrarbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registrarbtn.setForeground(Color.WHITE);
		registrarbtn.setBorderPainted(false);
		registrarbtn.setBackground(new Color(231, 111, 81));
		registrarbtn.setFont(new Font("SansSerif", Font.PLAIN, 18));

		registroPanel.add(registrarbtn);

	}

	public JFrame getFrame() {
		return frame;
	}

}
