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

        // 设置响应格式为文本（或 JSON）
        response.setContentType("text/plain;charset=UTF-8");

        String email = request.getParameter("email");
        String code = request.getParameter("code");

        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute("emailCode");
        String savedEmail = (String) session.getAttribute("emailAccount");

        // 1. 校验验证码是否过期
        if (savedCode == null || savedEmail == null) {
            response.getWriter().write("验证码已过期，请重新获取");
            return;
        }

        // 2. 校验验证码是否匹配
        if (!savedCode.equals(code) || !savedEmail.equals(email)) {
            response.getWriter().write("验证码错误");
            return;
        }

        // 3. 校验用户是否存在
        AccountService service = new AccountService();
        Account account = service.getAccountByEmail(email);

        if (account == null) {
            response.getWriter().write("该邮箱未注册，请先注册账号");
            return;
        }

        // 4. 验证通过，存入 Session
        session.setAttribute("loginAccount", account);

        if(account.isListOption()){
            CatalogService catalogService = new CatalogService();
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            session.setAttribute("myList", myList);
        }

        // 5. 关键：成功时不重定向，而是给前端发送 "success" 信号
        response.getWriter().write("success");
    }
}

