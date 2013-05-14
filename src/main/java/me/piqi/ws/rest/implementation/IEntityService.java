package me.piqi.ws.rest.implementation;

import javax.ws.rs.core.Response;

import me.piqi.ws.entities.Entity;

public interface IEntityService {
	Response getObject(String id);	
	Response removeObject(String id);
	Response createObject(Entity ent);
	Response updateObject(Entity ent);
	//Response getObjects(String keyword, String orderBy, String order, Integer pageNum, Integer pageSize);
}
