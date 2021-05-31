package it.polimi.tiw.tiw_project_2020_21.controllers;

import it.polimi.tiw.tiw_project_2020_21.util.Initializer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "GoToLogin" ,value = "/GoToLogin")
public class GoToLogin extends HttpServlet {

    private static TemplateEngine templateEngine;

    @Override
    public void init() {
        templateEngine = Initializer.templateEngineInit(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
        String path = "/login.html";
        templateEngine.process(path, webContext, response.getWriter());
    }
}
