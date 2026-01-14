<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>--%>

<%--</div>--%>

<%--<div id="Footer">--%>

<%--    <div id="PoweredBy">--%>
<%--        &nbsp<a href="http://www.csu.edu.cn">www.csu.edu.cn</a>--%>
<%--    </div>--%>


<%--    <div id="Banner">--%>
<%--        <c:if test="${sessionScope.loginAccount != null }">--%>
<%--            <c:if test="${sessionScope.loginAccount.bannerOption}">--%>
<%--                ${sessionScope.loginAccount.bannerName}--%>
<%--            </c:if>--%>
<%--        </c:if>--%>
<%--    </div>--%>

<%--</div>--%>

<%--<script src="js/productAuto.js"></script>--%>

<%--</body>--%>
<%--</html>--%>


</div>
</div>

<div id="Footer">
    <div id="Banner">
        <div id="PoweredBy">
            &nbsp<a href="http://www.csu.edu.cn">www.csu.edu.cn</a >
        </div>
        <div id="love">
            <c:if test="${sessionScope.loginAccount != null }">
                <c:if test="${sessionScope.loginAccount.bannerOption}">
                    ${sessionScope.loginAccount.bannerName}
                </c:if>
            </c:if>
        </div>
    </div>
    <script src="js/productAuto.js"></script>

</div>
</body>
</html>
