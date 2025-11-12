package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ConfirmOrderServlet extends HttpServlet {

    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/confirmOrder.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.sendRedirect("newOrderForm");
            return;
        }

        // 填写收货地址
        order.setShipToFirstName(req.getParameter("shipToFirstName"));
        order.setShipToLastName(req.getParameter("shipToLastName"));
        order.setShipAddress1(req.getParameter("shipAddress1"));
        order.setShipAddress2(req.getParameter("shipAddress2"));
        order.setShipCity(req.getParameter("shipCity"));
        order.setShipState(req.getParameter("shipState"));
        order.setShipZip(req.getParameter("shipZip"));
        order.setShipCountry(req.getParameter("shipCountry"));

        session.setAttribute("order", order);
        req.getRequestDispatcher(CONFIRM_ORDER).forward(req, resp);
    }
}
