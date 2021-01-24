package ru.javaops.masterjava.webapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.service.mail.SoapFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet("/send")
@Slf4j
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15, // 15 MB
        location = "D:/Uploads"
)
public class SendServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result;
        try {
            List<SoapFile> soapFiles = new ArrayList<>();
            byte[] bytes = null;
            List<Part> parts = req.getParts().stream()
                    .filter(part -> "attachments".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());
            for (Part part : parts) {
                bytes = IOUtils.toByteArray(part.getInputStream());
                SoapFile soapFile = new SoapFile();
                soapFile.setFileName(part.getSubmittedFileName());
                soapFile.setFileData(bytes);
                soapFiles.add(soapFile);
            }

            log.info("Start sending");
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String users = new String (req.getParameter("users").getBytes (ISO_8859_1), UTF_8);
            String subject = new String (req.getParameter("subject").getBytes (ISO_8859_1), UTF_8);
            String body = new String (req.getParameter("body").getBytes (ISO_8859_1), UTF_8);
            GroupResult groupResult = MailWSClient.sendBulk(MailWSClient.split(users), subject, body, soapFiles);
            result = groupResult.toString();
            log.info("Processing finished with result: {}", result);
        } catch (Exception e) {
            log.error("Processing failed", e);
            result = e.toString();
        }
        resp.getWriter().write(result);
    }
}
