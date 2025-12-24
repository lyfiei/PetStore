package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileFormServlet extends HttpServlet {

    private static final String PROFILE_FORM = "WEB-INF/jsp/account/profile.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Account loginAccount = (Account) req.getSession().getAttribute("loginAccount");

        if (loginAccount != null) {
            System.out.println("loginAccount username: " + loginAccount.getUsername());

            AccountService accountService = new AccountService();
            Account account = accountService.getAccountByUsername(loginAccount.getUsername());

            req.setAttribute("account", account);
        } else {
            resp.sendRedirect("signOnForm");
            return;
        }

        req.getRequestDispatcher(PROFILE_FORM).forward(req, resp);
    }
}
