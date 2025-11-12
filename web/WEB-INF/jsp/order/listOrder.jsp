<%@ page import="csu.web.mypetstore.domain.Order"%>
<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <h2>My Orders</h2>

    <table>
        <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Total Price</th>
        </tr>

        <c:forEach var="order" items="${orderList}">
            <tr>
                <td><a href="viewOrder?orderId=${order.orderId}">${order.orderId}</a></td>
                <td>${order.orderDate}</td>
                <td>${order.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="../common/bottom.jsp"%>
