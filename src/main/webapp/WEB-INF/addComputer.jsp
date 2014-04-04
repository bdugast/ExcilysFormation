
<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section id="main" class="col-md-6 col-md-offset-3">
	<h1>Add Computer</h1>

	<form:form commandName="compDto" class="form-horizontal"
		id="formValidation" action="add" method="POST">
		<div class="control-group">
			<form:label path="name">Computer name:</form:label>
			<div class="controls">
				<form:input path="name" class="form-control" id="inputName"
					value="${compDto.name}" />
				<form:errors path="name" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="introduced">Introduced date:</form:label>
			<div class="controls">
				<form:input path="introduced" class="form-control"
					id="dateIntroduced" value="${compDto.introduced}" />
				<form:errors path="introduced" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="discontinued">Discontinued date:</form:label>
			<div class="controls">
				<form:input path="discontinued" class="form-control"
					id="dateDiscontinued" value="${compDto.discontinued}" />
				<form:errors path="discontinued" class="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="companyId">Company :</form:label>
			<div class="controls">
				<form:select path="companyId">
					<option value="-1">Select a company</option>
					<form:options items="${companies}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="companyId" class="errors" />
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="Update" class="btn btn-primary">
			or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />
