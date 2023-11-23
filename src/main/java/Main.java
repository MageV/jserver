import interfaces.AccountService;
import interfaces.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.AccountServiceImpl;
import services.DbServiceImpl;
import presentation.servlets.SignInServlet;
import presentation.servlets.SignUpServlet;
import tools.AppContext;

//import servlets.MirrorServlet;
public class Main {
    public static void main(String[] args) throws Exception
    {
        AppContext appContext =new AppContext();
        ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
        appContext.add(DBService.class,DbServiceImpl.instance());
        appContext.add(AccountService.class,AccountServiceImpl.instance(appContext));
        SignInServlet signInServlet=new SignInServlet((AccountService) appContext.get(AccountService.class));
        SignUpServlet signUpServlet=new SignUpServlet((AccountService) appContext.get(AccountService.class));
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
