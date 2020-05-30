package com.rodrigo.model.dao.repo;

import java.sql.CallableStatement;
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
import com.rodrigo.model.Rol;
import com.rodrigo.model.dao.ConnectionManager;
import com.rodrigo.model.dao.IDAO;
import com.rodrigo.model.dao.IPersonaDAO;

/**
 * @author Usuario
 *
 */
public class PersonaDAO implements IPersonaDAO  {

	private static final Logger LOGGER = Logger.getLogger(PersonaDAO.class.getCanonicalName());

	private static PersonaDAO INSTANCE = null;

	private static String SQL_GET_ALL 				= " CALL personasGetAll() ";
	private static String SQL_GET_ALL_ALUMNOS 		= " CALL alumnosGetAll() ";
	private static String SQL_GET_ALL_PROFESORES 	= " CALL profesoresGetAll() ";
	private static String SQL_GET_BY_ROL 			= " CALL Sin Implementar ";
	private static String SQL_GET_BY_ID 			= " CALL personasGetById(?) ";

	private static String SQL_DELETE				= " CALL personaDelete(?) ";
	private static String SQL_INSERT				= " INSERT INTO persona ( nombre, avatar, sexo, rol_id) VALUES ( ?, ?, ?, ? ); ";
	private static String SQL_UPDATE				= " UPDATE persona SET nombre = ?, avatar = ?, sexo = ? WHERE id = ?;";
	
