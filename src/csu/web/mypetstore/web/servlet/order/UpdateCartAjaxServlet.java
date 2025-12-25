package csu.web.mypetstore.web.servlet.order;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateCartAjaxServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        String itemId = req.getParameter("itemId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Account account = (Account) session.getAttribute("loginAccount");
        CartService cartService = new CartService(new CartDaoImpl());

        if (account == null) {
            cart.setQuantityByItemId(itemId, quantity);
            if (quantity < 1) cart.removeItemById(itemId);
        } else {
            cartService.updateQuantity(cart, account.getUsername(), itemId, quantity);
            if (quantity < 1) cartService.removeCartItem(cart, account.getUsername(), itemId);
        }

        resp.setContentType("text/plain");
        resp.getWriter().print("success");
    }
}
