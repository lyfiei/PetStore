<%@ page import="csu.web.mypetstore.domain.Account" %>

<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <form action="confirmOrder" method="POST">
        <%
            Account account = (Account) session.getAttribute("account");
        %>

        <table>
            <tr>
                <th colspan="2">Shipping Address</th>
            </tr>

            <tr>
                <td>First name:</td>
                <td>
                    <input type="text" name="shipToFirstName" value="<%= account != null ? account.getFirstName() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>Last name:</td>
                <td>
                    <input type="text" name="shipToLastName" value="<%= account != null ? account.getLastName() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>Address 1:</td>
                <td>
                    <input type="text" size="40" name="shipAddress1" value="<%= account != null ? account.getAddress1() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>Address 2:</td>
                <td>
                    <input type="text" size="40" name="shipAddress2" value="<%= account != null ? account.getAddress2() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>City:</td>
                <td>
                    <input type="text" name="shipCity" value="<%= account != null ? account.getCity() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>State:</td>
                <td>
                    <input type="text" size="4" name="shipState" value="<%= account != null ? account.getState() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>Zip:</td>
                <td>
                    <input type="text" size="10" name="shipZip" value="<%= account != null ? account.getZip() : "" %>"/>
                </td>
            </tr>

            <tr>
                <td>Country:</td>
                <td>
                    <input type="text" size="15" name="shipCountry" value="<%= account != null ? account.getCountry() : "" %>"/>
                </td>
            </tr>
        </table>

        <input type="submit" value="Continue">
    </form>
</div>

<%@ include file="../common/bottom.jsp"%>
