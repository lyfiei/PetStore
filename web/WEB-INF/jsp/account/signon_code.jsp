<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="Catalog">
    <h2>Login with Email Verification Code</h2>

    <!-- Step 1: 发送验证码表单 -->
    <form id="sendCodeForm" action="sendEmailCode" method="post">
        <p>Please enter your registered email to receive a verification code.</p>

        <c:if test="${requestScope.signOnMsg != null}">
            <p><font color="red">${requestScope.signOnMsg}</font></p>
        </c:if>

        <p>
            Email: <input type="email" name="email" required
                          value="${requestScope.emailPrefill != null ? requestScope.emailPrefill : ''}" /> <br />
        </p>
        <input type="submit" value="Send Verification Code" />
    </form>


    <hr/>

    <!-- Step 2: 输入验证码登录表单 -->
    <form id="emailLoginForm" action="emailLogin" method="post">
        <p>Enter the verification code you received:</p>
        <p>
            Email: <input type="email" name="email" required /> <br />
            Code: <input type="text" name="code" required /> <br />
        </p>
        <input type="submit" value="Login" />
    </form>

    <hr/>
    <p>Back to standard login? <a href="signOnForm">Login with username/password</a></p>
</div>

<%@ include file="../common/bottom.jsp"%>
<script>
    document.getElementById('sendCodeForm').addEventListener('submit', function(event) {
        const email = this.querySelector('input[name="email"]').value;
        localStorage.setItem('emailPrefill', email); // 存储邮箱
    });

    window.addEventListener('DOMContentLoaded', function() {
        const savedEmail = localStorage.getItem('emailPrefill');
        if (savedEmail) {
            document.querySelector('#emailLoginForm input[name="email"]').value = savedEmail;
        }
    });
</script>
