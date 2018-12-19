package com.zetcode.embedded;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class Launcher {

    public static void main(String[] args) throws LifecycleException, ServletException {

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(webPort));

        String json = "json/webapp";

        tomcat.addWebapp("/json", new File(json).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}