<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Receipt</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body data-targetaccount=${destinationAccount} data-refno=${refNo}>
<div class="container">
    <h1>${trxType} Receipt</h1>
    <form>
        <div class="form-group row">
            <label for="trxDate" class="col-sm-2 col-form-label">Transaction Date</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext"  id="trxDate" value="${transaction_summary.transactionDate}">
            </div>
        </div>
        <div class="form-group row">
            <label for="account" class="col-sm-2 col-form-label">Source Account</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="account" value="${transaction_summary.sourceAccount}">
            </div>
        </div>
        <div class="form-group row" id="destAccount">
            <label for="account" class="col-sm-2 col-form-label">Destination Account</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="destinationAccount" value="${transaction_summary.destinationAccount}">
            </div>
        </div>
        <div class="form-group row">
            <label for="withdrawAmount" class="col-sm-2 col-form-label">Amount</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="withdrawAmount" value="${transaction_summary.amount}">
            </div>
        </div>
        <div class="form-group row" id="rNo">
            <label for="account" class="col-sm-2 col-form-label">Reference No</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" id="refNo" value="${transaction_summary.refNo}">
            </div>
        </div>
        <button type="button" class="btn btn-primary" onclick="location.href='/main'">Back</button>
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
/*
$(document).ready(function() {
  var target = $("body").attr("data-targetaccount");
  var ref = $("body").attr("data-refno");
  target==null ? $('#destAccount').hide() : $('#destAccount').show();
  ref==null ? $('#rNo').hide() : $('#rNo').show();
  $('#destAccount').click(function(){
      $('#transfer').submit();
  });
})
*/
</script>
</body>
</html>