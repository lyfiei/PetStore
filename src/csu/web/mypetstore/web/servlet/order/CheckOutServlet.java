package csu.web.mypetstore.web.servlet.order;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckOutServlet extends HttpServlet {

    private static final String CHECKOUT = "/WEB-INF/jsp/cart/checkout.jsp";
    private static final String SIGN_ON_FORM = "WEB-INF/jsp/account/signon.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null ) {
            // 若购物车为空，则返回购物车页面
            resp.sendRedirect("cartForm");
            return;
        }
        Account account = (Account) session.getAttribute("loginAccount");
        if (account == null) {
            // 保存当前请求路径，登录后返回
            session.setAttribute("redirectAfterLogin", "cartForm");
            resp.sendRedirect("signOnForm");
            return;
        }


        req.setAttribute("cart", cart);


        req.getRequestDispatcher(CHECKOUT).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
