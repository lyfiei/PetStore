<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div id="Catalog">
    <form action="signOn" method="post" id="loginForm">
        <p>Please enter your username and password.</p>
        <p id="messageBox"><font color="red">${requestScope.signOnMsg}</font></p>
        <p>
            Username: <input type="text" name="username" id="username"> <br />
            Password: <input type="password" name="password" id="password"/>
        </p>
        <input type="button" id="loginBtn" value="Login" >
    </form>

    <p>
        Need a user name and password?
        <a href="registerForm">Register Now!</a>
    </p>

    <p>
        Or login with email verification code?
        <a href="signOnCodeForm">Login with code</a>
    </p>
</div>

<script>
    $(function() {
        $("#loginBtn").click(function() {
            var username = $("#username").val();
            var password = $("#password").val();

            if(username === "" || password === "") {
                $("#messageBox").html("<font color='red'>用户名和密码不能为空</font>");
                return;
            }

            // 提交表单
            $("#loginForm").submit();
        });
    });
</script>

<%@ include file="../common/bottom.jsp"%>