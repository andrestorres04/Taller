package model;


public class SellingProposition {
	Integer cod_propuesta, cod_cliente, cod_ventas;
	String num_bastidor;
	String fecha_validez, precio;

	public SellingProposition(int cod_propuesta, int cod_cliente, int cod_ventas, String num_bastidor, String fecha_validez, String precio) {
		this.cod_propuesta = cod_propuesta;
		this.cod_cliente = cod_cliente;
		this.cod_ventas = cod_ventas;
		this.num_bastidor = num_bastidor;
		this.fecha_validez = fecha_validez;
		this.precio = precio;
		
	}

	public Integer getCod_propuesta() {
		return cod_propuesta;
	}

	public Integer getCod_cliente() {
		return cod_cliente;
	}

	public Integer getCod_ventas() {
		return cod_ventas;
	}

	public String getNum_bastidor() {
		return num_bastidor;
	}

	public String getFecha_validez() {
		return fecha_validez;
	}

	public String getPrecio() {
		return precio;
	}
	
}
