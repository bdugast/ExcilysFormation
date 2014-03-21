<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<section id="main">
	<h1 id="homeTitle">${fn:length(computers)}Computersfound</h1>
	<div id="actions">
		<form action="" class="form-inline" method="GET">
			<div class="form-group">
				<input type="search" class="form-control" name="search"
					id="searchbox" placeholder="Search name">
				<button type="submit" id="searchsubmit" class="btn btn-primary">Filter
					by name</button>
			</div>
		</form>

		<a type="button" id="add" class="btn btn-success"
			href="addcomputer">Add Computer</a>

	</div>

	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<th>Discontinued Date</th>
				<th>Company</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${computers}" var="computer">
				<tr>
					<td><a href="#" onclick=""><c:out value="${computer.name}" /></td>
					<td><c:out value="${computer.introduced}" /></td>
					<td><c:out value="${computer.discontinued}" /></td>
					<td><c:out value="${computer.company.name}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
