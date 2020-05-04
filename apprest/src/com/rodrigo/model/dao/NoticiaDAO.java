package com.rodrigo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.rodrigo.model.Curso;
import com.rodrigo.model.Noticia;

public class NoticiaDAO implements IDAO<Noticia>{

	private static final Logger LOGGER = Logger.getLogger(NoticiaDAO.class.getCanonicalName());
	
	public static NoticiaDAO INSTANCE = null;
	
	private static String SQL_GET_ALL = "SELECT * FROM noticia ORDER BY id DESC LIMIT 100;";
	
	private NoticiaDAO() {
		super();
	}
	
	public synchronized static NoticiaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NoticiaDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Noticia> getAll() { 
		
		LOGGER.info("GetAll Noticia DAO");
		ArrayList<Noticia> registros = new ArrayList<Noticia>();

		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();
				) {

			//pst.setString(1, pojo.getNombre());

			LOGGER.info(pst.toString());

			while(rs.next()) {
				registros.add(mapper(rs));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return registros;
	}

	@Override
	public Noticia getById(int id) throws Exception {
		throw new UnsupportedOperationException("MÉTODO SIN IMPLEMENTAR");
	}

	@Override
	public Noticia getByNombre(String nombre) throws Exception {
		throw new UnsupportedOperationException("MÉTODO SIN IMPLEMENTAR");
	}

	@Override
	public Noticia delete(int id) throws Exception, SQLException {
		throw new UnsupportedOperationException("MÉTODO SIN IMPLEMENTAR");
	}

	@Override
	public Noticia insert(Noticia pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("MÉTODO SIN IMPLEMENTAR");
	}

	@Override
	public Noticia update(Noticia pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("MÉTODO SIN IMPLEMENTAR");
	}
	
	private Noticia mapper(ResultSet rs) throws SQLException {
		
		Noticia n = new Noticia();
		
		n.setId(rs.getInt("id"));
		n.setTitulo(rs.getString("titulo"));
		n.setFecha(rs.getDate("fecha"));
		n.setContenido(rs.getString("contenido"));
		
		return n;
	}
}
