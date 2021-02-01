package ru.javaops.masterjava.service.mail.util;

import com.sun.xml.ws.util.ByteArrayDataSource;
import org.apache.commons.io.IOUtils;
import ru.javaops.masterjava.service.mail.Attachment;

import javax.activation.DataHandler;
import java.io.IOException;
import java.io.InputStream;

public class Attachments {
    public static Attachment getAttachment(String name, InputStream inputStream) throws IOException {
        return new Attachment(name, new DataHandler(new ByteArrayDataSource(IOUtils.toByteArray(inputStream), "application/octet-stream")));
    }
}
