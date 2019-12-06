<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
      <thead>
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
              <tr>
                <th>#</th>
                <th>${transaction.type}</th>
                <th>${transaction.sourceAccount}</th>
                <th>${transaction.destinationAccount}</th>
                <th>
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
                <th>${transaction.transactionDate}</th>
              </tr>
          </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
</body>

</html>