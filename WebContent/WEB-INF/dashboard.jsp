<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<section id="main">
	<h1 id="homeTitle">${countComputers} Computers found</h1>
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

     	<tags:pagination currentPage="${currentPage}" countPages="${countPages}" search="${search}" orderField="${orderField}"></tags:pagination>
	</div>

	<table class="table table-striped">
		<thead>
			<tr>
				<th><a href="dashboard?page=${currentPage}&search=${search}&orderField=COMPUTER">Computer Name</a></th>
				<th><a href="dashboard?page=${currentPage}&search=${search}&orderField=INTRODUCED">Introduced Date</a></th>
				<th><a href="dashboard?page=${currentPage}&search=${search}&orderField=DISCONTINUED">Discontinued Date</a></th>
				<th><a href="dashboard?page=${currentPage}&search=${search}&orderField=COMPANY">Company</a></th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${computers}" var="computer">
				<tr>
					<td>${computer.name}</td>
					<td><joda:format value="${computer.introduced}" pattern="yyyy-MM-dd"/></td>
					<td><joda:format value="${computer.discontinued}" pattern="yyyy-MM-dd"/></td>
					<td>${computer.company.name}</td>
					<td><a type="button" class="btn btn-warning" href="update?id=${computer.id}">Modify</a></td>
					<td><a type="button" class="btn btn-danger" href="delete?id=${computer.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
     
     
</section>

<jsp:include page="include/footer.jsp" />
