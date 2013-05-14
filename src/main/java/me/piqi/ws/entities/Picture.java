package me.piqi.ws.entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Jonny
 * Используется для 2х сущностей БД: Picture и uPhoto
 * т.к. набор полей по типам для данных сущностей идентичен
 */
@XmlRootElement(name = "picture")
public class Picture extends Entity {
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
