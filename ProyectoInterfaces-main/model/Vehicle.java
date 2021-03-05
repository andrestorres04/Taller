package model;

public class Vehicle {

	private String num_bastidor, marca, modelo, combustible, precio, tipoVehiculo, nombre, apellidos, anno, kilometros;
	private int cod_ventas, cod_cliente, cod_conce, cod_empleado;

	public Vehicle(String num_bastidor, String marca, String modelo, String combustible, String precio,
			String tipoVehiculo, String anno, String kilometros, int cod_ventas, int cod_cliente, int cod_conce) {
		this.num_bastidor = num_bastidor;
		this.marca = marca;
		this.modelo = modelo;
		this.combustible = combustible;
		this.precio = precio;
		this.tipoVehiculo = tipoVehiculo;
		this.anno = anno;
		this.kilometros = kilometros;
		this.cod_ventas = cod_ventas;
		this.cod_cliente = cod_cliente;
		this.cod_conce = cod_conce;
	}

	// Constructor con códigos sin año ni kilometros
	public Vehicle(String num_bastidor, String marca, String modelo, String combustible, String precio, int cod_ventas,
			int cod_cliente, int cod_conce, String vehicleType) {
		this.num_bastidor = num_bastidor;
		this.marca = marca;
		this.modelo = modelo;
		this.combustible = combustible;
		this.precio = precio;
		this.cod_ventas = cod_ventas;
		this.cod_cliente = cod_cliente;
		this.cod_conce = cod_conce;
		this.tipoVehiculo = vehicleType;
	}
	
	// Constructor básico
	public Vehicle(String num_bastidor, String marca, String modelo) {
		this.num_bastidor = num_bastidor;
		this.marca = marca;
		this.modelo = modelo;
	}
	
	public Vehicle(int cod_empleado, String nombre, String apellidos, String marca, String modelo, String precio, String tipo_vehiculo) {
		this.cod_empleado = cod_empleado;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
		this.tipoVehiculo = tipo_vehiculo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public int getCod_empleado() {
		return cod_empleado;
	}

	// Getters
	public String getNum_bastidor() {
		return num_bastidor;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public String getCombustible() {
		return combustible;
	}

	public String getPrecio() {
		return precio;
	}

	public int getCod_ventas() {
		return cod_ventas;
	}

	public int getCod_cliente() {
		return cod_cliente;
	}

	public int getCod_conce() {
		return cod_conce;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public String getAnno() {
		return anno;
	}

	public String getKilometros() {
		return kilometros;
	}
	
	

}
