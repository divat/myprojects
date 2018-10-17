<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css" media="all" />
<title>Maestro</title>
</head>
<body>
	<table id="example" class="display" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>Input File</th>
			<th>Output File</th>
			<th>Category</th>
			<th>Project</th>
			<th>Status</th>
			<th>Download Link</th>
			<th>Created Date</th>
			<th>Created By</th>
		</tr>
	</thead>
</table>

	<!-- Load jQuery, SimpleModal and Basic JS files -->
<!-- <script type='text/javascript' src='js/jquery.js'></script> -->
<script type='text/javascript' src='js/jquery.simplemodal.js'></script>
<script type='text/javascript' src='js/basic.js'></script>
<script src="js/jquery.js"></script>
<script src="js/jquery-ui-1.10.4.min.js"></script>
<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
		$(document).ready(
				function() {
					var table = $('#example').DataTable({
						"ajax": 'getJobs',
						lengthChange : false,
						buttons : [ 'copy', 'excel', 'pdf', 'colvis' ]
					});

					table.buttons().container().appendTo(
							'#example_wrapper .col-sm-6:eq(0)');
				});
</script>
</body>
</html>