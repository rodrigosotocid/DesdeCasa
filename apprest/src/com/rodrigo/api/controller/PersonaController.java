package com.rodrigo.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.rodrigo.model.dao.repo.PersonaDAO;

@Path("/personas")
@Produces("application/json")
@Consumes("application/json")
public class PersonaController {

	private static final Logger LOGGER = Logger.getLogger(PersonaController.class.getCanonicalName());

	private static PersonaDAO personaDAO = PersonaDAO.getInstance();
	private static CursoDAO cursoDAO = CursoDAO.getInstance();

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	ArrayList<String> errores = new ArrayList<String>();
	ResponseBody rb = new ResponseBody();
	
	@Context
	private ServletContext context;

	public PersonaController() {
		super();
	}

	@GET
	public Response getAll(
			@QueryParam("rol") String rol,		//Parámetro para busqueda por tipo de Rol  
			@QueryParam("name") String name) 	//Parámetro para busqueda por Nombre
	{
		
		LOGGER.info("@GET: getAll");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
		
		if( name != null && !name.trim().isEmpty() ) {											// Busqueda por Nombre
			
			 LOGGER.info("Buscar 1 Persona name: " + name);
			 
			 try {
				 //TODO filtraba busqueda por nombre
				 // filtraba busqueda por nombre
				 //Persona registro = personaDAO.getBy Name(name);
				 Persona registro = (Persona) personaDAO.getAll();
				 response = Response.status(Status.OK).entity(registro).build();
				
			} catch (Exception e) {
				
				LOGGER.info("Exception: Persona no encontrada!");
				
				rb.setInformacion("No hemos encontrado el nombre: " + name);
				rb.getHypermedias().add(new Hipermedia("Busca por Id", "GET", "personas/{id}"));
				rb.getHypermedias().add(new Hipermedia("Listar", "GET", "personas"));
				
				response = Response.status(Status.NOT_FOUND).entity(rb).build();
				LOGGER.info(rb.getHypermedias().toString());
			}
			 
		} else {																				// Lista todas las Personas
			
			LOGGER.info("Listado de Personas sin parámetros");
			
			ArrayList<Persona> registros = (ArrayList<Persona>) personaDAO.getAll();
			response = Response.status(Status.OK).entity(registros).build();
		}
		
		
		// SI rol es distinto a null Y 
		// SI la longitud de 'rol' es distinta a cero luego de borrar los espacios en blanco
		if(rol != null && !rol.trim().isEmpty()) {
			
			//TODO Mejorar la condición del If()
			if("alumno".equals(rol)) {				// Lista por Alumno
				
				try {
					
					LOGGER.info("Obtenemos la lista de Alumnos");
					ArrayList<Persona> alumnos = (ArrayList<Persona>) personaDAO.getAllAlumnos();
					response = Response.status(Status.OK).entity(alumnos).build();
					
				} catch (Exception e) {
					
					rb.setInformacion("Error: Lista de Alumnos no encontrada");
					response = Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
				}
				
			}  
			if( "profesor".equals(rol)){		// Lista por Profesor
					
				try {
					
					LOGGER.info("Obtenemos la lista de Profesores");
					ArrayList<Persona> profesores = (ArrayList<Persona>) personaDAO.getAllProfesores();
					response = Response.status(Status.OK).entity(profesores).build();
					
				} catch (Exception e) {
					
					rb.setInformacion("Error: Lista de Profesores no encontrada");
					response = Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
				}
			
			} 
		} else {																				// Lista todas las Personas
			
			LOGGER.info("Listado de Personas sin parámetros");
			
			ArrayList<Persona> registros = (ArrayList<Persona>) personaDAO.getAll();
			response = Response.status(Status.OK).entity(registros).build();
		} 
		
		return response;
	}

	
	@GET
	@Path("/{id}")
	public Object getById(@PathParam("id") int id) {
		
		LOGGER.info("@GET: getPersona");
		ArrayList<String> errores = new ArrayList<String>();
		Response response = null;
		
		Persona persona;
		try {
			persona = personaDAO.getById(id);

			if (persona != null) {
				response = Response.status(Status.OK).entity(persona).build();
			}
		} catch (SQLException e) {
			
			errores.add(e.getMessage());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(errores).build();
			e.printStackTrace();
			
		} catch (Exception e) {
			
			errores.add("No se ha encontrado ninguna persona con ese id.");
			response = Response.status(Status.NOT_FOUND).entity(errores).build();
		}
		return response;
	}
	
	/**
	 * Creamos el método POST, recibimos persona por parámetro, actualizamos y luego
	 * la añadimos al array
	 */
	@POST
	public Response insert(Persona persona) {
		
		LOGGER.info("@POST: Insert de Persona: " + persona);
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();

		// Validación POJO (javax.validation)
		Set<ConstraintViolation<Persona>> violations = validator.validate(persona);

		if (violations.isEmpty()) {

//			persona.setId(id);
//			id++;
//			personas.add(persona);

			try {
				
				persona = personaDAO.insert(persona);
				response = Response.status(Status.CREATED).entity(persona).build();
				
			} catch (Exception e) {
				
				//ResponseBody responseBody = new ResponseBody();
				
				rb.setInformacion("Lo siento este nombre ya existe en la Base de Datos!");
				response = Response.status(Status.CONFLICT).entity(rb).build();
				LOGGER.info(e.getMessage());
			}
		} else {

			ArrayList<String> errores = new ArrayList<String>();

			for (ConstraintViolation<Persona> violation : violations) {
				errores.add(violation.getPropertyPath() + " : " + violation.getMessage());
			}
			response = Response.status(Status.BAD_REQUEST).entity(errores).build();
			LOGGER.info(errores.toString());
		}

		return response;
	}

