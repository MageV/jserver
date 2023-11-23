package presentation.servlets;

import interfaces.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AccountServiceImpl;
import services.UserProfileServiceImpl;
import presentation.PageGenerator;
import tools.DBException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountServiceImpl;
    private Map<String,Object> pageVariables=new HashMap<>();

    public SignUpServlet(AccountService accountServiceImpl)
    {
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("signup.html",pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserProfileServiceImpl userProfileServiceImpl =new UserProfileServiceImpl(login,password,"");
        try {
            accountServiceImpl.addNewUser(userProfileServiceImpl);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("SignedUp");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
