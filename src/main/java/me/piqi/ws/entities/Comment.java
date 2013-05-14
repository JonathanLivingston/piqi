package me.piqi.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.piqi.ws.jaxb.adapter.JaxBDateAdapter;

@XmlRootElement(name = "comment")
public class Comment extends Entity {

	private Long questionID;
	private Long authorID;
	private Date datecommited;
	private String text;
	
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
