<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	
     <ul class="pagination">
     		<c:choose>
	     		<c:when test="${currentPage != 1}">
	     			<li><a href="dashboard?page=${currentPage -1}">&laquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&laquo;</a></li>
		        </c:otherwise>
	        </c:choose>	
            <c:forEach begin="1" end="${countPages}" var="i">
                <li><a href="dashboard?page=${i}"> ${i} </<li></td>
            </c:forEach>
            <c:choose>
	     		<c:when test="${currentPage lt countPages}">
	     			<li><a href="dashboard?page=${currentPage +1}">&raquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&raquo;</a></li>
		        </c:otherwise>
	        </c:choose>	
    </ul>
</section>

<jsp:include page="include/footer.jsp" />
