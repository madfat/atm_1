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
    <form:form style="max-width: 380px; margin: 0 auto" id="login" action="login" method="post" modelAttribute="loginParameter">
      <spring:bind path="accountNo">
        <div class="form-group ${status.error ? 'has-error' : ''}">
          <form:input class="form-control" path="accountNo" type="text" name="accountNo" placeholder="Account No" required=""></form:input>
          <form:errors path="accountNo"></form:errors>
        </div>
      </spring:bind>
      <spring:bind path="pin">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:input class="form-control" path="pin" style="padding: 10px" type="password" name="pin" placeholder="PIN" required=""></form:input>
          <form:errors path="pin"></form:errors>
        </div>
      </spring:bind>
        <button class="btn btn-lg btn-primary btn-block">Login</button>
    </form:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>