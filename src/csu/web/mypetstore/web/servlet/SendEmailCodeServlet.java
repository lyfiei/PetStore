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
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("signOnMsg", "邮箱不能为空");
            request.getRequestDispatcher("/WEB-INF/jsp/account/signon_code.jsp").forward(request, response);
            return;
        }

        // 检查邮箱是否存在
        AccountService accountService = new AccountService();
        if (accountService.getAccountByEmail(email) == null) {
            request.setAttribute("signOnMsg", "该邮箱未注册，请先注册账号");
            request.setAttribute("emailPrefill", email);
            request.getRequestDispatcher("/WEB-INF/jsp/account/signon_code.jsp").forward(request, response);
            return;
        }

        String code = CodeUtil.generateCode(6);
        new EmailService().sendEmail(email, code);

        // 存验证码到 session，有效期 5 分钟
        HttpSession session = request.getSession();
        session.setAttribute("emailCode", code);
        session.setAttribute("emailAccount", email);
        session.setMaxInactiveInterval(300);

        request.setAttribute("signOnMsg", "验证码已发送，请查收邮箱！");
        request.setAttribute("emailPrefill", email); // 可在表单中自动填充邮箱
        request.getRequestDispatcher("/WEB-INF/jsp/account/signon_code.jsp").forward(request, response);
    }
}
