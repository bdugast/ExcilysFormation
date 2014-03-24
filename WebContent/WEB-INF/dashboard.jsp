<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<section id="main">
	<h1 id="homeTitle">${fn:length(computers)} Computers found</h1>
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
			href="add">Add Computer</a>

	</div>

	<table class="table table-striped">
		<thead>
			<tr>
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<th>Discontinued Date</th>
				<th>Company</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${computers}" var="computer">
				<tr>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td><a type="button" class="btn btn-warning" href="update?id=${computer.id}">Modify</a></td>
					<td><a type="button" class="btn btn-danger" href="delete?id=${computer.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
