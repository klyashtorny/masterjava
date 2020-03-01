package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.*;
import ru.javaops.masterjava.xml.schema.Payload.Projects;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import java.io.IOException;
import java.util.*;
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
    private static final String PROJECT = "Project";
    private static final String PROJECTS = "Projects";

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    public static void main(String[] args) throws Exception {
        String projectName = args[0];

        parseUseJAXB(projectName);
        System.out.println();
        parseUseStax(projectName);
    }

    private static void parseUseJAXB(String projectName) throws javax.xml.bind.JAXBException, IOException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());

        Projects projects = payload.getProjects();
        List<Project> topjavaList = projects.getProject().stream().filter(project ->
                projectName.equals(project.getName())).collect(Collectors.toList());
        Project project = topjavaList.get(0);
        List<Group> groupList = project.getGroup();

        Set<User> userList = new HashSet<>();

        groupList = groupList.stream().map(group -> {
            User user = (User) group.getUser();
            userList.add(user);
            return group;
        }).collect(Collectors.toList());

        System.out.println(PROJECT + " " + projectName + ":");
        userList.stream().sorted(Comparator.comparing(User::getFullName)).forEach(user -> {
            System.out.println(user.getFullName() + " / " + user.getEmail());
        });
    }

    private static void parseUseStax(String projectName) throws Exception {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            List<String> userIds = new ArrayList<>();
            User user = null;
            List<User> userList = new ArrayList<>();
            while (processor.startElement("User", "Users")) {
                user = new User();
                user.setEmail(processor.getAttribute("email"));
                user.setId(processor.getAttribute("id"));
                user.setFullName(processor.getElementValue("fullName"));
                userList.add(user);
            }

            while (processor.startElement(PROJECT, PROJECTS)) {
                if (projectName.equals(processor.getElementValue("name"))) {
                    while (processor.startElement("Group", PROJECT)) {
                        userIds.add(processor.getAttribute("user"));
                    }
                }
            }

            System.out.println(PROJECT + " " + projectName + ":");

            userList.stream()
                    .filter(id -> userIds.contains(id.getId()))
                    .sorted(Comparator.comparing(User::getFullName))
                    .forEach(userOut -> {
                        System.out.println(userOut.getFullName() + " / " + userOut.getEmail());
                    });
        }
    }

}
