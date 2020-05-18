package ru.javaops.masterjava.xml.thymleaf;

import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.xml.schema.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
 * @author Anton Klyashtorny
 */
public class UploadApplication {

    private final static String UPLOAD_FORM = "uploadForm";

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        ctx.setVariable("currentDate", new Date());
        ThymeleafAppUtil.getTemplateEngine().process(UPLOAD_FORM, ctx, response.getWriter());
    }

    public void processUser(HttpServletRequest request, HttpServletResponse response, Set<User> users) throws IOException {
        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        ctx.setVariable("users", users);
        ThymeleafAppUtil.getTemplateEngine().process(UPLOAD_FORM, ctx, response.getWriter());
    }
}
