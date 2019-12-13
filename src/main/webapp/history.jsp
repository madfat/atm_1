<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Transaction History</title>
    <%@ include file="/header.jsp" %>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="container">
        <h1>Transaction History</h1>
        <ul>
        <li>
        <div>
          <a href="top10history"><h3>Top 10 Transactions</h3></a>
        </div>
        </li>
        <li>
        <div>
          <a href="history-all"><h3>All Transactions</h3></a>
        </div>
        </li>
        <li>
        <div>
        <h3>Within Date Range</h3>
            <form:form action="history" id="history" method="post" modelAttribute="param">
              <spring:bind path="startDate">
                Start Date: <input id="startDate" name="startDate" width="276" />
              </spring:bind>
              <spring:bind path="endDate">
                End Date: <input id="endDate" name="endDate" width="276" />
              </spring:bind>
            </form:form>
        </div>
        <div>
            <button type="submit" class="btn btn-primary" id="searchbtn">Search</button>
            <button type="button" class="btn btn-secondary" onclick="location.href='/main'">Back</button>
        </div>
        </li>
        </ul>
    </div>

</div>
<script>
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    $('#startDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        maxDate: function () {
            return $('#endDate').val();
        }
    });
    $('#endDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        minDate: function () {
            return $('#startDate').val();
        }
    });
</script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script>
$(document).ready(function() {
  $('#searchbtn').click(function(){
      $('#history').submit();
  });
})
</script>
</body>

</html>