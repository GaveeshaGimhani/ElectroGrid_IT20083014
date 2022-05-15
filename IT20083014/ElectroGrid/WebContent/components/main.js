$(document).ready(function()  
{   
 if ($("#alertSuccess").text().trim() == "")   
 {    
  $("#alertSuccess").hide();   
 }  
 $("#alertError").hide();  
});  
 
//SAVE ============================================  
$(document).on("click", "#btnSave", function(event)  
{   
 // Clear alerts---------------------   
 $("#alertSuccess").text("");   
 $("#alertSuccess").hide();   
 $("#alertError").text("");   
 $("#alertError").hide();  
 
 // Form validation-------------------   
 var status = validatePrivateSectorForm();   
 if (status != true)   
 {    
  $("#alertError").text(status);    
  $("#alertError").show();    
  return;   
 }  
 
 // If valid------------------------   
 var t = ($("#hidcustomerIDSave").val() == "") ? "POST" : "PUT"; 
  
 $.ajax( 
 { 
  url : "PrivateSectorApi", 
  type : t, 
  data : $("#formPrivateSector").serialize(), 
  dataType : "text", 
  complete : function(response, status) 
  { 
   onPrivateSectorSaveComplete(response.responseText, status); 
  } 
 }); 
});  
 
function onPrivateSectorSaveComplete(response, status){ 
 if(status == "success") 
 { 
  var resultSet = JSON.parse(response); 
    
  if(resultSet.status.trim() == "success") 
  { 
   $("#alertSuccess").text("Successfully Saved."); 
   $("#alertSuccess").show(); 
      
   $("#divItemsGrid").html(resultSet.data); 
  
  }else if(resultSet.status.trim() == "error"){ 
   $("#alertError").text(resultSet.data); 
   $("#alertError").show(); 
  } 
 }else if(status == "error"){ 
  $("#alertError").text("Error While Saving."); 
  $("#slertError").show(); 
 }else{ 
  $("#alertError").text("Unknown Error while Saving."); 
  $("#alertError").show(); 
 } 
 $("#hidcustomerIDSave").val(""); 
 $("#formPrivateSector")[0].reset(); 
} 
 
//UPDATE==========================================  
$(document).on("click", ".btnUpdate", function(event)  
  {      
 $("#hidcustomerIDSave").val($(this).closest("tr").find('#hidcustomerIDUpdate').val());      
 $("#customerName").val($(this).closest("tr").find('td:eq(0)').text());    
 $("#NIC").val($(this).closest("tr").find('td:eq(1)').text());  
 $("#customerEmail").val($(this).closest("tr").find('td:eq(2)').text());      
 $("#noOfUnits").val($(this).closest("tr").find('td:eq(3)').text());      
 $("#chargePerUnit").val($(this).closest("tr").find('td:eq(4)').text());  
  
 
}); 
 
 
//Remove Operation 
$(document).on("click", ".btnRemove", function(event){ 
 $.ajax( 
 { 
  url : "PrivateSectorApi", 
  type : "DELETE", 
  data : "customerID=" + $(this).data("customerid"), 
  dataType : "text", 
  complete : function(response, status) 
  { 
   onPrivateSectorDeletedComplete(response.responseText, status); 
  } 
 }); 
}); 
 
function onPrivateSectorDeletedComplete(response, status) 
{ 
 if(status == "success") 
 { 
  var resultSet = JSON.parse(response); 
    
  if(resultSet.status.trim() == "success") 
  { 
   $("#alertSuccess").text("Successfully Deleted."); 
   $("#alertSuccess").show(); 
      
   $("#divItemsGrid").html(resultSet.data); 
  
  }else if(resultSet.status.trim() == "error"){ 
   $("#alertError").text(resultSet.data); 
   $("#alertError").show(); 
  } 
 }else if(status == "error"){ 
  $("#alertError").text("Error While Deleting."); 
  $("#alertError").show(); 
 }else{ 
  $("#alertError").text("Unknown Error While Deleting."); 
  $("#alertError").show(); 
 } 
} 
 
//CLIENTMODEL 
function validatePrivateSectorForm() {   
 // NAME   
 if ($("#customerName").val().trim() == "")  {    
  return "Insert customerName.";   
   
 }  
 
  // NIC   
 if ($("#NIC").val().trim() == "")  {    
  return "Insert NIC.";   
   
 }  
  // Email  
 if ($("#customerEmail").val().trim() == "")  {    
  return "Insert Email.";   
 }  
  
  
 //No Of Units 
 if ($("#noOfUnits").val().trim() == "")  {    
  return "Insert noOfUnits.";  
    
 } 
   
    //Charge Per Unit
 if ($("#chargePerUnit").val().trim() == "")  {    
  return "Insert chargePerUnit."; 
  }
  
   
  
   
 
   
  return true;  
   
}