package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import templater.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends HttpServlet{
    private  String login="";

    public  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String parameters="";
        Map<String,Object> pageVariables=createPageVariablesMap(request);
        java.util.logging.Logger.getGlobal().info(pageVariables.get("pathInfo").toString());
        if(pageVariables.get("pathInfo").toString().contains("mirror"))
        {
            parameters= (String) pageVariables.get("parameters");
            pageVariables.put("message",parameters);
        }
        else
        {
            pageVariables.put("message","");
        }
        //doMakeHTML(response,pageVariables);
        response.getWriter().println(parameters);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        Map<String,Object> pageVariables=createPageVariablesMap(request);
        String message=request.getParameter("message");
        response.setContentType("text/html;charset=utf-8");
        if(message==null||message.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message",message==null?"":message);
        doMakeHTML(response,pageVariables);


    }
    private static Map<String,Object> createPageVariablesMap(HttpServletRequest request)
    {
        Map<String,Object> pageVariables=new HashMap<>();
        pageVariables.put("method",request.getMethod());
        pageVariables.put("URL",request.getRequestURL());
        pageVariables.put("pathInfo",request.getPathInfo());
        pageVariables.put("sessionId",request.getSession().getId());
        pageVariables.put("parameters",request.getParameter("key"));
        return pageVariables;
    }
    private static void doMakeHTML(HttpServletResponse response, Map<String,Object> pageVariables) throws IOException
    {
        response.getWriter().println(PageGenerator.instance().getPage("page_2.html",pageVariables));
    }
}