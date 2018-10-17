<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.9/datatables.min.css"/> -->

<!-- Bootstrap CSS -->    
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
   <!--  <link href="css/bootstrap-theme.css" rel="stylesheet"> -->
    <!--external css-->
    <!-- font icon -->
    <link href="css/elegant-icons-style.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />

<link href="css/jquery-dataTables.css" rel="stylesheet" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all">
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script>
function popup(id){
	//alert(id);
    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var w = 2000;
    var h = 500;
    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open('chapterDetails?jobId=' + id, 'test', 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
    }
}
</script>

<style type="text/css">
body {
  font: 130%/1.45em "Helvetica Neue", HelveticaNeue, Verdana, Arial, Helvetica, sans-serif;
  margin: 0;
  padding: 0;
  color: #333;
  /* background-color: #fff; */
}

 @font-face {
  font-family: 'MyWebFont';
  src: url('webfont.eot'); /* IE9 Compat Modes */
  src: url('webfont.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
       url('webfont.woff2') format('woff2'), /* Super Modern Browsers */
       url('webfont.woff') format('font-woff'), /* Pretty Modern Browsers */
       url('webfont.ttf')  format('truetype'), /* Safari, Android, iOS */
       url('webfont.svg#svgFontName') format('svg'); /* Legacy iOS */
}

#sNo{
	width: 150px;
}

#jobId{
	width: 177px;
}

#clientId{
	width: 47px;
}

#templateType{
	width: 200px;
}

#noOfMs{
	width: 250px;
}

#status{
	width: 150px;
}

#createdDate{
	width: 180px;
}

#download{
	width: 150px;
}
</style>
</head>
<body>
<br/><br/>
<!-- <h3 style="text-align: center;"><b>Maestro</b></h3> -->

<div class="content">
	<div class="container">
	<form method="post" class="form-inline" action="upload"  enctype="multipart/form-data">
		  <div class="form-group" style="margin-left:30%">
		    <label for="email"><b>Job upload:</b></label>
		    <input type="file" style="display:inline;width:200px" name="file" />
		  </div>
	  	  <button type="submit" class="btn btn-success btn-sm">Submit</button>
	  	  <a href="logout" class="btn btn-danger btn-sm">Log out</a> 
	  	  <br/>
	  	<h5 style="text-align: center;font-weight: bold;">${responseMsg}</h5>
	</form> 
	
	<form method="get" action="jobFeedDownload" class="form-inline">  
	  	  <div class="form-group" style="margin-left:30%">
		    <label for="email"><b>Sample Job feed download:</b></label>
		  </div>
	  	  <button type="submit" class="btn btn-primary btn-sm">Download</button>
	  	  <sec:authorize access="hasAuthority('ROLE_PRE-EDITING')">
	  	  	 <a href="getUpdateChapter" class="btn btn-primary btn-sm">Update Chapter Status</a>
	  	  </sec:authorize>
	  	  <sec:authorize access="hasAuthority('ROLE_ADMIN')">
	  	  	 <a href="getManuscripts" class="btn btn-primary btn-sm">Add Manuscripts</a>
	  	  </sec:authorize>
	  	  <sec:authorize access="hasAuthority('ROLE_DEVELOPER')">
	  	  	 <a href="swagger-ui.html" class="btn btn-primary btn-sm">Maestro API's</a>
	  	  </sec:authorize>
	  	  <br/>
	 </form> 	  
	 
	  	  
	<!-- <form method="post" class="form-inline" action="logout">
	  	  	<button type="submit" style="float: right;" class="btn btn-danger btn-sm">Log out</button>
	</form> -->
	<br/><br/><br/>
			<table id="example" cellpadding="0" cellspacing="0" border="0"
				class="table table-striped table-bordered" width="100%">
				<thead>
					<tr>
						<th id="sNo">Serial No</th>
						<th id="jobId">Job Name</th>
						<th id="clientId">Client</th>
						<th id="templateType">Template Type</th>
						<th id="noOfMs">No. of Manuscripts</th>
						<th id="status">Job Status</th>
						<!-- <th>Download Link</th>
						<th>Created Date</th> -->
						<th id="createdDate">Created Date</th>
						<th id="download">Download</th>
					</tr>
				</thead>
			</table>
    </div>
</div>	
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<div class="footer-section">
	<div class="container">
		<div class="footer-bottom">
			 <p style="text-align: center;"> Copyright &copy;2018  All rights  reserved | Powered by &nbsp;<a href="http://codemantra.com" target="target_blank">codemantra</a></p>
		</div>
	</div>
</div>
<!-- <script type="text/javascript" src="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.9/datatables.min.js"></script> -->
<script type="text/javascript" src="js/jquery-dataTables.js"></script>
<script type="text/javascript">
$(document).ready( function () {
	  var table = $('#example').DataTable({
		  "ajax": 'getJobs',
		  "order": [[6, "desc" ]]
	  });
	} );
	
var downloadFile = function(){
	alert("Download file.....")
	$.ajax({
		  dataType: 'native',
		  url: "download",
		  xhrFields: {
		    responseType: 'blob'
		  },
		  success: function(blob){
		    console.log(blob.size);
		      var link=document.createElement('a');
		      link.href=window.URL.createObjectURL(blob);
		      link.download="Dossier_" + new Date() + ".pdf";
		      link.click();
		  }
		});
}
</script>
 </body>
</html> 