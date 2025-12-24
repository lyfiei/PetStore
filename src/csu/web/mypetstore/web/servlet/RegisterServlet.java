package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.service.AccountService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private static final String REGISTER_FORM = "WEB-INF/jsp/account/register.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String captchaInput = req.getParameter("captchaInput");
        String captchaSession = (String) req.getSession().getAttribute("captcha");

        // 基础非空和逻辑校验
        String msg = null;
        if (username == null || username.trim().isEmpty()) msg = "用户名不能为空";
        else if (password == null || !password.equals(confirmPassword)) msg = "密码不匹配";
        else if (captchaInput == null || !captchaInput.equalsIgnoreCase(captchaSession)) msg = "验证码错误";

        if (msg != null) {
            req.setAttribute("registerMsg", msg);
            req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
            return;
        }

        // 调用 Service
        AccountService accountService = new AccountService();
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setFirstName(req.getParameter("firstName"));
        account.setLastName(req.getParameter("lastName"));
        account.setPhone(req.getParameter("phone"));
        account.setAddress1(req.getParameter("address1"));
        account.setAddress2(req.getParameter("address2"));
        account.setCity(req.getParameter("city"));
        account.setState(req.getParameter("state"));
        account.setZip(req.getParameter("zip"));
        account.setCountry(req.getParameter("country"));
        account.setLanguagePreference(req.getParameter("languagePreference"));
        account.setFavouriteCategoryId(req.getParameter("favouriteCategoryId"));
        account.setListOption("on".equals(req.getParameter("listOption")));
        account.setBannerOption("on".equals(req.getParameter("bannerOption")));

        try {
            accountService.insertAccount(account);
            resp.sendRedirect("signOnForm");
        } catch (Exception e) {
            req.setAttribute("registerMsg", e.getMessage());
            req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
        }
    }
}