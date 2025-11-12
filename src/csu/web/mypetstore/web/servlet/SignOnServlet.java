package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.AccountService;
import csu.web.mypetstore.service.CatalogService;
import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.service.CartService;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SignOnServlet extends HttpServlet {

    private static final String SIGN_ON_FORM = "WEB-INF/jsp/account/signon.jsp";
    private static final String NEW_ORDER = "/WEB-INF/jsp/order/newOrder.jsp";

    private String username;
    private String password;

    private String msg;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username = req.getParameter("username");
        this.password = req.getParameter("password");

        //校验用户输入的正确性
        if(!validate()){
            req.setAttribute("signOnMsg",this.msg);
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
        }else{
            AccountService accountService = new AccountService();
            Account loginAccount =  accountService.getAccount(username, password);
            if(loginAccount == null){
                this.msg = "用户名或者密码错误";
                req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
            }else{
                loginAccount.setPassword(null);
                HttpSession session = req.getSession();
                session.setAttribute("loginAccount",loginAccount);

                if(loginAccount.isListOption()){
                    CatalogService catalogService = new CatalogService();
                    List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
                    session.setAttribute("myList",myList);
                }

                //合并购物车
                Cart sessionCart = (Cart) session.getAttribute("cart");
                CartService cartService = new CartService(new CartDaoImpl());

                String userid = loginAccount.getUsername();

                List<CartItem> dbCartItems = cartService.getCartItemsByUserId(userid);

                // 合并 session 中未登录时的购物车
                if (sessionCart != null) {
                    Iterator<CartItem> sessionItems = sessionCart.getAllCartItems();
                    while (sessionItems.hasNext()) {
                        CartItem sessionItem = (CartItem) sessionItems.next();
                        boolean exists = false;
                        String sessionid = sessionItem.getItem().getItemId();
                        for (CartItem dbItem : dbCartItems) {
                            String itemid = dbItem.getItem().getItemId();
                            if (itemid.equals(sessionid)) {
                                dbItem.setQuantity(dbItem.getQuantity() + sessionItem.getQuantity());
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) dbCartItems.add(sessionItem);
                    }
                    // 更新 session和数据库
                    Cart mergedCart = new Cart();
                    for (CartItem item : dbCartItems) {
                        String itemid = item.getItem().getItemId();
                        cartService.addCartItem(mergedCart,itemid,userid);
                        cartService.updateQuantity(mergedCart,userid,itemid,item.getQuantity());
                    }
                    session.setAttribute("cart", mergedCart);
                }

                resp.sendRedirect("mainForm");
            }
        }

    }

    //补充做一下长短的校验
    private boolean validate() {
        if (this.username == null || this.username.trim().equals("")) {
            this.msg = "用户名不能为空";
            return false;
        }
        if(this.password == null || this.password.trim().equals("")) {
            this.msg = "密码不能为空";
            return false;
        }
        return true;
    }
}
