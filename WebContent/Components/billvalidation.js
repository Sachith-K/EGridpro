$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateBillingForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "BillingService",   
			type : type,  
			data : $("#formBilling").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onBillSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onBillSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divBillingGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidBillIDSave").val("");  
	$("#formBilling")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidBillIDSave").val($(this).closest("tr").find('#hidBillIDUpdate').val());     
	$("#UserID").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#UserEmail").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#Date").val($(this).closest("tr").find('td:eq(2)').text());
	$("#UnitsConsumed").val($(this).closest("tr").find('td:eq(3)').text());    
	$("#PricePerUnit").val($(this).closest("tr").find('td:eq(4)').text());
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "BillingService",   
		type : "DELETE",   
		data : "BillID=" + $(this).data("billid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onBillDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onBillDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divBillingGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateBillingForm() 
{  

	 var tmpID = $("#UserID").val().trim();
	 if (!$.isNumeric(tmpID)) 
		{
		return "Insert User ID.";
		}
	
	 /*if ($("#UserID").val().trim() == "")  
		{   
			return "Insert User ID.";  
		} */
	 
	if ($("#UserEmail").val().trim() == "")  
	{   
		return "Insert Email.";  
	} 
	
	
	if ($("#Date").val().trim() == "")  
	{   
		return "Insert Date.";  
	} 
	
	if ($("#UnitsConsumed").val().trim() == "")  
	{   
		return "Insert Number of Units Consumed.";  
	} 
	
	if ($("#PricePerUnit").val().trim() == "")  
	{   
		return "Insert Price Per Unit.";  
	} 
	return true; 
}