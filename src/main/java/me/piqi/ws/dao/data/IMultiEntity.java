package me.piqi.ws.dao.data;

import me.piqi.ws.entities.Entity;

public interface IMultiEntity {
	
	Entity getObject(Long id, String type);
	boolean removeObject(Long id, String type);
	Entity createObject(Entity ent, String type);
	Entity updateObject(Entity ent, String type);
	
}
