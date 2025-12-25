
<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<div id="Catalog">
    <h2>Login with Email Verification Code</h2>
    <p id="codeMsg"><font color="red">${requestScope.signOnMsg}</font></p>

    <form id="loginForm">
        <input type="hidden" name="email" id="hiddenEmail" />

        <table>
            <tr>
                <td style="text-align: right; width: 80px;">Email:</td>
                <td style="text-align: left;">
                    <input type="email" name="email" id="emailInput" required
                           value="${requestScope.emailPrefill}" />
                    <input type="button" id="sendBtn" value="Send Verification Code" />
                </td>
            </tr>

            <tr>
                <td colspan="2"><hr/></td>
            </tr>

            <tr>
                <td></td>
                <td style="text-align: left;">Enter the verification code you received:</td>
            </tr>

            <tr>
                <td style="text-align: right;">Code:</td>
                <td style="text-align: left;">
                    <input type="text" name="code" id="codeInput" required />
                </td>
            </tr>

            <tr>
                <td></td>
                <td style="text-align: left;">
                    <input type="submit" value="Login" />
                </td>
            </tr>
        </table>
    </form>

    <hr style="width: 50%; margin: 20px auto;"/>
    <p>Back to standard login? <a href="signOnForm">Login with username/password</a></p>
</div>

<script>
    $(function() {
        // --- 逻辑部分完全保持不变 ---
        $("#sendBtn").click(function() {
            var email = $("#emailInput").val();
            if(email === "") {
                $("#codeMsg").html("<font color='red'>请输入邮箱</font>");
                return;
            }
            var $btn = $(this);
            $btn.prop("disabled", true);
            $.ajax({
                url: "sendEmailCode",
                type: "POST",
                data: { email: email, isAjax: "true" },
                success: function(response) {
                    if(response === "success") {
                        $("#codeMsg").html("<font color='green'>验证码已发送至邮箱</font>");
                        $("#hiddenEmail").val(email);
                        var count = 60;
                        var timer = setInterval(function() {
                            $btn.val(count + "s");
                            if(count <= 0) {
                                clearInterval(timer);
                                $btn.prop("disabled", false).val("Send Verification Code");
                            }
                            count--;
                        }, 1000);
                    } else {
                        $("#codeMsg").html("<font color='red'>" + response + "</font>");
                        $btn.prop("disabled", false);
                    }
                }
            });
        });

        $("#loginForm").submit(function(e) {
            e.preventDefault();
            var email = $("#hiddenEmail").val();
            var code = $("#codeInput").val();
            if(!email) {
                $("#codeMsg").html("<font color='red'>请先获取并填写验证码</font>");
                return;
            }
            $.ajax({
                url: "emailLogin",
                type: "POST",
                data: { email: email, code: code },
                success: function(response) {
                    if (response === "success") {
                        window.location.href = "mainForm";
                    } else {
                        $("#codeMsg").html("<font color='red'>" + response + "</font>");
                    }
                },
                error: function() {
                    $("#codeMsg").html("<font color='red'>服务器响应异常</font>");
                }
            });
        });
    });
</script>
<%@ include file="../common/bottom.jsp"%>