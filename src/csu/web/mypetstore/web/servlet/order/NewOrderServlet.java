package csu.web.mypetstore.web.servlet.order;

import csu.web.mypetstore.domain.Order;
import csu.web.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class NewOrderServlet extends HttpServlet {

    private static final String VIEW_ORDER = "/WEB-INF/jsp/order/viewOrder.jsp";
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");

        if (order == null) {
            resp.sendRedirect("newOrderForm");
            return;
        }

        // 写入数据库
        orderService.insertOrder(order);

        // 清空购物车
        session.removeAttribute("cart");

        // 仍然保留 order 用于 JSP 显示
        session.setAttribute("order", order);

        req.getRequestDispatcher(VIEW_ORDER).forward(req, resp);
    }
}
