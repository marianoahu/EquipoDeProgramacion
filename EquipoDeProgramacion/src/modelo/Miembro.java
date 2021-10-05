package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DAOMiembro;


public class Miembro {
	private int id;
	private String nombre;
	private int edad;
	private String cargo;

	public Miembro() {
	}

	public Miembro(int id, String nombre, int edad, String cargo) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.cargo = cargo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void anadirMiembro() throws SQLException {
		
		DAOMiembro.getInstance().insert(this);
	}

	public void borrarMiembro(int id) throws SQLException {
		
		DAOMiembro.getInstance().delete(id);
	}

	public void modificarMiembro() throws SQLException {
		
		DAOMiembro.getInstance().update(this);
	}

	public List<Miembro> listarMiembros() throws SQLException {
		
		List<Miembro> lista = DAOMiembro.getInstance().obtenerLista();
		return lista;

	}

	public String contarMiembros() throws SQLException {
		
		return DAOMiembro.getInstance().contarMiembros();
	}

	public String mediaEdadMiembros() throws SQLException {
		
		return DAOMiembro.getInstance().getEdadMedia();

	}

	public List<Miembro> buscarPorCargo(Object buscarCargo) throws SQLException {
		List<Miembro> lista = DAOMiembro.getInstance().buscarPorCargo(buscarCargo);
		 return lista;
	}
}
