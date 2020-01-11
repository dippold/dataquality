package org.ftd.mytask.web.cmds;

import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.ftd.mytask.web.mvc.abstracts.MVC;
import org.ftd.mytask.web.mvc.interfaces.ICmd;
import org.neogrid.dataquality.daos.UserDAO;
import org.neogrid.dataquality.entities.UserApp;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 1.0.0 - 2018-05-17
 *
 */
public class LogOutCmd implements ICmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);

        if (session != null) {
            session.removeAttribute("userId");
            session.removeAttribute("userName");
            session.removeAttribute("companyId");
            session.removeAttribute("ruleId");
            session.invalidate();
        }

        String msg = (String) request.getAttribute(MVC.PARAMETER_NAME_MESSAGE);
        if ((msg == null) || (msg.equals(""))) {
            request.setAttribute(MVC.PARAMETER_NAME_MESSAGE, MVC.MSG_USER_LOG_OUT);
        }

        return MVC.VIEW_SIGN_IN;
    }

}
