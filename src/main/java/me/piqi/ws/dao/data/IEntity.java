package me.piqi.ws.dao.data;

import me.piqi.ws.entities.Entity;

public interface IEntity {
	
	Entity getObject(Long id);
	boolean removeObject(Long id);
	Entity createObject(Entity ent);
	Entity updateObject(Entity ent);
	
}	
