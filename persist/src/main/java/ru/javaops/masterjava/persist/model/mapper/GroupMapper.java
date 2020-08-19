package ru.javaops.masterjava.persist.model.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;
import ru.javaops.masterjava.persist.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
public class GroupMapper implements ResultSetMapper<Group> {

    public Group map(int index, ResultSet rs, StatementContext ctx) throws SQLException {

        return new Group(rs.getInt("id"),
                rs.getString("name"),
                GroupType.valueOf(rs.getString("type")),
                new Project(rs.getInt("project_fk"),
                        rs.getString(6),
                        rs.getString("description")));
    }
}
