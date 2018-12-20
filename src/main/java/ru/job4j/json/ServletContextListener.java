package ru.job4j.json;


import ru.job4j.util.methods.CommonUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Servlet context listenet.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String path = context.getInitParameter("properties");
        Properties properties = this.getDbConnectionProperties();
        properties.putAll(this.loadPropertiesFromFile(path));
        SingleHolder singleHolder = new SingleHolder(properties);
        context.setAttribute("singleHolder", singleHolder);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        SingleHolder singleHolder = (SingleHolder) context.getAttribute("singleHolder");
        singleHolder.close();
    }

    private Properties getDbConnectionProperties() {
        Properties result = new Properties();
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String dbUser = dbUri.getUserInfo().split(":")[0];
            String dbPassword = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            result.put("db.url", dbUrl);
            result.put("db.user", dbUser);
            result.put("db.password", dbPassword);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Properties loadPropertiesFromFile(String filePath) {
        return CommonUtils.loadProperties(this, filePath);
    }
}
