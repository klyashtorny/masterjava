package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import org.junit.Test;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

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
            XMLEventReader eventReader = processor.getEventReader();
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();
                    if("fullName".equals(localPart)){
                        System.out.println(eventReader.getElementText());
                    }
                    if ("User".equals(localPart)) {
                        Iterator<Attribute> attribue = event.asStartElement().getAttributes();
                        while (attribue.hasNext()) {
                            Attribute myAttribute = attribue.next();
                            if ("email".equals(myAttribute.getName().toString())) {
                                System.out.print(myAttribute.getValue() +" / ");
                            }
                        }
                    }
                }
            }
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