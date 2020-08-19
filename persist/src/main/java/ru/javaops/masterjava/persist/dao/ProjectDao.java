package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao {

    @SqlUpdate("TRUNCATE projects CASCADE")
    @Override
    public abstract void clean();

    public Project insert(Project project) {
        if (project.isNew()) {
            int id = insertGeneratedId(project);
            project.setId(id);
        } else {
            insertWitId(project);
        }
        return project;
    }

    @SqlUpdate("INSERT INTO projects (name, description) VALUES (:name, :description)")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO projects (id, name, description) VALUES (:name, :description))")
    abstract void insertWitId(@BindBean Project project);

    @SqlQuery("SELECT * FROM projects")
    public abstract List<Project> getProjects();

}
