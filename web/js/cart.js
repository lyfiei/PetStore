document.addEventListener('DOMContentLoaded', function() {

    const inputs = document.querySelectorAll('.quantity-input');

    inputs.forEach(input => {
        input.addEventListener('change', function() {
            // 1. 获取基础数据
            const itemId = this.getAttribute('data-itemid');
            const newQuantity = this.value;

            // 2. 简单的校验：防止输入负数
            if (newQuantity < 1) {
                alert("数量不能小于1");
                this.value = 1; // 恢复为 1
                return;
            }

            // 3. 发送 AJAX 请求
            const xhr = new XMLHttpRequest();

            xhr.open('POST', 'updateCart', true);

            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            // 4. 【核心修改】处理服务器返回的数据
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        try {
                            // A. 解析 JSON 字符串
                            // 服务器返回的是类似 '{"itemTotal": "$99.00", "subTotal": "$199.00"}'
                            var response = JSON.parse(xhr.responseText);
                            console.log("更新成功", response);

                            // B. 更新【单行总价】
                            // 必须保证 JSP 里有 id="total-EST-1" 这样的标签
                            var itemTotalSpan = document.getElementById('total-' + itemId);
                            if (itemTotalSpan) {
                                itemTotalSpan.innerText = response.itemTotal;
                            }

                            // C. 更新【购物车总价】
                            // 必须保证 JSP 里有 id="sub-total" 的标签
                            var subTotalSpan = document.getElementById('sub-total');
                            if (subTotalSpan) {
                                subTotalSpan.innerText = response.subTotal;
                            }

                        } catch (e) {
                            console.error("解析 JSON 失败，可能是后台报错了: " + e);
                        }
                    } else {
                        console.error("请求失败，状态码: " + xhr.status);
                    }
                }
            };

            // 5. 发送数据
            xhr.send('itemId=' + encodeURIComponent(itemId) +
                '&quantity=' + encodeURIComponent(newQuantity));
        });
    });
});

// AJAX 删除函数
function removeItemAjax(itemId, btnElement) {
    // 1. 浏览器原生弹窗确认
    if (!confirm("Are you sure you want to remove this item?")) {
        return;
    }

    // 2. 发送 AJAX 请求
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'removeCartItem?workingItemId=' + encodeURIComponent(itemId), true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    var response = JSON.parse(xhr.responseText);

                    var row = btnElement.closest('tr');
                    if (row) {
                        row.remove(); // 这一行瞬间消失！
                        console.log("行已删除");
                    }

                    var subTotalSpan = document.getElementById('sub-total');
                    if (subTotalSpan) {
                        subTotalSpan.innerText = response.subTotal;
                    }


                } catch (e) {
                    console.error("JSON解析错啦: " + e);
                }
            } else {
                alert("删除失败，服务器出错了");
            }
        }
    };

    xhr.send();
}