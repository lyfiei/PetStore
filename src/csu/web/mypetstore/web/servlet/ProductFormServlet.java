package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.CatalogService;
import csu.web.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductFormServlet extends HttpServlet {
    private CatalogService catalogService;

    private static final String PRODUCT_FORM = "/WEB-INF/jsp/catalog/product.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        catalogService = new CatalogService();

        String productId = req.getParameter("productId");

        Product product = catalogService.getProduct(productId);
        List<Item> itemList = catalogService.getItemListByProduct(productId);

        HttpSession session = req.getSession();
        session.setAttribute("itemList", itemList);
        session.setAttribute("product", product);

        LogService logService = new LogService();
        Account account = (Account) session.getAttribute("account");
        if(account != null){
            logService.logClickButton(session.getId(),account.getUsername(),"Product",productId);
        }
        else{
            logService.logClickButton(session.getId(),"Product",productId);
        }


        req.getRequestDispatcher(PRODUCT_FORM).forward(req, resp);
    }
}
