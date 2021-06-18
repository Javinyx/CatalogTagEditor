package it.polimi.tiw.controllers;

import it.polimi.tiw.dao.CategoryDAO;
import it.polimi.tiw.util.Initializer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "CreateCategory", value = "/CreateCategory")
public class CreateCategory extends HttpServlet {
    private static Connection connection;

    @Override
    public void init() {
        connection = Initializer.connectionInit(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("STARTED CreateCategory...");
        HttpSession session = request.getSession();
        String name = request.getParameter("categoryName");
        String categoryParent = request.getParameter("categoryParent");
        CategoryDAO categoryDAO = new CategoryDAO(connection);
        System.out.println();
        if (name.equals("")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty!");
            return;
        } else if (name.length() > 50) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is too long!");
            return;
        } else if (categoryParent == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Specify a category parent!");
            return;
        }

        try {
            int parentDatabaseId = Integer.parseInt(categoryParent);

            try {
                if (categoryDAO.findCategoryDatabaseId(parentDatabaseId, categoryDAO.findAllCategories()) == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't find the parent category!");
                    return;
                }
                categoryDAO.createNewCategory(name, parentDatabaseId);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create a new category!");
                return;
            }
            /*session.removeAttribute("newCategoryError");
            response.setStatus(200);*/

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Selected parent ID is not a valid number!");
        }
        
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}