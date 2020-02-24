package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import org.junit.Test;
import ru.javaops.masterjava.xml.schema.CityType;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Payload.Projects;
import ru.javaops.masterjava.xml.schema.Project;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JaxbParserTest {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    @Test
    public void testPayload() throws Exception {
//        JaxbParserTest.class.getResourceAsStream("/city.xml")
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        String strPayload = JAXB_PARSER.marshal(payload);
        JAXB_PARSER.validate(strPayload);
        System.out.println(strPayload);
    }

    @Test
    public void testProject() throws Exception {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        final String topjava = "topjava";

        Projects projects = payload.getProjects();
        List<Project> topjavaList = projects.getProject().stream().filter(project ->
                topjava.equals(project.getName())).collect(Collectors.toList());
        Project project = topjavaList.get(0);
        List<Group> groupList = project.getGroup();
        System.out.println("Project " + topjava);
        groupList.stream().sorted(Comparator.comparing(Group::getName)).forEach(group -> {
            System.out.println(group.getName()+ " " + group.getType());
        });
    }

    @Test
    public void testCity() throws Exception {
        JAXBElement<CityType> cityElement = JAXB_PARSER.unmarshal(
                Resources.getResource("city.xml").openStream());
        CityType city = cityElement.getValue();
        JAXBElement<CityType> cityElement2 =
                new JAXBElement<>(new QName("http://javaops.ru", "City"), CityType.class, city);
        String strCity = JAXB_PARSER.marshal(cityElement2);
        JAXB_PARSER.validate(strCity);
        System.out.println(strCity);
    }
}