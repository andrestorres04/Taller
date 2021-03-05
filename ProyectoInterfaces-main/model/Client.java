package model;

public class Client extends Profesor {
	
	private int clientCod;
	
	public Client(String dni, String nombre, String apellidos, String telefono) {
		super(dni, nombre, apellidos, telefono);
	}

	public Client(int clientCod, String dni, String nombre, String apellidos, String telefono) {
		super(dni, nombre, apellidos, telefono);
		this.clientCod = clientCod;
	}
	

	public int getClientCod() {
		return clientCod;
	}

	
}
