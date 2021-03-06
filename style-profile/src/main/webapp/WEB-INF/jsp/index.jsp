<!DOCTYPE HTML>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Style Profile</title>
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.9/datatables.min.css"/> -->
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

</style>
</head>
<body>
<br/><br/>
<!-- <h3 style="text-align: center;"><b>Maestro</b></h3> -->

<div class="content">
	<div class="container">
	<!-- <form method="get" action="jobFeedDownload" class="form-inline">  
	<a href="addPublisher" id="addPublisher" class="btn btn-primary btn-sm">Add Publisher/Publication &nbsp;&nbsp;&nbsp;</a>
	<a href="logout" class="btn btn-danger btn-sm" style="float: right;">Log out</a>
	</form>   -->
	
	<form method="post" class="form-inline" action="getStyleProfileData"  enctype="multipart/form-data">
		  <div class="form-group" style="margin-left:70%">
		  <sec:authorize access="hasAuthority('ROLE_ADMIN')">
		  	<a href="getPublisher" id="addPublisher" class="btn btn-primary btn-sm">Add Publisher/Publication</a>
		   </sec:authorize>
		   <button class="btn btn-primary btn-sm">Download</button>
	  	  <a href="logout" class="btn btn-danger btn-sm">Log out</a> 
		  </div>
	
	  	  <br/>
	  	<h5 style="text-align: center;font-weight: bold;">${responseMsg}</h5>
	</form> 
	
	<br/><br/><br/>
			<table id="example" cellpadding="0" cellspacing="0" border="0"
				class="table table-striped table-bordered" width="100%">
				<thead>
					<tr>
						<th>Client</th>
						<th>Publisher</th>
						<th>Publication</th>
						<!-- <th>Style ID</th> -->
						<th>Created On</th>
						<!-- <th>Download Link</th>
						<th>Created Date</th> -->
						<th>Modified On</th>
					</tr>
				</thead>
			</table>
    </div>
</div>	
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
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
		  "ajax": 'styleDetails',
		  "order": [[ 3, "desc" ]]
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