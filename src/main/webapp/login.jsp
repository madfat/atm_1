<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div style="margin-top: 80px; margin-bottom: 80px">
    <form:form style="max-width: 380px; margin: 0 auto" id="login" action="${contextPath}/login" method="post" modelAttribute="loginParameter">
      <spring:bind path="username">
        <div class="form-group">
          <form:input class="form-control" path="username" type="text" name="username" placeholder="Account No" required=""></form:input>
        </div>
      </spring:bind>
      <spring:bind path="password">
        <div class="form-group">
          <form:input class="form-control" path="password" style="padding: 10px" type="password" name="password" placeholder="PIN" required=""></form:input>
        </div>
      </spring:bind>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <p id="error" style="text-align:center; color:red"></p>
    </form:form>
    <button style="max-width: 380px; margin: 0 auto" id="btnLogin" class="btn btn-lg btn-primary btn-block">Login</button>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
    let searchParams = new URLSearchParams(window.location.search);
    if(searchParams.has('error')){
        $("#error").text("Login authentication failed");
    };

    $("#btnLogin").click(function() {
        $("#login").submit();
    });
})
</script>
</body>
</html>