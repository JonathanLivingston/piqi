package me.piqi.ws.rest.implementation;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.piqi.ws.dao.data.QuestionDAO;
import me.piqi.ws.entities.Entity;
import me.piqi.ws.entities.Question;
import me.piqi.ws.rest.exception.Error;
import me.piqi.ws.rest.response.ResponseCreator;

public class QuestionServiceJSON implements IEntityService {
	
	private QuestionDAO questionDAO;
	
	// for customersDAO bean property injection
	public QuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}
	
	// for retrieving request headers from context
	// an injectable interface that provides access to HTTP header information.
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return requestHeaders.getRequestHeader("version").get(0);
	}
	
	// get by id service
	@GET
	@Path("/{id}")
	public Response getObject(@PathParam("id") String sid) {
		Long id = Long.parseLong(sid);
		Question qst = questionDAO.getObject(id);
		if (qst != null) {
			return ResponseCreator.success(getHeaderVersion(), qst);
		} else {
			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
					getHeaderVersion());
		}
	}
	
	// remove row from the users table according with passed id and returned
	// status message in body
	@DELETE
	@Path("/{id}")
	public Response removeObject(@PathParam("id") String sid) {
		Long id = Long.parseLong(sid);
		if (questionDAO.removeObject(id)) {
			return ResponseCreator.success(getHeaderVersion(), "removed");
		} else {
			return ResponseCreator.success(getHeaderVersion(), "no such id");
		}
	}
	
	// create row representing user and returns created user as
	// object->JSON structure
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createObject(Entity qst) {
		Question newQst = questionDAO.createObject(qst);
		if (newQst != null) {
			return ResponseCreator.success(getHeaderVersion(), newQst);
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),
					getHeaderVersion());
		}
	}
	
	// update row and return previous version of row representing user as
	// object->JSON structure
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateObject(Entity qst) {
		Question updQst = questionDAO.updateObject(qst);
		if (updQst != null) {
			return ResponseCreator.success(getHeaderVersion(), updQst);
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),
					getHeaderVersion());
		}
	}
}
