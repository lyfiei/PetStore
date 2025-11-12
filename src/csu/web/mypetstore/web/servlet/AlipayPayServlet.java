package csu.web.mypetstore.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AlipayPayServlet extends HttpServlet {

    private static final String VIEW_PAGE = "/WEB-INF/jsp/order/alipayPay.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 这里之后可以接入支付宝SDK（暂时只是页面跳转）
        req.getRequestDispatcher(VIEW_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
