package presentation.servlets;

import interfaces.ChatService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import presentation.PageGenerator;
import tools.ChatWebSocket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;
    private final Map<String,Object> pageVariables=new HashMap<>();

    public WebSocketChatServlet(ChatService service) {
        this.chatService = service;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("chat.html",pageVariables));
    }
}