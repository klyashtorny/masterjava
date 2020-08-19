package ru.javaops.masterjava.persist;

import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javaops.masterjava.persist.ProjectTestData.MASTER_JAVA;
import static ru.javaops.masterjava.persist.ProjectTestData.TOP_JAVA;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
public class GroupTestData {

    public static Group TOP_JAVA06;
    public static Group TOP_JAVA07;
    public static Group TOP_JAVA08;
    public static Group MASTER_JAVA01;
    public static List<Group> GROUPS = new ArrayList<>();


    public static void init() {
        TOP_JAVA06 = new Group("topjava06", GroupType.finished, TOP_JAVA);
        TOP_JAVA07 = new Group("topjava07", GroupType.finished, TOP_JAVA);
        TOP_JAVA08 = new Group("topjava08", GroupType.current,  TOP_JAVA);
        MASTER_JAVA01 = new Group("masterjava01", GroupType.current, MASTER_JAVA);
        GROUPS.addAll(Arrays.asList(TOP_JAVA06, TOP_JAVA07, TOP_JAVA08, MASTER_JAVA01));

    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            GROUPS.forEach(dao::insert);
        });
    }
}
