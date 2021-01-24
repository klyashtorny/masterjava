package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.mail.MultiPartEmail;
import ru.javaops.masterjava.ExceptionType;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.service.mail.persist.MailCase;
import ru.javaops.masterjava.service.mail.persist.MailCaseDao;
import ru.javaops.web.WebStateException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@Slf4j
public class MailSender {
    private static final MailCaseDao MAIL_CASE_DAO = DBIProvider.getDao(MailCaseDao.class);
    private static final String UTF8 = "UTF8";

    static MailResult sendTo(Addressee to, String subject, String body, List<SoapFile> soapFiles) throws WebStateException {
        val state = sendToGroup(ImmutableSet.of(to), ImmutableSet.of(), subject, body, soapFiles);
        return new MailResult(to.getEmail(), state);
    }

    static String sendToGroup(Set<Addressee> to, Set<Addressee> cc, String subject, String body, List<SoapFile> soapFiles) throws WebStateException {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        String state = MailResult.OK;

        try {
            val email = MailConfig.createHtmlEmail();
            addAttachment(email, body, soapFiles);
            email.setSubject(subject);
            for (Addressee addressee : to) {
                email.addTo(addressee.getEmail(), addressee.getName());
            }
            for (Addressee addressee : cc) {
                email.addCc(addressee.getEmail(), addressee.getName());
            }

            //  https://yandex.ru/blog/company/66296
            email.setHeaders(ImmutableMap.of("List-Unsubscribe", "<mailto:masterjava@javaops.ru?subject=Unsubscribe&body=Unsubscribe>"));

            email.send();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            state = e.getMessage();
        }
        try {
            MAIL_CASE_DAO.insert(MailCase.of(to, cc, subject, state));
        } catch (Exception e) {
            log.error("Mail history saving exception", e);
            throw new WebStateException(e, ExceptionType.DATA_BASE);
        }
        log.info("Sent with state: " + state);
        return state;
    }

    private static void addAttachment(MultiPartEmail email, String body, List<SoapFile> soapFiles) throws Exception {
        email.setCharset(UTF8);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body, UTF8);
        multipart.addBodyPart(textPart);
        for (SoapFile soapFile : soapFiles) {
            DataSource ds = new ByteArrayDataSource(soapFile.getFileData(), "application/octet-stream");
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(ds));
            try {
                attachmentPart.setFileName(MimeUtility.encodeText(soapFile.getFileName(), UTF8, null));
            } catch (UnsupportedEncodingException ex) {
                attachmentPart.setFileName("attachName");
            }
            multipart.addBodyPart(attachmentPart);
            email.attach(ds, "Attachment", "email");
            email.setContent(multipart);
        }
    }
}
