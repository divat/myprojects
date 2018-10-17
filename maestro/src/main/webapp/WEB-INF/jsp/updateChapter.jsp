<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Maestro</title>

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
<form:form class="form-horizontal" id="update-chapter" action="updateChapter" method="post" modelAttribute="stage">   
<h5 style="text-align: center;font-weight: 500;color: #c70d0d;">${responseMsg}</h5>
	<div class="col-lg-12">
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="home">Home</a></li>
			<li><i class="fa fa-file-text-o"></i>Reset chapter status</li>
			<input type="submit" class="btn btn-success btn-sm" id="login-form" style="float: right;" name="btnSubmit" value="Update" />
		</ol>
	</div>
	<div class="row">
		<div class="col-lg-12">   
		               
			<div class="row">
				<div class="col-lg-6">
					<section class="panel">
						<header class="panel-heading">Job Information</header>
						<div class="panel-body">
							
								<div class="form-group">
									<label class="control-label col-sm-4">Customer Id<span class="required">*</span></label>
										<div class="col-sm-6">
											<input name="customerId" id="customerId" type="text" value="" size="16" class="form-control">
										</div>
                                        
                                </div>
                                          
								<div class="form-group">
									<label class="control-label col-sm-4">Job Id<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="jobId" name="jobId" type="text" value="" size="16" class="form-control">
                                        </div>
								</div>
                                          
								<div class="form-group">
									<label class="control-label col-sm-4">Chapter Name<span class="required">*</span></label>
										<div class="col-sm-6">
											<input id="chapterName" name="chapterName" type="text" value="" size="16" class="form-control">
										</div>
                                </div>
                            
                        </div>
					</section>
				</div>
				<div class="col-lg-5" id='maestroJobInputs' style="display: block;" >
					<section class="panel">
					 <header class="panel-heading">Stage Details</header>
						<div class="panel-body">
							<div class="form-horizontal">
								 <label class="label_check" for="checkbox-01">
								 	<form:checkbox path="cleanUpStage" value=""/> Clean up
								 </label><br/>
                                              
								 <label class="label_check" for="checkbox-01">
									<form:checkbox path="docVal" value=""/> Doc validation
								 </label><br/>
                                              
								 <label class="label_check" for="checkbox-01">
									<form:checkbox path="structVal" value=""/> Structuring
                                 </label><br/>
                                              
                                 <label class="label_check" for="checkbox-01">
									<form:checkbox path="postVal" value=""/> Post validation
                                 </label><br/>
                                              
								 <label class="label_check" for="checkbox-01">
									<form:checkbox path="postConv" value=""/> Post conversion
                                 </label><br/>
                                              
								<label class="label_check" for="checkbox-01">
									<form:checkbox path="maestroCert" value=""/> Maestro certification
                                </label><br/>
							</div>
						</div>
					</section>
			   </div>
		</div>
	</div>
</div>

<c:if test="${not empty chapter}">

<div class="row">
			</div>
              <!-- page start-->
              <div class="row">
                  <div class="col-sm-12">
                      <section class="panel">
                          <header class="panel-heading">
                              Updated Chapter
                          </header>
                          <table class="table">
                              <thead>
                              <tr>
                                  <!-- <th>#</th> -->
                                  <th>Job</th>
                                  <th>Client</th>
                                  <th>Chapter Name</th>
                                  <th>Document cleanup</th>
                                  <th>Document validation</th>
                                  <th>Document structuring</th>
                                  <th>Post validation</th>
                                  <th>Post conversion</th>
                                  <th>Maestro certification</th>	
                              </tr>
                              </thead>
                              <tbody>
                              <tr>
                                 <!--  <td>1</td> -->
                                  <td>${chapter.metadataJobId.jobId}</td>
                                  <td>${chapter.metadataJobId.clientId}</td>
                                  <td>${chapter.chapterName}</td>
                                  <td>${chapter.stageCleanUp}</td>
                                  <td>${chapter.docValidation}</td>
                                  <td>${chapter.structuringVal}</td>
                                  <td>${chapter.postVal}</td>
                                  <td>${chapter.postConv}</td>
                                  <td>${chapter.wordExportMapStatus}</td>
                              </tr>
                              
                              
                              </tbody>
                          </table>
                      </section>
                  </div>


</c:if>
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
//Job auto-completion    	
$('#jobId').autocomplete({
	serviceUrl : 'getJobId',
	paramName : "jobId",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				//console.log(data.jobId);
				return {
					value : data.jobId,
					data : data.id
				};
			})
		};
	}
});

$('#customerId').autocomplete({
	serviceUrl : 'getClientId',
	paramName : "customerId",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				console.log(data.clientId);
				return {
					value : data.clientName,
					data: data.clientId
				};
			})
		};
	}
});

$('#chapterName').autocomplete({
	serviceUrl : 'getChapter',
	paramName : "chapterName",
	delimiter : ",",
	transformResult : function(response) {
		return {
			//must convert json to javascript object before process
			suggestions : $.map($.parseJSON(response), function(data) {
				console.log(data.clientId);
				return {
					value : data.chapterName,
					data: data.id
				};
			})
		};
	}
});
</script>
						
</body>
</html>