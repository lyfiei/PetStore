<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div id="Catalog">
  <div id="updateNotice" style="font-weight: bold; margin-bottom: 10px;">
    <c:if test="${not empty updateMsg}">
      <font color="${updateMsg.contains('成功') ? 'green' : 'red'}">${updateMsg}</font>
    </c:if>
  </div>

  <h2>Account Information</h2>

  <form id="profileForm" action="${pageContext.request.contextPath}/updateProfile" method="post">
    <table class="profile-table">
      <tr><td class="label">Username:</td><td>${account.username}</td></tr>
      <tr><td class="label">First Name:</td><td><input type="text" name="firstName" value="${account.firstName}" required /></td></tr>
      <tr><td class="label">Last Name:</td><td><input type="text" name="lastName" value="${account.lastName}" required /></td></tr>
      <tr>
        <td class="label">Email:</td>
        <td>
          <input type="email" name="email" id="email" value="${account.email}" required />
          <span id="emailMsg"></span>
        </td>
      </tr>
      <tr><td class="label">Phone:</td><td><input type="text" name="phone" value="${account.phone}" /></td></tr>
      <tr><td class="label">Address 1:</td><td><input type="text" name="address1" value="${account.address1}" /></td></tr>
      <tr><td class="label">Address 2:</td><td><input type="text" name="address2" value="${account.address2}" /></td></tr>
      <tr><td class="label">City:</td><td><input type="text" name="city" value="${account.city}" /></td></tr>
      <tr><td class="label">State:</td><td><input type="text" name="state" value="${account.state}" /></td></tr>
      <tr><td class="label">Zip:</td><td><input type="text" name="zip" value="${account.zip}" /></td></tr>
      <tr><td class="label">Country:</td><td><input type="text" name="country" value="${account.country}" /></td></tr>
    </table>

    <h2>Profile Information</h2>
    <p>
      Language Preference:
      <select name="languagePreference">
        <option value="english" ${account.languagePreference eq 'english' ? 'selected' : ''}>English</option>
        <option value="chinese" ${account.languagePreference eq 'chinese' ? 'selected' : ''}>Chinese</option>
      </select> <br />

      Favourite Category:
      <select name="favouriteCategoryId">
        <option value="">-- Select --</option>
        <option value="FISH" ${account.favouriteCategoryId eq 'FISH' ? 'selected' : ''}>Fish</option>
        <option value="DOGS" ${account.favouriteCategoryId eq 'DOGS' ? 'selected' : ''}>Dogs</option>
        <option value="CATS" ${account.favouriteCategoryId eq 'CATS' ? 'selected' : ''}>Cats</option>
        <option value="BIRDS" ${account.favouriteCategoryId eq 'BIRDS' ? 'selected' : ''}>Birds</option>
        <option value="REPTILES" ${account.favouriteCategoryId eq 'REPTILES' ? 'selected' : ''}>Reptiles</option>
      </select> <br />

      <input type="checkbox" name="listOption" ${account.listOption ? 'checked' : ''}/> Enable MyList<br />
      <input type="checkbox" name="bannerOption" ${account.bannerOption ? 'checked' : ''}/> Display Banner<br />
    </p>

    <input type="submit" value="Save Changes" class="Button"/>
  </form>
</div>
<script>
  $(function() {
    // 1. 邮箱实时验证 (保持你原有的逻辑)
    $("#email").on("blur", function() {
      // ... 原有验证逻辑 ...
    });

    // 2. 表单提交逻辑修改
    $("#profileForm").submit(function(e) {
      e.preventDefault(); // 阻止默认的表单跳转提交

      var firstName = $("input[name='firstName']").val();
      var lastName = $("input[name='lastName']").val();

      if(firstName.trim() === "" || lastName.trim() === "") {
        alert("名字和姓氏不能为空");
        return;
      }

      // 使用 Ajax 提交表单数据
      $.ajax({
        url: $(this).attr("action"), // 即 /updateProfile
        type: "POST",
        data: $(this).serialize(),   // 自动序列化表单内所有 input 数据
        success: function(response) {
          if(response === "success") {
            // 修改成功：跳转到主页，并带上提示参数（或者利用 JS 弹出提示）
            alert("修改成功！");
            window.location.href = "${pageContext.request.contextPath}/mainForm";
          } else {
            // 修改失败：在当前页面提示错误
            $("#updateNotice").html("<font color='red'>" + response + "</font>");
          }
        },
        error: function() {
          alert("服务器异常，修改失败");
        }
      });
    });
  });
</script>
<%@ include file="../common/bottom.jsp"%>