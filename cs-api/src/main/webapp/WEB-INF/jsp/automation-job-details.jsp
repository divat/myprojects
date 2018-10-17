<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.9/datatables.min.css"/> -->
<link href="css/jquery-dataTables.css" rel="stylesheet" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all">
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CS-Metrics</title>

<style type="text/css">
body{
    font-size: 13px !important;
}

td {
    white-space: nowrap;
}
 
td.wrapok {
    white-space:normal
}

#wsMap{
	width: 92px;
}

#cm{
	width: 90px;
}

.ok{
	width:25%;

  height: auto;
	/* height:auto; */
}

.cancel{
	width:25%;
	 
  height: auto;
	/* height:auto; */
}
</style>

</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			Automation Job Details </header>
			<div class="table-responsive">
				<table class="table" id="example">
					<thead>
						<tr>
							<th>Job Id</th>
							<th>Job Type</th>
							<th>Created On</th>
							<th>Page/Image Count</th>
						</tr>
					</thead>

				</table>
			</div>

			</section>
		</div>
	</div>
	<input type="hidden" name="automationId" id="automationId" value="${automationId}"/>

<!-- <script type="text/javascript" src="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.9/datatables.min.js"></script> -->
<script type="text/javascript" src="js/jquery-dataTables.js"></script>
 <script>
 $(document).ready(function() {
    $('#example').dataTable( {
        "ajax": 'getAutomationJobs?automationId='+$('#automationId').val(),
        /* "order": [[ 4, "desc" ]], */
        "columnDefs":[{targets:[0], class:"wrapok"}]
    } );
} );
 </script>
</body>
</html>