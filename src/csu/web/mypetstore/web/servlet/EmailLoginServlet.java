package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.AccountService;
import csu.web.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EmailLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute("emailCode");
        String savedEmail = (String) session.getAttribute("emailAccount");

        if (savedCode == null || savedEmail == null) {
            request.setAttribute("msg", "验证码已过期，请重新获取");
            request.getRequestDispatcher("/WEB-INF/jsp/account/signon_code.jsp").forward(request, response);
            return;
        }

        if (!savedCode.equals(code) || !savedEmail.equals(email)) {
            request.setAttribute("msg", "验证码错误");
            request.getRequestDispatcher("/WEB-INF/jsp/account/signon_code.jsp").forward(request, response);
            return;
        }

        AccountService service = new AccountService();
        Account account = service.getAccountByEmail(email);

        if (account == null) {
            request.setAttribute("msg", "该邮箱未注册，请先注册账号");
            request.getRequestDispatcher("/WEB-INF/jsp/account/register.jsp").forward(request, response);
            return;
        }

        // 验证通过，登录
        session.setAttribute("loginAccount", account);

        if(account.isListOption()){
            CatalogService catalogService = new CatalogService();
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            session.setAttribute("myList", myList);
        }

        // 跳转主页
        response.sendRedirect(request.getContextPath() + "/mainForm");


    }

}

