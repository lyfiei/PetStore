package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateProfileServlet extends HttpServlet {

    private static final String EDIT_ACCOUNT_FORM = "WEB-INF/jsp/account/profile.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        // 改为 text/plain，方便 Ajax 接收简单字符串
        resp.setContentType("text/plain;charset=UTF-8");

        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            resp.getWriter().write("session_expired"); // 告知前端登录过期
            return;
        }

        // 1. 接收参数
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address1 = req.getParameter("address1");
        String address2 = req.getParameter("address2");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String country = req.getParameter("country");
        String languagePreference = req.getParameter("languagePreference");
        String favouriteCategoryId = req.getParameter("favouriteCategoryId");
        boolean listOption = "on".equals(req.getParameter("listOption")) || "true".equals(req.getParameter("listOption"));
        boolean bannerOption = "on".equals(req.getParameter("bannerOption")) || "true".equals(req.getParameter("bannerOption"));

        // 2. 校验
        String errorMsg = null;
        if (firstName == null || firstName.trim().isEmpty()) {
            errorMsg = "名字不能为空";
        } else if (lastName == null || lastName.trim().isEmpty()) {
            errorMsg = "姓氏不能为空";
        } else if (email == null || email.trim().isEmpty()) {
            errorMsg = "邮箱不能为空";
        }

        AccountService accountService = new AccountService();
        if (errorMsg == null) {
            Account existingAccount = accountService.getAccountByEmail(email);
            if (existingAccount != null && !existingAccount.getUsername().equals(account.getUsername())) {
                errorMsg = "该邮箱已被其他用户使用，请更换邮箱";
            }
        }

        // 如果校验失败，直接写回错误信息
        if (errorMsg != null) {
            resp.getWriter().write(errorMsg);
            return;
        }

        // 3. 更新对象
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        account.setLanguagePreference(languagePreference);
        account.setFavouriteCategoryId(favouriteCategoryId);
        account.setListOption(listOption);
        account.setBannerOption(bannerOption);

        // 4. 执行数据库更新
        try {
            accountService.updateAccount(account);
            session.setAttribute("loginAccount", account);
            // 成功后，给前端返回 success 信号
            resp.getWriter().write("success");
        } catch (Exception e) {
            resp.getWriter().write("数据库更新失败：" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("loginAccount") == null) {
            resp.sendRedirect("signOnForm");
            return;
        }
        req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
    }
}