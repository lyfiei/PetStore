<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div id="Catalog">
    <form action="register" method="post" id="registerForm">
        <p>Please fill in your account information below.</p>
        <p id="errorMsg"><font color="red">${requestScope.registerMsg}</font></p>

        <p>
            Username: <input type="text" name="username" id="username" />
            <span id="usernameNotice"></span> <br />

            Password: <input type="password" name="password" id="password" /> <br />

            Confirm Password: <input type="password" name="confirmPassword" id="confirmPassword" />
            <span id="passwordNotice"></span> <br />
        </p>
        <hr />
        <p>
            First Name: <input type="text" name="firstName" /> <br />
            Last Name: <input type="text" name="lastName" /> <br />
            Email: <input type="text" name="email" id="email" />
            <span id="emailNotice"></span> <br />
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
        <hr />
        <p>
            Verification Code:
            <input type="text" name="captchaInput" size="8" />
            <img src="captcha" onclick="this.src='captcha?'+Math.random()" style="cursor:pointer" />
        </p>
        <input type="submit" value="Register" />
    </form>
</div>

<script>
    $(function() {
        // 验证用户名
        $("#username").on("blur", function() {
            var val = $(this).val();
            if(val === "") return;
            $.get("validateAccount", {username: val}, function(data) {
                $("#usernameNotice").html(data === "exist" ? "<font color='red'>用户名已存在</font>" : "<font color='green'>OK</font>");
            });
        });

        // 验证邮箱
        $("#email").on("blur", function() {
            var val = $(this).val();
            if(val === "") return;
            $.get("validateAccount", {email: val}, function(data) {
                $("#emailNotice").html(data === "exist" ? "<font color='red'>邮箱已被注册</font>" : "<font color='green'>OK</font>");
            });
        });

        // 验证两次密码一致性
        $("#confirmPassword").on("keyup", function() {
            var p1 = $("#password").val();
            var p2 = $(this).val();
            $("#passwordNotice").html(p1 === p2 ? "<font color='green'>密码一致</font>" : "<font color='red'>密码不一致</font>");
        });
    });
</script>
<%@ include file="../common/bottom.jsp"%>