package com.rodrigo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rodrigo.model.Curso;
import com.rodrigo.model.Persona;

public class PersonaDAO implements IDAO<Persona> {

	private static final Logger LOGGER = Logger.getLogger(PersonaDAO.class.getCanonicalName());

	private static PersonaDAO INSTANCE = null;

	//TODO Convertir consultas en Procedimientos Almacenados!
	//ATENTO!! Que los IDs de las tablas intermedias SQL los generó al revés de lo que lo hizo Ander
	private static String SQL_GET_ALL			= " SELECT \r\n" + 
												"p.id as persona_id,\r\n" + 
												"p.nombre as persona_nombre, \r\n" + 
												"p.avatar as persona_avatar,\r\n" + 
												"p.sexo as persona_sexo,\r\n" + 
												"p.rol_id as persona_rol,\r\n" + 
												"c.id as curso_id,\r\n" + 
												"c.nombre as curso_nombre,\r\n" + 
												"c.precio as curso_precio,\r\n" + 
												"c.imagen  as curso_imagen\r\n" + 
												"FROM (persona p LEFT JOIN persona_has_curso pc ON p.id = pc.persona_id)\r\n" + 
												"LEFT JOIN curso c ON pc.curso_id = c.id;";

	private static String SQL_GET_BY_ID		= "  SELECT \n" + 
												  "	 p.id as persona_id,\n" + 
												  "	 p.nombre as persona_nombre,\n" + 
												  "	 p.avatar as persona_avatar,\n" + 
												  "  p.sexo as persona_sexo,\n" +
												  "  p.rol_id as persona_rol,\r\n" +
												  "	 c.id as curso_id,\n" + 
												  "	 c.nombre as curso_nombre,\n" + 
												  "	 c.precio as curso_precio,\n" + 
												  "	 c.imagen  as curso_imagen\n" + 
												  "  FROM (persona p LEFT JOIN persona_has_curso pc ON p.id = pc.persona_id)\n" + 
												  "  LEFT JOIN curso c ON pc.curso_id = c.id WHERE p.id = ? ;   ";

	private static String SQL_GET_BY_NOMBRE		= "  SELECT \n" + 
												  "	 p.id as persona_id,\n" + 
												  "	 p.nombre as persona_nombre,\n" + 
												  "	 p.avatar as persona_avatar,\n" + 
												  "  p.sexo as persona_sexo,\n" +
												  "  p.rol_id as persona_rol,\r\n" +
												  "	 c.id as curso_id,\n" + 
												  "	 c.nombre as curso_nombre,\n" + 
												  "	 c.precio as curso_precio,\n" + 
												  "	 c.imagen  as curso_imagen\n" + 
												  "  FROM (persona p LEFT JOIN persona_has_curso pc ON p.id = pc.persona_id)\n" + 
												  "  LEFT JOIN curso c ON pc.curso_id = c.id WHERE p.nombre = ? ;   ";
	
	private static String SQL_DELETE			= "DELETE FROM persona WHERE id = ?; ";
	private static String SQL_INSERT			= "INSERT INTO persona ( nombre, avatar, sexo, rol_id) VALUES ( ?, ?, ?, ? ); ";
	private static String SQL_UPDATE			= "UPDATE persona SET nombre = ?, avatar = ?, sexo = ?, rol_id = ? WHERE id = ?;";
	
	private static String SQL_ASIGNAR_CURSO	= "INSERT INTO persona_has_curso (persona_id, curso_id) VALUES ( ?, ?); ";
	private static String SQL_ELIMINAR_CURSO	= "DELETE FROM persona_has_curso WHERE persona_id = ? AND curso_id = ?; ";
	

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

		LOGGER.info("Get-All DAO Persona ");

