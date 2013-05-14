package me.piqi.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.piqi.ws.jaxb.adapter.JaxBDateAdapter;

@XmlRootElement(name = "question")
public class Question extends Entity {
	
	private int qtype;
	private int state;
	private int timetype;
	private Date timestarted;
	private String qLocation;
	private Long authorID;
	private String text;
	private Long pic1;
	private Long pic2;
	private float quant;
	
	public int getQtype() {
		return qtype;
	}
	
	public void setQtype(int qtype) {
		this.qtype = qtype;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getTimetype() {
		return timetype;
	}
	
	public void setTimetype(int timetype) {
		this.timetype = timetype;
	}
	
	@XmlJavaTypeAdapter(JaxBDateAdapter.class)
	public Date getTimestarted() {
		return timestarted;
	}
	
	public void setTimestarted(Date timestarted) {
		this.timestarted = timestarted;
	}
	
	public String getqLocation() {
		return qLocation;
	}
	
	public void setqLocation(String qLocation) {
		this.qLocation = qLocation;
	}
	
	public Long getAuthorID() {
		return authorID;
	}
	
	public void setAuthorID(Long authorID) {
		this.authorID = authorID;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Long getPic1() {
		return pic1;
	}
	
	public void setPic1(Long pic1) {
		this.pic1 = pic1;
	}
	
	public Long getPic2() {
		return pic2;
	}
	
	public void setPic2(Long pic2) {
		this.pic2 = pic2;
	}
	
	public float getQuant() {
		return quant;
	}
	
	public void setQuant(float quant) {
		this.quant = quant;
	}
}
