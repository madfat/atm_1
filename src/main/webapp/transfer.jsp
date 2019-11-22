<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Fund Transfer</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Fund Transfer</h1>
    <form id="transfer" action="transfer" method="post">
        <div class="form-group row">
            <label for="reference" class="col-sm-3 col-form-label">Reference</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="reference" name="reference" value="${refNo}"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="dstAccountNo" class="col-sm-3 col-form-label">Destination Account</label>
            <div class="col-sm-9">
                <input type="number" maxLength=6 class="form-control" id="dstAccountNo" autofocus name="dstAccountNo" />
            </div>
        </div>
        <div class="form-group row">
            <label for="trxAmount" class="col-sm-3 col-form-label">Transfer Amount</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" mandatory id="trxAmount" name="trxAmount" />
            </div>
        </div>
    </form>
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