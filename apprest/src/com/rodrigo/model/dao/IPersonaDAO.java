package com.rodrigo.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.rodrigo.model.Persona;
import com.rodrigo.model.Rol;

public interface IPersonaDAO extends IDAO<Persona>{

	static final String EXCEPTION_ROL = "No existe el rol o es null";
	static final String EXCEPTION_PERSONA_NO_EXISTE = "No existe la persona";
	static final String EXCEPTION_CURSO_NO_EXISTE = "No existe el curso";
	static final String EXCEPTION_CURSO_PERSONA_DUPLICADO = "Ya esta asociado el curso a la persona";
	
	/**
	 * Método que devuelve todas las Personas filtradas por Alumnos
	 * @return
	 * @throws Exception si no encuentra alumnos
	 */
	List<Persona> getAllAlumnos() throws Exception;
	
	/**
	 * Método que devuelve todas las Personas filtradas por Profesores
	 * @return
	 */
	List<Persona> getAllProfesores() throws Exception;
	
	/**
	 * Listado de personas que filtraremos por su Rol para discriminar entre "Profesores" y "Alumnos"
	 * @param rol
	 * @return
	 * @throws Exception sil Rol == null o NO EXISTE
	 */
	List<Persona> getAllByRol(Rol rol) throws Exception;
	
	//TODO documentar y decir cuando lanza las posibles Exception, SQLException
	/**
	 * 
	 * @param idPersona
	 * @param idCurso
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	boolean asignarCurso(int idPersona, int idCurso) throws Exception, SQLException;
	
	/**
	 * 
	 * @param idPersona
	 * @param idCurso
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	boolean eliminarCurso(int idPersona, int idCurso) throws Exception, SQLException;
}
