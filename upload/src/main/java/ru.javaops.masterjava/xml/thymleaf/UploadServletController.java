package ru.javaops.masterjava.xml.thymleaf;

import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.service.UploadService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Anton Klyashtorny
 */
@WebServlet("/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15, // 15 MB
        location = "C:/temp"
)
public class UploadServletController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UploadApplication application = new UploadApplication();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        applyResponseSettings(response);
        application.process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Set<User> users = UploadService.getUsersFromXmlFile(request);
        applyResponseSettings(response);
        application.processUser(request, response, users);
    }

    private void applyResponseSettings(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }
}
