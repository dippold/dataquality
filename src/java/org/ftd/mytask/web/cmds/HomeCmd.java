package org.ftd.mytask.web.cmds;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ftd.mytask.web.mvc.abstracts.MVC;
import org.ftd.mytask.web.mvc.interfaces.ICmd;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 1.0.0 - 2018-05-17
 *
 */
public class HomeCmd implements ICmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MVC.setDefaultAttributes(request);
        request.setAttribute("title", "Dataquality - Menu Principal");
        
        
        return "WEB-INF/views/Home.jsp";
    }


  

}
