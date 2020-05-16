package ru.javaops.masterjava.xml.thymleaf;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Anton Klyashtorny
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        UploadApplication application = new UploadApplication();
        application.process(request, response);
    }
}
