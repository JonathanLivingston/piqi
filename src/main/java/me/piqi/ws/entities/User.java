package me.piqi.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import me.piqi.ws.jaxb.adapter.JaxBDateAdapter;

@XmlRootElement(name = "user")
public class User extends Entity {
	
	private String name;
	private String surname;
	private Long photoID;
	private String place;
	private String about;
	private String nick;
	private String link;
	private String email;
	private int acctype;
	private Date regdate;
	private String login;
	private String pass;
	private String hash;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Long getPhotoID() {
		return photoID;
	}
	
	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAcctype() {
		return acctype;
	}
	
	public void setAcctype(int acctype) {
		this.acctype = acctype;
	}
	
	@XmlJavaTypeAdapter(JaxBDateAdapter.class)
	public Date getRegdate() {
		return regdate;
	}
	
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
}
