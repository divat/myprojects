<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Creative - Bootstrap 3 Responsive Admin Template">
<meta name="author" content="GeeksLabs">
<meta name="keyword"
	content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
<link rel="shortcut icon" href="img/favicon.png">

<title>Zinio - Login</title>

<!-- Bootstrap CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap theme -->
<link href="css/bootstrap-theme.css" rel="stylesheet">
<!--external css-->
<!-- font icon -->
<link href="css/elegant-icons-style.css" rel="stylesheet" />
<link href="css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles -->
<link href="css/style.css" rel="stylesheet">
<link href="css/style-responsive.css" rel="stylesheet" />

<!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->

<STYLE type="text/css">
body {
	background: url("img/zinio-login.png") no-repeat fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
}

.errorMessage {
	list-style: none;
}

.errors {
	padding-left: 50px;
	text-align: center;
	font-size: 13px;
	padding-bottom: 12px;
}

.error {
	text-align: center;
	padding-left: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
	
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
	text-align: center;
}
</STYLE>
</head>

<body class="">
<!-- <h2 style="text-align: center;color: #4cd964;font-size: 25px;font-weight: normal;">Maestro-Queuing System</h2> -->
	<div class="container">
		<%-- <c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if> --%>
		
		<form class="login-form" action="login" method="post">
		<div>
			<c:if test="${not empty error}">
				<div class="msg">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if> 
		</div>
			<div class="login-wrap">
				<div class="errors">
					<p class="login-img">
						<i class="icon_lock_alt"></i>
					</p>
					<div class="input-group">
						<span class="input-group-addon"><i class="icon_profile"></i></span>
						<input type="text" class="form-control" placeholder="Username"
							name="username" autofocus>
					</div>
					<div class="input-group">
						<span class="input-group-addon"><i class="icon_key_alt"></i></span>
						<input type="password" class="form-control" placeholder="Password"
							name="password">
					</div>
					<button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
