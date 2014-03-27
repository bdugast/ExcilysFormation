<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<h1>Delete Computer</h1>
	<form class="form-horizontal" action="delete" method="POST">
		<div class="control-group">
			<div class="controls">
				<input type="hidden" class="form-control" id="inputId" name="id"  value="${computer.id}"/>
				Delete this computer ? <br/>
				${computer.name}
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="Delete" class="btn btn-primary"> or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />