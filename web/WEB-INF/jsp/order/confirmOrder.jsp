<%@ include file="../common/top.jsp"%>

<div id="BackLink">
    <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">
    Please confirm the information below and then press continue...

    <table>
        <tr>
            <th align="center" colspan="2">
                <font size="4"><b>Order</b></font><br/>
                <font size="3"><b>
                    <fmt:formatDate value="${sessionScope.order.orderDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
                </b></font>
            </th>
        </tr>

        <tr><th colspan="2">Billing Address</th></tr>
        <tr><td>First name:</td><td>${sessionScope.order.billToFirstName}</td></tr>
        <tr><td>Last name:</td><td>${sessionScope.order.billToLastName}</td></tr>
        <tr><td>Address 1:</td><td>${sessionScope.order.billAddress1}</td></tr>
        <tr><td>Address 2:</td><td>${sessionScope.order.billAddress2}</td></tr>
        <tr><td>City:</td><td>${sessionScope.order.billCity}</td></tr>
        <tr><td>State:</td><td>${sessionScope.order.billState}</td></tr>
        <tr><td>Zip:</td><td>${sessionScope.order.billZip}</td></tr>
        <tr><td>Country:</td><td>${sessionScope.order.billCountry}</td></tr>

        <tr><th colspan="2">Shipping Address</th></tr>
        <tr><td>First name:</td><td>${sessionScope.order.shipToFirstName}</td></tr>
        <tr><td>Last name:</td><td>${sessionScope.order.shipToLastName}</td></tr>
        <tr><td>Address 1:</td><td>${sessionScope.order.shipAddress1}</td></tr>
        <tr><td>Address 2:</td><td>${sessionScope.order.shipAddress2}</td></tr>
        <tr><td>City:</td><td>${sessionScope.order.shipCity}</td></tr>
        <tr><td>State:</td><td>${sessionScope.order.shipState}</td></tr>
        <tr><td>Zip:</td><td>${sessionScope.order.shipZip}</td></tr>
        <tr><td>Country:</td><td>${sessionScope.order.shipCountry}</td></tr>
    </table>

    <br/>

    <form action="newOrder" method="POST">
        <input type="hidden" name="confirmed" value="true">
        <input type="submit" value="Confirm" class="Button">
    </form>

    <br/>

    <a href="shippingForm" class="Button">Go Back</a>

</div>

<%@ include file="../common/bottom.jsp"%>
