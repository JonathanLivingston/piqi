package me.piqi.ws.dao.data;

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
import me.piqi.ws.entities.Picture;

//TODO Можно отрефакторить методы, дабы не использовать несколько условий в теле
/**
 * 
 * @author Jonny
 * Класс работает с 2 сущностями БД - Picture и uPhoto
 */
public class PictureDAO implements IMultiEntity {
	
	Map<String, Picture> profsMap = new HashMap<String, Picture>();

	DataSource datasource;
	
	private SimpleJdbcInsert insertPicture;
	private JdbcTemplate templPicture;
	private SimpleJdbcInsert insertPhoto;

	public void setDataSource(DataSource dataSource) {
		this.templPicture = new JdbcTemplate(dataSource);
		this.insertPicture = new SimpleJdbcInsert(dataSource)
				.withTableName("Picture");
		this.insertPhoto = new SimpleJdbcInsert(dataSource)
				.withTableName("uPhoto");
	}	
	
	@Override
	public Picture getObject(Long id, String type) {
		if (type != null && type.equals("Picture")) {
			if ((templPicture
					.queryForInt("Select count(1) FROM Picture WHERE ID = " + id)) > 0) {
				Picture pic = (Picture) templPicture.queryForObject(
						"SELECT * FROM Picture WHERE ID = " + id,
						new ParameterizedRowMapper<Picture>() {
							public Picture mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Picture Pic = new Picture();
								Pic.setPath(rs.getString("path"));
								
								return Pic;
							}
						});
				return pic;
			} else {
				return null;
			}
		}
		
		if (type != null && type.equals("uPhoto")) {
			if ((templPicture
					.queryForInt("Select count(1) FROM uPhoto WHERE ID = " + id)) > 0) {
				Picture pic = (Picture) templPicture.queryForObject(
						"SELECT * FROM uPhoto WHERE ID = " + id,
						new ParameterizedRowMapper<Picture>() {
							public Picture mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Picture Pic = new Picture();
								Pic.setPath(rs.getString("path"));
								
								return Pic;
							}
						});
				return pic;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean removeObject(Long id, String type) {
		if (type != null && type.equals("Picture")) {
			if (templPicture
					.update("DELETE FROM Picture WHERE ID = " + id) > 0)
				return true;
		}
		
		if (type != null && type.equals("uPhoto")) {
			if (templPicture
					.update("DELETE FROM uPhoto WHERE ID = " + id) > 0) 
				return true;
		}
		return false;
	}

	@Override
	public Picture createObject(Entity ent, String type) {
		if (type != null && type.equals("Picture") &&
				ent != null && ent.getClass() == Picture.class) {
			Picture pic = (Picture) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(2);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			params.put("path", pic.getPath());
			
			insertPicture.execute(params);
			
			return pic;
		}
			
		if (type != null && type.equals("uPhoto") &&
				ent != null && ent.getClass() == Picture.class) {
			Picture pic = (Picture) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(2);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			params.put("path", pic.getPath());
			
			insertPhoto.execute(params);
			
			return pic;
		}
		return null;
	}

	@Override
	public Picture updateObject(Entity ent, String type) {
		if (type != null && type.equals("Picture") &&
				ent != null && ent.getClass() == Picture.class) {
			Picture pic = (Picture) ent;
			Picture oldPic = getObject(pic.getID(), "Picture");
			if (oldPic == null)
				return null;
			//TODO error updating picture exception
			String sqlUpdate = String
					.format("UPDATE Picture SET path = %s," +
							" WHERE ID = %s",
							"'" + pic.getPath() + "'", "'" + pic.getID() + "'");
			templPicture.update(sqlUpdate);
			return pic;
		}
			
		if (type != null && type.equals("uPhoto") &&
				ent != null && ent.getClass() == Picture.class) {
			Picture pic = (Picture) ent;
			Picture oldPic = getObject(pic.getID(), "uPhoto");
			if (oldPic == null)
				return null;
			//TODO error updating picture exception
			String sqlUpdate = String
					.format("UPDATE uPhoto SET path = %s," +
							" WHERE ID = %s",
							"'" + pic.getPath() + "'", "'" + pic.getID() + "'");
			templPicture.update(sqlUpdate);
			return pic;
		}
		return null;
	}

}
