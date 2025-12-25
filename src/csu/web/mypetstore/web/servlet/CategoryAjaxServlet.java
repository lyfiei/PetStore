package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryAjaxServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String categoryId = request.getParameter("categoryId");

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("["); // JSON数组开始

        long start = System.currentTimeMillis(); // 计时开始
        System.out.println("--- AJAX 请求开始: " + categoryId + " ---");

        CatalogService catalogService = new CatalogService();
        List<Product> productList = catalogService.getProductListByCategory(categoryId);

        System.out.println("查完 ProductList 耗时: " + (System.currentTimeMillis() - start) + "ms");

        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);

            long loopStart = System.currentTimeMillis();

            jsonBuilder.append("{");
            jsonBuilder.append("\"productId\":\"").append(p.getProductId()).append("\",");
            jsonBuilder.append("\"name\":\"").append(p.getName()).append("\",");

            List<Item> itemList = catalogService.getItemListByProduct(p.getProductId());


            System.out.println("  - 查产品 " + p.getProductId() + " 的 Item 耗时: " + (System.currentTimeMillis() - loopStart) + "ms");

            jsonBuilder.append("\"items\":[");
            for (int j = 0; j < itemList.size(); j++) {
                Item item = itemList.get(j);
                jsonBuilder.append("{");
                jsonBuilder.append("\"itemId\":\"").append(item.getItemId()).append("\",");
                // 根据你的数据，attr1 通常是描述，例如 "Large", "Spotted"
                jsonBuilder.append("\"desc\":\"").append(item.getAttribute1()).append("\",");
                jsonBuilder.append("\"price\":").append(item.getListPrice());
                jsonBuilder.append("}");
                if (j < itemList.size() - 1) jsonBuilder.append(",");
            }
            jsonBuilder.append("]");

            jsonBuilder.append("}");
            if (i < productList.size() - 1) jsonBuilder.append(",");
        }

        jsonBuilder.append("]"); // JSON数组结束

        System.out.println("--- AJAX 请求总耗时: " + (System.currentTimeMillis() - start) + "ms ---");

        // 5. 发送回前端
        response.getWriter().write(jsonBuilder.toString());
    }
}
