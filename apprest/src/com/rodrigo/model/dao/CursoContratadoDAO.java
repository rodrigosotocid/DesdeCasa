package com.rodrigo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.rodrigo.model.Curso;
import com.rodrigo.model.CursoContratado;
import com.rodrigo.model.Persona;

public class CursoContratadoDAO implements IDAO<CursoContratado> {
	
	private static final Logger LOGGER = Logger.getLogger(CursoContratadoDAO.class.getCanonicalName());
	
	private static CursoContratadoDAO INSTANCE = null;
	
	private static String SQL_GET_ALL = "SELECT persona_id, curso_id FROM cursos_contratados LIMIT 500;";
	private static String SQL_GET_BY_ID = "SELECT persona_id, curso_id FROM cursos_contratados WHERE id = ?;";
	private static String SQL_DELETE = "DELETE FROM cursos_contratados WHERE persona_id = ? AND curso_id = ?;";
	private static String SQL_INSERT = "INSERT INTO cursos ( titulo, imagen, precio) VALUES ( ?, ?, ? ); ";
	private static String SQL_UPDATE = "UPDATE cursos SET titulo = ?, imagen = ?, precio = ? WHERE id = ?;";
	
	private CursoContratadoDAO() {
		super();
	}
	
	public synchronized static CursoContratadoDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CursoContratadoDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<CursoContratado> getAll() {
		LOGGER.info("Get-All Curso Contratado DAO");
		
		ArrayList<CursoContratado> registros = new ArrayList<CursoContratado>();
		
		try (
				Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();
			){
				LOGGER.info(pst.toString());
				
				while(rs.next()) {
					
					Persona p = new Persona();
					Curso c = new Curso();
					CursoContratado ccon = new CursoContratado();
					
//					Persona p = new Persona();
//					p.setId(rs.getInt("id"));
//					p.setNombre(rs.getString("nombre"));
//					p.setAvatar(rs.getString("avatar"));
//					p.setSexo(rs.getString("sexo"));
//					
//					Curso c = new Curso();
//					c.setId(rs.getInt("id"));
//					c.setTitulo(rs.getString("titulo"));
//					c.setImagen(rs.getString("imagen"));
//					c.setPrecio(rs.getDouble("precio"));
					
					ccon.setPersona(p);
					ccon.setCurso(c);
					
					registros.add(ccon);
					
					//return registros;
					//registros.add(mapper(rs));
				}
			
			} catch (SQLException e) {
				 e.getMessage();
			}
		return registros;
	}

	@Override
	public CursoContratado getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CursoContratado delete(int id) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public CursoContratado insert(CursoContratado pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}

	@Override
	public CursoContratado update(CursoContratado pojo) throws Exception, SQLException {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
	}
	
//	private CursoContratado mapper(ResultSet rs) throws SQLException {
//		CursoContratado cc = new CursoContratado();
//		
//		c.setId(rs.getInt("id"));
//		c.setTitulo(rs.getString("titulo"))
//		
//		return cc;
//	}
	
}
