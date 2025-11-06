package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartFormServlet extends HttpServlet {
    private String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //HttpSession session = req.getSession();
        //Cart cart = (Cart) session.getAttribute("cart");
        //
        //req.setAttribute("cart", cart); // 传给 JSP


        //自己修改一个bug。写一个提示“不能等于空值”,当cart为空时不能访问update后的界面
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }
}
