package com.rodrigo.model.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para las operaciones básicas de CRUD
 * 
 * @author Usuario
 *
 * @param <P>
 */
public interface IDAO<P> {

	// Devuelve una lista con todos los registros
	List<P> getAll();

	// Buscamos registros por 'id' y si no encuentra, lanza una excepción
	P getById(int id) throws Exception;

	/**
	 * Elimina POJO por su 'id'
	 * 
	 * @param id
	 * @return el POJO eliminado
	 * @throws Exception: Si no encuentra el 'id'
	 * @throws SQLException: Si existe alguna 'constraint' con otras tablas
	 */
	P delete(int id) throws Exception, SQLException;
	
	/**
	 * Crea un nuevo POJO
	 * @param pojo
	 * @return EL pojo por el 'id' actualizado
	 * @throws Exception: Si no cumple las validaciones
	 * @throws SQLException: Si existe alguna 'constraint', por ejemplo UNIQUE_INDEX (no permíte nombres duplicados)
	 */
	P insert(P pojo) throws Exception, SQLException;
	
	/**
	 * Modifica un POJO
	 * @param pojo
	 * @return
	 * @throws Exception: Si no pasa validación o no encuentra por Id
	 * @throws SQLException: Si existe una constraint...
	 */
	P update(P pojo) throws Exception, SQLException;

}
