package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.LoginBusiness;
import dao.DBManager;
import model.UserInf;

/**
 * Servlet implementation class LoginAction
 */
@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAction() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // ユーザ情報モデル
        UserInf model = new UserInf(request.getParameter("userId"), request.getParameter("password"));
        DBManager db = (DBManager) request.getAttribute("dbConn");
        LoginBusiness business = new LoginBusiness(db.getConnection());
        model = business.loginUser(model);
        if (model != null) {
            // 画面遷移
            HttpSession session = request.getSession();
            session.setAttribute("userInf", model);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/menu.jsp");
            dispatcher.forward(request, response);
        } else {
            // 画面遷移
            request.setAttribute("resultMsg", "ユーザー情報が存在しません。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

    }

}
