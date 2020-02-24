package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Payload.Projects;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

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

        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());

        Projects projects = payload.getProjects();
        List<Project> topjavaList = projects.getProject().stream().filter(project ->
                projectName.equals(project.getName())).collect(Collectors.toList());
        Project project = topjavaList.get(0);
        List<Group> groupList = project.getGroup();
        System.out.println("Project " + projectName);
        groupList.stream().sorted(Comparator.comparing(Group::getName)).forEach(group -> {
            System.out.println(group.getName()+ " " + group.getType());
        });
    }

    private void parseStax(String name) {

    }
}
