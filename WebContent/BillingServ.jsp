<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.Billing"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/billvalidation.js"></script> 
<title>Billing Service</title>
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-10"> 
			<h1>Billing Function for ElectroGrid</h1>
				<form id="formBilling" name="formBilling" method="post" action="BillingServ.jsp">  
					User ID:  
 	 				<input id="UserID" name="UserID" type="text"  class="form-control form-control-sm">
					<br>
					User Email:   
  					<input id="UserEmail" name="UserEmail" type="text" class="form-control form-control-sm">   
  					<br>
  					Date:   
  					<input id="Date" name="Date" type="date"  class="form-control form-control-sm">
  					<br>
  					Units Consumed:   
  					<input id="UnitsConsumed" name="UnitsConsumed" type="text"  class="form-control form-control-sm">
					<br>
					Price Per Unit:   
  					<input id="PricePerUnit" name="PricePerUnit" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divBillingGrid">
					<%
						Billing bill = new Billing();
						out.print(bill.readBilling());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
</body>
</html>