package common;

public class Constants {
	// FICHERO BBDD CONFIG------------
	public static final String CONFIG_FILE_PATH = "src\\conection.properties";
	// ----------------

	// CONSULTAS-------
	// UserDAO
	public static final String SELECT_MECHANICALS_ALL_DATA = "SELECT * FROM taller.persona INNER JOIN taller.empleado ON persona.dni = empleado.dni INNER JOIN taller.mecanico ON empleado.cod_empleado = mecanico.cod_empleado;";
	// ClientDAO
	public static final String SELECT_CLIENTS_ALL_DATA = "SELECT cliente.cod_cliente, cliente.dni, persona.nombre, persona.apellidos, persona.telefono FROM persona INNER JOIN cliente on persona.dni = cliente.dni";
	// EmployeeDAO
	public static final String SELECT_EMPLOYEES_ALL_DATA = "SELECT * FROM taller.persona INNER JOIN taller.empleado ON persona.DNI = empleado.dni";
	// VehicleDAO
	public static final String SELECT_VEHICLES = "SELECT * FROM taller.vehiculo";
	public static final String SELECT_CARS_AlL_DATA = "SELECT * FROM taller.vehiculo INNER JOIN taller.coche ON vehiculo.num_bastidor = coche.num_bastidor;";
	public static final String SELECT_MOTORCICLES_ALL_DATA = "SELECT * FROM taller.vehiculo INNER JOIN taller.motocicleta ON vehiculo.num_bastidor = motocicleta.num_bastidor;";
	public static final String SELECT_MOPEDS_ALL_DATA = "SELECT * FROM taller.vehiculo INNER JOIN taller.ciclomotor ON vehiculo.num_bastidor = ciclomotor.num_bastidor;";
	public static final String SELECT_VEHICLES_UNSOLD = "SELECT * FROM taller.vehiculo WHERE vehiculo.cod_cliente is null";
	public static final String SELECT_COUNT_VEHICLES_UNSOLD = "SElECT count(*) from taller.vehiculo where cod_cliente is null";
	public static final String SELECT_VEHICLES_SOLD = "SELECT ventas.cod_empleado, persona.nombre, persona.apellidos, vehiculo.marca, vehiculo.modelo, vehiculo.precio, vehiculo.tipo_vehiculo, persona.dni FROM taller.ventas, taller.persona, taller.vehiculo, taller.empleado WHERE ventas.cod_ventas = vehiculo.cod_ventas AND ventas.cod_empleado = empleado.cod_empleado AND empleado.dni = persona.dni ORDER BY ventas.cod_empleado";
	// SellingPropositionDAO
	public static final String SELECT_PROPOSITION = "SELECT * FROM taller.propuesta;";
	// RepairDAO
	public static final String SELECT_REPAIRS = "SELECT * FROM taller.reparacion;";
	// ----------------

}
