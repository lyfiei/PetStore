package csu.web.mypetstore.web.servlet.order;

import csu.web.mypetstore.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShippingFormServlet extends HttpServlet {
    private static final String SHIPPING_FORM = "/WEB-INF/jsp/order/shippingForm.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            resp.sendRedirect("newOrderForm");
            return;
        }

        // 填写支付与账单信息
        order.setCardType(req.getParameter("cardType"));
        order.setCreditCard(req.getParameter("Number"));
        order.setExpiryDate(req.getParameter("EDate"));

        order.setBillToFirstName(req.getParameter("FirstName"));
        order.setBillToLastName(req.getParameter("LastName"));
        order.setBillAddress1(req.getParameter("Address1"));
        order.setBillAddress2(req.getParameter("Address2"));
        order.setBillCity(req.getParameter("City"));
        order.setBillState(req.getParameter("State"));
        order.setBillZip(req.getParameter("Zip"));
        order.setBillCountry(req.getParameter("Country"));

        session.setAttribute("order", order);
        req.getRequestDispatcher(SHIPPING_FORM).forward(req, resp);
    }

}
