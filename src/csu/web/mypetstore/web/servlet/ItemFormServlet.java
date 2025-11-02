package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ItemFormServlet extends HttpServlet {
    private CatalogService catalogService;

    private static final String ITEM_FORM = "/WEB-INF/jsp/catalog/item.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        catalogService = new CatalogService();

        String itemId = request.getParameter("itemId");
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();

        HttpSession session = request.getSession();
        session.setAttribute("item", item);
        session.setAttribute("product", product);
        request.getRequestDispatcher(ITEM_FORM).forward(request, response);

    }
}
