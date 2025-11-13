<%@ page import="csu.web.mypetstore.domain.Account" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <form action="${pageContext.request.contextPath}/shippingForm" method="POST">

        <%
            Account account = (Account) session.getAttribute("loginAccount");
        %>
        <table>
            <tr>
                <th colspan=2>Payment Details</th>
            </tr>
            <tr>
                <td>Card Type:</td>
                <td>
                    <label>
                        <select name="cardType">
                            <option value="Visa">Visa</option>
                            <option value="Mastercard">Mastercard</option>
                            <option value="AmericanExpress">American Express</option>
                        </select>
                    </label>
                </td>
            </tr>

            <tr>
                <td>Card Number:</td>
                <td>
                    <input type="text" name="Number" value="<%= account != null ? account.getPhone():"" %>"/> * Use a fake
                    number!
                </td>
            </tr>
            <tr>
                <td>Order Date (MM/YYYY):</td>
                <td>
                    <input type="text" name="ODate" value="<%= java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) %>" readonly/>
                </td>
            </tr>
            <tr>
                <td>Expiry Date (MM/YYYY):</td>
                <td>
                    <input type="text" name="EDate" value="<%= java.time.LocalDateTime.now().plusDays(10).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) %>"readonly/>
                </td>
            </tr>
            <tr>
                <th colspan=2>Billing Address</th>
            </tr>

            <tr>
                <td>First name:</td>
                <td>
                    <input type="text" name="FirstName" value="<%= account != null ? account.getFirstName() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td>
                    <input type="text" name="LastName" value="<%= account != null ? account.getLastName() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>Address 1:</td>
                <td>
                    <input type="text" size="40" name="Address1" value="<%= account != null ? account.getAddress1() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>Address 2:</td>
                <td>
                    <input type="text" size="40" name="Address2" value="<%= account != null ? account.getAddress2() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>City:</td>
                <td>
                    <input type="text" name="City" value="<%= account != null ? account.getCity() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>State:</td>
                <td>
                    <input type="text" size="4" name="State" value="<%= account != null ? account.getState() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>Zip:</td>
                <td>
                    <input type="text" size="10" name="Zip" value="<%= account != null ? account.getZip() : "" %>"/>
                </td>
            </tr>
            <tr>
                <td>Country:</td>
                <td>
                    <input type="text" size="15" name="Country" value="<%= account != null ? account.getCountry() : "" %>"/>
                </td>
            </tr>

            <%--        <tr>--%>
            <%--            <td colspan=2>--%>
            <%--                <input type="checkbox" name="shippingAddressRequired" />--%>
            <%--                Ship to different address...--%>
            <%--            </td>--%>
            <%--        </tr>--%>

        </table>

        <input type="submit" value="Continue" >

    </form>
</div>

<%@ include file="../common/bottom.jsp"%>