<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">

<div id="Catalog">

    <form action="register" method="post">
        <p>Please fill in your account information below.</p>

        <c:if test="${requestScope.registerMsg != null}">
            <p><font color="red">${requestScope.registerMsg}</font></p>
        </c:if>

        <p>
            Username: <input type="text" name="username" value="${param.username}" /> <br />
            Password: <input type="password" name="password" value="${param.password}" /> <br />
            Confirm Password: <input type="password" name="confirmPassword" value="${param.confirmPassword}" /> <br />
        </p>

        <hr />

        <p>
            First Name: <input type="text" name="firstName" value="${param.firstName}" /> <br />
            Last Name: <input type="text" name="lastName" value="${param.lastName}" /> <br />
            Email: <input type="text" name="email" value="${param.email}" /> <br />
            Phone: <input type="text" name="phone" value="${param.phone}" /> <br />
        </p>

        <hr />

        <p>
            Address 1: <input type="text" name="address1" value="${param.address1}" /> <br />
            Address 2: <input type="text" name="address2" value="${param.address2}" /> <br />
            City: <input type="text" name="city" value="${param.city}" /> <br />
            State: <input type="text" name="state" value="${param.state}" /> <br />
            Zip: <input type="text" name="zip" value="${param.zip}" /> <br />
            Country: <input type="text" name="country" value="${param.country}" /> <br />
        </p>

        <hr />

        <p>
            Language Preference:
            <select name="languagePreference">
                <option value="english">English</option>
                <option value="chinese">Chinese</option>
            </select> <br />

            Favourite Category:
            <select name="favouriteCategoryId">
                <option value="">-- Select --</option>
                <option value="FISH">Fish</option>
                <option value="DOGS">Dogs</option>
                <option value="CATS">Cats</option>
                <option value="BIRDS">Birds</option>
                <option value="REPTILES">Reptiles</option>
            </select> <br />

            <input type="checkbox" name="listOption" /> Enable MyList<br />
            <input type="checkbox" name="bannerOption" /> Display Banner<br />
        </p>

        <hr />

        <p>
            Verification Code:
            <input type="text" name="captchaInput" size="8" />
            <img src="captcha" alt="captcha"
                 onclick="this.src='captcha?'+Math.random()"
                 title="Click to refresh" />
            <br />
            <small>Click image to refresh.</small>
        </p>

        <input type="submit" value="Register" />
    </form>

    <p>
        Already have an account?
        <a href="signOnForm">Login here</a>
    </p>

</div>

<%@ include file="../common/bottom.jsp"%>
