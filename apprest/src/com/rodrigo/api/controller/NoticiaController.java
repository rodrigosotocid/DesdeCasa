package com.rodrigo.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rodrigo.model.Noticia;
import com.rodrigo.model.dao.NoticiaDAO;

@Path("/noticias")
@Produces("application/json")
@Consumes("application/json")
public class NoticiaController {

	private static final Logger LOGGER = Logger.getLogger(NoticiaController.class.getCanonicalName());
	private static NoticiaDAO noticiaDAO = NoticiaDAO.getInstance();
	
	ResponseBody rb = new ResponseBody();
	
	@Context
	private ServletContext context;
	
	public NoticiaController() {
		super();
	}
	
	@GET
	public Response getAll() {
		
		LOGGER.info("@GET: getAll");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
		try {
			
			LOGGER.info("Noticias cargadas con éxito!");
			
			ArrayList<Noticia> registros = (ArrayList<Noticia>) noticiaDAO.getAll();
			response = Response.status(Status.OK).entity(registros).build();
			
		} catch (Exception e) {
			
			LOGGER.info("Problemas al cargar listado de Noticias");
			rb.setInformacion("Los registos no han podido ser cargados");
			
			response = Response.status(Status.NOT_FOUND).entity(rb).build();
		}
		
		return response;
		
	}
}
