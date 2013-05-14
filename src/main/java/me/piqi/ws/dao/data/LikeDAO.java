package me.piqi.ws.dao.data;

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
import me.piqi.ws.entities.Like;

//TODO Можно отрефакторить методы, дабы не использовать несколько условий в теле
/**
 * 
 * @author Jonny
 * Класс работает с 3 сущностями БД - qLike, qDislike, qPiclike
 */
public class LikeDAO implements IMultiEntity {
	
	Map<String, Like> profsMap = new HashMap<String, Like>();
	
	DataSource datasource;
	
	private JdbcTemplate templLike;
	private SimpleJdbcInsert insertLike;
	private SimpleJdbcInsert insertDislike;
	private SimpleJdbcInsert insertPiclike;

	public void setDataSource(DataSource dataSource) {
		this.templLike = new JdbcTemplate(dataSource);
		this.insertLike = new SimpleJdbcInsert(dataSource)
				.withTableName("qLike");
		this.insertDislike = new SimpleJdbcInsert(dataSource)
				.withTableName("qDislike");
		this.insertPiclike = new SimpleJdbcInsert(dataSource)
			.withTableName("qPiclike");
	}

	@Override
	public Like getObject(Long id, String type) {
		if (type != null && type.equals("Like")) {
			if ((templLike
					.queryForInt("Select count(1) FROM qLike WHERE ID = " + id)) > 0) {
				Like lk = (Like) templLike.queryForObject(
						"SELECT * FROM qLike WHERE ID = " + id,
						new ParameterizedRowMapper<Like>() {
							public Like mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Like Lk = new Like();
								Lk.setQuestionID(rs.getLong("questionID"));
								Lk.setAuthorID(rs.getLong("authorID"));
								if (rs.getString("datecommited") != ""
										&& rs.getString("datecommited") != null)
									Lk.setDatecommited(Date.valueOf(rs
											.getString("datecommited")));
								
								return Lk;
							}
						});
				return lk;
			}
		}
		
		if (type != null && type.equals("Dislike")) {
			Like lk = (Like) templLike.queryForObject(
					"SELECT * FROM qDislike WHERE ID = " + id,
					new ParameterizedRowMapper<Like>() {
						public Like mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Like Lk = new Like();
							Lk.setQuestionID(rs.getLong("questionID"));
							Lk.setAuthorID(rs.getLong("authorID"));
							if (rs.getString("datecommited") != ""
									&& rs.getString("datecommited") != null)
								Lk.setDatecommited(Date.valueOf(rs
										.getString("datecommited")));
							
							return Lk;
						}
					});
			return lk;
		}
			
		if (type != null && type.equals("Piclike")) {
			Like lk = (Like) templLike.queryForObject(
					"SELECT * FROM qPiclike WHERE ID = " + id,
					new ParameterizedRowMapper<Like>() {
						public Like mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Like Lk = new Like();
							Lk.setQuestionID(rs.getLong("questionID"));
							Lk.setAuthorID(rs.getLong("authorID"));
							if (rs.getString("datecommited") != ""
									&& rs.getString("datecommited") != null)
								Lk.setDatecommited(Date.valueOf(rs
										.getString("datecommited")));
							
							return Lk;
						}
					});
			return lk;
		}
		
		return null;
	}

	@Override
	public boolean removeObject(Long id, String type) {
		if (type != null && type.equals("Like")) {
			if (templLike
					.update("DELETE FROM qLike WHERE ID = " + id) > 0)
				return true;
		}
		
		if (type != null && type.equals("Dislike")) {
			if (templLike
					.update("DELETE FROM qDislike WHERE ID = " + id) > 0)
				return true;
		}
		
		if (type != null && type.equals("Piclike")) {
			if (templLike
					.update("DELETE FROM qPiclike WHERE ID = " + id) > 0)
				return true;
		}
		
		return false;
	}

	@Override
	public Like createObject(Entity ent, String type) {
		if (type != null && type.equals("Like") &&
				ent != null && ent.getClass() == Like.class) {
			Like lk = (Like) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(4);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			
			params.put("questionID", lk.getQuestionID());
			params.put("authorID", lk.getAuthorID());
			if (lk.getDatecommited() != null)
				params.put("datecommited", lk.getDatecommited());
			
			insertLike.execute(params);
			
			return lk;
		}
		
		if (type != null && type.equals("Dislike") &&
				ent != null && ent.getClass() == Like.class) {
			Like lk = (Like) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(4);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			
			params.put("questionID", lk.getQuestionID());
			params.put("authorID", lk.getAuthorID());
			if (lk.getDatecommited() != null)
				params.put("datecommited", lk.getDatecommited());
			
			insertDislike.execute(params);
			
			return lk;
		}
		
		if (type != null && type.equals("Piclike") &&
				ent != null && ent.getClass() == Like.class) {
			Like lk = (Like) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(4);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			
			params.put("questionID", lk.getQuestionID());
			params.put("authorID", lk.getAuthorID());
			if (lk.getDatecommited() != null)
				params.put("datecommited", lk.getDatecommited());
			
			insertPiclike.execute(params);
			
			return lk;
		}
		
		return null;
	}

	@Override
	public Like updateObject(Entity ent, String type) {
		return null;
	}

}
