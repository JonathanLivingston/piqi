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

import me.piqi.ws.entities.Comment;
import me.piqi.ws.entities.Entity;

public class CommentDAO implements IEntity {
	
	Map<String, Comment> profsMap = new HashMap<String, Comment>();

	DataSource datasource;
	
	private SimpleJdbcInsert insertComment;
	private JdbcTemplate templComment;

	public void setDataSource(DataSource dataSource) {
		this.templComment = new JdbcTemplate(dataSource);
		this.insertComment = new SimpleJdbcInsert(dataSource)
				.withTableName("qComment");
	}
	
	@Override
	public Comment getObject(Long id) {
		if ((templComment
				.queryForInt("Select count(1) FROM qComment WHERE ID = " + id)) > 0) {
			Comment cmnt = (Comment) templComment.queryForObject(
					"SELECT * FROM qComment WHERE ID = " + id,
					new ParameterizedRowMapper<Comment>() {
						public Comment mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Comment Cmnt = new Comment();
							Cmnt.setQuestionID(rs.getLong("questionID"));
							Cmnt.setAuthorID(rs.getLong("AuthorID"));
							Cmnt.setText(rs.getString("text"));
							if (rs.getString("datecommited") != ""
									&& rs.getString("datecommited") != null)
								Cmnt.setDatecommited(Date.valueOf(rs
										.getString("datecommited")));
							
							return Cmnt;
						}
					});
			return cmnt;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeObject(Long id) {
		if (templComment
				.update("DELETE FROM qComment WHERE ID = " + id) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Comment createObject(Entity ent) {
		if (ent != null && ent.getClass() == Comment.class) {
			Comment cmnt = (Comment) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(5);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			
			params.put("questionID", cmnt.getQuestionID());
			params.put("authorID", cmnt.getAuthorID());
			params.put("text", cmnt.getText());
			if (cmnt.getDatecommited() != null)
				params.put("datecommited", cmnt.getDatecommited());
			
			insertComment.execute(params);
			
			return cmnt;
		} else {
			return null;
		}
	}

	@Override
	public Comment updateObject(Entity ent) {
		if (ent != null && ent.getClass() == Comment.class) {
			Comment cmnt = (Comment) ent;
			Comment oldCmnt = getObject(cmnt.getID());
			if (oldCmnt == null)
				return null;
			//TODO error updating comment exception
			String sqlUpdate = String
					.format("UPDATE qComment SET text = %s," +
							" WHERE ID = %s",
							"'" + cmnt.getText() + "'", "'" + cmnt.getID() + "'");
			templComment.update(sqlUpdate);
			return cmnt;
		} else {
			return null;
		}
	}

}
