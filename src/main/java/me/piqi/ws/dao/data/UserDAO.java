package me.piqi.ws.dao.data;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import me.piqi.ws.entities.Entity;
import me.piqi.ws.entities.User;

public class UserDAO implements IEntity{
	
	Map<String, User> profsMap = new HashMap<String, User>();
	
	DataSource datasource;
	
	private SimpleJdbcInsert insertUser;
	private SimpleJdbcInsert insertUserInfo;
	private JdbcTemplate templUser;
	
	public void setDataSource(DataSource dataSource) {
		this.templUser = new JdbcTemplate(dataSource);
		this.insertUser = new SimpleJdbcInsert(dataSource)
				.withTableName("User");
		this.insertUserInfo = new SimpleJdbcInsert(dataSource)
		.withTableName("UserInfo");
	}

	@Override
	public User getObject(Long id) {
		if ((templUser
				.queryForInt("Select count(1) FROM UserInfo WHERE ID = " + id)) > 0) {
			User user = (User) templUser.queryForObject(
					"SELECT * FROM UserInfo WHERE id = " + id,
					new ParameterizedRowMapper<User>() {
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							User User = new User();
							User.setName(rs.getString("Name"));
							User.setSurname(rs.getString("Surname"));
							User.setPhotoID(rs.getLong("PhotoID"));
							User.setPlace(rs.getString("Place"));
							User.setAbout(rs.getString("About "));
							User.setNick(rs.getString("Nick"));
							User.setLink(rs.getString("Link"));
							User.setEmail(rs.getString("email"));
							User.setAcctype(rs.getInt("acctype"));
							if (rs.getString("regdate") != ""
									&& rs.getString("regdate") != null)
								User.setRegdate(Date.valueOf(rs
										.getString("regdate")));
							
							return User;
						}
					});
			return user;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeObject(Long id) {
		if (templUser.update("DELETE FROM UserInfo WHERE ID = " + id) > 0
				&& templUser.update("DELETE FROM User WHERE ID = " + id) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User createObject(Entity ent) {
		if (ent != null && ent.getClass() == User.class) {
			User usr = (User) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(3);
			Map<String, Object> infoparams = new HashMap<String, Object>(11);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			infoparams.put("ID", uuid);
			
			if (usr.getLogin() != null)
				params.put("lohin", usr.getLogin());
			if (usr.getPass() != null)
				try {
					params.put("hash", getHash(usr.getPass()));
				} catch (Exception e) {
					//TODO wrong password exception
					return null;
				}
			
			if (usr.getName() != null)
				infoparams.put("Name", usr.getName());
			if (usr.getSurname() != null)
				infoparams.put("Surname", usr.getSurname());
			if (usr.getPhotoID() != null)
				infoparams.put("PhotoID", usr.getPhotoID());
			if (usr.getPlace() != null)
				infoparams.put("Place", usr.getPlace());
			if (usr.getAbout() != null)
				infoparams.put("About", usr.getAbout());
			if (usr.getNick() != null)
				infoparams.put("Nick", usr.getNick());
			if (usr.getLink() != null)
				infoparams.put("Link", usr.getLink());
			if (usr.getEmail() != null)
				infoparams.put("email", usr.getEmail());
			if (usr.getRegdate() != null)
				infoparams.put("regdate", usr.getRegdate());
			infoparams.put("acctype", usr.getAcctype());
			
			insertUser.execute(params);
			insertUserInfo.execute(infoparams);
			
			return usr;
		} else {
			return null;
		}
	}

	@Override
	public User updateObject(Entity ent) {
		if (ent != null && ent.getClass() == User.class) {
			User usr = (User) ent;
			User oldUser = getObject(usr.getID());
			if (oldUser == null)
				return null;
			//TODO error updating user exception
			String sqlUpdate = String
					.format("UPDATE UserInfo SET Name = %s, Surname = %s, PhotoID = %s, Place = %s, About = %s," +
							" Nick = %s, Link = %s, email = %s, acctype = %s WHERE ID = %s",
							"'" + usr.getName() + "'", "'" +  usr.getSurname() + "'",
							"'" + usr.getPhotoID() + "'", "'" + usr.getPlace() + "'",
							"'" + usr.getAbout() + "'", "'" + usr.getNick() + "'",
							"'" + usr.getLink() + "'", "'" + usr.getEmail() + "'",
							"'" + usr.getAcctype() + "'", "'" + usr.getID() + "'");
			templUser.update(sqlUpdate);
			return usr;
		} else {
			return null;
		}
	}
	
	private static String getHash(String str) throws NoSuchAlgorithmException,
		UnsupportedEncodingException {
		
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(str.getBytes("utf-8"));
		String s2 = new BigInteger(1, m.digest()).toString(16);
		StringBuilder sb = new StringBuilder(32);
		for (int i = 0, count = 32 - s2.length(); i < count; i++) {
		    sb.append("0");
		}
		return sb.append(s2).toString();
	}


}
