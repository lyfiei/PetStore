function checkSearch() {
    const input = document.getElementById("keyword");
    if (!input) return true; // 防止意外页面报错

    const keyword = input.value.trim();
    if (keyword === "") {
        // 空搜索：什么都不做，直接阻止提交
        return false;
    }
    return true;
}
