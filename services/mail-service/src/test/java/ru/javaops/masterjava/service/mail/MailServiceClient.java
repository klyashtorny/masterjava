package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableSet;
import ru.javaops.web.WebStateException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class MailServiceClient {

    public static void main(String[] args) throws MalformedURLException, WebStateException {
        Service service = Service.create(
                new URL("http://localhost:8080/mail/mailService?wsdl"),
                new QName("http://mail.javaops.ru/", "MailServiceImplService"));
                new QName("http://mail.javaops.ru/", "MailServiceImplService");

        MailService mailService = service.getPort(MailService.class);
        mailService.sendToGroup(ImmutableSet.of(
                new Addressee("masterjava@javaops.ru", null)), null, "Subject", "Body", null);

        String state = mailService.sendToGroup(ImmutableSet.of(new Addressee("lm_1@tut.by", null)), null,
                "Group mail subject", "Group mail body", null);
        System.out.println("Group mail state: " + state);

        GroupResult groupResult = mailService.sendBulk(ImmutableSet.of(
                new Addressee("Мастер Java <lm_1@tut.by>"),
                new Addressee("Bad Email <bad_email.ru>")), "Bulk mail subject", "Bulk mail body", null);
        System.out.println("\nBulk mail groupResult:\n" + groupResult);
    }
}
