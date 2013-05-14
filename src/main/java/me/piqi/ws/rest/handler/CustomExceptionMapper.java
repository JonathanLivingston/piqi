package me.piqi.ws.rest.handler;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import me.piqi.ws.rest.response.ResponseCreator;
import me.piqi.ws.rest.exception.Error;

public class CustomExceptionMapper {
	@Context
	private HttpHeaders requestHeaders;	
	
	private String getHeaderVersion() {		
		return requestHeaders.getRequestHeader("version").get(0);
	}
	
    public Response toResponse(Exception ex) {    	
    	return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
    }
}
