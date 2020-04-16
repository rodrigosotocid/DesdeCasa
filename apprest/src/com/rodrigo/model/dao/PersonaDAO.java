package com.rodrigo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.rodrigo.model.Persona;

public class PersonaDAO implements IDAO<Persona> {

	private static final Logger LOGGER = Logger.getLogger(PersonaDAO.class.getCanonicalName());

	private static PersonaDAO INSTANCE = null;

	private static String SQL_GET_ALL = "SELECT id, nombre, avatar,sexo FROM persona ORDER BY id DESC LIMIT 500;";
	private static String SQL_GET_BY_ID = "SELECT id, nombre, avatar, sexo FROM persona WHERE id = ?;";
	private static String SQL_DELETE = "DELETE FROM persona WHERE id = ?;";
	private static String SQL_INSERT = "INSERT INTO persona (nombre, avatar, sexo) VALUES(?,?,?);";
	private static String SQL_UPDATE = "UPDATE persona SET nombre = ?, avatar = ?, sexo = ? WHERE id = ?;";

	// constructor privado
	private PersonaDAO() {
		super();
	}

	public synchronized static PersonaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PersonaDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Persona> getAll() {

		LOGGER.info("getAll");

		ArrayList<Persona> registros = new ArrayList<Persona>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();

		) {
			LOGGER.info(pst.toString());

			while (rs.next()) {
				//devuelve una persona y lo añade a la lista
				registros.add(mapper(rs));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return registros;
	}

	@Override
	public Persona getById(int id) throws Exception {
		
		LOGGER.info("getById");

		Persona registro = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);

		) {
			pst.setInt(1, id);
			LOGGER.info(pst.toString());

			try(ResultSet rs = pst.executeQuery()){
				
				if (rs.next()) {
					
					registro = mapper(rs);
					
				}else {
					throw new Exception("Registro no encontrado para Id " + id);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return registro;
	}

	@Override
	public Persona delete(int id) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public Persona insert(Persona pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public Persona update(Persona pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}
	
	private Persona mapper(ResultSet rs) throws SQLException {
		Persona p = new Persona();
		p.setId(rs.getInt("id"));
		p.setNombre(rs.getString("nombre"));
		p.setAvatar(rs.getString("avatar"));
		p.setSexo(rs.getString("sexo"));
		return p;
	}

}
