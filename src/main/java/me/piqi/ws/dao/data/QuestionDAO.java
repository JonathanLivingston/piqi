package me.piqi.ws.dao.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import me.piqi.ws.entities.Entity;
import me.piqi.ws.entities.Question;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class QuestionDAO implements IEntity{
	
	Map<String, Question> profsMap = new HashMap<String, Question>();

	DataSource datasource;
	
	private SimpleJdbcInsert insertQuestion;
	private JdbcTemplate templQuestion;

	public void setDataSource(DataSource dataSource) {
		this.templQuestion = new JdbcTemplate(dataSource);
		this.insertQuestion = new SimpleJdbcInsert(dataSource)
				.withTableName("Question");
	}

	@Override
	public Question getObject(Long id) {
		if ((templQuestion
				.queryForInt("Select count(1) FROM Question WHERE ID = " + id)) > 0) {
			Question qst = (Question) templQuestion.queryForObject(
					"SELECT * FROM Question WHERE ID = " + id,
					new ParameterizedRowMapper<Question>() {
						public Question mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Question Qst = new Question();
							Qst.setQtype(rs.getInt("Qtype"));
							Qst.setState(rs.getInt("State"));
							Qst.setTimetype(rs.getInt("TimeType"));
							if (rs.getString("TimeStarted") != ""
									&& rs.getString("TimeStarted") != null)
								Qst.setTimestarted(Date.valueOf(rs
										.getString("TimeStarted")));
							Qst.setqLocation(rs.getString("qLocation"));
							Qst.setAuthorID(rs.getLong("authorID"));
							Qst.setText(rs.getString("text"));
							Qst.setPic1(rs.getLong("pic1"));
							Qst.setPic2(rs.getLong("pic2"));
							Qst.setQuant(rs.getFloat("quant"));
							
							return Qst;
						}
					});
			return qst;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeObject(Long id) {
		if (templQuestion
				.update("DELETE FROM Question WHERE ID = " + id) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Question createObject(Entity ent) {
		if (ent != null && ent.getClass() == Question.class) {
			Question qst = (Question) ent;
			
			Map<String, Object> params = new HashMap<String, Object>(11);
			
			String uuid = UUID.randomUUID().toString();
			
			params.put("ID", uuid);
			
			params.put("Qtype", qst.getQtype());
			params.put("State", qst.getState());
			params.put("TimeType", qst.getTimetype());
			if (qst.getTimestarted() != null)
				params.put("TimeStarted", qst.getTimestarted());
			if (qst.getqLocation() != null)
				params.put("qLocation", qst.getqLocation());
			params.put("authorID", qst.getAuthorID());
			if (qst.getText() != null)
				params.put("text", qst.getText());
			params.put("pic1", qst.getPic1());
			params.put("pic2", qst.getPic2());
			params.put("quant", qst.getQuant());
			
			insertQuestion.execute(params);
			
			return qst;
		} else {
			return null;
		}
	}

	@Override
	public Question updateObject(Entity ent) {
		if (ent != null && ent.getClass() == Question.class) {
			Question qst = (Question) ent;
			Question oldQuestion = getObject(qst.getID());
			if (oldQuestion == null)
				return null;
			//TODO error updating question exception
			String sqlUpdate = String
					.format("UPDATE Question SET Qtype = %s, State = %s, TimeType = %s, qLocation = %s," +
							" authorID = %s, text = %s, pic1 = %s, pic2 = %s, quant = %s WHERE ID = %s",
							"'" + qst.getQtype() + "'", "'" +  qst.getState() + "'",
							"'" + qst.getTimetype() + "'", "'" + qst.getqLocation() + "'",
							"'" + qst.getAuthorID() + "'", "'" + qst.getText() + "'",
							"'" + qst.getPic1() + "'", "'" + qst.getPic2() + "'",
							"'" + qst.getQuant() + "'", "'" + qst.getID() + "'");
			templQuestion.update(sqlUpdate);
			return qst;
		} else {
			return null;
		}
	}
}
