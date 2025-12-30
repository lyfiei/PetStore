<%@ page import="csu.web.mypetstore.domain.Account" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../common/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<style>
    /* 样式：控制步骤显示和进度条 */
    .step-container { display: none; background: #fff; padding: 20px; border-radius: 8px; }
    .step-container.active { display: block; }
    .checkout-steps { display: flex; justify-content: space-around; margin-bottom: 30px; background: #f8f9fa; padding: 15px; }
    .checkout-steps div { color: #999; font-weight: bold; }
    .checkout-steps div.active { color: #eaac00; border-bottom: 2px solid #eaac00; }
    .btn-group { margin-top: 20px; text-align: center; }
    .Button { cursor: pointer; padding: 5px 20px; }
    .Button-right{
        padding: 7px 20px;
        font-family: Arial, sans-serif;
        font-size: 14px;
        cursor: pointer;
        background-color: #FFC107;
        color: black;
        border: 1px solid #CCCCCC;
        border-radius: 4px;
        box-shadow: 0 1px 2px rgba(0,0,0,0.1);}
    .Button-right:hover {
        background-color: #eaac00;
    }
</style>

<div id="Catalog">
    <div class="checkout-steps">
        <div id="nav-1" class="active">Confirm Items</div>
        <div id="nav-2">Payment Method(s)</div>
        <div id="nav-3"> Fill in Address</div>
        <div id="nav-4">Preview and Submit</div>
    </div>

    <div id="step-1" class="step-container active">
        <h2>Checkout Summary</h2>
        <table>

            <tr>
                <td><b>Item ID</b></td>
                <td><b>Product ID</b></td>
                <td><b>Description</b></td>
                <td><b>In Stock?</b></td>
                <td><b>Quantity</b></td>
                <td><b>List Price</b></td>
                <td><b>Total Cost</b></td>
            </tr>

            <c:forEach var="cartItem" items="${sessionScope.cart.cartItems}">
                <tr>
                    <td>
                        <a href="itemForm?itemId=${cartItem.item.itemId}">${cartItem.item.itemId}</a>
                    </td>
                    <td>${cartItem.item.product.productId}</td>
                    <td>
                            ${cartItem.item.attribute1} ${cartItem.item.attribute2}
                            ${cartItem.item.attribute3} ${cartItem.item.attribute4}
                            ${cartItem.item.attribute5}
                            ${cartItem.item.product.name}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${cartItem.inStock}">Yes</c:when>
                            <c:otherwise>No</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${cartItem.quantity}</td>
                    <td><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00" /></td>
                    <td><fmt:formatNumber value="${cartItem.total}" pattern="$#,##0.00" /></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7">Sub Total:
                    <fmt:formatNumber value="${sessionScope.cart.subTotal}" pattern="$#,##0.00" /></td>
            </tr>
        </table>
        <div class="btn-group">
            <button class="Button" onclick="cartForm">Return to Shopping Cart</button>
            <button class="Button-right" onclick="changeStep(2)">Next Step: Select Payment Method</button>
        </div>
    </div>

    <div id="step-2" class="step-container">
        <h2>Payment Method</h2>
        <div style="text-align: center;">
            <img id="payQR" src="images/alipayPayImg.jpg" style="width: 200px; border: 1px solid #ddd;">
            <br><br>

            <label>
                <input type="radio" name="payMethod" checked onclick="switchPay('alipay')"> Alipay
            </label>
            <label>
                <input type="radio" name="payMethod" onclick="switchPay('wechat')"> WeChat
            </label>
        </div>

        <div class="btn-group">
            <button class="Button" onclick="changeStep(1)">Previous Step</button>
            <button class="Button-right" onclick="changeStep(3)"> I Have Paid, Fill in Address</button>
        </div>
    </div>

    <script>
        function switchPay(method) {
            const qrImage = document.getElementById('payQR');

            if (method === 'alipay') {
                qrImage.src = 'images/alipayPayImg.jpg';
            } else if (method === 'wechat') {
                qrImage.src = 'images/WeChatPayImg.jpg';
            }
        }
    </script>

    <div id="step-3" class="step-container">
        <form id="combinedAddressForm">
            <input type="hidden" name="action" value="saveInfo">
            <h3>Payment & Billing Address</h3>
            <%
                Account account = (Account) session.getAttribute("loginAccount");
            %>
            <table>
                <tr>
                    <th colspan=2>Payment Details</th>
                </tr>
                <tr>
                    <td>Card Type:</td>
                    <td>
                        <label>
                            <select name="cardType">
                                <option value="Visa">Visa</option>
                                <option value="Mastercard">Mastercard</option>
                                <option value="AmericanExpress">American Express</option>
                            </select>
                        </label>
                    </td>
                </tr>

                <tr>
                    <td>Card Number:</td>
                    <td>
                        <input type="text" name="Number" value="<%= account != null ? account.getPhone():"" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Order Date (MM/YYYY):</td>
                    <td>
                        <input type="text" name="ODate" value="<%= java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) %>" readonly/>
                    </td>
                </tr>
                <tr>
                    <td>Expiry Date (MM/YYYY):</td>
                    <td>
                        <input type="text" name="EDate" value="<%= java.time.LocalDateTime.now().plusDays(10).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) %>"readonly/>
                    </td>
                </tr>
                <tr>
                    <th colspan=2>Billing Address</th>
                </tr>

                <tr>
                    <td>First name:</td>
                    <td>
                        <input type="text" name="FirstName" value="<%= account != null ? account.getFirstName() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Last name:</td>
                    <td>
                        <input type="text" name="LastName" value="<%= account != null ? account.getLastName() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Address 1:</td>
                    <td>
                        <input type="text" size="40" name="Address1" value="<%= account != null ? account.getAddress1() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Address 2:</td>
                    <td>
                        <input type="text" size="40" name="Address2" value="<%= account != null ? account.getAddress2() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td>
                        <input type="text" name="City" value="<%= account != null ? account.getCity() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>State:</td>
                    <td>
                        <input type="text" size="4" name="State" value="<%= account != null ? account.getState() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Zip:</td>
                    <td>
                        <input type="text" size="10" name="Zip" value="<%= account != null ? account.getZip() : "" %>"/>
                    </td>
                </tr>
                <tr>
                    <td>Country:</td>
                    <td>
                        <input type="text" size="15" name="Country" value="<%= account != null ? account.getCountry() : "" %>"/>
                    </td>
                </tr>

                <%--        <tr>--%>
                <%--            <td colspan=2>--%>
                <%--                <input type="checkbox" name="shippingAddressRequired" />--%>
                <%--                Ship to different address...--%>
                <%--            </td>--%>
                <%--        </tr>--%>

            </table>

            <h3>Shipping Address</h3>

            <table>
                <tr>
                    <th colspan="2">Shipping Address</th>
                </tr>

                <tr>
                    <td>First name:</td>
                    <td>
                        <input type="text" name="shipToFirstName" value="<%= account != null ? account.getFirstName() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>Last name:</td>
                    <td>
                        <input type="text" name="shipToLastName" value="<%= account != null ? account.getLastName() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>Address 1:</td>
                    <td>
                        <input type="text" size="40" name="shipAddress1" value="<%= account != null ? account.getAddress1() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>Address 2:</td>
                    <td>
                        <input type="text" size="40" name="shipAddress2" value="<%= account != null ? account.getAddress2() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>City:</td>
                    <td>
                        <input type="text" name="shipCity" value="<%= account != null ? account.getCity() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>State:</td>
                    <td>
                        <input type="text" size="4" name="shipState" value="<%= account != null ? account.getState() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>Zip:</td>
                    <td>
                        <input type="text" size="10" name="shipZip" value="<%= account != null ? account.getZip() : "" %>"/>
                    </td>
                </tr>

                <tr>
                    <td>Country:</td>
                    <td>
                        <input type="text" size="15" name="shipCountry" value="<%= account != null ? account.getCountry() : "" %>"/>
                    </td>
                </tr>
            </table>


        </form>
        <div class="btn-group">
            <button class="Button" onclick="changeStep(2)">Previous Step</button>
            <button class="Button-right" onclick="ajaxSaveInfo()">Save and Preview Order</button>
        </div>
    </div>

    <div id="step-4" class="step-container">
        <h2>Please Confirm Your Information</h2>
        <table>
            <tr>
                <th align="center" colspan="2">
                    <font size="4"><b>Order</b></font><br/>
                    <font size="3"><b>
                        <fmt:formatDate value="${sessionScope.order.orderDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
                    </b></font>
                </th>
            </tr>

            <tr><th colspan="2">Billing Address</th></tr>
            <tr><td>First name:</td><td>${sessionScope.order.billToFirstName}</td></tr>
            <tr><td>Last name:</td><td>${sessionScope.order.billToLastName}</td></tr>
            <tr><td>Address 1:</td><td>${sessionScope.order.billAddress1}</td></tr>
            <tr><td>Address 2:</td><td>${sessionScope.order.billAddress2}</td></tr>
            <tr><td>City:</td><td>${sessionScope.order.billCity}</td></tr>
            <tr><td>State:</td><td>${sessionScope.order.billState}</td></tr>
            <tr><td>Zip:</td><td>${sessionScope.order.billZip}</td></tr>
            <tr><td>Country:</td><td>${sessionScope.order.billCountry}</td></tr>

            <tr><th colspan="2">Shipping Address</th></tr>
            <tr><td>First name:</td><td>${sessionScope.order.shipToFirstName}</td></tr>
            <tr><td>Last name:</td><td>${sessionScope.order.shipToLastName}</td></tr>
            <tr><td>Address 1:</td><td>${sessionScope.order.shipAddress1}</td></tr>
            <tr><td>Address 2:</td><td>${sessionScope.order.shipAddress2}</td></tr>
            <tr><td>City:</td><td>${sessionScope.order.shipCity}</td></tr>
            <tr><td>State:</td><td>${sessionScope.order.shipState}</td></tr>
            <tr><td>Zip:</td><td>${sessionScope.order.shipZip}</td></tr>
            <tr><td>Country:</td><td>${sessionScope.order.shipCountry}</td></tr>
        </table>
        <div class="btn-group">
            <button class="Button" onclick="changeStep(3)"> Edit Information</button>
            <button class="Button-right" onclick="ajaxFinalSubmit()">Confirm and Submit the Order</button>
        </div>
    </div>

    <div id="step-5" class="step-container">
        <div style="text-align: center; padding: 40px;">
            <h2 style="color: green;">OK!  Order Placed Successfully!</h2>
            <p>Thank you for your purchase. Your order has entered the processing stage...</p>
            <a href="mainForm" class="Button">Return to Main Menu</a>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    // 页面加载完成后执行
    $(document).ready(function() {
        console.log("jQuery 加载成功，页面已就绪");
    });

    // 切换步骤的显示
    function changeStep(step) {
        console.log("准备切换到步骤: " + step);

        // 隐藏所有容器并移除 active 类
        $(".step-container").hide().removeClass("active");

        // 显示目标容器并添加 active 类
        $("#step-" + step).fadeIn().addClass("active");

        // 更新导航进度条样式
        $(".checkout-steps div").removeClass("active");
        $("#nav-" + step).addClass("active");
    }

    // 同步地址小功能
    function syncAddress() {
        if($("#copyAddress").is(':checked')) {
            $("#s_fn").val($("input[name='FirstName']").val());
            $("#s_a1").val($("input[name='Address1']").val());
        }
    }

    // AJAX 提交地址信息并进入预览
    function ajaxSaveInfo() {
        $.ajax({
            url: "orderUnified",
            type: "POST",
            data: $("#combinedAddressForm").serialize(),
            success: function(res) {
                if(res.trim() === "success") {
                    let html = "<h4>Billing to:</h4>" + $("input[name='FirstName']").val() + "<br>" + $("input[name='Address1']").val();
                    html += "<h4>Shipping to:</h4>" + $("#s_fn").val() + "<br>" + $("#s_a1").val();
                    $("#previewArea").html(html);
                    changeStep(4);
                } else {
                    alert("Save failed: " + res);
                }
            },
            error: function() {
                alert("Server error, please try again.");
            }
        });
    }

    // AJAX 最终提交订单
    function ajaxFinalSubmit() {
        $.ajax({
            url: "orderUnified",
            type: "POST",
            data: { action: "confirmSubmit" },
            success: function(res) {
                if(res.trim() === "success") {
                    changeStep(5);
                }
            }
        });
    }
</script>

<%@ include file="../common/bottom.jsp"%>