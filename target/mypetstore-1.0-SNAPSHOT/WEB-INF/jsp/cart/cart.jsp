<%@ include file="../common/top.jsp"%>

<div id="BackLink">
  <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">

  <div id="Cart">

    <h2>Shopping Cart</h2>

        <table>
          <tr>
            <th><b>Item ID</b></th>
            <th><b>Product ID</b></th>
            <th><b>Description</b></th>
            <th><b>In Stock?</b></th>
            <th><b>Quantity</b></th>
            <th><b>List Price</b></th>
            <th><b>Total Cost</b></th>
            <th>&nbsp;</th>
          </tr>

          <c:if test="${sessionScope.cart.numberOfItems == 0}">
            <tr>
              <td colspan="8"><b>Your cart is empty.</b></td>
            </tr>
          </c:if>

          <c:forEach var="cartItem" items="${sessionScope.cart.cartItems}">
            <tr>
              <td>
              <a href="itemForm?itemId=${cartItem.item.itemId}"> ${cartItem.item.itemId}</a>
              </td>

              <td>${cartItem.item.product.productId}</td>
              <td>${cartItem.item.attribute1} ${cartItem.item.attribute2}
                  ${cartItem.item.attribute3} ${cartItem.item.attribute4}
                  ${cartItem.item.attribute5} ${cartItem.item.product.name}</td>
              <td>${cartItem.inStock}</td>
              <td>
                <input type = "number"
                        class = "quantity-input"
                        data-itemid = "${cartItem.item.itemId}"
                        value = "${cartItem.quantity}"
                        min = "1">
              </td>

              <td><fmt:formatNumber value="${cartItem.item.listPrice}"
                                    pattern="$#,##0.00" /></td>
              <td>
                <span id="total-${cartItem.item.itemId}">
                  <fmt:formatNumber value="${cartItem.total}" pattern="$#,##0.00" />
                </span>

              </td>
              <td>
                <a href="javascript:void(0);"
                   class="Button"
                   onclick="removeItemAjax('${cartItem.item.itemId}', this)">
                  remove
                </a>
              </td>
            </tr>
          </c:forEach>
          <tr>
            <td colspan="7">
              Sub Total: <span id="sub-total">
        <fmt:formatNumber value="${sessionScope.cart.subTotal}" pattern="$#,##0.00" />
                </span>
            </td>
            <td>&nbsp&nbsp;</td>
          </tr>
        </table>

      <c:if test="${sessionScope.cart.numberOfItems > 0}">

          <a href="orderUnified" class="Button">Proceed to Checkout</a>
      </c:if>



  </div>

  <div id="MyList">
    <c:if test="${sessionScope.loginAccount != null}">
      <c:if test="${!sessionScope.loginAcount.authenticated}">
        <c:if test="${!empty sessionScope.loginAccount.listOption}">
          <%@ include file="includeMyList.jsp"%>
        </c:if>
      </c:if>
    </c:if>
  </div>

  <div id="Separator">&nbsp;</div>
</div>

<%@ include file="../common/bottom.jsp"%>

<script src="${pageContext.request.contextPath}/js/cart.js"></script>
