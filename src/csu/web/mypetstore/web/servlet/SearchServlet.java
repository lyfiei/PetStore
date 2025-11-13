package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
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

public class SearchServlet extends HttpServlet {

    private CatalogService catalogService = new CatalogService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("SearchServlet被调用！");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取表单提交的搜索关键字
        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("message", "请输入搜索关键字！");
            request.getRequestDispatcher("/WEB-INF/jsp/catalog/searchResult.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        LogService logService = new LogService();
        Account account = (Account) session.getAttribute("account");
        if(account != null){
            logService.search(session.getId(),account.getUsername(),keyword);
        }
        else{
            logService.search(session.getId(),keyword);
        }


        // 调用 Service 层执行搜索
        List<Product> productList = catalogService.searchProductList(keyword);

        // 将结果存入 request 范围
        request.setAttribute("productList", productList);
        request.setAttribute("keyword", keyword);

        // 跳转到搜索结果页面
        request.getRequestDispatcher("/WEB-INF/jsp/catalog/searchResult.jsp").forward(request, response);
    }

    // 兼容 GET 请求（如果有人直接访问 searchForm）
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
