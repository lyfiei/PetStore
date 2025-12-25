package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.service.AccountService;
import csu.web.mypetstore.service.EmailService;
import csu.web.mypetstore.util.CodeUtil;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SendEmailCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        String email = request.getParameter("email");
        String isAjax = request.getParameter("isAjax");

        response.setContentType("text/plain;charset=UTF-8");

        if (email == null || email.isEmpty()) {
            response.getWriter().write("邮箱不能为空");
            return;
        }

        AccountService accountService = new AccountService();
        if (accountService.getAccountByEmail(email) == null) {
            response.getWriter().write("该邮箱未注册，请先注册账号");
            return;
        }

        try {
            String code = CodeUtil.generateCode(6);
            new EmailService().sendEmail(email, code);

            HttpSession session = request.getSession();
            session.setAttribute("emailCode", code);
            session.setAttribute("emailAccount", email);
            session.setMaxInactiveInterval(300);

            response.getWriter().write("success");
        } catch (Exception e) {
            response.getWriter().write("邮件发送失败：" + e.getMessage());
        }
    }
}