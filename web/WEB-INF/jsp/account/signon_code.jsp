<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="Catalog">
    <h2>Login with Email Verification Code</h2>
    <p id="codeMsg"><font color="red">${requestScope.signOnMsg}</font></p>

    <p>
        Email: <input type="email" name="email" id="emailInput" required
                      value="${requestScope.emailPrefill}" />
        <input type="button" id="sendBtn" value="Send Verification Code" />
    </p>

    <hr/>

    <form id="loginForm">
        <p>Enter the verification code you received:</p>
        <input type="hidden" name="email" id="hiddenEmail" />
        <p>
            Code: <input type="text" name="code" id="codeInput" required /> <br />
        </p>
        <input type="submit" value="Login" />
    </form>

    <hr/>
    <p>Back to standard login? <a href="signOnForm">Login with username/password</a></p>
</div>

<script>
    $(function() {
        // --- 原有的发送验证码逻辑 ---
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

        // --- 新增：处理登录表单 Ajax 提交 ---
        $("#loginForm").submit(function(e) {
            e.preventDefault(); // 阻止表单默认跳转行为

            var email = $("#hiddenEmail").val();
            var code = $("#codeInput").val();

            if(!email) {
                $("#codeMsg").html("<font color='red'>请先获取并填写验证码</font>");
                return;
            }

            $.ajax({
                url: "emailLogin",
                type: "POST",
                data: {
                    email: email,
                    code: code
                },
                success: function(response) {
                    if (response === "success") {
                        // 登录成功：由前端控制跳转
                        window.location.href = "mainForm"; // 或者你的 main.jsp 对应的 Servlet 路径
                    } else {
                        // 登录失败：在当前页显示后端返回的错误信息
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