	private static String SQL_ASIGNAR_CURSO		= " INSERT INTO persona_has_curso (persona_id, curso_id) VALUES ( ?, ?); ";
	private static String SQL_ELIMINAR_CURSO		= " DELETE FROM persona_has_curso WHERE persona_id = ? AND curso_id = ?; ";
	

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
				CallableStatement s = con.prepareCall(SQL_GET_ALL);
				//PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = s.executeQuery();

		) {
			LOGGER.info(s.toString());

			while (rs.next()) {
				// devuelve una persona y lo añade a la lista
				mapper(rs, hmPersonas);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			LOGGER.info("PersonaDAO - Get All - SQL Exception ");
		}

		// convert hashmap to array
		registros = new ArrayList<Persona> ( hmPersonas.values() );
		
		return registros;
	}
	
	@Override
	public List<Persona> getAllAlumnos() {
		
		LOGGER.info("getAllAlumnos(): PersonaDAO");
		ArrayList<Persona> alumnos = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmAlumno = new HashMap<Integer, Persona>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				CallableStatement s = con.prepareCall(SQL_GET_ALL_ALUMNOS);
				//PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_ALUMNOS);
				ResultSet rs = s.executeQuery();

			){
			LOGGER.info(s.toString());

			while (rs.next()) {
			
				alumnosMapper(rs, hmAlumno);

			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			LOGGER.info("getAllAlumnos(): Error al obtener la lista de los Alumnos ");
			//throw new SQLException("Error al obtener la lista de los Alumnos");
			
		}
		
		alumnos = new ArrayList<Persona>(hmAlumno.values());
		return alumnos;
	}

	@Override
	public List<Persona> getAllProfesores() throws Exception{
		
		LOGGER.info("getAllProfesores(): PersonaDAO");
		
		ArrayList<Persona> profesores = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmProfesor = new HashMap<Integer, Persona>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				CallableStatement s = con.prepareCall(SQL_GET_ALL_PROFESORES);
				//PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_PROFESORES);
				ResultSet rs = s.executeQuery();

			){
			LOGGER.info(s.toString());

			while (rs.next()) {
				
				profesorMapper(rs, hmProfesor);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			LOGGER.info("getAllAlumnos(): Error al obtener la lista de los Profesores ");
			throw new SQLException("Error al obtener la lista de los Profesores");
			
		}
		
		profesores = new ArrayList<Persona>(hmProfesor.values());
		return profesores;
	}
	
	@Override
	public List<Persona> getAllByRol(Rol rol) throws Exception {
		
		LOGGER.info("Get All By Rol - PersonaDAO ");

		String rolOpcion = "";
		
		if(rol.getId() == 1) {
			
			rolOpcion = SQL_GET_BY_ROL; 
			
		} else if(rol.getId() == 2) {
			
			rolOpcion = SQL_GET_BY_ROL; 
		}
		
		ArrayList<Persona> registros = new ArrayList<Persona>();
		HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(rolOpcion);
				ResultSet rs = pst.executeQuery();

		) {
			LOGGER.info(pst.toString());

			while (rs.next()) {
				// devuelve una persona y lo añade a la lista
				mapper(rs, hmPersonas);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			LOGGER.info("PersonaDAO - Get All By Rol - SQL Exception ");
		}

		// convert hashmap to array
		registros = new ArrayList<Persona> ( hmPersonas.values() );
		// TODO getAllByRol-falta implementar
		
		return registros;
	}
	
	@Override
	public Persona getById(int id) throws Exception {

		LOGGER.info("getById DAO Persona");
		Persona registro = null;

		try (
				Connection con = ConnectionManager.getConnection();
				//PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);
				CallableStatement s = con.prepareCall(SQL_GET_BY_ID);

		) {
			s.setInt(1, id);
			LOGGER.info(s.toString());

			try (ResultSet rs = s.executeQuery()) {

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

//	@Override
//	public Persona getByNombre(String nombre) throws Exception {
//		
//		LOGGER.info("getByNombre DAO Persona");
//		Persona registro = null;
//
//		try (
//				Connection con = ConnectionManager.getConnection();
//				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_NOMBRE);
//
//		) {
//			pst.setString(1, nombre);
//			LOGGER.info(pst.toString());
//
//			try (ResultSet rs = pst.executeQuery()) {
//
//				HashMap<Integer, Persona> hmPersonas = new HashMap<Integer, Persona>();
//				if (rs.next()) {
//
//					registro = mapper(rs, hmPersonas);
//
//				} else {
//					throw new Exception("Ups! Registro NO encontrado para Nombre " + nombre);
//				}
//			}
//		} catch (SQLException e) {
//			LOGGER.log(Level.SEVERE, "Exception de SQL", e);
//			//e.printStackTrace();
//		}
//
//		return registro;
//	}
	
	@Override
	public Persona delete(int id) throws Exception, SQLException {

		LOGGER.info("DELETE DAO Persona");
		Persona registro = null;

		// Recuperamos Persona antes de eliminar
		registro = getById(id);

		try (
				Connection con = ConnectionManager.getConnection();
				//PreparedStatement pst = con.prepareStatement(SQL_DELETE);
				CallableStatement s = con.prepareCall(SQL_DELETE);

		) {
			s.setInt(1, id);
			LOGGER.info(s.toString());

			// eliminamos la Persona
			int affectedRows = s.executeUpdate();

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
				//TODO Averiguar como aplicar callableStatement a lo de arriba (registerOutParameter ???)
		) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getAvatar());
			pst.setString(3, pojo.getSexo());
			pst.setObject(4, pojo.getRol().getId());

			//s.registerOutParameter(5, java.sql.Types.INTEGER); ???
			
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
			//pst.setObject(4, pojo.getRol().getId());
			pst.setInt(4, pojo.getId());

			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				
				LOGGER.warning("No se ha modificado el registro para el idAlumno=" + pojo.getId());
				throw new Exception("No se puede Actualizar el registro con el id: " + pojo);
			}
			
			LOGGER.info("UPDATE ejecutado correctamente de: " + pojo);
			
		} catch (SQLException e) {
			// getMessage(): lanzaría violate constraint exception
			//throw new Exception("Ups! No se puede Actualizar el registro: " + e.getMessage());
			throw new Exception("Ups! No se puede Actualizar el registro: ");
		}

		return pojo;
	}

	@Override
	public boolean asignarCurso( int idPersona, int idCurso ) throws Exception, SQLException {
		
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
	
	@Override
	public boolean eliminarCurso( int idPersona, int idCurso ) throws Exception, SQLException {
		
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
		
		//Se añade el Rol
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setTipo(rs.getString("rol_tipo"));

		//Si no existe en el HashMap se crea
		if( p == null) {
			
			p = new Persona();
			p.setId(key);
			p.setNombre( rs.getString("persona_nombre"));
			p.setAvatar( rs.getString("persona_avatar"));
			p.setSexo( rs.getString("persona_sexo"));
			p.setRol(rol);
			
		}
		
		//Se añade el Curso
		int idCurso = rs.getInt("curso_id");
		
		if ( idCurso != 0) {
			Curso c = new Curso();
			c.setId(idCurso);
			c.setNombre(rs.getString("curso_nombre"));
			c.setPrecio( rs.getDouble("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));
			
//			Persona profe = new Persona();
//			
//			profe.setId(rs.getInt("profesor_id"));
//			profe.setNombre( rs.getString("profesor_nombre"));
//			profe.setAvatar( rs.getString("profesor_avatar"));
//			profe.setSexo( rs.getString("profesor_sexo"));
//			profe.setRol(rol);
//			
//			c.setProfesor(profe);
		
			p.getCursos().add(c);
		}	
	
		
		//Actualizar HashMap
		hm.put(key, p);

		return p;
	}
	
private Persona alumnosMapper(ResultSet rs, HashMap<Integer,Persona> hmAlumno) throws SQLException {
		
		int key = rs.getInt("persona_id");
		Persona alumno = hmAlumno.get(key);
		
		//Se añade el Rol
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setTipo(rs.getString("rol_tipo"));
		
		//Si no existe en el HashMap se crea
		if( alumno == null) {
			
			alumno = new Persona();
			alumno.setId(key);
			alumno.setNombre( rs.getString("persona_nombre"));
			alumno.setAvatar( rs.getString("persona_avatar"));
			alumno.setSexo( rs.getString("persona_sexo"));
			alumno.setRol(rol);
			
		}
		
		//Se añade el Curso
		int idCurso = rs.getInt("curso_id");
		
		if ( idCurso != 0) {
			Curso c = new Curso();
			c.setId(idCurso);
			c.setNombre(rs.getString("curso_nombre"));
			c.setPrecio( rs.getDouble("curso_precio"));
			c.setImagen(rs.getString("curso_imagen"));	
			
			//Se añade el Profesor
			Persona profe = new Persona();
					
			//profe.setId(rs.getInt("profesor_id"));
			profe.setNombre(rs.getString("profesor_nombre"));
//			profe.setAvatar(rs.getString("profesor_avatar"));
//			profe.setSexo(rs.getString("profesor_sexo"));
//			profe.setRol(rol);

			c.setProfesor(profe);
			alumno.getCursos().add(c);
		}	
		
		//Actualizar HashMap
		hmAlumno.put(key, alumno);

		return alumno;
	}

	
	
		private Persona profesorMapper(ResultSet rs, HashMap<Integer, Persona> hmProfesor) throws SQLException {
			
			int key = rs.getInt("profesor_id");
			Persona profesor = hmProfesor.get(key);
			
			//Se añade el Rol
			Rol rol = new Rol();
			rol.setId(rs.getInt("rol_id"));
			rol.setTipo(rs.getString("rol_tipo"));
			
			if(profesor == null) {
				
				profesor = new Persona();
				
				profesor.setId(key);
				profesor.setNombre(rs.getString("profesor_nombre"));
				profesor.setAvatar(rs.getString("profesor_avatar"));
				profesor.setSexo(rs.getString("profesor_sexo"));
				profesor.setRol(rol);
				
			}
			
			//Se añade el Curso
			int idCurso = rs.getInt("curso_id");
					
			if ( idCurso != 0) {
				
				Curso c = new Curso();
				
				c.setId(idCurso);
				c.setNombre(rs.getString("curso_nombre"));
				c.setImagen(rs.getString("curso_imagen"));
				c.setPrecio( rs.getDouble("curso_precio"));
				
				
				//Se añade el Profesor
				Persona p = new Persona();
				
				p.setId(rs.getInt("profesor_id"));
				p.setNombre(rs.getString("profesor_nombre"));
				p.setAvatar(rs.getString("profesor_avatar"));
				p.setSexo(rs.getString("profesor_sexo"));
				p.setRol(rol);
				
				c.setProfesor(p);
				profesor.getCursos().add(c);
				
				}
			
			//Actualizar HashMap
			hmProfesor.put(key, profesor);
			
			return profesor;
		}

	
	


}
