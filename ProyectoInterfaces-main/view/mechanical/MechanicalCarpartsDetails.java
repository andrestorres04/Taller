package view.mechanical;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Cursor;

public class MechanicalCarpartsDetails {
	
	private JFrame frmDepartamentoDeMecnica;
	private JTextArea textAreaCarparts;
	private JButton returnButton;
	
	private String carParts;

	
	/**
	 * Crea la aplicación
	 * @param i 
	 */
	public MechanicalCarpartsDetails(String carParts) {
		this.carParts = carParts;
		initialize();
	}

	/**
	 * Inicializa el contenido del frame
	 */
	private void initialize() {
		frmDepartamentoDeMecnica = new JFrame();
		frmDepartamentoDeMecnica.setBounds(100, 100, 450, 300);
		frmDepartamentoDeMecnica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUIComponents();
		setControllers();
	}

	/**
	 * Contiene los controladores
	 */
	private void setControllers() {
		// Al abrir la ventana carga los datos de las piezas de la reparación
		frmDepartamentoDeMecnica.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				textAreaCarparts.setText(carParts);
				textAreaCarparts.setEditable(false);
			}
		});
		
		// Botón cerrar
		returnButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frmDepartamentoDeMecnica.dispose();
			}
		});
	}

	/**
	 * Contiene los componentes de la interfaz de usuario
	 */
	private void setUIComponents() {
		frmDepartamentoDeMecnica.setTitle("Departamento de mecánica");
		frmDepartamentoDeMecnica.setMinimumSize(new Dimension(700, 500));

		/*
		 * Paneles externos. Solo hay que añadir en el bottomPanel
		 */
		JPanel topPanel = new JPanel();
		frmDepartamentoDeMecnica.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setBackground(new Color(233, 196, 106));
		topPanel.setBounds(100, 100, 100, 100);

		JPanel leftPanel = new JPanel();
		frmDepartamentoDeMecnica.getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setBackground(new Color(233, 196, 106));

		JPanel rightPanel = new JPanel();
		frmDepartamentoDeMecnica.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setBackground(new Color(233, 196, 106));

		JPanel bottomPanel = new JPanel();
		frmDepartamentoDeMecnica.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setBackground(new Color(233, 196, 106));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		JLabel lblBlank = new JLabel(" ");
		lblBlank.setForeground(new Color(220, 20, 60));
		lblBlank.setFont(new Font("SansSerif", Font.BOLD, 9));
		bottomPanel.add(lblBlank);

		/*
		 * mainPanel. Dentro se crean otros paneles para añadir los distintos
		 * componentes.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(0, 0, 0)));
		frmDepartamentoDeMecnica.getContentPane().add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 857, 0 };
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
		JLabel vehiclesDatesLbl = new JLabel("Añadir vehículo");
		vehiclesDatesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		vehiclesDatesLbl.setFont(new Font("SansSerif", Font.BOLD, 25));
		vehiclesPanel.add(vehiclesDatesLbl, BorderLayout.CENTER);

		// Panel para rellenar datos de cliente.
		JPanel vehiclesDatesPanel = new JPanel();
		GridBagConstraints gbc_vehiclesDatesPanel = new GridBagConstraints();
		gbc_vehiclesDatesPanel.fill = GridBagConstraints.BOTH;
		gbc_vehiclesDatesPanel.insets = new Insets(0, 0, 5, 0);
		gbc_vehiclesDatesPanel.gridx = 0;
		gbc_vehiclesDatesPanel.gridy = 1;
		mainPanel.add(vehiclesDatesPanel, gbc_vehiclesDatesPanel);
		GridBagLayout gbl_vehiclesDatesPanel = new GridBagLayout();
		gbl_vehiclesDatesPanel.columnWidths = new int[] { 663, 0 };
		gbl_vehiclesDatesPanel.rowHeights = new int[] { 281, 0 };
		gbl_vehiclesDatesPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_vehiclesDatesPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		vehiclesDatesPanel.setLayout(gbl_vehiclesDatesPanel);

		JPanel vehiclesDatesPanelLeft = new JPanel();
		GridBagConstraints gbc_vehiclesDatesPanelLeft = new GridBagConstraints();
		gbc_vehiclesDatesPanelLeft.fill = GridBagConstraints.BOTH;
		gbc_vehiclesDatesPanelLeft.gridx = 0;
		gbc_vehiclesDatesPanelLeft.gridy = 0;
		vehiclesDatesPanel.add(vehiclesDatesPanelLeft, gbc_vehiclesDatesPanelLeft);
		SpringLayout sl_vehiclesDatesPanelLeft = new SpringLayout();
		vehiclesDatesPanelLeft.setLayout(sl_vehiclesDatesPanelLeft);

		// Componentes panel derecho
		// Añadir Jlabel y JText para los distintos datos del ciente
		JLabel lblCarparts = new JLabel("Descripción de la reparación (piezas):");
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, lblCarparts, 31, SpringLayout.NORTH, vehiclesDatesPanelLeft);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, lblCarparts, -187, SpringLayout.EAST, vehiclesDatesPanelLeft);
		lblCarparts.setFont(new Font("SansSerif", Font.BOLD, 15));
		vehiclesDatesPanelLeft.add(lblCarparts);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.NORTH, scrollPane, 16, SpringLayout.SOUTH, lblCarparts);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, vehiclesDatesPanelLeft);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.SOUTH, scrollPane, 215, SpringLayout.SOUTH, lblCarparts);
		sl_vehiclesDatesPanelLeft.putConstraint(SpringLayout.EAST, scrollPane, 653, SpringLayout.WEST, vehiclesDatesPanelLeft);
		vehiclesDatesPanelLeft.add(scrollPane);
		
		textAreaCarparts = new JTextArea();
		textAreaCarparts.setFont(new Font("SansSerif", Font.PLAIN, 15));
		scrollPane.setViewportView(textAreaCarparts);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_botonPanel = new GridBagConstraints();
		gbc_botonPanel.fill = GridBagConstraints.BOTH;
		gbc_botonPanel.gridx = 0;
		gbc_botonPanel.gridy = 2;
		mainPanel.add(buttonPanel, gbc_botonPanel);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[] { 276, 0, 0, 113, 0 };
		gbl_buttonPanel.rowHeights = new int[] { 100, 29, 0 };
		gbl_buttonPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buttonPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		buttonPanel.setLayout(gbl_buttonPanel);

		// Botones
		returnButton = new JButton("    Cerrar    ");
		returnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		returnButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		returnButton.setBackground(new Color(244, 162, 97));
		returnButton.setForeground(Color.WHITE);
		GridBagConstraints gbc_returnButton = new GridBagConstraints();
		gbc_returnButton.insets = new Insets(0, 0, 5, 5);
		gbc_returnButton.anchor = GridBagConstraints.WEST;
		gbc_returnButton.gridx = 1;
		gbc_returnButton.gridy = 0;
		buttonPanel.add(returnButton, gbc_returnButton);
	}

	public JFrame getFrame() {
		return frmDepartamentoDeMecnica;
	}
}
