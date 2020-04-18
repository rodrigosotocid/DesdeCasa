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

import com.rodrigo.model.Curso;
import com.rodrigo.model.dao.CursoDAO;

@Path("/cursos")
@Produces("application/json")
@Consumes("application/json")
public class CursoController {
	
	private static final Logger LOGGER = Logger.getLogger(CursoController.class.getCanonicalName());
	
	private static CursoDAO cursoDAO = CursoDAO.getInstance();
	
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	@Context
	private ServletContext context;
	
	public CursoController() {
		super();
	}
	
	@GET
	public ArrayList<Curso> GetAll() {
		LOGGER.info("GET: getAll from Curso Rest Service");
		ArrayList<Curso> registros = (ArrayList<Curso>) cursoDAO.getAll();
		
		return registros;		
	}

}
