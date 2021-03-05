package model;

public class Mechanical extends Profesor {

	private int cod_mecanico, cod_mecanico_jefe, cod_especialdiad, cod_empleado;

	public Mechanical(String dni, String nombre, String apellidos, String telefono, int cod_mecanico,
			int cod_mecanico_jefe, int cod_especialdiad, int cod_empleado) {
		super(dni, nombre, apellidos, telefono);
		this.cod_mecanico = cod_mecanico;
		this.cod_mecanico_jefe = cod_mecanico_jefe;
		this.cod_especialdiad = cod_especialdiad;
		this.cod_empleado = cod_empleado;
	}

	public int getCod_mecanico() {
		return cod_mecanico;
	}

	public int getCod_mecanico_jefe() {
		return cod_mecanico_jefe;
	}

	public int getCod_especialdiad() {
		return cod_especialdiad;
	}

	public int getCod_empleado() {
		return cod_empleado;
	}
	
	

}
