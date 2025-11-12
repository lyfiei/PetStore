<%@ include file="../common/top.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="BackLink">
    <c:choose>
        <c:when test="${not empty sessionScope.lastSearchUrl}">
            <a href="${pageContext.request.contextPath}/${sessionScope.lastSearchUrl}">Return to Search Results</a>
        </c:when>
        <c:otherwise>
            <a href="mainForm">Return to Main Menu</a>
        </c:otherwise>
    </c:choose>
</div>

<div id="Catalog">

  <h2>${sessionScope.product.name}</h2>

  <table>
    <tr>
      <th>Item ID</th>
      <th>Product ID</th>
      <th>Description</th>
      <th>List Price</th>
      <th>&nbsp;</th>
    </tr>
    <c:forEach var="item" items="${sessionScope.itemList}">
      <tr>
        <td>
          <a href="itemForm?itemId=${item.itemId}">${item.itemId}</a>
        </td>


        <td>${item.product.productId}</td>
        <td>${item.attribute1} ${item.attribute2} ${item.attribute3}
            ${item.attribute4} ${item.attribute5} ${sessionScope.product.name}</td>
        <td><fmt:formatNumber value="${item.listPrice}"
                              pattern="$#,##0.00" /></td>


        <td>
          <form action="addItemToCart" method="post">
            <input type="hidden" name="workingItemId" value="${item.itemId}">
            <button type="submit" class="Button">Add to Cart</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

</div>

<%@ include file="../common/bottom.jsp"%>

