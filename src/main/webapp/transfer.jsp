<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Fund Transfer</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Fund Transfer</h1>
    <form:form id="transfer" action="transfer" method="post" modelAttribute="param">
      <spring:bind path="srcAccountNo">
        <form:errors path="srcAccountNo"></form:errors>
      </spring:bind>
      <div class="form-group ${status.error ? 'has-error' : ''}">
          <label for="reference" class="col-sm-3 col-form-label">Reference</label>
          <div class="col-sm-9">
              <input type="text" readonly class="form-control-plaintext" id="reference" name="reference" value="${tfParam.reference}"/>
          </div>
      </div>

      <spring:bind path="dstAccountNo">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="dstAccountNo" class="col-sm-3 col-form-label">Destination Account</label>
            <div class="col-sm-9">
                <form:input type="number" path="dstAccountNo" class="form-control" id="dstAccountNo" name="dstAccountNo"></form:input>
                <form:errors path="dstAccountNo"></form:errors>
            </div>
        </div>
      </spring:bind>
      <spring:bind path="trxAmount">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="trxAmount" class="col-sm-3 col-form-label">Transfer Amount</label>
            <div class="col-sm-9">
                <form:input type="number" path="trxAmount" class="form-control" id="trxAmount" name="trxAmount"></form:input>
                <form:errors path="trxAmount"></form:errors>
            </div>
        </div>
      </spring:bind>
    </form:form>
    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#confirmationModal">Transfer</button>
    <button class="btn btn-secondary" onclick="location.href='/main'">Cancel</button>
</div>
<!-- Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Transfer Confirmation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="form-group row">
          <label for="refModal" class="col-sm-5 col-form-label">Reference</label>
          <div class="col-sm-7">
            <input type="text" readonly class="form-control-plaintext" id="refModal" name="refModal"/>
          </div>
          <label for="dstAccountNoModal" class="col-sm-5 col-form-label">Destination Account</label>
          <div class="col-sm-7">
            <input type="text" readonly class="form-control-plaintext"  id="dstAccountNoModal" name="dstAccountNoModal" />
          </div>
          <label for="amountModal" class="col-sm-5 col-form-label">Amount</label>
          <div class="col-sm-7">
            <input type="text" readonly class="form-control-plaintext"  id="amountModal" name="amountModal" />
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="transferConfirm">Transfer</button>
      </div>
    </div>
  </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
  $("#refModal").val($("#reference").val());
  $("#dstAccountNo").change(function(){
    $("#dstAccountNoModal").val($("#dstAccountNo").val());
  });
  $("#trxAmount").change(function(){
    $("#amountModal").val($("#trxAmount").val());
  });
  $('#transferConfirm').click(function(){
      $('#transfer').submit();
  });
})
</script>
</body>
</html>