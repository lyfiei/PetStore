<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>

<div id="Catalog">
    <h2>Add New Address</h2>

    <form action="addAddress" method="post">
        <table>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" required></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" required></td>
            </tr>
            <tr>
                <td>Address 1:</td>
                <td><input type="text" name="address1" size="40" required></td>
            </tr>
            <tr>
                <td>Address 2:</td>
                <td><input type="text" name="address2" size="40"></td>
            </tr>
            <tr>
                <td>City:</td>
                <td><input type="text" name="city" required></td>
            </tr>
            <tr>
                <td>State:</td>
                <td><input type="text" name="state" size="4" required></td>
            </tr>
            <tr>
                <td>Zip:</td>
                <td><input type="text" name="zip" size="10" required></td>
            </tr>
            <tr>
                <td>Country:</td>
                <td><input type="text" name="country" size="15" required></td>
            </tr>
        </table>

        <div style="margin-top:10px;">
            <input type="submit" value="Save Address">
            <a href="addressList">Cancel</a>
        </div>
    </form>
</div>

<%@ include file="../common/bottom.jsp" %>
