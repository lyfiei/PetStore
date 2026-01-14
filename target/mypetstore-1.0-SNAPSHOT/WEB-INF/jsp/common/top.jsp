<%--<%@ page contentType="text/html;charset=UTF-8" language="java"%>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>--%>

<%--<!DOCTYPE html>--%>

<%--<html>--%>

<%--<head>--%>

<%--    <title>MyPetStore</title>--%>
<%--    <link rel="StyleSheet" href="css/mypetstore.css" type="text/css" media="screen" />--%>
<%--    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.1/jquery.min.js"></script>--%>
<%--    <script src="js/search.js"></script>--%>

<%--</head>--%>

<%--<body>--%>
<%--<div id="Header">--%>

<%--    <div id="Logo">--%>
<%--        <div id="LogoContent">--%>
<%--            <a href="mainForm"><img src="images/logo-topbar.gif" /></a>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div id="Menu">--%>
<%--        <div id="MenuContent">--%>
<%--            <a href="cartForm"><img align="middle" name="img_cart" src="images/cart.gif" /></a>--%>
<%--            <img align="middle" src="images/separator.gif" />--%>

<%--            <c:if test="${sessionScope.loginAccount == null}">--%>
<%--                <a href="signOnForm">Sign In</a>--%>
<%--                <img align="middle" src="images/separator.gif">--%>
<%--            </c:if>--%>

<%--        <c:if test="${sessionScope.loginAccount != null}">--%>
<%--          <a href="signOut">Sign Out</a>--%>
<%--          <img align="middle" src="images/separator.gif" />--%>
<%--          <a href="profileForm">My Account</a>--%>
<%--          <img align="middle" src="images/separator.gif" />--%>
<%--        </c:if>--%>

<%--            <a href="help.html">?</a>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div id="Search">--%>
<%--        <div id="SearchContent">--%>
<%--            <div class="search-wrapper">--%>
<%--                <form action="search" method="post" onsubmit="return checkSearch();">--%>
<%--                    <input type="text" name="keyword" size="14" id="keyword" />--%>
<%--                    <input type="submit" value="Search" />--%>
<%--                </form>--%>
<%--                <div id="productAutoComplete">--%>
<%--                    <ul id="productAutoList">--%>
<%--&lt;%&ndash;                        <li class="productAutoItem"> Amazon Parrot</li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li class="productAutoItem"> Amazon Parrot</li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li class="productAutoItem"> Amazon Parrot</li>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <li class="productAutoItem"> Amazon Parrot</li>&ndash;%&gt;--%>

<%--                    </ul>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div id="QuickLinks">--%>
<%--        <a href="categoryForm?categoryId=FISH" onmouseover="showCategory(this, 'FISH')" onmouseout="hideCategory()"><img src="images/sm_fish.gif" /></a>--%>
<%--        <img src="images/separator.gif" />--%>
<%--        <a href="categoryForm?categoryId=DOGS" onmouseover="showCategory(this, 'DOGS')" onmouseout="hideCategory()"><img src="images/sm_dogs.gif" /></a>--%>
<%--        <img src="images/separator.gif" />--%>
<%--        <a href="categoryForm?categoryId=REPTILES" onmouseover="showCategory(this, 'REPTILES')" onmouseout="hideCategory()"><img src="images/sm_reptiles.gif" /></a>--%>
<%--        <img src="images/separator.gif" />--%>
<%--        <a href="categoryForm?categoryId=CATS" onmouseover="showCategory(this, 'CATS')" onmouseout="hideCategory()"><img src="images/sm_cats.gif" /></a>--%>
<%--        <img src="images/separator.gif" />--%>
<%--        <a href="categoryForm?categoryId=BIRDS" onmouseover="showCategory(this, 'BIRDS')" onmouseout="hideCategory()"><img src="images/sm_birds.gif" /></a>--%>
<%--    </div>--%>

<%--    <div id="hover-menu-container"></div>--%>

<%--    <script type="text/javascript">--%>
<%--        var contextPath = "${pageContext.request.contextPath}";--%>
<%--    </script>--%>

<%--    <script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>--%>


<%--    <div id="Content">--%>

