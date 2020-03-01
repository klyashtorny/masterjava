package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import org.junit.Test;
import ru.javaops.masterjava.xml.schema.User;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class StaxStreamProcessorTest {
    @Test
    public void readCities() throws Exception {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    if ("City".equals(reader.getLocalName())) {
                        System.out.println(reader.getElementText());
                    }
                }
            }
        }
    }

    @Test
    public void readUsers() throws Exception {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            User user = null;
            List<User> userList = new ArrayList<>();

            while (processor.startElement("User", "Users")) {
                //System.out.println(processor.getAttribute("email") + processor.getElementValue("fullName"));
                user = new User();
                user.setEmail(processor.getAttribute("email"));
                user.setFullName(processor.getElementValue("fullName"));
                userList.add(user);
            }

            while (processor.startElement("Project", "Projects")) {
                if ("topjava".equals(processor.getElementValue("name"))) {
                    while (processor.startElement("Group", "Project")) {
                        System.out.println(processor.getAttribute("user"));
                    }
                }
            }

            userList.forEach(userOut -> System.out.println(userOut.getFullName() + "/" + userOut.getEmail()));
        }
    }

    @Test
    public void readCities2() throws Exception {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            String city;
            while ((city = processor.getElementValue("City")) != null) {
                System.out.println(city);
            }
        }
    }
}