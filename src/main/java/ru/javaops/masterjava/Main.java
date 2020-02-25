package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Payload.Projects;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    public static void main(String[] args) throws Exception{
        String projectName = args[0];

        parseUseJAXB(projectName);
    }

    private static void parseUseJAXB(String projectName) throws javax.xml.bind.JAXBException, IOException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());

        Projects projects = payload.getProjects();
        List<Project> topjavaList = projects.getProject().stream().filter(project ->
                projectName.equals(project.getName())).collect(Collectors.toList());
        Project project = topjavaList.get(0);
        List<Group> groupList = project.getGroup();

        List<User> userList = new ArrayList<>();

        groupList = groupList.stream().map(group -> {
            User user = (User) group.getUser();
            userList.add(user);
            return group;
        }).collect(Collectors.toList());

        System.out.println("Project " + projectName);
        userList.stream().sorted(Comparator.comparing(User::getFullName)).forEach(user -> {
            System.out.println(user.getFullName()+ " " + user.getEmail());
        });
    }

}
