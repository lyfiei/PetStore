var cacheData = {};
var hideTimer;
var menuContainer;

function initMenu() {
    menuContainer = document.getElementById("hover-menu-container");

    // 给容器本身加鼠标移出事件，防止鼠标在菜单上操作时菜单消失
    if(menuContainer){
        menuContainer.onmouseleave = function() {
            menuContainer.style.display = "none";
        };
        // 当鼠标移入菜单时，清除隐藏计时器
        menuContainer.onmouseover = function() {
            clearTimeout(hideTimer);
        };
    }
}

function showCategory(element, categoryId) {
    if (!menuContainer) initMenu();

    clearTimeout(hideTimer);

    // 1. 定位逻辑
    var rect = element.getBoundingClientRect();
    menuContainer.style.top = (rect.bottom + window.scrollY) + "px";
    menuContainer.style.left = rect.left + "px";
    menuContainer.style.display = "block";

    // 2. 检查缓存
    if (cacheData[categoryId]) {
        renderMenu(cacheData[categoryId]);
        return;
    }

    // 显示加载中
    menuContainer.innerHTML = "<div style='padding:20px;'>Loading...</div>";

    // 3. 发送 AJAX 请求
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/categoryAjax?categoryId=" + categoryId;

    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var jsonResponse = JSON.parse(xhr.responseText);
            cacheData[categoryId] = jsonResponse;
            renderMenu(jsonResponse);
        }
    };
    xhr.send();
}

function hideCategory() {
    // 给他一点延迟，给用户时间把鼠标从 "Dogs" 移动到悬浮框上
    hideTimer = setTimeout(function() {
        if(menuContainer) menuContainer.style.display = "none";
    }, 200); // 200毫秒延迟
}


function renderMenu(data) {
    if (!data || data.length === 0) {
        menuContainer.innerHTML = "<div style='padding:10px;'>暂无商品</div>";
        return;
    }

    var html = "<div class='menu-content'>";

    // 遍历每一个大产品
    for (var i = 0; i < data.length; i++) {
        var product = data[i];

        // 每一列
        html += "<div class='product-column'>";

        html += "<a href='productForm?productId=" +product.productId + "' class='menu-product-title'>";
        html += product.name;
        html += "</a>";


        // 遍历下面的具体项目
        for (var j = 0; j < product.items.length; j++) {
            var item = product.items[j];

            html += "<a href='itemForm?itemId=" + item.itemId + "' class='menu-item-row'>";
            html += item.desc + " ($" + item.price + ")";
            html += "</a>";
        }

        html += "</div>";
    }

    html += "</div>";

    menuContainer.innerHTML = html;
}
