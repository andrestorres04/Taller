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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import model.Mechanical;
import model.Repair;
import model.Vehicle;
import view.LoginView;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import dao.RepairDAO;
import dao.UserDAO;
import dao.VehicleDAO;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MechanicalAddRepairView {

	private JFrame frame;
	private JButton btnLogOut;
	private JButton btnBackToMenu;
	private JButton addRepairBtn;
	private JTextField numberTextField;
	private JTextArea partsTextArea;
	private JDateChooser initialDateChooser;
	private JDateChooser finishDateChooser;
	private JComboBox<Object> vehicleTypeComboBox;
	private JComboBox<Object> mechanicalComboBox;
	private JSpinner spinnerPrice;

	private UserDAO userDAO;
	private RepairDAO repairDAO;
	private VehicleDAO vehicleDAO;

	private Mechanical user;
	private boolean isBoss;

	/**
	 * Crea la aplicación
	 */
	public MechanicalAddRepairView(Mechanical user, boolean isBoss) {
		this.user = user;
		this.isBoss = isBoss;
		this.userDAO = new UserDAO();
		this.repairDAO = new RepairDAO();
		this.vehicleDAO = new VehicleDAO();
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
		var mechanicals = userDAO.getMechanicals();
		var vehicles = vehicleDAO.getVehicles();
		
		// Por defecto al abrir carga los mecánicos de coches
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				addTypeMechanicalsToComboBox(mechanicals, 1, comboboxModel);
			}
		});

		// Rellenar el comboBox con los mecánicos según el tipo de vehículo elegido en
		// el otro comboBox
		vehicleTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				var comboboxModel = new DefaultComboBoxModel<>();
				// Elimina los elementos anteriores y carga los mecánicos según la especialidad
				if (vehicleTypeComboBox.getSelectedItem().toString().equalsIgnoreCase("Coche")) {
					comboboxModel.removeAllElements();
					addTypeMechanicalsToComboBox(mechanicals, 1, comboboxModel);

				} else if (vehicleTypeComboBox.getSelectedItem().toString().equalsIgnoreCase("Motocicleta")) {
					comboboxModel.removeAllElements();
					addTypeMechanicalsToComboBox(mechanicals, 2, comboboxModel);

				} else {
					comboboxModel.removeAllElements();
					addTypeMechanicalsToComboBox(mechanicals, 3, comboboxModel);
				}
			}
		});

		// Botón añadir reparación
		addRepairBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var repair = createRepair(mechanicals, vehicles);
				if (repair != null) {
					repairDAO.addRepair(repair);
					JOptionPane.showMessageDialog(frame, "Reparación añadida", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Volver al menú principal
		btnBackToMenu.addMouseListener(new MouseAdapter() {
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
		gbl_mainPanel.columnWidths = new int[] { 1053, 0 };
		gbl_mainPanel.rowHeights = new int[] { 145, 581, 0 };
		gbl_mainPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.anchor = GridBagConstraints.WEST;
		gbc_headerPanel.fill = GridBagConstraints.VERTICAL;
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

		JLabel lblMainMenu = new JLabel("Añadir reparación");
		lblMainMenu.setFont(new Font("SansSerif", Font.BOLD, 25));
		headerPanel.add(lblMainMenu);

		JPanel bodyPanel = new JPanel();
		GridBagConstraints gbc_bodyPanel = new GridBagConstraints();
		gbc_bodyPanel.fill = GridBagConstraints.BOTH;
		gbc_bodyPanel.gridx = 0;
		gbc_bodyPanel.gridy = 1;
		mainPanel.add(bodyPanel, gbc_bodyPanel);
		bodyPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel leftDataPanel = new JPanel();
		bodyPanel.add(leftDataPanel);
		GridBagLayout gbl_leftDataPanel = new GridBagLayout();
		gbl_leftDataPanel.columnWidths = new int[] { 499, 0 };
		gbl_leftDataPanel.rowHeights = new int[] { 112, 334, 0, 0 };
		gbl_leftDataPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_leftDataPanel.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		leftDataPanel.setLayout(gbl_leftDataPanel);

		JLabel lblTextArea = new JLabel("Descripción de la reparación (piezas):");
		lblTextArea.setFont(new Font("SansSerif", Font.BOLD, 18));
		GridBagConstraints gbc_lblTextArea = new GridBagConstraints();
		gbc_lblTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_lblTextArea.gridx = 0;
		gbc_lblTextArea.gridy = 0;
		leftDataPanel.add(lblTextArea, gbc_lblTextArea);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		leftDataPanel.add(scrollPane, gbc_scrollPane);

		partsTextArea = new JTextArea();
		partsTextArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
		scrollPane.setViewportView(partsTextArea);

		JPanel rightDataPanel = new JPanel();
		bodyPanel.add(rightDataPanel);
		GridBagLayout gbl_rightDataPanel = new GridBagLayout();
		gbl_rightDataPanel.columnWidths = new int[] { 257, 236, 0 };
		gbl_rightDataPanel.rowHeights = new int[] { 114, 74, 66, 68, 64, 69, 61, 59, 0 };
		gbl_rightDataPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_rightDataPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		rightDataPanel.setLayout(gbl_rightDataPanel);

		JLabel lblRepairData = new JLabel("Datos de la reparación:");
		lblRepairData.setFont(new Font("SansSerif", Font.BOLD, 18));
		GridBagConstraints gbc_lblRepairData = new GridBagConstraints();
		gbc_lblRepairData.anchor = GridBagConstraints.EAST;
		gbc_lblRepairData.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepairData.gridx = 0;
		gbc_lblRepairData.gridy = 0;
		rightDataPanel.add(lblRepairData, gbc_lblRepairData);

		JLabel vehicleTypeLbl = new JLabel("Tipo de vehículo:");
		vehicleTypeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		vehicleTypeLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_vehicleTypeLbl = new GridBagConstraints();
		gbc_vehicleTypeLbl.anchor = GridBagConstraints.CENTER;
		gbc_vehicleTypeLbl.insets = new Insets(0, 0, 5, 5);
		gbc_vehicleTypeLbl.gridx = 0;
		gbc_vehicleTypeLbl.gridy = 1;
		rightDataPanel.add(vehicleTypeLbl, gbc_vehicleTypeLbl);

		vehicleTypeComboBox = new JComboBox<Object>();
		vehicleTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Coche", "Motocicleta", "Ciclomotor" }));
		vehicleTypeComboBox.setSelectedIndex(0);
		vehicleTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_vehicleTypeComboBox = new GridBagConstraints();
		gbc_vehicleTypeComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_vehicleTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_vehicleTypeComboBox.gridx = 1;
		gbc_vehicleTypeComboBox.gridy = 1;
		rightDataPanel.add(vehicleTypeComboBox, gbc_vehicleTypeComboBox);

		JLabel frameNumberLbl = new JLabel("Número de bastidor:");
		frameNumberLbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_frameNumberLbl = new GridBagConstraints();
		gbc_frameNumberLbl.anchor = GridBagConstraints.CENTER;
		gbc_frameNumberLbl.insets = new Insets(0, 0, 5, 5);
		gbc_frameNumberLbl.gridx = 0;
		gbc_frameNumberLbl.gridy = 2;
		rightDataPanel.add(frameNumberLbl, gbc_frameNumberLbl);

		numberTextField = new JTextField();
		numberTextField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		numberTextField.setColumns(10);
		GridBagConstraints gbc_numberTextField = new GridBagConstraints();
		gbc_numberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_numberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_numberTextField.gridx = 1;
		gbc_numberTextField.gridy = 2;
		rightDataPanel.add(numberTextField, gbc_numberTextField);

		JLabel lblStartDate = new JLabel("Fecha comienzo:");
		lblStartDate.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.anchor = GridBagConstraints.CENTER;
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 3;
		rightDataPanel.add(lblStartDate, gbc_lblStartDate);

		initialDateChooser = new JDateChooser();
		initialDateChooser.setPreferredSize(new Dimension(200, 25));
		initialDateChooser.getCalendarButton().setPreferredSize(new Dimension(50, 25));
		initialDateChooser.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 3;
		rightDataPanel.add(initialDateChooser, gbc_dateChooser);

		JLabel lblFinishDate = new JLabel("Fecha finalización:");
		lblFinishDate.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_lblFinishDate = new GridBagConstraints();
		gbc_lblFinishDate.anchor = GridBagConstraints.CENTER;
		gbc_lblFinishDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblFinishDate.gridx = 0;
		gbc_lblFinishDate.gridy = 4;
		rightDataPanel.add(lblFinishDate, gbc_lblFinishDate);

		finishDateChooser = new JDateChooser();
		finishDateChooser.setPreferredSize(new Dimension(200, 25));
		finishDateChooser.getCalendarButton().setPreferredSize(new Dimension(50, 25));
		finishDateChooser.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_finishDateChooser = new GridBagConstraints();
		gbc_finishDateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_finishDateChooser.gridx = 1;
		gbc_finishDateChooser.gridy = 4;
		rightDataPanel.add(finishDateChooser, gbc_finishDateChooser);

		JLabel lblMecnicoAsignado = new JLabel("Mecánico asignado:");
		lblMecnicoAsignado.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_lblMecnicoAsignado = new GridBagConstraints();
		gbc_lblMecnicoAsignado.anchor = GridBagConstraints.CENTER;
		gbc_lblMecnicoAsignado.insets = new Insets(0, 0, 5, 5);
		gbc_lblMecnicoAsignado.gridx = 0;
		gbc_lblMecnicoAsignado.gridy = 5;
		rightDataPanel.add(lblMecnicoAsignado, gbc_lblMecnicoAsignado);

		mechanicalComboBox = new JComboBox<Object>();
		mechanicalComboBox.setSelectedIndex(-1);
		mechanicalComboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_mechanicalComboBox = new GridBagConstraints();
		gbc_mechanicalComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_mechanicalComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_mechanicalComboBox.gridx = 1;
		gbc_mechanicalComboBox.gridy = 5;
		rightDataPanel.add(mechanicalComboBox, gbc_mechanicalComboBox);
		
		JLabel lblPrice = new JLabel("Precio de la reparación:");
		lblPrice.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 6;
		rightDataPanel.add(lblPrice, gbc_lblPrice);
		
		spinnerPrice = new JSpinner();
		spinnerPrice.setModel(new SpinnerNumberModel(20, 20, 999999, 1));
		spinnerPrice.setFont(new Font("SansSerif", Font.PLAIN, 15));
		GridBagConstraints gbc_spinnerPrice = new GridBagConstraints();
		gbc_spinnerPrice.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPrice.gridx = 1;
		gbc_spinnerPrice.gridy = 6;
		rightDataPanel.add(spinnerPrice, gbc_spinnerPrice);

		addRepairBtn = new JButton("Añadir reparación");
		addRepairBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addRepairBtn.setForeground(Color.WHITE);
		addRepairBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
		addRepairBtn.setBackground(new Color(231, 111, 81));
		GridBagConstraints gbc_addRepairBtn = new GridBagConstraints();
		gbc_addRepairBtn.gridx = 1;
		gbc_addRepairBtn.gridy = 7;
		rightDataPanel.add(addRepairBtn, gbc_addRepairBtn);

	}

	private Repair createRepair(List<Mechanical> mechanicals, List<Vehicle> vehicles) {
		Repair repair = null;
		var frameNumber = numberTextField.getText();
		var carParts = partsTextArea.getText();
		var price = spinnerPrice.getValue() + "";

		var selectedMechanical = getMechanicalByName(mechanicals);

		// Control de errores
		var frameNumberExist = vehicles.stream()
				.filter(vehicle -> vehicle.getNum_bastidor().equalsIgnoreCase(frameNumber))
				.collect(Collectors.toList());

		if (frameNumberExist.size() == 0) {
			showErrorMessage("Error, no existe el número de bastidor introducido, regístrelo primero");
			numberTextField.requestFocus();

		} else if (carParts.length() > 500) {
			showErrorMessage("Error, la descripción no puede contener más de 500 carácteres");
			partsTextArea.requestFocus();

		} else if (initialDateChooser.getDate() == null || finishDateChooser.getDate() == null) {
			showErrorMessage("Seleccione las fechas de entrada y estimación de salida");
			initialDateChooser.requestFocus();

		} else if (!frameNumberExist.get(0).getTipoVehiculo().equalsIgnoreCase(vehicleTypeComboBox.getSelectedItem().toString())) {
			showErrorMessage("Debes asignar un vehículo compatible con la especialidad del mecánico elegido");
			numberTextField.requestFocus();

		} else {
			// Cambiar formato de la fecha
			var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			var initialDate = dateFormat.format(initialDateChooser.getDate());
			var finishDate = dateFormat.format(finishDateChooser.getDate());

			// Crear instancia de reparación con los datos de la vista
			repair = new Repair(0, selectedMechanical.get(0).getCod_mecanico(), frameNumber, carParts, initialDate,
					finishDate, price);
		}

		return repair;
	}

	/**
	 * Filtra los mecánicos con especialidad indicada y para cada uno los inserta en el comboBox
	 * 
	 * @param mechanicals   lista de mecánicos
	 * @param specialty     especialidad del mecánico
	 * @param comboboxModel comboBox al que añadir
	 */
	private void addTypeMechanicalsToComboBox(List<Mechanical> mechanicals, int specialty, DefaultComboBoxModel<Object> comboboxModel) {
		mechanicals.stream().filter(mechanical -> mechanical.getCod_especialdiad() == specialty)
				.forEach(mechanical -> comboboxModel.addElement(mechanical.getNombre() + " " + mechanical.getApellidos()));

		// Inserta el modelo del comboBox con los datos
		mechanicalComboBox.setModel(comboboxModel);
	}

	/**
	 * Obtiene el mecánico que coincide con el nombre y apellidos seleccionado en el comboBox
	 * 
	 * @param mechanicals
	 * @return List mecánico
	 */
	private List<Mechanical> getMechanicalByName(List<Mechanical> mechanicals) {
		var mechanicalNameAndSurnames = mechanicalComboBox.getSelectedItem().toString();
		var selectedMechanical = mechanicals.stream()
				.filter(mechanical -> mechanicalNameAndSurnames.equalsIgnoreCase(mechanical.getNombre() + " " + mechanical.getApellidos()))
				.collect(Collectors.toList());
		return selectedMechanical;
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
