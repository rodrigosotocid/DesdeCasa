package com.rodrigo.model.dao.repo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.rodrigo.model.Curso;
import com.rodrigo.model.Persona;
import com.rodrigo.model.Rol;
import com.rodrigo.model.dao.ConnectionManager;
import com.rodrigo.model.dao.IDAO;

public class CursoDAO implements IDAO<Curso> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getCanonicalName());

	private static CursoDAO INSTANCE = null;

	private static String SQL_GET_ALL 	 = "CALL cursosGetAll()";	
	private static String SQL_GET_BY_ID = "CALL cursosGetById(?)";	
	private static String SQL_GET_LIKE_NOMBRE   = "SELECT id, nombre, precio, imagen FROM curso WHERE nombre LIKE ? ORDER BY id DESC LIMIT 100; ";
	private final static String SQL_UPDATE = "UPDATE curso SET persona_id = ? WHERE id = ?;";
	
	private CursoDAO() {
		super();
	}

	public synchronized static CursoDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CursoDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Curso> getAll() {
		
		LOGGER.info("GetAll Curso DAO");
		ArrayList<Curso> registros = new ArrayList<Curso>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				//PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				CallableStatement s = con.prepareCall(SQL_GET_ALL);
				ResultSet rs = s.executeQuery();
			){
				LOGGER.info(s.toString());
				
				while(rs.next()) {
					registros.add(mapper(rs));
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return registros;
	}
	
	/** 
	 * Busca cursos por nombre que coincidan LIKE con el parametro busqueda
	 * @param busqueda String: Término a buscar dentro de la columna nombre 
	 * @return List<Curso>
	 */
	public List<Curso> getAllLikeNombre(String busqueda) {
		
		LOGGER.info("getAll Curso DAO %or%");
		ArrayList<Curso> registros = new ArrayList<Curso>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_LIKE_NOMBRE);
				) {
			
			//CUIDADO: LOS SIMBOLOS DEL LIKE(%OR%) NO SE PUEDEN PONER EN LA SQL, SIEMPRE EN EL PST
			pst.setString(1, "%" + busqueda + "%");
			
			try(ResultSet rs = pst.executeQuery()){
				
				LOGGER.info(pst.toString());
				while(rs.next()) {
					registros.add(mapper(rs));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.severe("SQLException: No se puede listar filtro por nombre");
		}
		
		return registros;
	}
	
	
	@Override
	public Curso getById(int id) throws Exception {
		
		Curso registro = null;
		
		try (
				Connection con = ConnectionManager.getConnection();
				//PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)
				CallableStatement s = con.prepareCall(SQL_GET_BY_ID);
			  ){
			
			s.setInt(1, id);
			LOGGER.info(s.toString());
			
			try (ResultSet rs = s.executeQuery()) {
				
				if(rs.next()) {
					
					registro = mapper(rs);
					
				} else {
					
					throw new Exception("Registro no encontrado para el Id: " + id);
					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}                                                                                                              
		return registro;
	}
	
	@Override
	public Curso delete(int id) throws Exception, SQLException {
		throw new UnsupportedOperationException("SIN IMPLEMENTAR");
	}

	@Override
	public Curso insert(Curso pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("SIN IMPLEMENTAR");
	}

	@Override
	public Curso update(Curso pojo) throws Exception, SQLException {
		
		LOGGER.info("UPDATE DAO Curso: (" + pojo + ")");
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);
		) {
			pst.setInt(1, pojo.getProfesor().getId());
			pst.setInt(2, pojo.getId());
			
			LOGGER.info(pst.toString());

			// eliminamos la Persona
			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				
				LOGGER.warning("No se ha modificado el registro para el curso id= " + pojo.getId());
				throw new Exception("No se puede Actualizar el registro con el id: " + pojo);
			}
			
			LOGGER.info("UPDATE ejecutado correctamente de: " + pojo);
			
		} catch (SQLException e) {
			
			LOGGER.warning("Error al modificar el curso");
			throw new SQLException("Ups! No se puede Actualizar el registro: ");
		}
		
		return pojo;
	}
	
	private Curso mapper(ResultSet rs) throws SQLException {
		
		Curso c = new Curso();
		Persona profesor = new Persona();
		Rol rol = new Rol();
		
		//CURSO
		c.setId(rs.getInt("curso_id"));
		c.setNombre(rs.getString("curso_nombre"));
		c.setImagen(rs.getString("curso_imagen"));
		c.setPrecio(rs.getFloat("curso_precio"));
		
		
		//PROFESOR
		profesor.setId(rs.getInt("profesor_id"));
		profesor.setNombre(rs.getString("profesor_nombre"));
		profesor.setAvatar(rs.getString("profesor_avatar"));
		profesor.setSexo(rs.getString("profesor_sexo"));
		profesor.setRol(rol);
		
		//ROL
		//rol.setId(rs.getInt("rol_id"));
		//rol.setTipo(rs.getString("rol_tipo"));
		
		c.setProfesor(profesor);
		
		return c;
	}



}
