<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Withdrawal Menu</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <%@ include file="/header.jsp" %>
</head>
<body>
<div class="container">
    <h1>Withdrawal</h1>
    <c:forEach items="${menus}" var="menu">
        <div><button type="button" class="btn btn-link" onclick="location.href='${menu.route}'"><h3>${menu.description}</h3></button></div>
    </c:forEach>
    <p style="color:red">${error}</p>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>