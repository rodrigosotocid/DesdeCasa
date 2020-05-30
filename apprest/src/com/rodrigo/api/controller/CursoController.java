package com.rodrigo.api.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rodrigo.model.Curso;
import com.rodrigo.model.Persona;
import com.rodrigo.model.dao.repo.CursoDAO;

@Path("/cursos")
@Produces("application/json")
@Consumes("application/json")
public class CursoController {
	
	private static final Logger LOGGER = Logger.getLogger(CursoController.class.getCanonicalName());
	
	private static CursoDAO cursoDAO = CursoDAO.getInstance();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	@Context
	private ServletContext context;
	
	public CursoController() {
		super();
	}

	@GET
	public Response GetAll(@QueryParam("filtro") String filtro) {
		
		LOGGER.info("@GET: All Curso" + filtro);
		ArrayList<Curso> registros = new ArrayList<Curso>();
		
		// SI "filtro" es diferente de "null" y
		// SI "filtro" contiene algún caracter
		if(filtro != null && !"".equals(filtro.trim())) {
			
			registros = (ArrayList<Curso>) cursoDAO.getAllLikeNombre(filtro);
			
		}else {
			
			registros = (ArrayList<Curso>) cursoDAO.getAll();
		}
		
		Response response = Response.status(Status.OK).entity(registros).build();
		
		return response;		
	}
	
	@PUT
	@Path("/{id: \\d+}")
	public Response update(@PathParam("id") int id, Curso curso) {
		
		LOGGER.info("@PUT: Update (" + id + ", " + curso + ")");
		
		Response response = Response.status(Status.NOT_FOUND).entity(curso).build();
		
		Set<ConstraintViolation<Curso>> violations = validator.validate(curso);
		ArrayList<String> errores = new ArrayList<String>();

		if (violations.isEmpty()) {

			try {
				
				cursoDAO.update(curso);
				response = Response.status(Status.OK).entity(curso).build();
				LOGGER.info("Curso modificado correctamente");
				
			} catch (Exception e) {
				
				errores.add("Error al introducir el nombre del curso");
				LOGGER.warning("Error al introducir el nombre del curso");
				
				response = Response.status(Status.CONFLICT).entity(errores).build();
				
			}
		} else {
			
			for (ConstraintViolation<Curso> violation : violations) {
				errores.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}
			
			LOGGER.warning("No se cumplen las validaciones para modificar el curso: " + errores);
			response = Response.status(Status.BAD_REQUEST).entity(errores).build();
		}

		return response;
	}
}
