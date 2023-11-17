import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.AccountService;
import servlets.*;

//import servlets.MirrorServlet;
public class Main {
    public static void main(String[] args) throws Exception
    {
        ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
        //context.addServlet(new ServletHolder(frontend), "/*");
        Server server=new Server(8080);
        server.setHandler(initServer(context));
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

    private static HandlerList  initServer(ServletContextHandler context)
    {
        AccountService accountService=new AccountService();
        UsersServlet usersServlet=new UsersServlet(accountService);
        SessionServlet sessionServlet=new SessionServlet(accountService);
        context.addServlet(new ServletHolder(usersServlet),"/signup");
        context.addServlet(new ServletHolder(sessionServlet),"/signin");
        ResourceHandler resourceHandler=new ResourceHandler();
        resourceHandler.setResourceBase("templates");
        HandlerList handlerList=new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler,context});
        return handlerList;
    }
}
