package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;
import csu.web.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

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
        LogService logService = new LogService();

        if (account == null) {
            if (cart == null) {
                cart = new Cart();
            }
            cart.removeItemById(workingItemId);
            logService.removeFromCart(session.getId(),workingItemId);
        }else{
            String userId = account.getUsername();
            CartItem cartItem = cartservice.getCartItem(userId, workingItemId);
            cartservice.removeCartItem(cart,userId, workingItemId);
            logService.removeFromCart(session.getId(),account.getUsername(),workingItemId);
        }

        session.setAttribute("cart", cart);

        BigDecimal subTotal = cart.getSubTotal();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String subTotalStr = currencyFormat.format(subTotal);

        String jsonResponse = String.format(
                "{\"status\":\"success\", \"subTotal\": \"%s\"}",
                subTotalStr
        );

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);

    }
}
