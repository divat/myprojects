<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
	<html lang="en">
	<head>
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
		<title>CS-Metrics</title>
	</head>
<body>
<form:form name="" action="productionReport" method="post" enctype="" modelAttribute="productionReportForm">
<%-- <form name="reprotForm" id="reportForm"> --%>
	 <div class="col-lg-12">
	<h3 class="page-header">
		<i class="fa fa-file-text-o"></i> Production Report
	</h3>
	<ol class="breadcrumb">
		<li><!-- <i class="fa fa-home"></i> --><a href="home">Home</a></li>
		<li><i class="icon_document_alt"></i>Reports</li>
		<!-- <li><i class="fa fa-file-text-o"></i>Job Information</li> -->
		<input type="submit" id="reportForm" style="float: right;" name="btnSubmit" value="Process" />
	</ol>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> Reports </header>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group" id="divisionId">
								<label class="col-sm-2 control-label">Division<span data-toggle="tooltip" data-placement="top" title="" style="color:red;font-size:12px;cursor: default" data-original-title="Mandatory">*</span></label>
								<div class="col-sm-9  col-lg-3">
									<form:select id="projCatId" cssClass="form-control m-bot15" path="division">
										<form:option value="0">ALL</form:option>
										<form:options itemValue="id" itemLabel="divisionName" items="${divisionList}"></form:options>
									</form:select>
								</div>
							</div> 
							<div class="form-group" id="clientId">
								<label class="col-sm-2 control-label">Client<span data-toggle="tooltip" data-placement="top" title="" style="color:red;font-size:12px;cursor: default" data-original-title="Mandatory">*</span></label>
								<div class="col-sm-9  col-lg-3">
									<form:select id="projCatId" cssClass="form-control m-bot15" path="client">
										<form:option value="0">ALL</form:option>
										<form:options itemValue="id" itemLabel="clientName" items="${clientList}"></form:options>
									</form:select>
								</div>
							</div>   
							<div class="form-group" id="jobDate">
								<label class="col-sm-2 control-label">From date <span data-toggle="tooltip" data-placement="top" title="" style="color:red;font-size:12px;cursor: default" data-original-title="Mandatory">*</span></label>
								<div id="sandbox-container" class="col-sm-9  col-lg-3">
	    							<input type="text" id="fromDate" name="fromDate" class="form-control" />
								</div>
								<label class="col-sm-2 control-label">To date <span data-toggle="tooltip" data-placement="top" title="" style="color:red;font-size:12px;cursor: default" data-original-title="Mandatory">*</span></label>
								<div id="sandbox-container" class="col-sm-9  col-lg-3">
	    							<input type="text" id="toDate" name="toDate" class="form-control" />
								</div>
							</div>
						</div>
					</div>
		</div>
			</section>
	<%-- </form> --%>
	</form:form>
	</div>
			
<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.10.4.min.js"></script>
<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="acs-js/acs-form-validation.js" ></script>
<script type="text/javascript" src="js/date-picker.js" ></script>
<script src="<c:url value="js/jquery.autocomplete.min.js" />"></script>
<script>
    /* $(document).ready(function(){
		$('#project').hide();
	}) */
	
	$("#projectCat").change(function(){
		$('#project').show();
	});
    
    $('#sandbox-container input').datepicker({
        autoclose: true
    });

    $('#sandbox-container input').on('show', function(e){
        console.debug('show', e.date, $(this).data('stickyDate'));
        
        if ( e.date ) {
             $(this).data('stickyDate', e.date);
        }
        else {
             $(this).data('stickyDate', null);
        }
    });

    $('#sandbox-container input').on('hide', function(e){
        console.debug('hide', e.date, $(this).data('stickyDate'));
        var stickyDate = $(this).data('stickyDate');
        
        if ( !e.date && stickyDate ) {
            console.debug('restore stickyDate', stickyDate);
            $(this).datepicker('setDate', stickyDate);
            $(this).data('stickyDate', null);
        }
    });
    
    $("#sandbox-container input").on("change",function(){
        var selected = $(this).val();
    });
    
	//User auto-completion    	
	$('#w-input-search').autocomplete({
		serviceUrl : 'getUser',
		paramName : "username",
		delimiter : ",",
		transformResult : function(response) {
			return {
				//must convert json to javascript object before process
				suggestions : $.map($.parseJSON(response), function(data) {
					return {
						value : data.username,
						data : data.userId
					};
				})
			};
		}
	});
	
	$("#reportForm1").submit(function(e){
        e.preventDefault();
        var form = $(this);
        var action = form.attr("action");
        var data = form.serializeArray();
        console.log(JSON.stringify(getFormData(data)));
        //alert(data);
        $.ajax({
                    url: 'acsJobReport',
                    dataType: 'json',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(getFormData(data)),
                    success: function(data){
                    	alert(data);
                    	//location.reload();
                        console.log("DATA POSTED SUCCESSFULLY"+data);
                    },
                    error: function( jqXhr, textStatus, errorThrown ){
                    	//location.reload();
                        console.log( errorThrown );
                    }
        });
	});
	
	//utility function
	function getFormData1(data) {
	   var unindexed_array = data;
	   var indexed_array = {};

	   $.map(unindexed_array, function(n, i) {
	    indexed_array[n['name']] = n['value'];
	   });
		console.log(indexed_array)
	   return indexed_array;
	}
	
	function getFormData(formArray) {//serialize data function
		  var returnArray = {};
		  for (var i = 0; i < formArray.length; i++){
		    returnArray[formArray[i]['name']] = formArray[i]['value'];
		  }
		  alert(returnArray)
		  return returnArray;
		}

$("#reportForm").click(function(){
	var flag = false;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if(fromDate === null || fromDate === ""){
		alert("Please enter the from date")
		return false;
	}
	
	if(toDate === null || toDate === ""){
		alert("Please enter the to date")
		return false;
	}
});
</script>
</body>
</html>