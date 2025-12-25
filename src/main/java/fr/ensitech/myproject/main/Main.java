package fr.ensitech.myproject.main;

import fr.ensitech.myproject.repository.HibernateConnector;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;

import java.net.URI;

public class Main {

    public static void main(String[] args) {

        try {
            Session session = HibernateConnector.getSession();
            System.out.println("Session : " + session);
            session.close();

            final URI BASE_URI = URI.create("http://localhost:9991/ws/rest");

            ResourceConfig rc =  new ResourceConfig().packages("fr.ensitech.myproject.controller.rest");
            ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc, locator);
            server.start();
            Thread.sleep(2000);
            System.out.println("Server started with success.");
            System.out.println("Server started on => http://localhost:9991/ws/rest");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
