package ru.javaops.masterjava.service.mail.rest;


import com.google.common.collect.ImmutableList;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ru.javaops.masterjava.service.mail.Attachment;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailServiceExecutor;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.service.mail.util.Attachments;
import ru.javaops.masterjava.web.WebStateException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/")
public class MailRS {
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Test";
    }

    @POST
    @Path("send")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public GroupResult send(@FormDataParam("users") String users,
                            @FormDataParam("subject") String subject,
                            @FormDataParam("body") String body,
                            @FormDataParam("attach") InputStream uploadedInputStream,
                            @FormDataParam("attach") FormDataContentDisposition fileDetail) throws WebStateException, IOException {

        List<Attachment> attachments = fileDetail == null ? null :
                ImmutableList.of(Attachments.getAttachment(fileDetail.getFileName(), uploadedInputStream));
        return MailServiceExecutor.sendBulk(MailWSClient.split(users), subject, body, attachments);
    }
}