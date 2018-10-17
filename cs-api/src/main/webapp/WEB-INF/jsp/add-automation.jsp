<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CS-Metrics</title>

<!-- Bootstrap CSS -->    
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="css/elegant-icons-style.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />
    <!-- date picker -->
    
    <!-- color picker -->
    
    <!-- Custom styles -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />
    
    <link rel="stylesheet" href="css/formelements.css">
		<link rel="stylesheet" href="css/calendar.css">
		<link rel="stylesheet" href="css/date-picker.css">
		<link href="<c:url value="css/main.css" />" rel="stylesheet">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<br/>
<br/><br/>
<div class="form">
<form:form class="form-horizontal" id="automationInputs" action="addAutomation" method="post" modelAttribute="automationInputs">   
<h5 style="text-align: center;font-weight: 500;color: #c70d0d;">${responseMsg}</h5>
	<div class="col-lg-12">
		<ol class="breadcrumb">
			<li><!-- <i class="fa fa-home"></i> --><a href="home">Home</a></li>
			<li><!-- <i class="fa fa-file-text-o"></i> -->Add Automation</li>
			<input type="submit" class="btn btn-success btn-sm" id="login-form" style="float: right;" name="btnSubmit" value="Add" onclick="return validate();"/>
		</ol>
	</div>
	<div class="row">
		<div class="col-lg-12">   
		               
			<div class="row">
				<div class="col-lg-6">
					<section class="panel">
						<header class="panel-heading">Automation Details</header>
						<div class="panel-body">
							
								<div class="form-group">
									<label class="control-label col-sm-4">Automation/Tool Name<span class="required">*</span></label>
										<div class="col-sm-6">
											<input name="automationName" id="automationName" type="text" value="" size="16" class="form-control">
											<%-- <form:input path="automationName" id="automationName" cssClass="form-control"/> --%>
										</div>
                                        
                                </div>
                                          
								<!-- <div class="form-group">
									<label class="control-label col-sm-4">Division<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="division" name="division" type="text" value="" size="16" class="form-control">
                                        </div>
								</div> -->
								
							
								<div class="form-group">
                                      <label class="control-label col-lg-4" for="inputSuccess">Division<span class="required">*</span></label>
                                      <div class="col-lg-6">
                                          <form:select id="projCatId" cssClass="form-control m-bot15" path="division">
												<%-- <form:option value="0">Choose division</form:option> --%>
												<form:options itemValue="id" itemLabel="divisionName" items="${divisionList}"></form:options>
										  </form:select>
                                      </div>
                                 </div>
								
                                          
                                 <div class="form-group">
                                      <label class="control-label col-lg-4" for="inputSuccess">Client<span class="required">*</span></label>
                                      <div class="col-lg-6">
                                          <form:select id="projCatId" cssClass="form-control m-bot15" path="client">
												<%-- <form:option value="0">Choose client</form:option> --%>
												<form:options itemValue="id" itemLabel="clientName" items="${clientList}"></form:options>
										  </form:select>
                                      </div>
                                 </div>
                                          
								<!-- <div class="form-group">
									<label class="control-label col-sm-4">Client<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="client" name="client" type="text" value="" size="16" class="form-control">
										</div>
                                </div> -->
                                
                                <div class="form-group">
									<label class="control-label col-sm-4">Manual Metrics (mins)<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="manualMetrics" name="manualMetrics" type="text" value="" size="16" class="form-control" placeholder="Enter metrics in mins">
											<%-- <form:input path="manualMetrics" id="manualMetrics" cssClass="form-control"/> --%>
										</div>
                                </div>
                                <div class="form-group">
									<label class="control-label col-sm-4">Manual Metrics Pages/Images<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="manualPages" name="manualPages" type="text" value="" size="16" class="form-control">
											<%-- <form:input path="manualPages" id="manualPages" cssClass="form-control"/> --%>
										</div>
                                </div>
                                
                                <div class="form-group">
									<label class="control-label col-sm-4">Automation Metrics (mins)<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="automationMetrics" name="automationMetrics" type="text" value="" size="16" class="form-control" placeholder="Enter metrics in mins">
											<%-- <form:input path="automationMetrics" id="automationMetrics" cssClass="form-control"/> --%>
										</div>
                                </div>
                            
                            	<div class="form-group">
									<label class="control-label col-sm-4">Automation Metrics Pages/Images<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="automationPages" name="automationPages" type="text" value="" size="16" class="form-control">
										</div>
                                </div>
                                <div class="form-group">
									<label class="control-label col-sm-4">Description<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="toolDescription" name="toolDescription" type="text" value="" size="50" class="form-control">
											<%-- <form:input path="toolDescription" id="toolDescription" cssClass="form-control"/> --%>
										</div>
                                </div>
                        </div>
					</section>
				</div>
				
		</div>
	</div>
</div>


</form:form>   
</div>

<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.10.4.min.js"></script>
<script src="js/jquery-1.8.3.min.js"></script>
<script src="<c:url value="js/jquery.autocomplete.min.js" />"></script>
 <script src="js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="js/jquery.scrollTo.min.js"></script>
    <script src="js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- jquery validate js -->
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <!-- custom form validation script for this page-->
    <script src="js/form-automation-validation.js"></script>
     <script src="js/scripts.js"></script>    
    

<script type="text/javascript">
//Job auto-completion    	
/* $('#automationName').autocomplete({
	serviceUrl : 'getAutomationTools',
	paramName : "automationName",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				//console.log(data.jobId);
				return {
					value : data.automationName,
					data : data.id
				};
			})
		};
	}
}); */

/* $('#manualMetrics').keypress(function(e){ 
	   if (this.value.length == 0 && e.which == 48 ){
		   alert("Zero not allowed");
	      return false;
	   }
	}); */

function validate(){
	var manualMetrics = $("#manualMetrics").val();
	if(manualMetrics == 0){
		alert("Manual metrics zero not allowed.");
		return false;
	}

	var automationMetrics = $("#automationMetrics").val();
	if(automationMetrics == 0){
		alert("Automation metrics zero not allowed.")
		return false;
	}
	return true;
}
	
$('#manualPages').keypress(function(e){ 
	   if (this.value.length == 0 && e.which == 48 ){
		  alert("Zero not allowed");
	      return false;
	   }
	});
	
/* $('#automationMetrics').keypress(function(e){ 
	   if (this.value.length == 0 && e.which == 48 ){
		  alert("Zero not allowed");
	      return false;
	   }
	}); */
	
/* $('#automationPages').keypress(function(e){ 
	   if (this.value.length == 0 && e.which == 48 ){
		  alert("Zero not allowed");
	      return false;
	   }
	});
	 */
$('#automationMetrics').on('keyup',  function() {
    newval = $(this).val().replace(/[^0-9.]/g, "");
    $(this).val(newval);
    //alert("")
    return false;
});

$('#manualPages').on('keyup',  function() {
    newval = $(this).val().replace(/[^0-9.]/g, "");
    $(this).val(newval);
    //alert("")
    return false;
});

$('#manualMetrics').on('keyup',  function() {
    newval = $(this).val().replace(/[^0-9.]/g, "");
    $(this).val(newval);
    //alert("")
    return false;
});

$('#manualPages').on('change', function(){ 
	$('#automationPages').attr('readonly', true);
	var pages = $("#manualPages").val();
	   //alert(pages)
	   $('#automationPages').val(pages);
	}).change();

/* $('#automationPages').on('change', function(){ 
	var pages = $("#manualPages").val();
	   alert(pages)
	   $('#automationPages').val(pages);
	}).change();
 */

</script>
						
</body>
</html>