package csu.web.mypetstore.web.servlet.order;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Order;
import csu.web.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListOrdersServlet extends HttpServlet {

    private static final String LIST_ORDERS = "/WEB-INF/jsp/order/listOrders.jsp";

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        req.setAttribute("orderList", orderList);
        req.getRequestDispatcher(LIST_ORDERS).forward(req, resp);

    }
}