<%--    <c:if test="${not empty sessionScope.updateMsg}">--%>
<%--    <div style="background-color:#e6ffe6; color:#006600; font-weight:bold; padding:8px; border-radius:6px; margin:10px 0; text-align:center;">--%>
<%--        ${sessionScope.updateMsg}--%>
<%--    </div>--%>
<%--      <c:remove var="updateMsg" scope="session"/>--%>
<%--    </c:if>--%>

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>

<head>

    <title>MyPetStore</title>
    <link rel="StyleSheet" href="css/mypetstore.css" type="text/css" media="screen" />

    <script src="https://kit.fontawesome.com/59c20db7cc.js" crossorigin="anonymous"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="js/search.js"></script>

</head>

<body>
<div class="nav-top">
    <div class="w">
        <div class="user-info">
            <c:if test="${sessionScope.loginAccount == null}">
                <span>
                    <a href="signOnForm" class="link">Sign On</a >
                </span>
            </c:if>


            <c:if test="${sessionScope.loginAccount != null}">
                <span class="link-text">
                Welcome，<span class="username">${sessionScope.loginAccount.username}</span>
                </span>
                <a href="signOut" class="link">Sign Out</a >
            </c:if>

        </div>
        <ul class="nav-top-list">
            <li class="nav-top-item">
                <a href="cartForm" class="link">
                    <i class="fa fa-shopping-cart"></i>
                    Cart
                </a >
            </li>

            <c:if test="${sessionScope.loginAccount != null}">
                <li class="nav-top-item">
                    <a href="listOrders" class="link">My Orders</a >
                </li>
                <li class="nav-top-item">
                    <a href="profileForm" class="link">My Account</a >
                </li>
            </c:if>

            <li class="nav-top-item">
                <a href="help.html" class="link">?</a >
            </li>
        </ul>
    </div>
</div>

<div class="nav-search">
    <div class="w">
        <div id="logo">
            <a href="mainForm"><img src="images/logo-topbar.gif" /></a >
        </div>
        <div class="search-content">
            <div id="Search">
                <div id="SearchContent">
                    <div class="search-wrapper">
                        <form action="search" method="post" onsubmit="return checkSearch();">
                            <input type="text" name="keyword" size="14" id="keyword" class="search-input" />
                            <button type="submit" class="btn search-btn">搜索</button>
                        </form>
                        <div id="productAutoComplete">
                            <ul id="productAutoList">
                                <%--                        <li class="productAutoItem"> Amazon Parrot</li>--%>
                                <%--                        <li class="productAutoItem"> Amazon Parrot</li>--%>
                                <%--                        <li class="productAutoItem"> Amazon Parrot</li>--%>
                                <%--                        <li class="productAutoItem"> Amazon Parrot</li>--%>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="crumb">
    <div class="w">
        <div id="QuickLinks">
                    <a href="categoryForm?categoryId=FISH" onmouseover="showCategory(this, 'FISH')" onmouseout="hideCategory()"><img src="images/sm_fish.gif" /></a>
                    <img src="images/separator.gif" />
                    <a href="categoryForm?categoryId=DOGS" onmouseover="showCategory(this, 'DOGS')" onmouseout="hideCategory()"><img src="images/sm_dogs.gif" /></a>
                    <img src="images/separator.gif" />
                    <a href="categoryForm?categoryId=REPTILES" onmouseover="showCategory(this, 'REPTILES')" onmouseout="hideCategory()"><img src="images/sm_reptiles.gif" /></a>
                    <img src="images/separator.gif" />
                    <a href="categoryForm?categoryId=CATS" onmouseover="showCategory(this, 'CATS')" onmouseout="hideCategory()"><img src="images/sm_cats.gif" /></a>
                    <img src="images/separator.gif" />
                    <a href="categoryForm?categoryId=BIRDS" onmouseover="showCategory(this, 'BIRDS')" onmouseout="hideCategory()"><img src="images/sm_birds.gif" /></a>

        </div>
    </div>
</div>


<div id="hover-menu-container"></div>

<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>


<div id="Content">
    <div class="w w1">

        <c:if test="${not empty sessionScope.updateMsg}">
        <div style="background-color:#e6ffe6; color:#006600; font-weight:bold; padding:8px; border-radius:6px; margin:10px 0; text-align:center;">
                ${sessionScope.updateMsg}
        </div>
            <c:remove var="updateMsg" scope="session"/>
        </c:if>
