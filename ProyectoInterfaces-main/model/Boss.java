package model;

public class Boss extends Profesor {
	
	int cod_jefe, cod_empleado;

	public Boss(String dni, String nombre, String apellidos, String telefono, int cod_jefe, int cod_empleado) {
		super(dni, nombre, apellidos, telefono);
		this.cod_jefe = cod_jefe;
		this.cod_empleado = cod_empleado;
	}

	public int getCod_jefe() {
		return cod_jefe;
	}

	public int getCod_empleado() {
		return cod_empleado;
	}

}
