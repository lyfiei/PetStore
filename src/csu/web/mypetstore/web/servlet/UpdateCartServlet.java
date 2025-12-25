package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpdateCartServlet extends HttpServlet {

    private String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 设置请求和响应编码，防止乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("loginAccount");

        LogService logService = new LogService();

        // 2. 直接获取 AJAX 发来的两个参数
        String itemId = req.getParameter("itemId");
        String quantityStr = req.getParameter("quantity");

        // 简单校验
        if (itemId == null || quantityStr == null) {
            resp.setStatus(400); // 参数不对，报错
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            CartService cartService = new CartService(new CartDaoImpl());

            // 3. 核心业务逻辑（保留你原本的判断逻辑）
            if (account == null) {
                // 未登录用户的购物车逻辑
                cart.setQuantityByItemId(itemId, quantity);
                if (quantity < 1) {
                    cart.removeItemById(itemId);
                }
                logService.updateCart(session.getId(),quantity);
            } else {
                // 已登录用户的购物车逻辑
                cartService.updateQuantity(cart, account.getUsername(), itemId, quantity);
                if (quantity < 1) {
                    cartService.removeCartItem(cart, account.getUsername(), itemId);
                }

                logService.updateCart(account.getUsername(),session.getId(),quantity);
            }

            // 4. 更新 Session
            session.setAttribute("cart", cart);

            BigDecimal subTotal = cart.getSubTotal();
            BigDecimal itemTotal = BigDecimal.ZERO;
            Iterator<CartItem> items = cart.getAllCartItems();
            while (items.hasNext()) {
                CartItem cartItem = items.next();
                if (cartItem.getItem().getItemId().equals(itemId)) {
                    itemTotal = cartItem.calculateTotal();
                    break;
                }
            }

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String subTotalStr = currencyFormat.format(subTotal);
            String itemTotalStr = currencyFormat.format(itemTotal);

            String jsonResponse = String.format(
                    "{\"itemTotal\": \"%s\", \"subTotal\": \"%s\"}",
                    itemTotalStr,
                    subTotalStr
            );

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);




        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.setStatus(400); // 告诉前端数字格式不对
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 服务器内部错误
        }
    }

    /*protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        CartService cartService = new CartService(new CartDaoImpl());
        Account account = (Account) session.getAttribute("loginAccount");

        LogService logService = new LogService();
        int quantity = 0;

        if (account == null) {
            //resp.sendRedirect("signonForm");
            //return;

            if (cart == null) {
                cart = new Cart();
            }
            Iterator<CartItem> cartItems = cart.getAllCartItems();
            while (cartItems.hasNext()) {
                CartItem cartItem = (CartItem) cartItems.next();
                String itemId = cartItem.getItem().getItemId();
                try {
                    quantity = Integer.parseInt((String) req.getParameter(itemId));
                    cart.setQuantityByItemId(itemId, quantity);
                    if (quantity < 1) {
                        cart.removeItemById(itemId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logService.updateCart(session.getId(),quantity);
        }
        else {
            String userId = account.getUsername();
            Iterator<CartItem> cartItems = cart.getAllCartItems();
            while (cartItems.hasNext()) {
                CartItem cartItem = (CartItem) cartItems.next();
                String itemId = cartItem.getItem().getItemId();
                try {
                    String quantityString = req.getParameter(itemId);
                    quantity = Integer.parseInt(quantityString);

                    cartService.updateQuantity(cart, userId, itemId, quantity);
                    if (quantity < 1) {
                        cartService.removeCartItem(cart, userId, itemId);
                    }
                } catch (Exception e) {
                    //ignore parse exceptions on purpose
                    e.printStackTrace();
                }
            }
            logService.updateCart(account.getUsername(),session.getId(),quantity);
        }


        session.setAttribute("cart", cart);
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }*/

}
