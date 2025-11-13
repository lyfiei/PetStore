<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="BackLink">
    <c:choose>
        <!-- 优先返回到上一次搜索结果（如果存在） -->
        <c:when test="${not empty sessionScope.lastSearchUrl}">
            <a href="${pageContext.request.contextPath}/${sessionScope.lastSearchUrl}">Return to Search Results</a>
        </c:when>

        <!-- 否则返回到 product 页面（原逻辑） -->
        <c:otherwise>
            <a href="productForm?productId=${sessionScope.product.productId}"> Return to ${sessionScope.product.productId}</a>
        </c:otherwise>
    </c:choose>
</div>

<div id="Catalog">

  <table>
    <tr>
      <td>${sessionScope.product.description}</td>
    </tr>
    <tr>
      <td><b> ${sessionScope.item.itemId} </b></td>
    </tr>
    <tr>
      <td><b><font size="4"> ${sessionScope.item.attribute1}
        ${sessionScope.item.attribute2} ${sessionScope.item.attribute3}
        ${sessionScope.item.attribute4} ${sessionScope.item.attribute5}
        ${sessionScope.product.name} </font></b></td>
    </tr>
    <tr>
      <td>${sessionScope.product.name}</td>
    </tr>
    <tr>
      <td><c:if test="${sessionScope.item.quantity <= 0}">
        Back ordered.
      </c:if> <c:if test="${sessionScope.item.quantity > 0}">
        ${sessionScope.item.quantity} in stock.
      </c:if></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${sessionScope.item.listPrice}"
                            pattern="$#,##0.00" /></td>
    </tr>

    <tr>
      <td>
        <form action="addItemToCart" method="post">
          <input type="hidden" name="workingItemId" value="${sessionScope.item.itemId}">
          <button type="submit" class="Button">Add to Cart</button>
        </form>
      </td>
    </tr>
  </table>

</div>

<%@ include file="../common/bottom.jsp"%>




