package dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Miembro;

public class DAOMiembro {
	private Connection con = null;

	private static DAOMiembro instance = null;

	private DAOMiembro() throws SQLException {
		con = DBConnection.getConnection();
	}

	// Se utiliza patron sigleton para todas las consultas o modificaciones a la
	// base
	public static DAOMiembro getInstance() throws SQLException {
		if (instance == null) {
			instance = new DAOMiembro();
		}
		return instance;
	}

	public void insert(Miembro m) throws SQLException {
		// CRUD anadir miembro a la base
		PreparedStatement ps = con.prepareStatement("INSERT INTO miembros(nombre, edad, cargo) VALUES (?, ?, ?)");
		ps.setString(1, m.getNombre());
		ps.setInt(2, m.getEdad());
		ps.setString(3, m.getCargo());

		ps.executeUpdate();

		ps.close();
	}

	public void delete(int id) throws SQLException {
		// CRUD borrar miembro de la base
		if (id <= 0)
			return;

		PreparedStatement ps = con.prepareStatement("DELETE FROM Miembros WHERE idMiembros = ?");
		ps.setInt(1, id);

		ps.executeUpdate();

		ps.close();
	}

	public void update(Miembro m) throws SQLException {
		// CRUD actualiar miembro en la base
		if (m.getId() != 0) {

			PreparedStatement ps = con
					.prepareStatement("UPDATE miembros SET nombre = ?, edad = ?, cargo = ? WHERE idMiembros = ?");

			ps.setInt(4, m.getId());
			ps.setString(1, m.getNombre());
			ps.setInt(2, m.getEdad());
			ps.setString(3, m.getCargo());

			ps.executeUpdate();

			ps.close();

		}
	}

	public List<Miembro> obtenerLista() throws SQLException {

		PreparedStatement ps = con.prepareStatement("SELECT * FROM miembros");
		ResultSet rs = ps.executeQuery();

		List<Miembro> result = null;

		while (rs.next()) {
			if (result == null)
				result = new ArrayList<>();

			result.add(new Miembro(rs.getInt("idMiembros"), rs.getString("nombre"), rs.getInt("edad"),
					rs.getString("cargo")));
		}

		rs.close();
		ps.close();

		return result;
	}

	public String contarMiembros() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT COUNT(idMiembros) FROM miembros");
		ResultSet rs = ps.executeQuery();
		rs.next();
		String cantidadMiembros = rs.getString(1);

		rs.close();
		ps.close();
		return cantidadMiembros;
	}

	public String getEdadMedia() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT AVG(edad) FROM miembros");
		ResultSet rs = ps.executeQuery();
		rs.next();
		float f = Float.parseFloat(rs.getString(1));
		String edadMedia = String.format("%.02f", f);

		rs.close();
		ps.close();
		return edadMedia;

	}

	public List<Miembro> buscarPorCargo(Object buscarCargo) throws SQLException {
		boolean cargoExiste = true;
		Miembro m = new Miembro();
		String cargo = String.valueOf(buscarCargo);
		// obtiene a traves del cargo seleccionado en que ahora es un string en cargo,
		// una lista de los miembros con ese cargo
		PreparedStatement ps = con.prepareStatement("SELECT * FROM miembros WHERE cargo = ?");
		ps.setString(1, cargo);

		ResultSet rs = ps.executeQuery();

		List<Miembro> result = null;

		while (rs.next()) {
			if (result == null)
				result = new ArrayList<>();
			result.add(new Miembro(rs.getInt("idMiembros"), rs.getString("nombre"), rs.getInt("edad"),
					rs.getString("cargo")));
		}

		rs.close();
		ps.close();

		return result;
	}

	public Miembro obtenerPorId(int id) throws SQLException {
		// obtiene a traves de la linea seleccionada el id del miembro y asi completa
		// los campos.
		PreparedStatement ps = con.prepareStatement("SELECT * FROM miembros WHERE idMiembros = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		Miembro result = null;

		if (rs.next()) {
			result = new Miembro(rs.getInt("idMiembros"), rs.getString("nombre"), rs.getInt("edad"),
					rs.getString("cargo"));

		}
		rs.close();
		ps.close();

		return result;

	}
}
