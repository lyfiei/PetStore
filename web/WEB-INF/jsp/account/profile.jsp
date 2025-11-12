<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div id="Catalog">
    <h2>Account Information</h2>

    <form action="${pageContext.request.contextPath}/updateProfile" method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td>${account.username}</td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" value="${account.firstName}" /></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" value="${account.lastName}" /></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email" value="${account.email}" size="40"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input type="text" name="phone" value="${account.phone}" /></td>
            </tr>
            <tr>
                <td>Address 1:</td>
                <td><input type="text" name="address1" value="${account.address1}" size="40"/></td>
            </tr>
            <tr>
                <td>Address 2:</td>
                <td><input type="text" name="address2" value="${account.address2}" size="40"/></td>
            </tr>
            <tr>
                <td>City:</td>
                <td><input type="text" name="city" value="${account.city}" /></td>
            </tr>
            <tr>
                <td>State:</td>
                <td><input type="text" name="state" value="${account.state}" size="4"/></td>
            </tr>
            <tr>
                <td>Zip:</td>
                <td><input type="text" name="zip" value="${account.zip}" size="10"/></td>
            </tr>
            <tr>
                <td>Country:</td>
                <td><input type="text" name="country" value="${account.country}" size="15"/></td>
            </tr>
        </table>

        <div id="BackLink">
            <a href="listOrders">My Orders</a>
        </div>


        <h2>Profile Information</h2>

        <table>
            <tr>
                <td>Language Preference:</td>
                <td>
                    <select name="languagePreference">
                        <c:forEach var="lang" items="${sessionScope.languages}">
                            <option value="${lang}" ${lang eq account.languagePreference ? 'selected' : ''}>
                                    ${lang}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Favourite Category:</td>
                <td>
                    <select name="favouriteCategoryId">
                        <c:forEach var="cat" items="${sessionScope.categories}">
                            <option value="${cat.categoryId}"
                                ${cat.categoryId eq account.favouriteCategoryId ? 'selected' : ''}>
                                    ${cat.name}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Enable MyList:</td>
                <td><input type="checkbox" name="listOption" ${account.listOption ? 'checked' : ''} /></td>
            </tr>
            <tr>
                <td>Enable MyBanner:</td>
                <td><input type="checkbox" name="bannerOption" ${account.bannerOption ? 'checked' : ''} /></td>
            </tr>
        </table>

        <input type="submit" value="Save Changes" class="Button"/>
    </form>


</div>


<script>
    document.querySelector('form').addEventListener('submit', function(e) {
        console.log('表单提交事件触发');
        console.log('firstName:', document.querySelector('[name="firstName"]').value);
        // 可以添加其他字段的日志
    });
</script>


<%@ include file="../common/bottom.jsp"%>