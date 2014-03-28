<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main" class="col-md-6 col-md-offset-3">
	<h1>Add Computer</h1>
	<form class="form-horizontal" id="formValidation" action="add"
		method="POST">
		<div class="control-group">
			<label class="control-label" for="name">Computer name:</label>
			<div class="controls">
				<input type="text" class="form-control" id="inputName" name="name" value="${name}"/>
				<c:if test="${errors.get('nameError')!=null}">
					<label><c:out value="${errors.get('nameError')}"/></label>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="introduced">Introduced date :</label>
			<div class="controls">
				<input type="text" class="form-control" id="dateIntroduced"	name="introduced" value="${introduced}">
				<c:if test="${errors.get('introducedError')!=null}">
					<label><c:out value="${errors.get('introducedError')}"/></label>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="discontinued">Discontinued date :</label>
			<div class="controls">
				<input type="text" class="form-control" id="dateDiscontinued" name="discontinued" value="${discontinued}">
				<c:if test="${errors.get('discontinuedError')!=null}">
					<label><c:out value="${errors.get('discontinuedError')}"/></label>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label for="company">Company Name:</label>
			<div class="controls">
				<select name="company" class="form-control">
					<option value="">Select a company</option>
					<c:forEach items="${companies}" var="company">
						<option value="${company.id}" ${companyId==company.id ? 'selected' : ''}>${company.name}</option>
					</c:forEach>
				</select>
				<c:if test="${errors.get('companyError')!=null}">
					<label><c:out value="${errors.get('companyError')}"/></label>
				</c:if>
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="Add" class="btn btn-primary"> or
			<a href="dashboard" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />