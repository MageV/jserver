package presentation.servlets;

import interfaces.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AccountServiceImpl;
import presentation.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInServlet extends HttpServlet {
    private final AccountService accountServiceImpl;
    private final Map<String,Object> pageVariables=new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("signin.html",pageVariables));
    }

    public SignInServlet(AccountService accountServiceImpl)
    {
        this.accountServiceImpl = accountServiceImpl;
    }



    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean loggedIn = accountServiceImpl.getUserByLogin(login);
        response.setContentType("text/html;charset=utf-8");
        if (loggedIn) {
            response.getWriter().println("Authorized: "+login);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
