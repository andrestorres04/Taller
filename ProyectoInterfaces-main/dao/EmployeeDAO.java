package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import model.Client;
import model.Employee;

public class EmployeeDAO extends AbstractDAO {

	public EmployeeDAO() {
		super();
	}

	/**
	 * Añade un cliente a la BBDD (tabla persona y tabla cliente)
	 * 
	 * @param e Objeto cliente
	 */
	public void addEmployee(Employee e) {
		try {
			con.setAutoCommit(false);
			stm = con.createStatement();
			
			stm.executeUpdate("INSERT INTO taller.persona (dni, nombre, apellidos, telefono) VALUES ('" + e.getDni() + "', '" + 
			e.getNombre() + "', '" + e.getApellidos() + "', '" + e.getTelefono() + "')");
			
			stm.executeUpdate("INSERT INTO taller.empleado (cod_empleado, dni, cod_conce, usuario, contrasena) VALUES "
					+ "(0, '" + e.getDni() + "', '" + e.getCod_Conce() + "', '" +
					e.getUsername() + "', MD5('" + e.getPassword() + "'))");
			con.commit();
			
			//Asignar el cod_empleado
			try {
				stm = con.createStatement();
				rs = stm.executeQuery("SELECT empleado.cod_empleado FROM taller.empleado WHERE dni = '" + e.getDni() + "'");
				while (rs.next()) {
					e.setCod_empleado(rs.getInt(1));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			//Por si en un futuro hubiese que implementar el añadir jefes
			if(e.getRol().equals("Jefe")) {
				stm.execute("INSERT INTO taller.jefe (cod_jefe, cod_empleado) VALUES (0, '" + e.getCod_Empleado() + "')");
			} else if (e.getRol().equals("Ventas")){
				stm.execute("INSERT INTO taller.ventas (cod_ventas, cod_empleado) VALUES (0, '" + e.getCod_Empleado() + "')");
			} else if (e.getRol().equals("Mecánico")){
				stm.execute("INSERT INTO taller.mecanico (cod_mecanico, cod_mecanico_jefe, cod_especialidad, cod_empleado) VALUES "
						+ "(0, '1', '" + e.getCod_especialidad() + "', '" + e.getCod_Empleado() + "')");
			} 

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
	public List<Employee> getEmployees() {
		var employees = new ArrayList<Employee>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_EMPLOYEES_ALL_DATA);
			while (rs.next()) {
				employees.add(
						new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(7), 0, rs.getString(8), rs.getString(9)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return employees;
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
	

}
