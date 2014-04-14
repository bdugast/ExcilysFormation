<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>

<section id="main" class="col-md-6 col-md-offset-3">
	<h1><spring:message code="title.add" /></h1>

	<form:form commandName="compDto" class="form-horizontal"
		id="formValidation" action="add" method="POST">
		<div class="control-group">
			<form:label path="name"><spring:message code="form.computername" /></form:label>
			<div class="controls">
				<form:input path="name" class="form-control" id="inputName"
					value="${compDto.name}" />
				<form:errors path="name" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="introduced"><spring:message code="form.introduced" /></form:label>
			<div class="controls">
				<form:input path="introduced" class="form-control"
					id="dateIntroduced" value="${compDto.introduced}" />
				<form:errors path="introduced" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="discontinued"><spring:message code="form.discontinued" /></form:label>
			<div class="controls">
				<form:input path="discontinued" class="form-control"
					id="dateDiscontinued" value="${compDto.discontinued}" />
				<form:errors path="discontinued" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="companyId"><spring:message code="form.company" /></form:label>
			<div class="controls">
				<form:select path="companyId">
					<option value="-1"><spring:message code="form.selectcompany" /></option>
					<form:options items="${companies}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="companyId" class="errors" />
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="<spring:message code="form.add" />" class="btn btn-primary">
			<spring:message code="form.or" /> <a href="dashboard" class="btn"><spring:message code="form.cancel" /></a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />
