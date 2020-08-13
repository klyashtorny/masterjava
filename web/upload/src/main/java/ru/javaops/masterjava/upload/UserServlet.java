package ru.javaops.masterjava.upload;

import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

/**
 * @author Anton Klyashtorny 11.08.2020
 */
@WebServlet(urlPatterns = "/users", loadOnStartup = 1)
public class UserServlet extends HttpServlet {
    private final UserProcessor userProcessor = new UserProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        try {
            String limit = req.getParameter("limit");
            Integer limitValue = Integer.valueOf(limit);
            if (limitValue < 0 ) {
                throw new Exception("Limit size must be > 0");
            }
            if (limit.isEmpty()) {
                limitValue = 0;
            }
            List<User> users = userProcessor.getWithLimit(limitValue);
            webContext.setVariable("users", users);
            engine.process("result", webContext, resp.getWriter());
        } catch (Exception e) {
            webContext.setVariable("exception", e);
            engine.process("exception", webContext, resp.getWriter());
        }
    }
}
