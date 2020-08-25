package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;

import java.util.List;

@Slf4j
public class MailSender {
    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
            Email email = getConfigMail();
            to.forEach(addressee -> {
                try {
                    email.setSubject(subject);
                    email.setMsg(body);
                    email.addTo(addressee.getEmail());
                    email.send();
                } catch (EmailException e) {
                    log.error(e.getMessage());
                }

            });
    }

    private static Email getConfigMail() {

        Config mailConfig = Configs.getConfig("mail.conf", "mail");

        Email email = new SimpleEmail();
        email.setSmtpPort(mailConfig.getInt("port"));
        email.setAuthenticator(new DefaultAuthenticator(mailConfig.getString("username"),
                mailConfig.getString("password")));
        email.setDebug(mailConfig.getBoolean("debug"));
        email.setSSL(mailConfig.getBoolean("useSSL"));
        email.setTLS(mailConfig.getBoolean("useTLS"));
        email.setHostName(mailConfig.getString("host"));
        try {
            email.setFrom(mailConfig.getString("fromName"));
        } catch (EmailException e) {
            log.error(e.getMessage());
        }

        return email;
    }


}
