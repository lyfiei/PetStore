package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.AccountService;
import csu.web.mypetstore.service.CatalogService;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SignOnServlet extends HttpServlet {
    private static final String SIGN_ON_FORM = "WEB-INF/jsp/account/signon.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("signOnMsg", "用户名或密码不能为空");
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
            return;
        }

        AccountService accountService = new AccountService();
        Account loginAccount = accountService.getAccount(username, password);

        if (loginAccount == null) {
            req.setAttribute("signOnMsg", "用户名或者密码错误");
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
        } else {
            loginAccount.setPassword(null);
            HttpSession session = req.getSession();
            session.setAttribute("loginAccount", loginAccount);

            if (loginAccount.isListOption()) {
                CatalogService catalogService = new CatalogService();
                List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
                session.setAttribute("myList", myList);
            }
            resp.sendRedirect("mainForm");
        }
    }
}