package com.rodrigo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.rodrigo.model.Curso;

public class CursoDAO implements IDAO<Curso> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getCanonicalName());

	private static CursoDAO INSTANCE = null;

	private static String SQL_GET_ALL = "SELECT id, nombre, imagen, precio FROM curso ORDER BY id DESC LIMIT 100;";
	private static String SQL_GET_LIKE_NOMBRE   = "SELECT id, nombre, precio, imagen FROM curso WHERE nombre LIKE ? ORDER BY id DESC LIMIT 100; ";
	
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
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();
			){
				LOGGER.info(pst.toString());
				
				while(rs.next()) {
					registros.add(mapper(rs));
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return registros;
	}
	
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
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public Curso delete(int id) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public Curso insert(Curso pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public Curso update(Curso pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}
	
	private Curso mapper(ResultSet rs) throws SQLException {
		Curso c = new Curso();
		
		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));
		c.setImagen(rs.getString("imagen"));
		c.setPrecio(rs.getDouble("precio"));
		
		return c;
	}

}
