package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class SearchServlet extends HttpServlet {

    private CatalogService catalogService = new CatalogService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取表单提交的搜索关键字
        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            // 如果为空，使用 redirect 回到来源页面并把提示放到 session 中（避免 forward 导致的浏览器历史问题）
            HttpSession session = request.getSession();
            session.setAttribute("message", "Please enter a search keyword.");
            String referer = request.getHeader("Referer");
            if (referer == null || referer.isEmpty()) {
                // fallback 到主页或 category 列表
                response.sendRedirect(request.getContextPath() + "/mainForm");
            } else {
                response.sendRedirect(referer);
            }
            return;
        }

          // 调用 Service 层执行搜索（保持原有行为）
        List<Product> productList = catalogService.searchProductList(keyword);

        // 将结果存入 request 范围
        request.setAttribute("productList", productList);
        request.setAttribute("keyword", keyword);

        // 把“上次搜索的 URL”保存到 session，便于后续页面能返回
        HttpSession session = request.getSession();
        // 使用 GET 风格的 URL 以便直接跳回（doGet 已经调用 doPost）
        String encoded = URLEncoder.encode(keyword, "UTF-8");
        String lastSearchUrl = "search?keyword=" + encoded;
        session.setAttribute("lastSearchUrl", lastSearchUrl);
        session.setAttribute("lastSearchKeyword", keyword);

        // 转发到 searchResult.jsp 显示结果
        request.getRequestDispatcher("/WEB-INF/jsp/catalog/searchResult.jsp").forward(request, response);
    }

    // 兼容 GET 请求（如果外部使用 search?keyword=...）
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
