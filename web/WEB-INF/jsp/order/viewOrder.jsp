<%@ include file="../common/top.jsp"%>

<div id="BackLink">
    <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">
    <table>
        <tr>
            <th colspan="2">
                Order #${sessionScope.order.orderId}
                <fmt:formatDate value="${sessionScope.order.orderDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
            </th>
        </tr>

        <tr><th colspan="2">Payment Details</th></tr>
        <tr><td>Card Type:</td><td>${sessionScope.order.cardType}</td></tr>
        <tr><td>Card Number:</td><td>${sessionScope.order.creditCard} (Fake Number)</td></tr>
        <tr><td>Expiry Date:</td><td>${sessionScope.order.expiryDate}</td></tr>

        <tr><th colspan="2">Billing Address</th></tr>
        <tr><td>First Name:</td><td>${sessionScope.order.billToFirstName}</td></tr>
        <tr><td>Last Name:</td><td>${sessionScope.order.billToLastName}</td></tr>
        <tr><td>Address 1:</td><td>${sessionScope.order.billAddress1}</td></tr>
        <tr><td>Address 2:</td><td>${sessionScope.order.billAddress2}</td></tr>
        <tr><td>City:</td><td>${sessionScope.order.billCity}</td></tr>
        <tr><td>State:</td><td>${sessionScope.order.billState}</td></tr>
        <tr><td>Zip:</td><td>${sessionScope.order.billZip}</td></tr>
        <tr><td>Country:</td><td>${sessionScope.order.billCountry}</td></tr>

        <tr><th colspan="2">Shipping Address</th></tr>
        <tr><td>First Name:</td><td>${sessionScope.order.shipToFirstName}</td></tr>
        <tr><td>Last Name:</td><td>${sessionScope.order.shipToLastName}</td></tr>
        <tr><td>Address 1:</td><td>${sessionScope.order.shipAddress1}</td></tr>
        <tr><td>Address 2:</td><td>${sessionScope.order.shipAddress2}</td></tr>
        <tr><td>City:</td><td>${sessionScope.order.shipCity}</td></tr>
        <tr><td>State:</td><td>${sessionScope.order.shipState}</td></tr>
        <tr><td>Zip:</td><td>${sessionScope.order.shipZip}</td></tr>
        <tr><td>Country:</td><td>${sessionScope.order.shipCountry}</td></tr>

        <tr><td>Courier:</td><td>${sessionScope.order.courier}</td></tr>
        <tr><td colspan="2">Status: ${sessionScope.order.status}</td></tr>

        <tr>
            <td colspan="2">
                <table>
                    <tr>
                        <th>Item ID</th>
                        <th>Description</th>
                        <th>Qty</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>

                    <c:forEach items="${sessionScope.order.lineItems}" var="item">
                        <tr>
                            <td>
                                <a href="itemForm?itemId=${item.item.itemId}">
                                        ${item.item.itemId}
                                </a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.item != null}">
                                        ${item.item.product.name}
                                    </c:when>
                                    <c:otherwise><i>Description unavailable</i></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${item.quantity}</td>
                            <td><fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
                            <td><fmt:formatNumber value="${item.total}" pattern="#,##0.00"/></td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <th colspan="5">
                            Total: <fmt:formatNumber value="${sessionScope.order.totalPrice}" pattern="#,##0.00"/>
                        </th>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>

<%@ include file="../common/bottom.jsp"%>
