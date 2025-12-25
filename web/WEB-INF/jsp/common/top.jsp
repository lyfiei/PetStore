<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>

<head>

    <title>MyPetStore</title>
    <link rel="StyleSheet" href="css/mypetstore.css" type="text/css" media="screen" />
    <script src="https://kit.fontawesome.com/59c20db7cc.js" crossorigin="anonymous"></script>
</head>

<body>
<div class="nav-top">
    <div class="w">
        <div class="user-info">
            <c:if test="${sessionScope.loginAccount == null}">
                <span>
                    <a href="signonForm" class="link">Sign In</a>
                </span>
            </c:if>


            <c:if test="${sessionScope.loginAccount != null}">
                <span class="link-text">
                Welcome，<span class="username">${sessionScope.loginAccount.username}</span>
            </span>
                <a href="#" class="link">Sign Out</a>
            </c:if>

        </div>
        <ul class="nav-top-list">
            <li class="nav-top-item">
                <a href="cartForm" class="link">
                    <i class="fa fa-shopping-cart"></i>
                    Cart
                </a>
            </li>

            <c:if test="${sessionScope.loginAccount != null}">
                <li class="nav-top-item">
                    <a href="listOrders" class="link">My Orders</a>
                </li>
                <li class="nav-top-item">
                    <a href="profileForm" class="link">My Account</a>
                </li>
            </c:if>

            <li class="nav-top-item">
                <a href="help.html" class="link">?</a>
            </li>
        </ul>
    </div>
</div>

<div class="nav-search">
    <div class="w">
        <div id="logo">
            <a href="mainForm"><img src="images/logo-topbar.gif" /></a>
        </div>
        <div class="search-content">
            <input type="text" name="keyword" class="search-input" placeholder="请输入商品名称"/>
            <button type="submit" class="btn search-btn">搜索</button>
        </div>
    </div>
</div>


<div class="crumb">
    <div class="w">
        <div id="QuickLinks">
            <a href="categoryForm?categoryId=FISH"><img src="images/sm_fish.gif" /></a>
            <img src="images/separator.gif" />
            <a href="categoryForm?categoryId=DOGS"><img src="images/sm_dogs.gif" /></a>
            <img src="images/separator.gif" />
            <a href="categoryForm?categoryId=REPTILES"><img src="images/sm_reptiles.gif" /></a>
            <img src="images/separator.gif" />
            <a href="categoryForm?categoryId=CATS"><img src="images/sm_cats.gif" /></a>
            <img src="images/separator.gif" />
            <a href="categoryForm?categoryId=BIRDS"><img src="images/sm_birds.gif" /></a>
        </div>
    </div>
</div>

<div id="Content">
    <div class="w w1">


