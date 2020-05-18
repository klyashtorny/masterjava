package ru.javaops.masterjava.xml.util;

import javax.xml.bind.*;
import javax.xml.validation.Schema;
import java.io.StringWriter;
import java.io.Writer;

public class JaxbMarshaller {

    private final ThreadLocal<Marshaller> marshaller = new ThreadLocal<>();

    public JaxbMarshaller(JAXBContext ctx) throws JAXBException {
        marshaller.set(ctx.createMarshaller());
        marshaller.get().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.get().setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.get().setProperty(Marshaller.JAXB_FRAGMENT, true);
    }

    public void setProperty(String prop, Object value) throws PropertyException {
        marshaller.get().setProperty(prop, value);
    }

    public synchronized void setSchema(Schema schema) {
        marshaller.get().setSchema(schema);
    }

    public String marshal(Object instance) throws JAXBException {
        StringWriter sw = new StringWriter();
        marshal(instance, sw);
        return sw.toString();
    }

    public synchronized void marshal(Object instance, Writer writer) throws JAXBException {
        marshaller.get().marshal(instance, writer);
    }

}
