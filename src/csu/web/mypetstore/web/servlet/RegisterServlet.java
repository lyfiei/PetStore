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
    private static final String SIGN_ON_FORM =  "WEB-INF/jsp/account/signon.jsp"; // 注册成功跳转登录页面

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置请求编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // 获取表单参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
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
        if(username == null || username.trim().isEmpty()) {
            msg = "用户名不能为空";
        } else if(password == null || password.trim().isEmpty()) {
            msg = "密码不能为空";
        } else if(!password.equals(confirmPassword)) {
            msg = "两次输入的密码不一致";
        } else if(firstName == null || firstName.trim().isEmpty()) {
            msg = "名字不能为空";
        } else if(lastName == null || lastName.trim().isEmpty()) {
            msg = "姓氏不能为空";
        }

        if(msg != null) {
            req.setAttribute("registerMsg", msg);
            req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
            return;
        }

        AccountService accountService = new AccountService();

        System.out.println("注册用户名: " + username);
        System.out.println("注册邮箱: " + email);

        // 处理邮箱重复
        String emailInput = email != null ? email.trim().toLowerCase() : null;
        Account existEmail = accountService.getAccountByEmail(emailInput);
        if (existEmail != null) {
            req.setAttribute("registerMsg", "该邮箱已注册");
            req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
            return;
        }

        // 封装 Account 对象
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
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

        // 调用 Service 保存到数据库
        try {
            accountService.insertAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("registerMsg", e.getMessage());
            req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
            return;
        }


        // 注册成功，重定向到登录页
        resp.sendRedirect("signOnForm");
    }
}

