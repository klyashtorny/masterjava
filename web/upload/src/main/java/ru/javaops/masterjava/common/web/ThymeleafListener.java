package ru.javaops.masterjava.common.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThymeleafListener implements ServletContextListener {

    public static TemplateEngine engine;

    public void contextInitialized(ServletContextEvent sce) {
        engine = ThymeleafUtil.getTemplateEngine(sce.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public static class ThymeleafUtil {

        private ThymeleafUtil() {
        }

        public static TemplateEngine getTemplateEngine(ServletContext context) {
            final ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setPrefix("/WEB-INF/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCacheTTLMs(1000L);
            final TemplateEngine engine = new TemplateEngine();
            engine.setTemplateResolver(templateResolver);
            return engine;
        }
    }
}
