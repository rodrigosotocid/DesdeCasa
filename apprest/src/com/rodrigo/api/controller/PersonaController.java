package com.rodrigo.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rodrigo.model.Persona;

@Path("/personas")
@Produces("application/json")
@Consumes("application/json")
public class PersonaController {

	private static final Logger LOGGER = Logger.getLogger(PersonaController.class.getCanonicalName());
	
	private static int id = 1;
	
	@Context
	private ServletContext context;
	
	private static ArrayList<Persona> personas = new ArrayList<Persona>();
	
	static {
		personas.add( new Persona(1,"Arantxa","avatar1.png", "m") );
		personas.add( new Persona(2,"Markel","avatar2.png", "h") );
		personas.add( new Persona(3,"Iker","avatar3.png", "h") );
		personas.add( new Persona(4,"María","avatar4.png", "m") );
		personas.add( new Persona(5,"Zuriñe","avatar5.png", "m") );
		personas.add( new Persona(6,"Robert","avatar6.png", "h") );
		personas.add( new Persona(7,"Eneritz","avatar7.png", "m") );
		personas.add( new Persona(8,"Peter","avatar8.png", "h") );
		personas.add( new Persona(9,"José","avatar9.png", "h") );
		personas.add( new Persona(10,"Arantxa","avatar10.png", "m") );
//		personas.add( new Persona(11,"Jon","avatar11.png", "h") );
//		personas.add( new Persona(12,"Aritz","avatar12.png", "h") );
//		personas.add( new Persona(13,"Ander","avatar13.png", "h") );
//		personas.add( new Persona(14,"Antonio","avatar14.png", "h") );
//		personas.add( new Persona(15,"Idoia","avatar15.png", "m") );
//		personas.add( new Persona(16,"La Yenny","avatar16.png", "m") );
	}
	
	public PersonaController() {
		super();
	}

	@GET
	public ArrayList<Persona> getAll() {	
		LOGGER.info("getAll");
		return personas;
	}
	
	@POST
	public Response insert(Persona persona) {
		LOGGER.info("...ejecutando POST/Insert de Persona: " + persona);
		
		//TODO validar datos de la persona con javax.validation
		
		persona.setId(id);
		id++;		
		personas.add(persona);
		
		return Response.status(Status.CREATED).entity(persona).build();
	}
}
