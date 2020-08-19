package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.mapper.GroupMapper;

import java.util.List;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
public abstract class GroupDao implements AbstractDao {

    @SqlUpdate("TRUNCATE groups CASCADE")
    public abstract void clean();

    public Group insert(Group group) {
        if (group.isNew()) {
            int id = insertGeneratedId(group, group.getProject().getDescription());
            group.setId(id);
        } else {
            insertWitId(group);
        }
        return group;
    }

    @SqlUpdate("INSERT INTO groups (name, type, project_fk) VALUES (:name,  CAST(:type AS GROUP_TYPE), (SELECT id FROM projects WHERE description = :description))")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Group group, @Bind("description") String description);

    @SqlUpdate("INSERT INTO groups (id, name, project_fk) VALUES (:name, :project.id)")
    abstract void insertWitId(@BindBean Group group);

    @SqlQuery("SELECT * FROM groups left join projects on groups.project_fk = projects.id")
    @Mapper(GroupMapper.class)
    public abstract List<Group> getGroups();
}
