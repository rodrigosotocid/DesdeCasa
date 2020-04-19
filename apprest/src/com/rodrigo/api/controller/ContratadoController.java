package com.rodrigo.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.rodrigo.model.CursoContratado;
import com.rodrigo.model.dao.CursoContratadoDAO;

@Path("/contratados")
@Produces("application/json")
@Consumes("application/json")
public class ContratadoController {
	
	private static final Logger LOGGER = Logger.getLogger(ContratadoController.class.getCanonicalName());
	
	private static CursoContratadoDAO contratadoDAO = CursoContratadoDAO.getInstance();
	
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	@Context
	private ServletContext context;
	
	public ContratadoController() {
		super();
	}

	@GET
	public ArrayList<CursoContratado> GetAll() {
		LOGGER.info("GET: getAll from Curso Rest Service");
		ArrayList<CursoContratado> registros = (ArrayList<CursoContratado>) contratadoDAO.getAll();
		return registros;		
	}

}
