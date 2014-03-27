<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="css/jquery-ui.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/validationForm.js"></script>
<script>
	$(function() {
		$("#dateIntroduced").datepicker({ dateFormat: "yy-mm-dd" });
	});
	$(function() {
		$("#dateDiscontinued").datepicker({ dateFormat: "yy-mm-dd" });
	});
</script>
</head>
<body>
	<header class="navbar-inverse">
		<h1 class="fill">
			<a href="dashboard"> Application - Computer Database </a>
		</h1>
	</header>