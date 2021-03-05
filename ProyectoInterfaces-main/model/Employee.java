package model;

public class Employee extends Profesor {
	
	private String username;
	private String password;
	String dni;
	String nombre;
	String apellidos;
	String rol;
	int telefono;
	int cod_empleado;
	int cod_conce;
	int cod_especialidad;


	public Employee(String dni, String nombre, String apellidos, String telefono) {
		super(dni, nombre, apellidos, telefono);
	}
	

	public Employee(String dni, String nombre, String apellidos, String telefono, String rol, int cod_conce, int cod_especialidad, String username, String password) {
		super(dni, nombre, apellidos, telefono);
		this.rol = rol;
		this.cod_conce = cod_conce;
		this.cod_especialidad = cod_especialidad;
		this.username = username;
		this.password = password;
	}
	
	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	
	public Employee(String dni, String nombre, String apellidos, String telefono, int cod_empleado, int cod_conce, String usuario, String contrasena) {
		super(dni, nombre, apellidos, telefono);
		this.cod_empleado = cod_empleado;
		this.cod_conce = cod_conce;
		this.username = usuario;
		this.password = contrasena;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public Integer getCod_Empleado() {
		return cod_empleado;
	}
	
	public Integer getCod_Conce() {
		
		return cod_conce;
	}
	
	public String getRol() {
		return rol;
	}

	public void setCod_empleado(int cod_empleado) {
		this.cod_empleado = cod_empleado;
	}
	
	public int getCod_especialidad() {
		return cod_especialidad;
	}

}
