package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;
import csu.web.mypetstore.service.CartService;
import csu.web.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AddItemToCartServlet extends HttpServlet {

    private static final String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workingItemId = req.getParameter("workingItemId");

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        CartService cartService = new CartService(new CartDaoImpl());
        Account account = (Account) session.getAttribute("loginAccount");
        if (account == null) {
            //resp.sendRedirect("signonForm");
            //return;
            if (cart == null) {
                cart = new Cart();
            }
            CatalogService catalogService = new CatalogService();
            Item item = catalogService.getItem(workingItemId);
            // 1. 内存 Cart 添加商品
            CartItem cartItem = cart.addItem(item, catalogService.isItemInStock(item.getItemId()));
        }else{
            if (cart == null) {
                cart = new Cart();
                List<CartItem> dbItems = cartService.getCartItemsByUserId(account.getUsername());
                for (CartItem item : dbItems) {
                    cart.addItem(item.getItem(), item.isInStock());
                    cart.setQuantityByItemId(item.getItem().getItemId(), item.getQuantity());
                }
            }
            String userId = account.getUsername();
            cartService.addCartItem(cart, workingItemId,userId);
        }

        session.setAttribute("cart", cart);
        resp.sendRedirect(req.getContextPath() + "/cartForm");
    }

    //@Override
    //protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //    String workingItemId = req.getParameter("workingItemId");
    //
    //    HttpSession session = req.getSession();
    //    Cart cart = (Cart) session.getAttribute("cart");
    //
    //    if(cart == null) {
    //        cart = new Cart();
    //    }
    //
    //    if (cart.containsItemId(workingItemId)) {
    //        cart.incrementQuantityByItemId(workingItemId);
    //    } else {
    //        CatalogService catalogService = new CatalogService();
    //        boolean isInStock = catalogService.isItemInStock(workingItemId);
    //        Item item = catalogService.getItem(workingItemId);
    //        cart.addItem(item, isInStock);
    //
    //    }
    //
    //    session.setAttribute("cart", cart);
    //    req.getRequestDispatcher(CART_FORM).forward(req, resp);
    //}

    //@Override
    //protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //    String workingItemId = req.getParameter("workingItemId");
    //
    //    HttpSession session = req.getSession();
    //    Cart cart = (Cart) session.getAttribute("cart");
    //
    //    if(cart == null) {
    //        cart = new Cart();
    //    }
    //
    //    if (cart.containsItemId(workingItemId)) {
    //        cart.incrementQuantityByItemId(workingItemId);
    //    } else {
    //        CatalogService catalogService = new CatalogService();
    //        boolean isInStock = catalogService.isItemInStock(workingItemId);
    //        Item item = catalogService.getItem(workingItemId);
    //        cart.addItem(item, isInStock);
    //
    //    }
    //
    //    session.setAttribute("cart", cart);
    //    resp.sendRedirect(req.getContextPath() + "/cartForm");
    //}
}
