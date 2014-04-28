<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>

	<c:if test="${valide !=null}">
		<div class="alert alert-success"><c:out value="${valide}" /></div>
	</c:if>
	<c:if test="${fail !=null}">
		<div class="alert alert-danger"><c:out value="${fail}" /></div>
	</c:if>

<section id="main">
	<h1 id="homeTitle">${wrap.count} <spring:message code="title.computers" /></h1>
	<div id="actions">
		<form action="" class="form-inline" method="GET">
			<div class="form-group col-md-11">
				<input type="search" class="form-control" name="search"
					id="searchbox" placeholder="<spring:message code="form.search" />">
				<button type="submit" id="searchsubmit" class="btn btn-primary"><spring:message code="form.searchbutton" /></button>
			</div>
		</form>
	<div class="form-group col-md-1">
			<a type="button" id="add" class="btn btn-success"
				href="add"><spring:message code="form.addcomputer" /></a>
	</div>
	<div class="form-group col-md-12">
     	<tags:pagination wrap="${wrap}"></tags:pagination>
     	</div>
	</div>

	<table class="table table-striped">
		<thead>
			<tr>
				<th class="col-md-2 col-md-offset-1"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=COMPUTER&order=${wrap.orderField=='COMPUTER' ? !wrap.order : 'true' }"><spring:message code="form.computername" /></a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=INTRODUCED&order=${wrap.orderField=='INTRODUCED' ? !wrap.order : 'true' }"><spring:message code="form.introduced" /></a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=DISCONTINUED&order=${wrap.orderField=='DISCONTINUED' ? !wrap.order : 'true' }"><spring:message code="form.discontinued" /></a></th>
				<th class="col-md-2"><a	href="dashboard?page=${wrap.currentPage}&search=${wrap.search}&orderField=COMPANY&order=${wrap.orderField=='COMPANY' ? !wrap.order : 'true' }"><spring:message code="form.company" /></a></th>
				<th class="col-md-1"></th>
				<th class="col-md-1"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${wrap.computers}" var="computer">
				<tr>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.companyName}</td>
					<td><a type="button" class="btn btn-warning" href="update?id=${computer.id}"><spring:message code="form.updatecomputer" /></a></td>
					<td><a type="button" class="btn btn-danger" href="delete?id=${computer.id}" onclick="return confirm('<spring:message code="form.deletemsg" />')"><spring:message code="form.delete" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>    
</section>

<jsp:include page="include/footer.jsp" />
