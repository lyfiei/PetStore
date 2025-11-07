package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class UpdateCartServlet extends HttpServlet {

    private String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        CartService cartService = new CartService(new CartDaoImpl());
        Account account = (Account) session.getAttribute("loginAccount");
        String userId = account.getUsername();

        //// 遍历所有前端提交的参数
        //Map<String, String[]> paramMap = req.getParameterMap();
        //for (String itemId : paramMap.keySet()) {
        //    try {
        //        int quantity = Integer.parseInt(req.getParameter(itemId));
        //        if (quantity > 0) {
        //            cartService.updateQuantity(cart,userId,itemId, quantity);
        //        } else {
        //            cartService.removeCartItem(cart,userId, itemId);
        //        }
        //    } catch (NumberFormatException e) {
        //        e.printStackTrace(); // 忽略非法输入
        //    }
        //}

        Iterator<CartItem> cartItems = cart.getAllCartItems();
        while (cartItems.hasNext()) {
            CartItem cartItem = (CartItem) cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            try {
                String quantityString = req.getParameter(itemId);
                int quantity = Integer.parseInt(quantityString);

                cartService.updateQuantity(cart,userId, itemId, quantity);
                if (quantity < 1) {
                    cartService.removeCartItem(cart,userId, itemId);
                }
            } catch (Exception e) {
                //ignore parse exceptions on purpose
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }

}
