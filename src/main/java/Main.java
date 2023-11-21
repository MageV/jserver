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
        AccountService accountService=new AccountService();
        SignInServlet signInServlet=new SignInServlet(accountService);
        SignUpServlet signUpServlet=new SignUpServlet(accountService);
        context.addServlet(new ServletHolder(signInServlet),"/signin");
        context.addServlet(new ServletHolder(signUpServlet),"/signup");
        Server server=new Server(8080);
        server.setHandler(context);
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

    private static void  initServer(ServletContextHandler context)
    {
    }
}