	@PUT
	@Path("/{id: \\d+}")
	public Response update(@PathParam("id") int id, Persona persona) {
		 
		LOGGER.info("@PUT: Update (" + id + ", " + persona + ")");

		//Valida objeto Persona (javax.validation CODE:400)
		Response response = Response.status(Status.NOT_FOUND).entity(persona).build();

		//Comprueba si no encuentra a la persona (devolver 404)
		Set<ConstraintViolation<Persona>> violations = validator.validate(persona);

		if (!violations.isEmpty()) {
			
			ArrayList<String> errores = new ArrayList<String>();

			for (ConstraintViolation<Persona> violation : violations) {
				errores.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}
			response = Response.status(Status.BAD_REQUEST).entity(errores).build();

		} else {
			try {
				persona = personaDAO.update(persona);
				response = Response.status(Status.OK).entity(persona).build();
				LOGGER.info("Update de Persona realizado correctamente");

			} catch (Exception e) {
				
				LOGGER.warning("Nombre duplicado");
				rb.setInformacion("Lo siento este nombre no está disponible!");
				response = Response.status(Status.CONFLICT).entity(rb).build();
			}
		}
		return response;
	}

	@DELETE
	@Path("/{id: \\d+}")
	public Response eliminar(@PathParam("id") int id) {
		
		LOGGER.info("@DELETE: Eliminar (" + id + ")");

		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		ResponseBody responseBody = new ResponseBody();
		Persona persona = null;

		try {
			
			//Sí intenta borrar y no lo encuentra lanza la Excepción de SQL
			persona = personaDAO.delete(id);
			 
			responseBody.setData(persona);
			responseBody.setInformacion("Persona eliminada");
			responseBody.addError("Esto es una prueba");
			responseBody.addError("Esto es una segunda prueba");
			
			responseBody.getHypermedias().add(new Hipermedia("Lista de Personas", "GET", "http://localhost:8080/apprest/api/personas/"));
			responseBody.getHypermedias().add(new Hipermedia("Detalle de Personas", "GET", "http://localhost:8080/apprest/api/personas/{id}"));
			
			response = Response.status(Status.OK).entity(responseBody).build();
			
		} catch (SQLException e) {
				
			LOGGER.info(e.getMessage());
			responseBody.addError("Esto es una prueba");
			
			if(e.getMessage().contains("a foreign key constraint fails")) {
				errores.add("No se puede eliminar si tiene cursos activos");
			}
			response = Response.status(Status.CONFLICT).entity(errores).build();
			
			LOGGER.info("Response body loggeer" + responseBody.toString());
		} catch (Exception e) {
			
			responseBody.setInformacion("¡Persona no encontrada!");
			response = Response.status(Status.NOT_FOUND).entity(responseBody).build();
			LOGGER.info(responseBody.toString());
		}

		return response;
	}
	
	@POST
	@Path("/{idPersona}/curso/{idCurso}")
	public Response asignarCurso(@PathParam("idPersona") int idPersona, @PathParam("idCurso") int idCurso) {
		
		LOGGER.info("asignarCurso idPersona=" + idPersona + " idCurso= " + idCurso);
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		ResponseBody responseBody = new ResponseBody();
		
		try {	
			
			personaDAO.asignarCurso(idPersona, idCurso);
			Curso c = cursoDAO.getById(idCurso);
			
			responseBody.setInformacion("Curso asignado con éxito!");
			responseBody.setData(c);
			response = Response.status(Status.CREATED).entity(responseBody).build();
			
		} catch (Exception e) {	
			
			LOGGER.info(e.getMessage() + "error1");
			
			if(e.getMessage().contains("Duplicate entry")) {
				errores.add("Ya tienes este curso...intenta con otro!");
			}
			
			
			responseBody.setInformacion("Error curso duplicado");
			response = Response.status(Status.CONFLICT).entity(errores).build();
		}

		return response;
		
	}
	
	@DELETE
	@Path("/{idPersona}/curso/{idCurso}")
	public Response eliminarCurso(@PathParam("idPersona") int idPersona, @PathParam("idCurso") int idCurso) {
		
		LOGGER.info("eliminarCurso idPersona=" + idPersona + " idCurso= " + idCurso);
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		ResponseBody responseBody = new ResponseBody();

		try {	
			
			personaDAO.eliminarCurso(idPersona, idCurso);
			Persona p = personaDAO.getById(idPersona);
			
			responseBody.setInformacion("Curso eliminado con éxito!");
			responseBody.setData(p);
			response = Response.status(Status.OK).entity(responseBody).build();
			
		} catch (Exception e) {		
				LOGGER.info("Curso no encontrado: " + e.getMessage());
				responseBody.setInformacion("Curso no encontrado para eliminar. Consulte con el administrador de la aplicación.");
				response = Response.status(Status.NOT_FOUND).entity(responseBody).build();
		}

		return response;

	}
	
}

	