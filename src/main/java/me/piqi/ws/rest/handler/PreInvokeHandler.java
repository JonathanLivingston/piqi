package me.piqi.ws.rest.handler;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import me.piqi.ws.rest.response.ResponseCreator;
import me.piqi.ws.rest.exception.Error;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;

public class PreInvokeHandler {

	private boolean validate(String ss_id) {
		//TODO Сюда прикрутить авторизацию при обработке запроса
		return true;
	}

	public Response handleRequest(Message message, ClassResourceInfo arg1) {
		Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message
				.get(Message.PROTOCOL_HEADERS));
			
		if (headers.get("ss_id") != null && validate(headers.get("ss_id").get(0))) {
			// let request to continue
			return null;
		} else {
			// authentication failed, request the authentication, add the realm			
			return ResponseCreator.error(401, Error.NOT_AUTHORIZED.getCode(), headers.get("version").get(0));
		}	
		
	}
}
