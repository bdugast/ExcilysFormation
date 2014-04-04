<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
<link href="resources/css/jquery-ui.min.css" rel="stylesheet" media="screen">
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.validate.min.js"></script>
<script src="resources/js/validationForm.js"></script>
<script>
	$(function() {
		$("#dateIntroduced").datepicker({ dateFormat: "yy-mm-dd",  changeMonth: true, changeYear : true });
	});
	$(function() {
		$("#dateDiscontinued").datepicker({ dateFormat: "yy-mm-dd",  changeMonth: true, changeYear : true });
	});
</script>
</head>
<body>
	<header class="navbar-inverse">
		<h1 class="fill">
			<a href="dashboard"> Application - Computer Database </a>
		</h1>
	</header>