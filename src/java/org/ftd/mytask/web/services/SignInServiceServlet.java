package org.ftd.mytask.web.services;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.ftd.mytask.web.mvc.abstracts.MVC;
import org.neogrid.dataquality.daos.UserDAO;
import org.neogrid.dataquality.entities.UserApp;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 1.0.0 - 2018-05-17
 *
 */
@WebServlet(name = "SignInServiceServlet", urlPatterns = {"/signin"})
public class SignInServiceServlet extends HttpServlet {

    private static final long serialVersionUID = 2388257921769362666L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = MVC.readParameter(request, "email");
        String passwd = MVC.readParameter(request, "passwd");

        if ((email != null) && (passwd != null)) {

            UserApp user = this.findUser(email, passwd);

            if ((user != null) && (!user.isBlocked())) {

                HttpSession session = request.getSession(true);
                session.setAttribute("userId", Long.toString(user.getId()));
                session.setAttribute("userName", user.getName());
                session.setAttribute("ruleId", Long.toString(user.getRuleId()));

                final String resource = MVC.URL_MVC_SERVICE
                        + "?"
                        + MVC.PARAMETER_NAME_CMD
                        + "="
                        + MVC.CMD_HOME;

                request.getRequestDispatcher(resource).forward(request, response);

            } else {

                request.setAttribute(MVC.PARAMETER_NAME_MESSAGE, MVC.MSG_INVALID_PASSWD);
                request.getRequestDispatcher(MVC.VIEW_SIGN_IN).forward(request, response);

            }
        } else {

            request.setAttribute(MVC.PARAMETER_NAME_MESSAGE, MVC.MSG_INVALID_EMAIL);
            request.getRequestDispatcher(MVC.VIEW_SIGN_IN).forward(request, response);

        }

    }

    private UserApp findUser(String email, String passwd) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        UserDAO dao = new UserDAO(factory);
        UserApp user;
        try {
            user = dao.authenticate(email, passwd);
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
