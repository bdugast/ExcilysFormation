<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<section id="main">
	<h1>Update Computer</h1>
	<form class="form-horizontal" id="formValidation" action="update"
		method="POST">
		<div class="control-group">
			<label class="control-label" for="name">Computer name:</label>
			<div class="controls">
				<input type="text" class="form-control"	id="inputName" name="name" value="${computer.name}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="introduced">Introduced date
				:</label>
			<div class="controls">
				<input type="text" class="form-control" id="dateIntroduced"	name="introduced" value="<joda:format value="${computer.introduced}" pattern="yyyy-MM-dd"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="discontinued">Discontinued
				date :</label>
			<div class="controls">
				<input type="text" class="form-control" id="dateDiscontinued" name="discontinued" value="<joda:format value="${computer.discontinued}" pattern="yyyy-MM-dd"/>">
			</div>
		</div>
		<div class="control-group">
			<label for="company">Company Name:</label>
			<div class="controls">
				<select name="company" class="form-control">
					<option value="">Select a company</option>
					<c:forEach items="${companies}" var="company">
						<option value="${company.id}"
							${computer.company.id==company.id ? 'selected' : ''}>${company.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="Update" class="btn btn-primary">
			or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />