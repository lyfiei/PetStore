</div>
</div>

<div id="Footer">
    <div id="Banner">
        <div id="PoweredBy">
            &nbsp<a href="http://www.csu.edu.cn">www.csu.edu.cn</a>
        </div>
        <div id="love">
            <c:if test="${sessionScope.loginAccount != null }">
                <c:if test="${sessionScope.loginAccount.bannerOption}">
                    ${sessionScope.loginAccount.bannerName}
                </c:if>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>