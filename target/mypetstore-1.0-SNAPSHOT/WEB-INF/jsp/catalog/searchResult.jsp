
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <h2>搜索结果：${keyword}</h2>

    <c:if test="${empty productList}">
        <p>未找到相关商品。</p>
    </c:if>

    <c:if test="${not empty productList}">
        <table>
            <tr>
                <th>Product ID</th>
                <th>Category</th>
                <th>Name</th>
                <th>Description</th>
                <th>&nbsp;</th>
            </tr>

            <c:forEach var="product" items="${productList}">
                <tr>
                    <td>${product.productId}</td>
                    <td>${product.categoryId}</td>
                    <td>${product.name}</td>
                    <td>${product.description}</td>
                    <td>
                        <form action="productForm" method="get">
                            <input type="hidden" name="productId" value="${product.productId}" />
                            <button type="submit" class="Button">View Details</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<%@ include file="../common/bottom.jsp"%>
