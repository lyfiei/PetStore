<%@ include file="../common/top.jsp"%>

<div id="BackLink">
    <a href="cartForm">Return to Shopping Cart</a>
</div>

<div id="Catalog">


    <h2>Checkout Summary</h2>

    <table>

        <tr>
            <td><b>Item ID</b></td>
            <td><b>Product ID</b></td>
            <td><b>Description</b></td>
            <td><b>In Stock?</b></td>
            <td><b>Quantity</b></td>
            <td><b>List Price</b></td>
            <td><b>Total Cost</b></td>
        </tr>

        <c:forEach var="cartItem" items="${sessionScope.cart.cartItems}">
            <tr>
                <td>
                    <a href="itemForm?itemId=${cartItem.item.itemId}">${cartItem.item.itemId}</a>
                </td>
                <td>${cartItem.item.product.productId}</td>
                <td>
                        ${cartItem.item.attribute1} ${cartItem.item.attribute2}
                        ${cartItem.item.attribute3} ${cartItem.item.attribute4}
                        ${cartItem.item.attribute5}
                        ${cartItem.item.product.name}
                </td>
                <td>
                    <c:choose>
                        <c:when test="${cartItem.inStock}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
                <td>${cartItem.quantity}</td>
                <td><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00" /></td>
                <td><fmt:formatNumber value="${cartItem.total}" pattern="$#,##0.00" /></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="7">Sub Total:
                <fmt:formatNumber value="${sessionScope.cart.subTotal}" pattern="$#,##0.00" /></td>
        </tr>
    </table>

    <br/>
    <div class="pay">
        <h3 align="middle">Payment Method</h3>

        <center>
            <a href="alipayPay">
                <img src="images/alipayPay.png" alt="支付宝支付">
            </a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="weChatPay">
                <img src="images/WeChatPay.png" alt="微信支付">
            </a>
        </center>

    </div>


</div>

<%@ include file="../common/bottom.jsp"%>