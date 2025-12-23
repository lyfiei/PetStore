package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.Order;
import csu.web.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderUnifiedServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    // GET: 初始化页面，加载购物车和用户信息
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        Cart cart = (Cart) session.getAttribute("cart");

        if (loginAccount == null) {
            resp.sendRedirect("signOnForm"); // 未登录跳转
            return;
        }
        if (cart == null) {
            resp.sendRedirect("cartForm"); // 购物车空跳转
            return;
        }

        // 初始化订单对象并存入 Session，供后续步骤修改
        Order order = new Order();
        order.initOrder(loginAccount, cart);
        session.setAttribute("order", order);

        req.getRequestDispatcher("/WEB-INF/jsp/order/unifiedCheckout.jsp").forward(req, resp);
    }

    // POST: 异步处理数据保存与最终提交
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");

        if ("saveInfo".equals(action)) {
            // 接收支付、账单及收货地址（合并了之前的 newOrder 和 shipping 逻辑）
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
            resp.getWriter().write("success");

        } else if ("confirmSubmit".equals(action)) {
            // 最终落库
            orderService.insertOrder(order);
            session.removeAttribute("cart"); // 清空购物车
            resp.getWriter().write("success");
        }
    }
}