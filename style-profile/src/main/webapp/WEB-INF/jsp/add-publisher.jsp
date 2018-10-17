<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Style Profile</title>
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<br/>
<br/><br/>
<div class="form">
<form:form class="form-horizontal" id="add-publisher" action="addPublisher" method="post" modelAttribute="publisher">   
<h5 style="text-align: center;font-weight: 500;color: #c70d0d;">${responseMsg}</h5>
	<div class="col-lg-12">
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="home">Home</a></li>
			<!-- <li><i class="fa fa-file-text-o"></i>Add </li> -->
			<input type="submit" class="btn btn-success btn-sm" id="login-form" style="float: right;" name="btnSubmit" value="Add" />
		</ol>
	</div>
	<div class="row">
		<div class="col-lg-12">   
		               
			<div class="row">
				<div class="col-sm-6">
					<section class="panel">
						<header class="panel-heading">Add Publisher/Publication</header>
						<div class="panel-body">
							
								<div class="form-group">
									<label class="control-label col-sm-4">Client Name<span class="required">*</span></label>
										<div class="col-sm-6">
											<input name="clientName" id="clientName" type="text" value="" size="" class="form-control">
										</div>
                                        
                                </div>
                                          
								<div class="form-group">
									<label class="control-label col-sm-4">Publisher Name<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="publisherName" name="publisherName" type="text" value="" size="" class="form-control">
                                        </div>
								</div>
                                          
								<div class="form-group">
									<label class="control-label col-sm-4">Publication Name<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="publicationName" name="publicationName" type="text" value="" size="" class="form-control">
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
<script src="js/form-chapter-validation.js"></script>
<script src="js/scripts.js"></script>

<script type="text/javascript">
$('#clientName').autocomplete({
	serviceUrl : 'getClientList',
	paramName : "clientName",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				//console.log(data.jobId);
				return {
					value : data.clientName,
					data : data.id
				};
			})
		};
	}
});

$('#publisherName').autocomplete({
	serviceUrl : 'getPublisherList',
	paramName : "publisherName",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				//console.log(data.jobId);
				return {
					value : data.publisherName,
					data : data.id
				};
			})
		};
	}
});

/* $('#publicationName').autocomplete({
	serviceUrl : 'getPublicationList',
	paramName : "publicationName",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				//console.log(data.jobId);
				return {
					value : data.publicationName,
					data : data.id
				};
			})
		};
	}
}); */
</script>
    	
</body>
</html>