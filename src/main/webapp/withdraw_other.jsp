<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Withdrawal</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <%@ include file="/header.jsp" %>
</head>
<body>
<div class="container">
    <h1>Other Withdrawal</h1>
    <form id="withdraw" action="withdraw" method="post">
        <div class="form-group row">
            <label for="amount" class="col-sm-3 col-form-label">Enter amount to withdraw</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" id="amount" autofocus name="amount">
            </div>
            <label class="col-sm-3"></label>
            <div class="col-sm-9"><p>${error}</p></div>
        </div>
        <button class="btn btn-primary">Withdraw</button>
    </form>
    <button class="btn btn-secondary" onclick="location.href='/main'">Cancel</button>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>