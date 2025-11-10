package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveCartItemServlet extends HttpServlet {
    private String ERROR_FORM = "/WEB-INF/jsp/common/error.jsp";
    private String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String workingItemId = req.getParameter("workingItemId");

        CartService cartservice = new CartService(new CartDaoImpl());
        Account account = (Account) session.getAttribute("loginAccount");


        if (account == null) {
            //resp.sendRedirect("signonForm");
            //return;
            if (cart == null) {
                cart = new Cart();
            }
            cart.removeItemById(workingItemId);
        }else{
            String userId = account.getUsername();
            CartItem cartItem = cartservice.getCartItem(userId, workingItemId);
            cartservice.removeCartItem(cart,userId, workingItemId);
        }



        //if (cartItem == null) {
        //    session.setAttribute("errorMessage", "Attempted to remove null CartItem from Cart.");
        //    req.getRequestDispatcher(ERROR_FORM).forward(req, resp);
        //} else {
        //    req.getRequestDispatcher(CART_FORM).forward(req, resp);
        //}

        session.setAttribute("cart", cart);
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }
}
