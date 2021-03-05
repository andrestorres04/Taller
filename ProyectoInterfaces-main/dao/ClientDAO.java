package dao;

import java.util.ArrayList;
import java.util.List;

import common.Constants;
import model.Client;

public class ClientDAO extends AbstractDAO {

	public ClientDAO() {
		super();
	}

	/**
	 * Añade un cliente a la BBDD (tabla persona y tabla cliente)
	 * 
	 * @param e Objeto cliente
	 */
	public void addClient(Client e) {
		try {
			con.setAutoCommit(false);
			stm = con.createStatement();
			stm.executeUpdate("INSERT INTO taller.persona (dni, nombre, apellidos, telefono) VALUES ('" + e.getDni()
					+ "', '" + e.getNombre() + "', '" + e.getApellidos() + "', '" + e.getTelefono() + "')");
			stm.executeUpdate("INSERT INTO taller.cliente (cod_cliente, dni) VALUES (0, '" + e.getDni() + "')");
			con.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			conectionRollback();
		}
	}

	/**
	 * Rellena la tabla de contenido inicial (Todos los clientes)
	 * 
	 * @return Lista de clientes
	 */
	public List<Client> getClients() {
		var clients = new ArrayList<Client>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_CLIENTS_ALL_DATA);
			while (rs.next()) {
				clients.add(
						new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clients;
	}

	/**
	 * Busca si hay un cliente con el dni y si existe devuelve un objeto cliente con
	 * sus datos
	 * 
	 * @param dni String
	 * @return Objeto cliente
	 */
	public Client searchClient(String dni) {
		Client client = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT cliente.cod_cliente, cliente.dni, persona.nombre, persona.apellidos, persona.telefono FROM persona INNER JOIN cliente on persona.dni = cliente.dni WHERE cliente.dni='"
							+ dni + "'");
			if (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return client;
	}
	
	/**
	 * Busca un cliente con el número de bastidor y si existe devuelve un objeto cliente 
	 * 
	 * 
	 * @param String
	 * @return Objeto cliente
	 */
	public Client searchClientByFrameNumber(String num_bastidor) {
		Client client = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					""
							);
			if (rs.next()) {
				//client = new Client();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return client;
	}

}
