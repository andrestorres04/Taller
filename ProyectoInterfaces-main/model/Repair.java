package model;

public class Repair {

	private int cod_reparacion, cod_mecanico;
	String num_bastidor, piezas, precio;
	String fecha_entrada, fecha_salida;

	public Repair(int cod_reparacion, int cod_mecanico, String num_bastidor, String piezas, String initialDate,
			String finishDate, String precio) {
		this.cod_reparacion = cod_reparacion;
		this.cod_mecanico = cod_mecanico;
		this.num_bastidor = num_bastidor;
		this.piezas = piezas;
		this.fecha_entrada = initialDate;
		this.fecha_salida = finishDate;
		this.precio = precio;
	}

	public int getCod_reparacion() {
		return cod_reparacion;
	}

	public int getCod_mecanico() {
		return cod_mecanico;
	}

	public String getNum_bastidor() {
		return num_bastidor;
	}

	public String getPiezas() {
		return piezas;
	}

	public String getFecha_entrada() {
		return fecha_entrada;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public String getPrecio() {
		return precio;
	}

}
