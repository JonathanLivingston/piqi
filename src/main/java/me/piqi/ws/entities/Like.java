package me.piqi.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.piqi.ws.jaxb.adapter.JaxBDateAdapter;

/**
 * 
 * @author Jonny
 * Используется для трёх сущностей БД - Like, Dislike, Piclike
 * т.к. набор полей по типам для трёх сущностей идентичен.
 */
@XmlRootElement(name = "like")
public class Like extends Entity {
	
	private Long questionID;
	private Long authorID;
	private Date datecommited;
	
	public Long getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}
	
	public Long getAuthorID() {
		return authorID;
	}
	
	public void setAuthorID(Long authorID) {
		this.authorID = authorID;
	}
	
	@XmlJavaTypeAdapter(JaxBDateAdapter.class)
	public Date getDatecommited() {
		return datecommited;
	}
	
	public void setDatecommited(Date datecommited) {
		this.datecommited = datecommited;
	}
}
