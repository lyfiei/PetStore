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
        // 从 session 里获取登录用户
        Account loginAccount = (Account) req.getSession().getAttribute("loginAccount");

        System.out.println("=== ProfileFormServlet Debug ===");
        System.out.println("loginAccount from session: " + loginAccount);
        System.out.println("Session ID: " + req.getSession().getId());
        System.out.println("Session isNew: " + req.getSession().isNew());

        if (loginAccount != null) {
            System.out.println("loginAccount username: " + loginAccount.getUsername());

            AccountService accountService = new AccountService();
            Account account = accountService.getAccountByUsername(loginAccount.getUsername());

            System.out.println("Account from database: " + account);
            System.out.println("Account username: " + account.getUsername());
            System.out.println("Account firstName: " + account.getFirstName());
            System.out.println("Account lastName: " + account.getLastName());


            // 使用request属性而不是session
            req.setAttribute("account", account);
            System.out.println("Set account to request attribute");
        } else {
            resp.sendRedirect("signOnForm");
            return;
        }

        // 转发到 profile.jsp
        req.getRequestDispatcher(PROFILE_FORM).forward(req, resp);
    }
}
