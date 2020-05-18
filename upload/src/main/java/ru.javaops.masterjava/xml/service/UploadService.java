package ru.javaops.masterjava.xml.service;

import com.google.common.base.Splitter;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Strings.nullToEmpty;

/**
 * @author Anton Klyashtorny 18.05.2020
 */
public class UploadService {

    private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getValue).thenComparing(User::getEmail);

    public static Set<User> getUsersFromXmlFile(HttpServletRequest request) throws IOException {
        Set<User> users = null;
        try {
            final Part filePart = request.getPart("file");
            users = new TreeSet<>(USER_COMPARATOR);
            try (StaxStreamProcessor processor = new StaxStreamProcessor(filePart.getInputStream())) {
                final Set<String> groupNames = new HashSet<>();
                while (processor.startElement("Project", "Projects")) {
                    while (processor.startElement("Group", "Project")) {
                        groupNames.add(processor.getAttribute("name"));
                    }
                }
                JaxbParser parser = new JaxbParser(User.class);
                while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
                    String groupRefs = processor.getAttribute("groupRefs");
                    if (!Collections.disjoint(groupNames, Splitter.on(' ').splitToList(nullToEmpty(groupRefs)))) {
                        User user = parser.unmarshal(processor.getReader(), User.class);
                        users.add(user);
                    }
                }
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return users;
    }
}
