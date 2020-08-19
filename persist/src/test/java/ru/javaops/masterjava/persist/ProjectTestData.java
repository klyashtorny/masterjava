package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
public class ProjectTestData {

    public static Project TOP_JAVA;
    public static Project MASTER_JAVA;
    public static List<Project> PROJECTS;

    public static void init() {
        TOP_JAVA = new Project("topjava", "Topjava");
        MASTER_JAVA = new Project("masterjava", "Masterjava");
        PROJECTS = ImmutableList.of(TOP_JAVA, MASTER_JAVA);
    }

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            PROJECTS.forEach(dao::insert);
        });
    }
}
