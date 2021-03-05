package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import model.SellingProposition;

public class SellingPropositionDAO extends AbstractDAO {

	public SellingPropositionDAO() {
		super();
	}

	/**
	 * Devuelve todas las proposiciones de venta
	 * 
	 * @return Lista de propuestas
	 */
	public List<SellingProposition> getSellingProposition() {
		var sellingProposition = new ArrayList<SellingProposition>();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(Constants.SELECT_PROPOSITION);
			while (rs.next()) {
				sellingProposition.add(new SellingProposition(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return sellingProposition;
	}

	/**
	 * Inserta en la BBDD la propuesta de venta que se le pasa como parámetro
	 * 
	 * @param sp propuesta de venta
	 */
	public void addSellingProposition(SellingProposition sp) {
		try {
			stm = con.createStatement();
			stm.executeUpdate(
					"INSERT INTO taller.propuesta (cod_propuesta, cod_cliente, cod_ventas, num_bastidor, fecha_validez) VALUES ("
							+ sp.getCod_propuesta() + ", " + sp.getCod_cliente() + ", " + sp.getCod_ventas() + ", '"
							+ sp.getNum_bastidor() + "', '" + sp.getFecha_validez() + "', " + sp.getPrecio() + ")");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public String getDni_cliente(Integer cod_cliente) {
		String dni = "";
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"SELECT cliente.dni from taller.cliente where cliente.cod_cliente = " + cod_cliente + ";");
			if (rs.next()) {
				dni = rs.getString(1);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return dni;
	}

	/**
	 * Busca la propuesta de venta por dni de cliente
	 * 
	 * @param dni
	 * @return
	 */
	public SellingProposition searchProposition(String dni) {
		SellingProposition proposition = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT * from taller.propuesta inner join taller.cliente where cliente.dni ='" + dni
					+ "'" + "AND cliente.cod_cliente = propuesta.cod_cliente;");
			if (rs.next()) {
				proposition = new SellingProposition(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return proposition;
	}

	/**
	 * Finaliza la venta de un vehículo a un cliente
	 * 
	 * @param sp
	 */
	public void finishSellingProposition(SellingProposition sp) {
		try {
			con.setAutoCommit(false);
			stm = con.createStatement();
			// Actualizar código de cliente, código de ventas del vehículo y el nuevo precio (propuesta final)
			stm.executeUpdate("UPDATE taller.vehiculo SET cod_ventas=" + sp.getCod_ventas() + ", cod_cliente="
					+ sp.getCod_cliente() + ", precio=" + sp.getPrecio() +  " WHERE vehiculo.num_bastidor=" + sp.getNum_bastidor() + ";");

			// Eliminar propuesta de venta
			stm.executeUpdate("DELETE FROM taller.propuesta WHERE propuesta.cod_propuesta=" + sp.getCod_propuesta() + ";");
			con.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			conectionRollback();
		}
	}

}
