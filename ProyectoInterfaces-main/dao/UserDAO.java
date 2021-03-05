package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import model.Boss;
import model.Employee;
import model.Mechanical;
import model.Sales;

public class UserDAO extends AbstractDAO {

	public UserDAO() {
		super();
	}

	/**
	 * Comprueba si el empleado existe en la BBDD
	 * 
	 * @param e Empleado
	 * @return boolean
	 */
	public boolean login(Employee e) {
		boolean isLogin = false;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT * FROM empleado WHERE usuario='" + e.getUsername() + "' AND contrasena='"
					+ e.getPassword() + "'");
			isLogin = rs.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isLogin;
	}

	/**
	 * Comprueba si el empleado pertenece al departamento de ventas
	 * 
	 * @param e Empleado
	 * @return boolean
	 */
	public boolean isSalesEmployee(Employee e) {
		var isSales = false;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.empleado INNER JOIN taller.ventas ON empleado.cod_empleado = ventas.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "'");
			isSales = rs.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isSales;
	}

	/**
	 * Comprueba si el empleado es un empleado jefe del departamento de mecánica
	 * 
	 * @param e Empleado
	 * @return boolean
	 */
	public boolean isBossMechanical(Employee e) {
		var isBoss = false;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.empleado INNER JOIN taller.mecanico ON empleado.cod_empleado = mecanico.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "'");
			// Comprobar si el código de jefe y el de mecánico es igual (es jefe)
			if (rs.next() && rs.getInt("cod_mecanico") == rs.getInt("cod_mecanico_jefe")) {
				isBoss = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isBoss;
	}

	/**
	 * Comprueba si el empleado es un empleado jefe (ceo)
	 * 
	 * @param e Empleado
	 * @return boolean
	 */
	public boolean isBoss(Employee e) {
		var isBoss = false;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.empleado INNER JOIN taller.jefe ON empleado.cod_empleado = jefe.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "'");
			// Comprobar si el código de jefe y el de mecánico es igual (es jefe)
			if (rs.next()) {
				isBoss = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isBoss;
	}

	/**
	 * Devuelve el empleado jefe que coincida con el empleado (usuario y contraseña)
	 * que se le pasa como parámetro
	 * 
	 * @param e Empelado
	 * @return Empleado de mecánica
	 */
	public Boss getBossEmployee(Employee e) {
		Boss bossEmployee = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.persona INNER JOIN taller.empleado ON persona.dni = empleado.dni INNER JOIN taller.jefe ON empleado.cod_empleado = jefe.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "';");
			if (rs.next()) {
				bossEmployee = new Boss(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getInt("cod_jefe"), rs.getInt("cod_empleado"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bossEmployee;
	}

	/**
	 * Devuelve el empleado de ventas que coincida con el empleado (usuario y
	 * contraseña) que se le pasa como parámetro
	 * 
	 * @param e Empelado
	 * @return Empleado de ventas
	 */
	public Sales getSalesEmployee(Employee e) {
		Sales salesEmployee = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.persona INNER JOIN taller.empleado ON persona.dni = empleado.dni INNER JOIN taller.ventas ON empleado.cod_empleado = ventas.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "';");
			if (rs.next()) {
				salesEmployee = new Sales(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getInt("cod_ventas"), rs.getInt("cod_empleado"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return salesEmployee;
	}

	/**
	 * Devuelve el empleado de mecánica que coincida con el empleado (usuario y
	 * contraseña) que se le pasa como parámetro
	 * 
	 * @param e Empelado
	 * @return Empleado de mecánica
	 */
	public Mechanical getMechanicalEmployee(Employee e) {
		Mechanical mechanicalEmployee = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT * FROM taller.persona INNER JOIN taller.empleado ON persona.dni = empleado.dni INNER JOIN taller.mecanico ON empleado.cod_empleado = mecanico.cod_empleado WHERE usuario='"
							+ e.getUsername() + "' AND contrasena='" + e.getPassword() + "';");
			if (rs.next()) {
				mechanicalEmployee = new Mechanical(rs.getString("dni"), rs.getString("nombre"),
						rs.getString("apellidos"), rs.getString("telefono"), rs.getInt("cod_mecanico"),
						rs.getInt("cod_mecanico_jefe"), rs.getInt("cod_especialidad"), rs.getInt("cod_empleado"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mechanicalEmployee;
	}

	/**
	 * Devuelve una lista con todos los mecánicos y sus datos
	 * 
	 * @return Lista de mecánicos
	 */
	public List<Mechanical> getMechanicals() {
		var mechanicals = new ArrayList<Mechanical>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_MECHANICALS_ALL_DATA);
			while (rs.next()) {
				mechanicals.add(new Mechanical(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getInt("cod_mecanico"), rs.getInt("cod_mecanico_jefe"),
						rs.getInt("cod_especialidad"), rs.getInt("cod_empleado")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return mechanicals;
	}

}
