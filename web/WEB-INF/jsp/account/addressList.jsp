<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../common/top.jsp"%>

<h2>My Addresses</h2>

<table border="1" cellpadding="6">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Address 1</th>
        <th>Address 2</th>
        <th>City</th>
        <th>State</th>
        <th>Zip</th>
        <th>Country</th>
    </tr>
    <c:forEach var="addr" items="${addressList}">
        <tr>
            <td>${addr.firstName}</td>
            <td>${addr.lastName}</td>
            <td>${addr.address1}</td>
            <td>${addr.address2}</td>
            <td>${addr.city}</td>
            <td>${addr.state}</td>
            <td>${addr.zip}</td>
            <td>${addr.country}</td>
            <td>
                <form action="shippingForm" method="get" style="display:inline;">
                    <input type="hidden" name="addressId" value="${addr.addressId}">
                    <input type="submit" value="Select">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<div style="margin-top:10px;">
    <a href="addAddressForm">Add New Address</a>
</div>
</div>
<%@ include file="../common/bottom.jsp"%>