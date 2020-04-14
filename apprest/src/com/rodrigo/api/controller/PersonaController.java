package com.rodrigo.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
		personas.add(new Persona(1, "Arantxa", "avatar1.png", "m"));
		personas.add(new Persona(2, "Markel", "avatar2.png", "h"));
		personas.add(new Persona(3, "Iker", "avatar3.png", "h"));
		personas.add(new Persona(4, "María", "avatar4.png", "m"));
		personas.add(new Persona(5, "Zuriñe", "avatar5.png", "m"));
		personas.add(new Persona(6, "Robert", "avatar6.png", "h"));
		personas.add(new Persona(7, "Eneritz", "avatar7.png", "m"));
		personas.add(new Persona(8, "Peter", "avatar8.png", "h"));
		personas.add(new Persona(9, "José", "avatar9.png", "h"));
		personas.add(new Persona(10, "Arantxa", "avatar10.png", "m"));
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

	/**
	 * Creamos el método POST, recibimos persona por parámetro, actualizamos y luego
	 * la añadimos al array
	 */

	@POST
	public Response insert(Persona persona) {
		LOGGER.info("...ejecutando POST/Insert de Persona: " + persona);

		// TODO validar datos de la persona con javax.validation

		persona.setId(id);
		id++;
		personas.add(persona);

		return Response.status(Status.CREATED).entity(persona).build();
	}

	/**
	 * PUT: modificar,
	 *
	 */

	@PUT
	@Path("/{id: \\d+}")
	public Persona update(@PathParam("id") Integer id, Persona persona) {
		LOGGER.info("update(" + id + ", " + persona + ")");

		// TODO Validar objeto Persona con javax.validation

		// TODO Comprobar si no encuentra a la persona

		for (int i = 0; i < personas.size(); i++) {

			if (id == personas.get(i).getId()) {
				personas.remove(i);
				personas.add(i, persona);
				break;
			}
		}
		
		if (id != persona.getId()) {
			LOGGER.warning("No concuerdan los id: " + id + ", " + persona);

			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}
		
//
//		if (!persona.containsKey(id)) {
//			LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + persona);
//
//			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
//		}
//
//		persona.put(id, persona);

		return persona;
	}

	/**
	 * DELETE
	 *
	 */

	@DELETE
	@Path("/{id: \\d+}")
	public Response eliminar(@PathParam("id") int id) {
		LOGGER.info("Eliminar(" + id + ")");

		Persona persona = null;

		for (int i = 0; i < personas.size(); i++) {

			if (id == personas.get(i).getId()) {
				persona = personas.get(i);
				personas.remove(i);
				break;
			}
		}

		if (persona == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.status(Status.OK).entity(persona).build();
		}

	}
}
