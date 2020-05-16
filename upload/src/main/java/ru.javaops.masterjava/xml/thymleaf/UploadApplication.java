package ru.javaops.masterjava.xml.thymleaf;

import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Anton Klyashtorny
 */
public class UploadApplication {
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        WebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale());
        ctx.setVariable("currentDate", new Date());
        ThymeleafAppUtil.getTemplateEngine().process("uploadForm", ctx, response.getWriter());
    }
}
