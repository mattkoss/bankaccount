package com.saraphie.bankaccount;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Server server = new Server(8080);

        // initialise servlet processing, all endpoints from the package com.saraphie.bankaccount.endpoint.rest will be made accessible
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ctx.setContextPath("/");
        server.setHandler(ctx);
        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/api/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages", "com.saraphie.bankaccount.endpoint.rest");
        serHol.setInitParameter("javax.ws.rs.Application", "com.saraphie.bankaccount.dependency.BankAccountConfig");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            LOGGER.error("Error during server lifecycle.", ex);
        } finally {
            server.destroy();
        }
    }
}

