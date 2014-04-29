<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
<link href="resources/css/jquery-ui.min.css" rel="stylesheet"
	media="screen">

<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.validate.min.js"></script>
<script src="resources/js/jquery.ui.datepicker-fr.js"></script>
<script src="resources/js/validationForm.js"></script>
<script>
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional["<spring:message code="date.code.lang" />"]);
		$(".datepicker").datepicker({
			dateFormat : "<spring:message code="date.format.picker" />",
			changeMonth : true,
			changeYear : true,
		});
	});
</script>
</head>
<body>
	<header class="navbar-inverse">
		<span id="lang"> <a href="?lang=us"><img alt="us"
				src="resources/img/us.png"></a> | <a href="?lang=fr"><img
				alt="fr" src="resources/img/fr.png"></a>
				<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
					<a href="<c:url value="j_spring_security_logout" />" > Logout</a>
				</sec:authorize>
		</span>
		<h1 class="fill">
			<a href="dashboard"> Application - Computer Database </a>
		</h1>
	</header>