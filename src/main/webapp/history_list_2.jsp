<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Transaction History</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
    <%@ include file="/header.jsp" %>
</head>
<body>
  <div class="container">
    <h1>History List</h1>
    <div class="form-group row">
        <label for="acct" class="col-sm-3 col-form-label">Account</label>
        <div class="col-sm-9">
            <input type="text" class="form-control-plaintext" readonly id="acct" name="acct" value="${acct} - ${acctName}"/>
        </div>

        <label for="acct" class="col-sm-3 col-form-label">Balance</label>
        <div class="col-sm-9">
            <input type="text" class="form-control-plaintext" readonly id="balance" name="balance" value="${balance}"/>
        </div>
    </div>
    <div class="form-group row">
        <button type="button" class="btn btn-primary" onclick="location.href='/main'">Back</button>
    </div>
    <table class="table">
      <thead style="text-align:center">
        <th scope="col">#</th>
        <th scope="col">Type</th>
        <th scope="col">Source Account</th>
        <th scope="col">Destination Account</th>
        <th scope="col">Amount</th>
        <th scope="col">Reference No</th>
        <th scope="col">Date</th>
      </thead>
      <tbody>
          <c:forEach items="${transaction_list}" var="transaction">
              <tr style="text-align:center">
                <th>#</th>
                <th>${transaction.type}</th>
                <th>${transaction.sourceAccount}</th>
                <th>${transaction.destinationAccount}</th>
                <th style="text-align:right">
                    <c:choose>
                      <c:when test="${transaction.sourceAccount==acct && transaction.type=='Transfer' || transaction.type=='Withdraw'}">
                        - ${transaction.amount}
                      </c:when>
                      <c:otherwise>
                        + ${transaction.amount}
                      </c:otherwise>
                    </c:choose>
                </th>
                <th>${transaction.refNo}</th>
                <th>
                  ${transaction.transactionDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}
                </th>
              </tr>
          </c:forEach>
      </tbody>
    </table>
    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-end">
        <c:if test="${number_of_elements > 0}">
          <c:if test="${number ne 0}">
            <li class="page-item"><a class="page-link" href="history-all?page=${number - 1}">Previous</a></li>
          </c:if>
          <c:forEach var = "i" begin = "0" end = "${total_pages - 1}">
             <li class="page-item"><a class="page-link" href="history-all?page=${i}"><c:out value = "${i + 1}"/></a></li>
          </c:forEach>
          <c:if test="${number ne total_pages - 1}">
              <li class="page-item"><a class="page-link" href="history-all?page=${number + 1}">Next</a></li>
          </c:if>
        </c:if>
      </ul>
    </nav>
  </div>
</div>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
</body>

</html>