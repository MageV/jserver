package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AccountService;
import services.UserProfile;
import templater.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    private Map<String,Object> pageVariables=new HashMap<>();

    public SignUpServlet(AccountService accountService)
    {
        this.accountService=accountService;
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
        UserProfile userProfile=new UserProfile(login,password,"");
        accountService.addNewUser(userProfile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("SignedUp");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
