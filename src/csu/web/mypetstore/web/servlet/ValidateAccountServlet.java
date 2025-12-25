package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateAccountServlet extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        resp.setContentType("text/plain;charset=UTF-8");

        if (username != null) {
            Account account = accountService.getAccountByUsername(username);
            resp.getWriter().write(account != null ? "exist" : "ok");
        } else if (email != null) {
            Account account = accountService.getAccountByEmail(email);
            resp.getWriter().write(account != null ? "exist" : "ok");
        }
    }
}
