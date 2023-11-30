import interfaces.AccountService;
import interfaces.ChatService;
import interfaces.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import presentation.servlets.WebSocketChatServlet;
import services.AccountServiceImpl;
import services.ChatServiceImpl;
import services.DbServiceImpl;
import tools.AppContext;
import tools.ChatWebSocket;

//import servlets.MirrorServlet;
public class Main {
    public static void main(String[] args) throws Exception
    {
        AppContext appContext =new AppContext();
        ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
    //    appContext.add(DBService.class,DbServiceImpl.instance());
    //    appContext.add(AccountService.class,AccountServiceImpl.instance(appContext));
        ChatService chatService=new ChatServiceImpl();
        appContext.add(ChatService.class, chatService);
    //    SignInServlet signInServlet=new SignInServlet((AccountService) appContext.get(AccountService.class));
     //   SignUpServlet signUpServlet=new SignUpServlet((AccountService) appContext.get(AccountService.class));
        WebSocketChatServlet webSocketChatServlet=new WebSocketChatServlet((ChatService) appContext.get(ChatService.class));
        context.addServlet(new ServletHolder(webSocketChatServlet),"/chat");
     //   context.addServlet(new ServletHolder(signInServlet),"/signin");
     //  context.addServlet(new ServletHolder(signUpServlet),"/signup");
        Server appServer=new Server(8080);
        appServer.setHandler(context);
        appServer.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        appServer.join();
    }

    private static void  initServer(ServletContextHandler context)
    {
    }
}
