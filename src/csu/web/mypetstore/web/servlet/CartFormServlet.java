package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.persistence.CartDao;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CartFormServlet extends HttpServlet {
    private String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("loginAccount");
        if (account == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        CartService cartService = new CartService(new CartDaoImpl());
        // 从数据库取该用户的购物车
        List<CartItem> cartItems = cartService.getCartItemsByUserId(account.getUsername());

        HttpSession session = req.getSession();
        // 计算小计和数量

        //要注意是从哪里set 的cart，emmm我们还是采用创建临时购物车的功能，但是结算前必须登录，这里还只是一个数据库购物车，不完整
        Cart cart = (Cart) session.getAttribute("cart");
            if(cart == null) {
                cart = new Cart();
                for (CartItem item : cartItems) {
                    cart.addItem(item.getItem(), item.isInStock());
                    cart.setQuantityByItemId(item.getItem().getItemId(), item.getQuantity());
                }
            }
        req.getSession().setAttribute("cart", cart);
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }
}
