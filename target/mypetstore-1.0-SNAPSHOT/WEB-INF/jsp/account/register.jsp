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
            Username: <input type="text" name="username" /> <br />
            Password: <input type="password" name="password" /> <br />
            Confirm Password: <input type="password" name="confirmPassword" /> <br />
        </p>

        <hr />

        <p>
            First Name: <input type="text" name="firstName" /> <br />
            Last Name: <input type="text" name="lastName" /> <br />
            Email: <input type="text" name="email" /> <br />
            Phone: <input type="text" name="phone" /> <br />
        </p>

        <hr />

        <p>
            Address 1: <input type="text" name="address1" /> <br />
            Address 2: <input type="text" name="address2" /> <br />
            City: <input type="text" name="city" /> <br />
            State: <input type="text" name="state" /> <br />
            Zip: <input type="text" name="zip" /> <br />
            Country: <input type="text" name="country" /> <br />
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

        <input type="submit" value="Register" />
    </form>

    Already have an account?
    <a href="signOnForm">Login here</a>

</div>

<%@ include file="../common/bottom.jsp"%>
