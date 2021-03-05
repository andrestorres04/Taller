package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import model.Car;
import model.Moped;
import model.Motorcycle;
import model.Vehicle;

public class VehicleDAO extends AbstractDAO {

	public VehicleDAO() {
		super();
	}

	/**
	 * Comprueba el tipo de vehiculo y lo añade a la BBDD
	 * 
	 * @param v          objeto vehiculo
	 * @param enrollment matrícula del vehículo en cuestión, viene de un txtField
	 */
	public void addVehicle(Vehicle v, String enrollment, boolean isSaleVehicle) {
		// Asignar tipo de vehiculo para insertarlo en su tabla (Coche, moto o
		// ciclomotor)
		var vehicleTypeInsert = getInsertByVehicleType(v, enrollment);

		// Comprobar si es un vehículo a la venta o un vehículo a reparar (evitar error
		// SQL)
		var vehicleInsert = getInsertByVehicle(v, isSaleVehicle);

		try {
			stm = con.createStatement();
			stm.executeUpdate(vehicleInsert);
			stm.executeUpdate(vehicleTypeInsert);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Devuelve una lista con todos los coches
	 * 
	 * @return Lista de coches
	 */
	public List<Car> getCars() {
		var cars = new ArrayList<Car>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_CARS_AlL_DATA);
			while (rs.next()) {
				cars.add(new Car(rs.getString("num_bastidor"), rs.getString("marca"), rs.getString("modelo"),
						rs.getString("combustible"), rs.getString("precio"), rs.getInt("cod_ventas"),
						rs.getInt("cod_cliente"), rs.getInt("cod_conce"), rs.getString("tipo_vehiculo"),
						rs.getString("mat_coche"), rs.getString("anno"), rs.getString("kilometros")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return cars;
	}

	/**
	 * Devuelve una lista con todas las motocicletas
	 * 
	 * @return Lista de motocicletas
	 */
	public List<Motorcycle> getMotorcycles() {
		var motorcycles = new ArrayList<Motorcycle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_MOTORCICLES_ALL_DATA);
			while (rs.next()) {
				motorcycles.add(new Motorcycle(rs.getString("num_bastidor"), rs.getString("marca"),
						rs.getString("modelo"), rs.getString("combustible"), rs.getString("precio"),
						rs.getInt("cod_ventas"), rs.getInt("cod_cliente"), rs.getInt("cod_conce"),
						rs.getString("tipo_vehiculo"), rs.getString("mat_moto"), rs.getString("anno"), rs.getString("kilometros")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return motorcycles;

	}

	/**
	 * Devuelve una lista con todos los ciclomotores
	 * 
	 * @return Lista de ciclomtores
	 */
	public List<Moped> getMopeds() {
		var moped = new ArrayList<Moped>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_MOPEDS_ALL_DATA);
			while (rs.next()) {
				moped.add(new Moped(rs.getString("num_bastidor"), rs.getString("marca"), rs.getString("modelo"),
						rs.getString("combustible"), rs.getString("precio"), rs.getInt("cod_ventas"),
						rs.getInt("cod_cliente"), rs.getInt("cod_conce"), rs.getString("tipo_vehiculo"),
						rs.getString("mat_ciclo"), rs.getString("anno"), rs.getString("kilometros")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return moped;
	}

	/**
	 * Devuelve una lista con todos los vehículos
	 * 
	 * @return Lista de vehículos
	 */
	public List<Vehicle> getVehicles() {
		var vehicles = new ArrayList<Vehicle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_VEHICLES);
			while (rs.next()) {
				vehicles.add(new Vehicle(rs.getString("num_bastidor"), rs.getString("marca"), rs.getString("modelo"),
						rs.getString("combustible"), rs.getString("precio"), rs.getString("tipo_vehiculo"),
						rs.getString("anno"), rs.getString("kilometros"), rs.getInt("cod_ventas"),
						rs.getInt("cod_cliente"), rs.getInt("cod_conce")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return vehicles;
	}

	/**
	 * Devuelve una lista de vehículos según el DNI
	 * 
	 * @param dni String
	 * @return Lista de vehículos
	 */
	public List<Vehicle> getVehicleByClient(String dni) {
		var vehicle = new ArrayList<Vehicle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT num_bastidor, marca, modelo FROM taller.persona INNER JOIN taller.cliente ON persona.dni = cliente.dni INNER JOIN taller.vehiculo ON cliente.cod_cliente = vehiculo.cod_cliente WHERE persona.dni = '"
							+ dni + "'");
			while (rs.next()) {
				vehicle.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return vehicle;
	}

	public Integer getCountVehiclesUnsold() {
		int vehiclesUnsold = 0;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_COUNT_VEHICLES_UNSOLD);
			if (rs.next()) {
				vehiclesUnsold = rs.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return vehiclesUnsold;
	}

	public List<Vehicle> getVehiclesUnsold() {
		var vehicle = new ArrayList<Vehicle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_VEHICLES_UNSOLD);
			while (rs.next()) {
				vehicle.add(new Vehicle(rs.getString("num_bastidor"), rs.getString("marca"), rs.getString("modelo"),
						rs.getString("combustible"), rs.getString("precio"), rs.getInt("cod_ventas"),
						rs.getInt("cod_cliente"), rs.getInt("cod_conce"), rs.getString("tipo_vehiculo")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return vehicle;

	}

	public List<Vehicle> getVehiclesSold() {
		var vehicle = new ArrayList<Vehicle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_VEHICLES_SOLD);
			while (rs.next()) {
				vehicle.add(new Vehicle(rs.getInt("cod_empleado"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("marca"), rs.getString("modelo"), rs.getString("precio"),
						rs.getString("tipo_vehiculo")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return vehicle;

	}

	public List<Vehicle> searchVehicleByDni(String dni) {
		var vehicle = new ArrayList<Vehicle>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT ventas.cod_empleado, persona.nombre, persona.apellidos, vehiculo.marca, vehiculo.modelo, vehiculo.precio, vehiculo.tipo_vehiculo, persona.dni FROM taller.ventas, taller.persona, taller.vehiculo, taller.empleado WHERE ventas.cod_ventas = vehiculo.cod_ventas AND ventas.cod_empleado = empleado.cod_empleado AND empleado.dni = persona.dni AND persona.dni = '"
							+ dni + "' ORDER BY ventas.cod_empleado");
			while (rs.next()) {
				vehicle.add(new Vehicle(rs.getInt("cod_empleado"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("marca"), rs.getString("modelo"), rs.getString("precio"),
						rs.getString("tipo_vehiculo")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vehicle;
	}

	/**
	 * Obtiene el insert según el tipo de vehículo
	 * 
	 * @param v          Vehículo
	 * @param enrollment Matrícula
	 * @return String con el insert
	 */
	private String getInsertByVehicleType(Vehicle v, String enrollment) {
		var vehicleTypeInsert = "";

		if (v.getTipoVehiculo().equalsIgnoreCase("coche")) {
			vehicleTypeInsert = "INSERT INTO taller.coche (mat_coche, num_bastidor) VALUES ('" + enrollment + "', '"
					+ v.getNum_bastidor() + "')";
		} else if (v.getTipoVehiculo().equalsIgnoreCase("motocicleta")) {
			vehicleTypeInsert = "INSERT INTO taller.motocicleta (mat_moto, num_bastidor) VALUES ('" + enrollment
					+ "', '" + v.getNum_bastidor() + "')";
		} else {
			vehicleTypeInsert = "INSERT INTO taller.ciclomotor (mat_ciclo, num_bastidor) VALUES ('" + enrollment
					+ "', '" + v.getNum_bastidor() + "' )";
		}
		return vehicleTypeInsert;
	}

	/**
	 * Obtiene el insert con los campos que sean necesarios según si es un vehículo
	 * de ventas o en reparaciones
	 * 
	 * @param v             Vehículo
	 * @param isSaleVehicle boolean
	 * @return String con el insert
	 */
	private String getInsertByVehicle(Vehicle v, boolean isSaleVehicle) {
		var vehicleInsert = "";
		if (isSaleVehicle) {
			vehicleInsert = "INSERT INTO taller.vehiculo (num_bastidor, cod_ventas, cod_cliente, cod_conce, tipo_vehiculo, marca, modelo, combustible, precio) VALUES ("
					+ "'" + v.getNum_bastidor() + "', " + v.getCod_ventas() + ", " + v.getCod_cliente() + ", "
					+ v.getCod_conce() + ", '" + v.getTipoVehiculo() + "', '" + v.getMarca() + "', '" + v.getModelo()
					+ "', '" + v.getCombustible() + "', " + v.getPrecio() + ");";
		} else {
			vehicleInsert = "INSERT INTO taller.vehiculo (num_bastidor, cod_ventas, cod_cliente, cod_conce, tipo_vehiculo, marca, modelo, combustible, precio) VALUES ("
					+ "'" + v.getNum_bastidor() + "', NULL, " + v.getCod_cliente() + ", " + v.getCod_conce() + ", '"
					+ v.getTipoVehiculo() + "', '" + v.getMarca() + "', '" + v.getModelo() + "', '" + v.getCombustible()
					+ "', " + v.getPrecio() + ");";
		}
		return vehicleInsert;
	}

}