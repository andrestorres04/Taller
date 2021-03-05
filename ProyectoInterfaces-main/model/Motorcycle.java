package model;

public class Motorcycle extends Vehicle {

	private String mat_moto;

	public Motorcycle(String num_bastidor, String marca, String modelo, String combustible, String precio, int cod_ventas,
			int cod_cliente, int cod_conce, String vehicleType, String mat_moto, String anno, String km) {
		super(num_bastidor, marca, modelo, combustible, precio, vehicleType, anno, km, cod_ventas, cod_cliente, cod_conce);
		this.mat_moto = mat_moto;
	}

	public String getMat_moto() {
		return mat_moto;
	}

}
