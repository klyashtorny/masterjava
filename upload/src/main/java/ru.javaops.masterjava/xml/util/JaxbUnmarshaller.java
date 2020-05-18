package ru.javaops.masterjava.xml.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class JaxbUnmarshaller {
    private final ThreadLocal<Unmarshaller> unmarshaller = new ThreadLocal<>();

    public JaxbUnmarshaller(JAXBContext ctx) throws JAXBException {
        unmarshaller.set(ctx.createUnmarshaller());
    }

    public synchronized void setSchema(Schema schema) {
        unmarshaller.get().setSchema(schema);
    }

    public synchronized Object unmarshal(InputStream is) throws JAXBException {
        return unmarshaller.get().unmarshal(is);
    }

    public synchronized Object unmarshal(Reader reader) throws JAXBException {
        return unmarshaller.get().unmarshal(reader);
    }

    public Object unmarshal(String str) throws JAXBException {
        return unmarshal(new StringReader(str));
    }

    public synchronized <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
        return unmarshaller.get().unmarshal(reader, elementClass).getValue();
    }
}