		ArrayList<Persona> registros = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();

		) {
			LOGGER.info(pst.toString());

			while (rs.next()) {
				// devuelve una persona y lo añade a la lista
				mapper(rs, hmPersonas);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			LOGGER.info("dao sql exception ");
		}

		// convert hashmap to array
		registros = new ArrayList<Persona> ( hmPersonas.values() );
		return registros;
	}

	@Override
	public Persona getById(int id) throws Exception {

		LOGGER.info("getById DAO Persona");
		Persona registro = null;

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);

		) {
			pst.setInt(1, id);
			LOGGER.info(pst.toString());

			try (ResultSet rs = pst.executeQuery()) {

				HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
				if (rs.next()) {

					registro = mapper(rs, hmPersonas);

				} else {
					throw new Exception("Ups! Registro NO encontrado para Id " + id);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return registro;
	}

	@Override
	public Persona getByNombre(String nombre) throws Exception {
		LOGGER.info("getByNombre DAO Persona");
		Persona registro = null;

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_NOMBRE);

		) {
			pst.setString(1, nombre);
			LOGGER.info(pst.toString());

			try (ResultSet rs = pst.executeQuery()) {

				HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
				if (rs.next()) {

					registro = mapper(rs, hmPersonas);

				} else {
					throw new Exception("Ups! Registro NO encontrado para Nombre " + nombre);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception de SQL", e);
			//e.printStackTrace();
		}

		return registro;
	}
	
	@Override
	public Persona delete(int id) throws Exception, SQLException {

		LOGGER.info("DELETE DAO Persona");
		Persona registro = null;

		// Recuperamos Persona antes de eliminar
		registro = getById(id);

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE);

		) {
			pst.setInt(1, id);
			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				LOGGER.info("affectedRows is != 1");
				throw new Exception("Ups! No se puede Eliminar el registro con id : " + id);
			}
		} catch (SQLException e) {
			LOGGER.info("DELETE DAO Persona: Violate Constraint Exception!");
			throw new SQLException(e.getMessage());
		}

		return registro;
	}

	@Override
	public Persona insert(Persona pojo) throws Exception, SQLException {

		LOGGER.info("INSERT DAO Persona");

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
		) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getAvatar());
			pst.setString(3, pojo.getSexo());
			pst.setInt(4, pojo.getRol());

			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {

				// recuperar ID generado automáticamente
				ResultSet rs = pst.getGeneratedKeys();
				
				if(rs.next()) {
					pojo.setId(rs.getInt(1));					
				}

			} else {
				throw new Exception("Ups! No se puede Modificar el registro con id : " + pojo);
			}
		} catch (SQLException e) {
			
			LOGGER.info("MANDA EXCEPCTION AL CONTROLADOR (INSERT-DAO): Violate Constraint Exception!");
			throw new SQLException("No se puede Modificar el registro: " + e.getMessage());
		}

		return pojo;
	}

	@Override
	public Persona update(Persona pojo) throws Exception, SQLException {
		//throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		
		LOGGER.info("UPDATE DAO Persona");

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);
		) {
			
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getAvatar());
			pst.setString(3, pojo.getSexo());
			pst.setInt(4, pojo.getRol());
			pst.setInt(5, pojo.getId());

			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				throw new Exception("Ups! No se puede Actualizar el registro con id 33 : " + pojo);
			}
			
		} catch (SQLException e) {
			// getMessage(): lanzaría violate constraint exception
			//throw new Exception("Ups! No se puede Actualizar el registro: " + e.getMessage());
			throw new Exception("Ups! No se puede Actualizar el registro: ");
		}

		return pojo;
	}

	public boolean asignarCurso(int idPersona, int idCurso) throws Exception, SQLException {
		
		boolean correcto = false;
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_ASIGNAR_CURSO);
				
			){
			pst.setInt(1, idPersona);
			pst.setInt(2, idCurso);
			LOGGER.info(pst.toString());
			
			//Eliminamos a la Persona
			int affectedRows = pst.executeUpdate();
			if(affectedRows == 1) {
				correcto = true;
			} else {
				correcto = false;
				throw new SQLException("ERROR DUPLICADO");
			}
		}
		
		return correcto;
	}
	
	public boolean eliminarCurso(int idPersona, int idCurso) throws Exception, SQLException {
		
		boolean correcto = false;
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_ELIMINAR_CURSO);
				
			) {
			pst.setInt(1, idPersona);
			pst.setInt(2, idCurso);
			LOGGER.info(pst.toString());
			
			//eliminamos la persona
			int affetedRows = pst.executeUpdate();	
			if (affetedRows == 1) {
				correcto = true;
			}else {
				throw new Exception("No se encontrado registro id_persona =" + idPersona + " id_curso=" + idCurso );		
			}
			
		}
		
		return correcto;
	}
	
	
	
	private Persona mapper(ResultSet rs, HashMap<Integer,Persona> hm) throws SQLException {
		
		int key = rs.getInt("persona_id");
		
		Persona p = hm.get(key);
		
		//Si no existe en el HashMap se crea
		if( p == null) {
			
			p = new Persona();
			p.setId(key);
			p.setNombre( rs.getString("persona_nombre"));
			p.setAvatar( rs.getString("persona_avatar"));
			p.setSexo( rs.getString("persona_sexo"));
			p.setRol(rs.getInt("persona_rol"));
		}
		
		//Se añade el Curso
		int idCurso = rs.getInt("curso_id");
		
		if ( idCurso != 0) {
			Curso c = new Curso();
			c.setId(idCurso);
			c.setNombre(rs.getString("curso_nombre"));
			c.setPrecio( rs.getFloat("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));			
			p.getCursos().add(c);
		}	
		
		//Actualizar HashMap
		hm.put(key, p);

		return p;
	}
	


}
