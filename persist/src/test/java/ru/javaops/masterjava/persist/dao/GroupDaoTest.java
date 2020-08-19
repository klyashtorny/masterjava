package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.GroupTestData;
import ru.javaops.masterjava.persist.ProjectTestData;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

import static ru.javaops.masterjava.persist.GroupTestData.GROUPS;

/**
 * @author Anton Klyashtorny 18.08.2020
 */
public class GroupDaoTest extends AbstractDaoTest<GroupDao> {

    public GroupDaoTest() {
        super(GroupDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        ProjectTestData.init();
        GroupTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        ProjectTestData.setUp();
        GroupTestData.setUp();
    }

    @Test
    public void getGroups() throws Exception {
        List<Group> groups = dao.getGroups();
        Assert.assertEquals(GROUPS, groups);
    }
}
