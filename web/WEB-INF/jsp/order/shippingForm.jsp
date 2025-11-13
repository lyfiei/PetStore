<%@ page import="csu.web.mypetstore.domain.Account" %>
<%@ page import="csu.web.mypetstore.domain.Address" %>
<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <form action="addressList" method="get" style="display:inline;">
        <input type="submit" value="Change Address">
    </form>

    <form action="confirmOrder" method="POST">
        <%
            Account account = (Account) session.getAttribute("account");
            Address selectedAddress = (Address) request.getAttribute("selectedAddress");
        %>

        <table>
            <tr>
                <th colspan="2">Shipping Address</th>
            </tr>

            <tr>
                <td>First name:</td>
                <td><input type="text" name="shipToFirstName"
                           value="<%= selectedAddress != null ? selectedAddress.getFirstName() : (account != null ? account.getFirstName() : "") %>"/></td>
            </tr>

            <tr>
                <td>Last name:</td>
                <td><input type="text" name="shipToLastName"
                           value="<%= selectedAddress != null ? selectedAddress.getLastName() : (account != null ? account.getLastName() : "") %>"/></td>
            </tr>

            <tr>
                <td>Address 1:</td>
                <td><input type="text" name="shipAddress1" size="40"
                           value="<%= selectedAddress != null ? selectedAddress.getAddress1() : (account != null ? account.getAddress1() : "") %>"/></td>
            </tr>

            <tr>
                <td>Address 2:</td>
                <td><input type="text" name="shipAddress2" size="40"
                           value="<%= selectedAddress != null ? selectedAddress.getAddress2() : (account != null ? account.getAddress2() : "") %>"/></td>
            </tr>

            <tr>
                <td>City:</td>
                <td><input type="text" name="shipCity"
                           value="<%= selectedAddress != null ? selectedAddress.getCity() : (account != null ? account.getCity() : "") %>"/></td>
            </tr>

            <tr>
                <td>State:</td>
                <td><input type="text" name="shipState" size="4"
                           value="<%= selectedAddress != null ? selectedAddress.getState() : (account != null ? account.getState() : "") %>"/></td>
            </tr>

            <tr>
                <td>Zip:</td>
                <td><input type="text" name="shipZip" size="10"
                           value="<%= selectedAddress != null ? selectedAddress.getZip() : (account != null ? account.getZip() : "") %>"/></td>
            </tr>

            <tr>
                <td>Country:</td>
                <td><input type="text" name="shipCountry" size="15"
                           value="<%= selectedAddress != null ? selectedAddress.getCountry() : (account != null ? account.getCountry() : "") %>"/></td>
            </tr>
        </table>

        <input type="submit" value="Continue">
    </form>
</div>

<%@ include file="../common/bottom.jsp"%>