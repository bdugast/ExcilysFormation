<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

	<c:if test="${valide !=null}">
		<div class="alert alert-success"><c:out value="${valide}" /></div>
	</c:if>
	<c:if test="${fail !=null}">
		<div class="alert alert-danger"><c:out value="${fail}" /></div>
	</c:if>

<section id="main">
	<h1 id="homeTitle">${wrap.count} Computers found</h1>
	<div id="actions">
		<form action="" class="form-inline" method="GET">
			<div class="form-group col-md-11">
				<input type="search" class="form-control" name="search"
					id="searchbox" placeholder="Search name">
				<button type="submit" id="searchsubmit" class="btn btn-primary">Filter
					by name</button>
			</div>
		</form>
	<div class="form-group col-md-1">
			<a type="button" id="add" class="btn btn-success"
				href="add">Add Computer</a>
	</div>
	<div class="form-group col-md-12">
     	<tags:pagination wrap="${wrap}"></tags:pagination>
     	</div>
	</div>

	<table class="table table-striped">
		<thead>
			<tr>
				<th class="col-md-2 col-md-offset-1"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=COMPUTER&order=${wrap.orderField=='COMPUTER' ? !wrap.order : 'true' }">Computer Name</a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=INTRODUCED&order=${wrap.orderField=='INTRODUCED' ? !wrap.order : 'true' }">Introduced	Date</a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=DISCONTINUED&order=${wrap.orderField=='DISCONTINUED' ? !wrap.order : 'true' }">Discontinued Date</a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=COMPANY&order=${wrap.orderField=='COMPANY' ? !wrap.order : 'true' }">Company</a></th>
				<th class="col-md-1"></th>
				<th class="col-md-1"></th>
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
					<td><a type="button" class="btn btn-danger" href="delete?id=${computer.id}" onclick="return confirm('Are you sure to delete this computer?')">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>    
</section>

<jsp:include page="include/footer.jsp" />
