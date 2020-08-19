package ru.javaops.masterjava.persist.model.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anton Klyashtorny 17.08.2020
 */
public class UserMapper implements ResultSetMapper<User> {

    public User map(int index, ResultSet rs, StatementContext ctx) throws SQLException {

        return new User(rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                UserFlag.valueOf(rs.getString("flag")),
                new City(rs.getInt("city_fk"),
                        rs.getString("short_name"),
                        rs.getString("value")));
    }
}
