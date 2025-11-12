<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/top.jsp" %>

<div id="Catalog">
    <h2>Search Results for: ${keyword}</h2>

    <c:if test="${empty productList}">
        <p>No matching products found.</p>
    </c:if>

    <c:if test="${not empty productList}">
        <table border="1" cellspacing="0" cellpadding="6" width="100%">
            <tr style="background-color: #f2f2f2;">
                <th>Product ID</th>
                <th>Category</th>
                <th>Name</th>
                <th>Description</th>
                <th>Action</th>
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

<%@ include file="../common/bottom.jsp" %>
