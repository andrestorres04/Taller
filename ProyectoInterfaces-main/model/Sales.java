package model;

public class Sales extends Profesor {
	
	int cod_ventas, cod_empleado;

	public Sales(String dni, String nombre, String apellidos, String telefono, int cod_ventas, int cod_empleado) {
		super(dni, nombre, apellidos, telefono);
		this.cod_ventas = cod_ventas;
		this.cod_empleado = cod_empleado;
	}

	public int getCod_ventas() {
		return cod_ventas;
	}

	public int getCod_empleado() {
		return cod_empleado;
	}

}
