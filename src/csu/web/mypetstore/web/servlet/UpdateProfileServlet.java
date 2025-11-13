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
    private static final String SIGN_ON_FORM = "WEB-INF/jsp/account/signon.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            resp.sendRedirect("signOnForm");
            return;
        }

        // 获取表单参数
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
        boolean listOption = "on".equals(req.getParameter("listOption"));
        boolean bannerOption = "on".equals(req.getParameter("bannerOption"));

        // 简单校验
        String msg = null;
        if (firstName == null || firstName.trim().isEmpty()) {
            msg = "名字不能为空";
        } else if (lastName == null || lastName.trim().isEmpty()) {
            msg = "姓氏不能为空";
        } else if (email == null || email.trim().isEmpty()) {
            msg = "邮箱不能为空";
        }

        // 邮箱唯一性检查
        if (msg == null) {
            AccountService accountService = new AccountService();
            Account existingAccount = accountService.getAccountByEmail(email);

            // 如果查到的账号不是当前登录用户 → 冲突
            if (existingAccount != null && !existingAccount.getUsername().equals(account.getUsername())) {
                msg = "该邮箱已被其他用户使用，请更换邮箱";
            }
        }


        if (msg != null) {
            req.setAttribute("updateMsg", msg);
            req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
            return;
        }

        // 更新 Account 对象
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

        // 调用 Service 更新数据库
        AccountService accountService = new AccountService();
        try {
            accountService.updateAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            req.setAttribute("updateMsg", "资料更新失败：" + e.getMessage());
            req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
            return;
        }

        // 更新成功
        session.setAttribute("loginAccount", account);
        session.setAttribute("updateMsg", "资料修改成功！");
        resp.sendRedirect(req.getContextPath() + "/mainForm");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loginAccount");
        if (account == null) {
            resp.sendRedirect("signOnForm");
            return;
        }
        req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
    }
}
