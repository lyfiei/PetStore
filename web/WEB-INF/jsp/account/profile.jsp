
<%@ include file="../common/top.jsp"%>



<div id="Catalog">

  <c:if test="${not empty updateMsg}">
  <div style="color: green; font-weight: bold; margin-bottom: 10px;">
      ${updateMsg}
  </div>
  </c:if>

  <h2>Account Information</h2>

  <form action="${pageContext.request.contextPath}/updateProfile" method="post">
    <table class="profile-table">
      <tr><td class="label">Username:</td><td>${account.username}</td></tr>
      <tr><td class="label">First Name:</td><td><input type="text" name="firstName" value="${account.firstName}" /></td></tr>
      <tr><td class="label">Last Name:</td><td><input type="text" name="lastName" value="${account.lastName}" /></td></tr>
      <tr><td class="label">Email:</td><td><input type="email" name="email" value="${account.email}" /></td></tr>
      <tr><td class="label">Phone:</td><td><input type="text" name="phone" value="${account.phone}" /></td></tr>
      <tr><td class="label">Address 1:</td><td><input type="text" name="address1" value="${account.address1}" /></td></tr>
      <tr><td class="label">Address 2:</td><td><input type="text" name="address2" value="${account.address2}" /></td></tr>
      <tr><td class="label">City:</td><td><input type="text" name="city" value="${account.city}" /></td></tr>
      <tr><td class="label">State:</td><td><input type="text" name="state" value="${account.state}" /></td></tr>
      <tr><td class="label">Zip:</td><td><input type="text" name="zip" value="${account.zip}" /></td></tr>
      <tr><td class="label">Country:</td><td><input type="text" name="country" value="${account.country}" /></td></tr>
    </table>
    <div id="BackLink">
      <a href="listOrders">My Orders</a >
    </div>
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
    </p >

    <input type="submit" value="Save Changes" class="Button"/>
  </form>
</div>

<%@ include file="../common/bottom.jsp"%>
