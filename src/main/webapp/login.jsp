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
        <div class="form-group ${status.error ? 'has-error' : ''}">
          <form:input class="form-control" path="username" type="text" name="username" placeholder="Account No" required=""></form:input>
          <form:errors path="username"></form:errors>
        </div>
      </spring:bind>
      <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''}">
        <form:input class="form-control" path="password" style="padding: 10px" type="password" name="password" placeholder="PIN" required=""></form:input>
          <form:errors path="password"></form:errors>
        </div>
      </spring:bind>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>