package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NewOrderFormServlet extends HttpServlet {

    private static final String NEW_ORDER_FORM = "/WEB-INF/jsp/order/newOrder.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        Cart cart = (Cart) session.getAttribute("cart");
        if(loginAccount == null){
            resp.sendRedirect("signonForm");
        }else {
            // ✅ 创建订单对象并保存到 session （关键步骤）
            Order order = new Order();
            order.initOrder(loginAccount, cart);
            session.setAttribute("order", order);
            req.getRequestDispatcher(NEW_ORDER_FORM).forward(req, resp);
        }
    }
}